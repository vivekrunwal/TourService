# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#web)
* Spring data rest hal browser is added for browser like API requests: http://localhost:8080/browser/index.html#/

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)


### Endpoints
* GET: http://localhost:8080/tours?size=3&page=1&sort=title,asc 
* GET: http://localhost:8080/tours/search/findByTourPackageCode?code=CC&size=2&size=3&sort=title,asc
* GET: http://localhost:8080/packages
* GET:  http://localhost:8080/packages/CC
* POST: http://localhost:8080/tours/{tourId}/ratings/
* GET: http://localhost:8080/tours/1/ratings/average
* POST: http://localhost:8080/tours/1/ratings
* GET: http://localhost:8080/tours/1/ratings?size=2&page=1&sort=score,desc
* Get all tour ratings for tour 3 sorted by the customer identifier number, lowest to highest:http://localhost:8080/tours/3/ratings?sort=pk.customerId,asc
* Get all tour ratings for tour 3 sorted by the comment in alphabetical order: http://localhost:8080/tours/3/ratings?sort=comment,asc
*

### Controlling API Exposure
* @RepositoryRestResource(exported=false) Class annotation
* @RestResource(exported=false) Method annotation


### Why use Spring Web MVC
* Not using Spring Data repositories
* API launches an algorithm
* Hide internal data model (entity)
* Requires business layer service


### Response Codes
I expect the API to return 201 (Created) when new rating has been successfully saved to the database.
I expect the API to return 400 (Bad Request) when the request body is not valid.
I expect the API to return 404 (Not found) for invalid tour ID's.






