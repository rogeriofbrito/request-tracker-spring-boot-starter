# request-tracker-spring-boot-starter

The request-tracker-spring-boot-starter is a Spring Boot Starter dependency that transparently creates a Servlet Filter in a Spring Boot Application that collect some information from request and response Servlet objects and performs a custom action asynchronously (in a new thread).

The custom action is defined by application that uses request-tracker-spring-boot-starter through the creation of a bean that implements de interface _RequestTrackerActionAdapter.java_.

The following data is tracked and sent to custom action as a json string:
* url: url requested composed by protocol, server name, port number and server path
* queryString: query string that is contained in the request URL after the path
* method: HTTP method with which this request was made
* status: status code of HTTP response
* remoteHost: fully qualified name of the client or the last proxy that sent the request
* remotePort: Internet Protocol (IP) source port of the client or last proxy that sent the request
* protocol: ame and version of the protocol the request uses in the form protocol/majorVersion
* timestamp: timestamp of instant that RequestTrackerFilter is executed

## How to use

To use this lib in your Spring Boot Application, follow the steps below:

1) Add the request-tracker-spring-boot-starter dependency in your project

If you are using Maven, add this fragment in your pom.xml file:

```xml
<dependency>
    <groupId>dev.rogeriofbrito</groupId>
    <artifactId>request-tracker-spring-boot-starter</artifactId>
    <version>{version}</version>
</dependency>
```

For others build automation tools, see the proper way to add the dependency in [MVN Repository site](https://mvnrepository.com/artifact/dev.rogeriofbrito/request-tracker-spring-boot-starter).

2) Implement the interface _RequestTrackerActionAdapter_

```java
public class MyRequestTrackerAction implements RequestTrackerActionAdapter {

    @Override
    public Thread doAction(String requestTrackerElementsJson) {

        return new Thread(() -> {
            // do something...
        });
    }
}
```

3) Create a bean with the class that implements _RequestTrackerActionAdapter_

```java
@Configuration
public class RequestTrackerConfiguration {
    
    @Bean
    public MyRequestTrackerAction myRequestTrackerAction() {
        return new MyRequestTrackerAction();
    }
}
```
