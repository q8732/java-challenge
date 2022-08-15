### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui/
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own GitHub named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8




### My experience in Java
- I have 10+ years experience in Java.
- I started to use Spring Boot from 2019, and before that 
I had used other DI container which made my experience with Spring Boot much easier.

### What I did
- upgraded dependencies to the latest
  - Spring Boot **v2.7.2**
  - Springfox   **v3.0.0**
- implemented validation logic for entities
  - implement general advice to handle validation errors and provide detailed responses
- added tests
- enabled transaction management
- improved performance with 
  - enable caching
  - add pagination support on list operation
- tweaked springfox/swagger
  - show relevant API document only

### What I will do in the future
- implement authentication to protect end points
  - authentication based on token
- use a cache provider rather than the default
  - possible providers
    - JCache 
    - Redis
