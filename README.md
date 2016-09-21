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
  - The app is configure to listen on port - 9008 and under the context root external-api/
  - The endpoint are protected by oauth and hence to obtain the token we need to visit - http://localhost:9008/external-api/oauth/token (Refer to the screenshot -https://github.com/codequester/MyRetail/blob/master/Screenshot_external_api_token.JPG)
  - Once the token is obtain it can be passed in the header as a bearer token and invoke the REST point to get the prodcut details (refer to the screen shot - https://github.com/codequester/MyRetail/blob/master/Screenshot_external_api_prod_desc.JPG)
  
2.Products (Main Application) 
 - The requires mondo db running on the default port and if not the port on which it runns can be edited in the yml file.
 - Please use the included mongo commands to create the initial data to test the working of the endpoint - https://github.com/codequester/MyRetail/blob/master/products/mongo_commands.txt
 - Once mongo db is up and running , this project can be run as a standlone application by itself.
 - The app is configure to liste on port 8008 (http) or 9180(https). These can again be configured using the include yml file.
 - This run under the context too api/
 - The URL of getting the product details along with its pricing are
      - GET - https://localhost:8108/api/products/100001 -> where 10001 is the product id,
  - The URL for updating the price is
      - POST - https://localhost:8108/api/products/100001 -> where 10001 is the prodcut id. This also requires us to send the updating pricing information as a request body i.e {	"value": 51.99,	"code":"USD"}

