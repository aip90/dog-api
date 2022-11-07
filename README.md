# Dog API

https://dog.ceo/dog-api/

## This project is about RESTful Dog API 

![multithread](https://th.bing.com/th/id/OIP.fRoA47zdduViSDjwOVmFhwAAAA?w=115&h=180&c=7&r=0&o=5&dpr=1.5&pid=1.7)

Tech stack:
- Java 8
- Spring boot
- H2 database
- Lombok
- Swagger
- Maven
- Cache

Note :
- Data source refer to data consume from https://dog.ceo/dog-api/
- CRUD (GET, PUT, POST, and DELETE) endpoints Dog API RESTful service
- Embedded memory sql database (h2)
- Unit test
- Singleton pattern example on http://localhost:8080/rest/v1/singleton
- Rest API client timeout 5000 ms on http://localhost:8080/rest/v1/breed/list-dog-api
- Rest API client timeout 2000 ms on 
  http://localhost:8080/rest/v1/sub-breed/list-by-breed?name=hound
- Implement lambda
- Get response odd number of images when dog breed is shiba 
  on http://localhost:8080/rest/v1/breed/list-breed-images?breedName=shiba
- When dog breed is sheepdog then extract the sub breed become parent breed
  on http://localhost:8080/rest/v1/breed/list
- When dog breed is terrier then extract the sub breed become parent breed
  and fetch the images by breed name on http://localhost:8080/rest/v1/breed/list
- Using maven for dependencies
- Using lombok
- Using swagger for API documentation on http://localhost:8080/swagger-ui.html#
- Using spring bean validation for breed validator on http://localhost:8080/rest/v1/breed/create
- Adding caching layer using memory cache 
  on http://localhost:8080/rest/v1/breed/get/{id} ,
  http://localhost:8080/rest/v1/breed/update/{id} ,
  and http://localhost:8080/rest/v1/breed/delete/{id}
