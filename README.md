# Orders Application
    /orders : the REST API service implemented with Java 8 + Spring boot + JPA + MySql
		/orders/README.md : doc about API endpoints , DB schema and how to run the API service seperately.
	
	/orders-ui : the Angular UI calling orders API service
		/orders-ui/README.md : doc about building and starting orders-ui seperately
	
## To Run orders API and orders-ui on Docker

1) Build orders API serivce
	$ mvn clean package -f ./orders/pom.xml -DskipTests

2) run the docker-compose file to create/pull docker images for orders API/order-ui/MySql;
	and bring these serivces up
	
	$ docker-compose -f ./docker-compose.yml up -d
	
	services port configuartion refer to service-ports.jpg
	
3) then you can access the swagger-ui of the API serivce :
	http://localhost:8080/orders/swagger-ui/

	To access the order-ui, go to http://localhost:4200
	
	UI images refer to Dashboard-1.jpg / CreateOrder.jpg

4) to shut down all the services on Docker	   
	$ docker-compose -f ./docker-compose.yml down

## Load Test Data file after deployment 
 - after deploy to docker, we can also run the test directly to load the \orders\src\test\data\data.json into database:

1) stop the container orders;
	$ docker stop orders
	$ docker ps -a
	
2) from MySQL Workbench:
	USE MyDB;
	SHOW TABLES;
	SELECT * FROM items;
	DELETE FROM items WHERE order_number>=0; 
	
	-- DROP TABLE items;
	-- SHOW CREATE TABLE items;
  
3) run the unit test to load data.json into database
	$ cd OrdersApp/orders
	$ mvn -Dtest=OrderRepositoryIntegrationTest#loadJsonToDB test
