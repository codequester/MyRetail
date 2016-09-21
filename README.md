## Notes

1. external_products 
  - This is to mimic the external api call to fetch the product desctiption
  - Uses an Embedded instance of MongoDb to Host the Data.
  - Test cases are not written as this is used to Dogfeed the service call from the main app
2. products
  - Contains code for eposing a a Restful API to display the product name and its pricing details
  - Product name is fetched by making a call to the REST endoint exposed by the 'external_products' project
  - Uses MongoDB as its data store
  - The commands (SQLs) for mondo db can be found in mongo_commands.txt 
  - startMongoDB.bat can be edited and used to start the mongodb process

