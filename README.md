# Name: Weather_API

## 1. Version

- Java: 22
- Maven: 3.9.9
- Spring Boot: 3.3.3
- Mysql: 9.0.1
- Docker: 3.9

## 2. Run Project

### B1: Clone project from GitHub

```angular2html
git clone https://github.com/Ghost-rider-HCMUT/weather_api_spb.git
```

### B2: Build project

```angular2html
mvn clean install
```

### B3: Run project

##### Run using mvn

```angular2html
mvn spring-boot:run
```

#### Run using Docker

```angular2html
docker compose up --build
```

## 3. Project description

* Data: Some important fields for weather are retrieved from https://www.weatherapi.com/docs/
* Database:
    * id: ID (PrimaryKey - Auto Increment)
    * The remaining fields follow the key of the data retrieved from the API.
* Features:
    * Jobs:
        * Automatically call data from database every 15 minutes
        * Update new data into DB and delete old data (1 day) from DB
    * API:
        * API to get latest weather information of all provinces with pagination
        * API to get the latest weather information of a province by name
        * API creates a new record
        * API updates weather information of a province by name
        * API deletes all weather data of a province by name
        * Expand: Search function to get all weather information of a province by name in 1 day
    * Knowledge researched and applied:
        * Design Pattern Builder and Annotation @Builder
        * Exception and Custom ExceptionHandler
        * Repository Pattern and Write methods to query in Repository
        * Mapper, DTO
        * Internationalization (i18n)
        * MultiThread (Call API)
        * File I/O
        * Send Email Report
