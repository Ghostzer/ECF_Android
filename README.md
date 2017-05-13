It's simple application Android for see result of regate race.
This application use Slim PHP Framework for API REST.

- Folder API, configure your DB in src/settings.php
- In Android Studio, config/ConfigApi, configure your IP

# API :

## Download dependencies

`composer install`


## Run

`composer start`


## Available API 

 - get all regates:

 `GET http://localhost:8080/api/regates`

 - get regate by id=49 :

 `GET http://localhost:8080/api/regatebyid/49`

 - get regate by winter

 `GET http://localhost:8080/api/regate/winter`

 - get regate by summer

 `GET http://localhost:8080/api/regate/summer`

 - get scores by regate=49 :

 `GET http://localhost:8080/api/resultatsbyregate/49` 


