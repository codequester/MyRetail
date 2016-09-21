## Summary

This repo contains 2 mavenized spring boot projects that can be imported into any IDE as maven projects

1. external_products (External Application)
  - This is to mimic the external api call to fetch the product description
  - Uses an Embedded instance of MongoDb to Host the Data.
  - Test cases are not written as this is used to Dogfeed the service call from the main app
  - The endpoints are protected by oauth with the client credential grant.
2. products (Main Application)
  - Contains code for exposing a a Restful API to display the product name and its pricing details
  - Product name is fetched by making a call to the REST endoint exposed by the 'external_products' project
  - The call to the REST endpoint is made after obtaining the oauth token using the client credentials.
  - Uses MongoDB as its data store
  - The commands (SQLs) for mondo db can be found in mongo_commands.txt 
  - startMongoDB.bat can be edited and used to start the mongodb process
  
## Steps for Execution

1. external_products (External Application)
  - This is a self contained module and hence there are no dependency on any other software.
  - The jar file - 'external_products-0.0.1' in https://github.com/codequester/MyRetail/tree/master/external_products/target is a runnable jar and can be executed using the command -  **java -jar target/external_products-0.0.1.jar**
  - For the above to run please ensure that there are not process running on port 28017 as the embedded mongodb is configured to run on this port. This can be edited in the yml file
  - **Please Note** while starting the application either by running the jar or directly from the IDE, due to some issue in the Embedded mongo DB start / shut process, the app errors out. IF this happens we just need to restart it.
2. 

