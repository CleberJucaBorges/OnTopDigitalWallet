
# OnTop Challenge

This project is a programming challenge from OnTop, which involved making calls to some mocks such as transaction and balance, and persisting transactions and destination accounts. The goal was to showcase programming skills by building a functional and efficient solution to the challenge.

As a C# specialist, I faced some challenges since I had only done minor maintenance on Java-based systems and had never created a Spring Boot project from scratch. Despite this, I decided to expand my scope by implementing some services that were not required for the challenge, in order to improve the overall functionality and quality of the solution such as Swagger documentation and services to create user for example.

## How to Use

To use this project, follow these steps:

Clone the repository to your local machine.

Navigate to the root directory of the project.

Run the following command in your terminal to start the MySQL database using Docker:

```code
docker-compose -f stack.yml up

```

This command will create a container with the MySQL database that the project uses for persistence.

Once the database is running, you can start the project in your preferred Java IDE. The application should now be able to connect to the MySQL database and be ready to use.

Note: Make sure that Docker is installed on your machine before running the above command.
```bash
  git clone https://github.com/CleberJucaBorges/OnTopDigitalWallet.git
```




## Swagger
To access the Swagger documentation, open your web browser and go to:

```link
http://localhost:8099/swagger-ui/index.html
```
Note: If port 8099 is already in use on your machine, you can change it by editing the application.properties file and setting the server.port property to a different value.

## Postman
In addition to the Swagger documentation, a Postman collection is also available on the project's GitHub repository. 
The file name is:
```
Api Documentation.postman_collection.json
```
## Roadmap


This project has the potential for several possible improvements in the future. Some potential areas of improvement include:

- Implement Security: To ensure the security of the application and protect against unauthorized access, it may be beneficial to implement security measures such as authentication and authorization. One approach to consider is implementing JWT (JSON Web Tokens), which can provide a secure method for transmitting authentication data between parties.

- Separate Transaction and Customer Domains into Different Microservices: Splitting the application into separate microservices for transaction and customer domains can help to simplify the codebase, improve scalability, and allow for more flexibility in the deployment process.

- API Gateway: Implementing an API gateway can help to manage the communication between different microservices and provide a unified interface for clients to access the different services.

- Implement HATEOAS: By implementing HATEOAS (Hypermedia as the Engine of Application State), the API can provide links to related resources to help clients navigate the API and discover available actions.

- Implement Messaging with Kafka: Implementing messaging with Kafka can help to ensure that transactions are reliably sent to the provider bank and can be retried if necessary.

These are just a few of the potential improvements that could be made to this project. By continuously improving and updating the project, it can stay relevant and meet the evolving needs of its users.

## Autores

- [@CleberJucaBorges](https://github.com/CleberJucaBorges)

