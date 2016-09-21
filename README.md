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

