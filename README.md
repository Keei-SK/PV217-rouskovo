# PV217-rouskovo
Eshop určen k prodeji roušek, respirátorů a jiného zboží k ochraně zdraví obyvatel v době COVID-19.

# Project Structure
## Types of microservices
### User Defaults microservice - Juraj
* user login (authenticate and generate token)
* register new user
### User Account microservice - Juraj
* store user information
* show order history
### Product microservice - Aleš
* display a single product 
* show list of products
* add a new product
* change product description
* add a price
### Order microservice - Jozef
* make new order
* cancel order
* can show metrics collection
### Availibility service - Aleš
* can show health check capabilities

## Technologies

* quarkus.io
* swagger-ui
* kubernetes for cloud deployment
