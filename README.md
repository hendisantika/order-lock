# order-lock
### Use Case
1. (Selecting) As a customer I can select item which I want, if stock is available
2. (Ordering) As a customer I can order items which I’ve already selected is stock available
Situations which must to be handled
1. Susan and Manda is ordering apple concurrently
2. Apple stock = 5, How do you serve Susan and Manda order?

![Sequence Diagram](img/sequence.png "Sequence Diagram")

#### Things to do to run this project:
1. Clone this repo : `git clone https://github.com/hendisantika/order-lock.git`
2. Go to the folder it self : `cd order-lock`
3. Make sure you have installed python on your environment.
4. Make sure you have installed maven on your environment.
5. If you don't have maven you can run by this command: `./mvnw clean spring-boot:run`
    ```java
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v2.1.2.RELEASE)
    
    2019-01-23 06:19:00.275  INFO 29760 --- [           main] c.h.orderlock.OrderLockApplication       : Starting OrderLockApplication on Hendis-MacBook-Pro.local with PID 29760 (/Users/hendisantika/IdeaProjects/order-lock/target/classes started by hendisantika in /Users/hendisantika/IdeaProjects/order-lock)
    2019-01-23 06:19:00.277  INFO 29760 --- [           main] c.h.orderlock.OrderLockApplication       : No active profile set, falling back to default profiles: default
    2019-01-23 06:19:00.868  INFO 29760 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data repositories in DEFAULT mode.
    2019-01-23 06:19:00.936  INFO 29760 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 58ms. Found 1 repository interfaces.
    2019-01-23 06:19:01.292  INFO 29760 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$71ccfa48] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
    2019-01-23 06:19:01.684  INFO 29760 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
    2019-01-23 06:19:01.718  INFO 29760 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
    2019-01-23 06:19:01.718  INFO 29760 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.14]
    2019-01-23 06:19:01.735  INFO 29760 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/Users/hendisantika/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.]
    2019-01-23 06:19:01.838  INFO 29760 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
    2019-01-23 06:19:01.900  INFO 29760 --- [           main] o.s.boot.web.servlet.RegistrationBean    : Servlet webServlet was not registered (possibly already registered?)
    2019-01-23 06:19:02.068  INFO 29760 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    2019-01-23 06:19:02.282  INFO 29760 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    Hibernate: drop table product if exists
    Hibernate: drop sequence if exists hibernate_sequence
    Hibernate: create sequence hibernate_sequence start with 1 increment by 1
    Hibernate: create table product (id bigint not null, name varchar(255), stock bigint, version bigint, primary key (id))
    2019-01-23 06:19:03.871  INFO 29760 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
    2019-01-23 06:19:04.365  INFO 29760 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
    2019-01-23 06:19:04.435  WARN 29760 --- [           main] aWebConfiguration$JpaWebMvcConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
    2019-01-23 06:19:04.683  INFO 29760 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
    2019-01-23 06:19:04.686  INFO 29760 --- [           main] c.h.orderlock.OrderLockApplication       : Started OrderLockApplication in 5.101 seconds (JVM running for 10.866)
    2019-01-23 06:19:21.467  INFO 29760 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
    ```

6. Open new terminal to test the app.
7. In your new terminal run this script : `python test-order.py`
```python
User A get: {u'stock': 3, u'version': 7, u'id': 1, u'name': u'Apple'}
User B get: {u'stock': 3, u'version': 7, u'id': 1, u'name': u'Apple'}
User B save: {u'stock': 4, u'version': 8, u'id': 1, u'name': u'Apple'}
User A save: {u'status': 500, u'timestamp': u'2019-01-22T23:20:38.297+0000', u'message': u'Object of class [com.hendisantika.orderlock.entity.Product] with identifier [1]: optimistic locking failed; nested exception is org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect) : [com.hendisantika.orderlock.entity.Product#1]', u'path': u'/products', u'error': u'Internal Server Error'}
```





