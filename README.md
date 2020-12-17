# PV217-rouskovo
Eshop určen k prodeji roušek, respirátorů a jiného zboží k ochraně zdraví obyvatel v době COVID-19.

# Project Structure
## Types of microservices
### User defaults microservice - Juraj
* login
* register new user
### Userinfo microservice - Juraj
* store user information
* store order history
### Product microservice
* add a new product
* change product description
* add a price
### Order microservice
* make new order
* cancel order
* can show metrics collection
### Display product microservice
* display a single product 
* show list of products
### Availibility service
* can show health check capabilities
### Authentization service - Juraj
* token generation
* authentication of users

## Technologies

* quarkus.io
* swagger-ui
* kubernetes for cloud deployment
