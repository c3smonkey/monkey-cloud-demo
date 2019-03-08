# Monkey Cloud Demo  [![CircleCI](https://circleci.com/gh/c3smonkey/monkey-cloud-demo.svg?style=svg)](https://circleci.com/gh/c3smonkey/monkey-cloud-demo) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/ddcc5a06288c4fc0b066dd95be4f217b)](https://www.codacy.com/app/marzelwidmer/monkey-cloud-demo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=c3smonkey/monkey-cloud-demo&amp;utm_campaign=Badge_Grade)

This Kotlin Spring Cloud project is heavily inspired by the awesome [Ryan Baxter`s beginners-guide-to-spring-cloud](https://github.com/ryanjbaxter/beginners-guide-to-spring-cloud)
 
 
### Prerequisites
 
Before you run the applications in this repository you should install the [Spring Cloud CLI.](https://cloud.spring.io/spring-cloud-cli/)

```bash
spring cloud eureka configserver zipkin
```
    
    
## Config Server
Check if the configuration are loaded.
 
- [Foo-Service](http://localhost:8888/foo-service/default)
- [Bar-Service](http://localhost:8888/bar-service/default)

