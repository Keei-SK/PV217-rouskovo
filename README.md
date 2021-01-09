# PV217-rouskovo
Eshop určen k prodeji roušek, respirátorů a jiného zboží k ochraně zdraví obyvatel v době COVID-19.

# Project Structure
## Types of microservices
### User defaults microservice - Juraj
* user login (authenticate and generate token)
* register new user
### Userinfo microservice - Juraj
* store user information
* store order history
### Product microservice - Aleš
* add a new product
* change product description
* add a price
### Order microservice - Jozef
* make new order
* cancel order
* can show metrics collection
### Display product microservice - Jozef
* display a single product 
* show list of products
### Availibility service - Aleš
* can show health check capabilities

## Technologies

* quarkus.io
* swagger-ui
* kubernetes for cloud deployment
