# am-digital-challenge

This is the response to The Asset Management Digital Challenge developed by Rafael Banzo

###Comments about the implementation

A BDD approach has been followed in the development of the challenge.

HATEOAS (Hypertext As The Engine Of Application State) has been used.  
In my opinion, its main advantage is that it allows the client to navigate easily through the provided links.

The operations exposed by the REST API are:
* /shops/nearestShop (GET) --> receives latitude and longitude as parameters. It returns the nearest shop.
* /shops/{name} (GET) -->receives the name of the shop as a path variable. It returns the shop.
* /shops (POST) -->receives a JSON Shop as parameter.  The JSON object is validated using Annotations of javax.validation.constraints  
If the shop replaces another shop, it returns the replaced shop in the body.

Three layers have been used in the design:
* Controller: receives the URL parameters or JSON, calls the service and prepares the JSON for the response
* Service: handles the logic
* Repository: stores the data

There is a global exception handler GlobalExceptionHandler.


The location of the coordinates of the Postcode has been done using google-maps-services. Using this the integration has been easy as this API exposed even methods to make asynchronous calls. The asynchronous calls have been used so that the response to the addition operation is fast. The user does not need to receive the postcode in the response and it is update asynchronously.
When the coordinates are obtained from google, the Shop is updated. I have implemented a versioning system with a timestap in order to ensure that a newer shop is not updated.

There is a YML file with the configuration of the API key to connect to the google maps API.

The data is kept in memory using a ConcurrentHashMap. An object of this class has been used because the previous value is returned in the put method, it is thread safe, it supports full concurrency of retrievals and high expected concurrency for updates.


### Prerequisites

It is necessary to have gradle configured in the machine.

### Installing

* Download the code First clone the git repository from **https://github.com/rafaelbanzo/am-digital-challenge/**
* Build the jar and run tests **gradlew build**
* Run the springboot application **java -jar build/libs/am-digital-challenge-1.0.0.jar**


### Tests

There are three types of tests:
* BDD: I have written BDD tests using JBehave and Rest Assured. These tests check the functionalities of the challenge:
  * Add a shop
  * Add two shops with the same name
  * Find the nearest shop
* UnitTest: There is an example of UnitTest. Mockito has been used to mock. 
* Postman tests: They have been exported to the postman directory. They have to be imported to postman in order to run them. They connect to localhost on port 8080.

While building the project, the junit and BDD tests are automatically run.

## Comments

### How you would expand this solution, given a longer development period?

I would store the shops in a database, ensuring that the updating and concurrency requirements are met.

## How would you go about testing this solution?

I would add more scenarios to the tests written with JBehave and Rest Assured.  
Besides, the end-to-end tests should be written using the RESTful client that the Retail Managers will use. If it is Chrome's Postman, an expansion of my Postman tests maybe used.   

## How would you integrate this solution into an existing collection of solutions used by the Retail Manager?

It would depend on how the existing solutions had been implemented.  

## How would you go about deploying this solution to production systems?

I would run the application inside docker containers.

#### Authors

**Rafael.Banzo@gft.com**
