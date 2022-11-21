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
