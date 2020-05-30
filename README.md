# Sleuth + ELK + Zipkin monitoring

This repository shows you basic concept of Sleuth + ELK + Zipkin monitoring. This is a very simple project to proof the concepts.

You can use the Kibana to see the logs. And you can use the Zipkin to see the visualized request flows across the services.

## Usage

First, build two services. The project has two services: product, shop. The shop service is dependent on the product service.

```console
cd product
gradle build clean && gradle build
cd ..
cd shop
gradle build clean && gradle build
```

Next, start docker-compose in the root directory.

```console
docker-compose up
```

If you want to start with daemon mode, do this:

```console
docker-compose up -d
```

It may takes some time to run all services.

## Zipkin

You can access with URL http://localhost:9411.

## Kibana

You can access with URL http://localhost:5601.

## How to test

First, make a request to http://localhost:8080/products. This is the shop service. It returns some json data.

It will create some logs and send it to the logstash. And then logstash send to the elasticsearch. Finally, elasticsearch save and create indexes.

At this time, you can see the request flows in the Zipkin.

In case of Kibana, you need to create a index pattern first. Just type 'logstash-*' in the input box. And select @timestamp in the next page. Then you can list the logs in the Discover page.

## Key part to integrate with sleuth, logstash and zipkin

Put your service name in the bootstrap.yml.

```yml
spring.application.name: product-service
```

Set zipkin url in the application.properties. Note that the zipkin in the url is docker link name as this project composed with docker-compose, So, you can not use localhost.

```yml
spring.zipkin.baseUrl=http://zipkin:9411/
```

To send your logs to the logstash, you should set up the logback.xml as belows. In the destination element, It also uses docker link name(logstash).

```xml
<appender name="logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>logstash:5000</destination>
        <!-- encoder is required -->
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp>
                    <timeZone>UTC</timeZone>
                </timestamp>
                <pattern>
                    <pattern>
                        {
                        "severity": "%level",
                        "service": "${springAppName:-}",
                        "trace": "%X{X-B3-TraceId:-}",
                        "span": "%X{X-B3-SpanId:-}",
                        "parent": "%X{X-B3-ParentSpanId:-}",
                        "exportable": "%X{X-Span-Export:-}",
                        "pid": "${PID:-}",
                        "thread": "%thread",
                        "class": "%logger{40}",
                        "rest": "%message"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>
    </appender>
```

An important note here. If you use RestTemplate, RestTemplate instance must be a spring-managed bean. That is, if you use RestTemplate as belows, it dose not send any trace-id to target http requests automatically. So, you should use auto-wired bean or some spring-managed bean.

```java
// It does not work automatically!
RestTemplate restTemplate = new RestTemplate();
```

## Tips

If you want to change service's source code to see what's going on. Then you need to remove existing docker images. You can remove existing docker images with one command as belows.

```console
docker-compose down --rmi all
``` 

