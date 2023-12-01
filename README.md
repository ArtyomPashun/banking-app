# Bank app

```
Language:   Java 17
DB:         PostgreSQL latest
Deploy:     Docker v20.10.23
```

# Api Docs

Swagger Documentation link:
> http://localhost:8080/swagger-ui/index.html

## Project Description:

This app provides several features:

1. Receive information about each expense transaction in tenge (KZT), rubles (RUB) and
other currencies in real time and save it in your own database (DB);

2. Keep the monthly spending limit in U.S. dollars (USD) separate for two expense
categories: goods and services. If not set, accept the limit of 1000 USD;

3. Request data on exchange rates of KZT/USD, RUB/USD currency pairs on a daily
interval (1day/daily) and store them in your own database. When calculating rates, use
close data. If they are not available for the current day (weekend or holiday), then use the
data of the last close (previous_close);

4. Mark transactions that have exceeded the monthly transaction limit (technical flag
limit_exceeded);

5. Allow the client to set a new limit. When a new limit is set, the microservice
automatically sets the current date, not allowing it to be set in the past or future tense. It
is forbidden to update existing limits;

6. At the client's request, return a list of transactions that have exceeded the limit, indicating
the limit that has been exceeded (date of setting, limit amount, currency (USD)).

## How to start the app locally

1. Clone this repository to your pc.
2. Register free account on https://twelvedata.com/pricing (free version) to get your api-key.
3. Go to `src/main/resources/application.properties` and add your api-key value to line
   with `exchange.client.apikey = enter your api-key`. As a result it should look
   like `exchange.client.apikey = e42w950ebc364a498f0ec2ea998f67a3`
4. Go to your app folder and run in terminal `docker-compose up`.
5. Wait few minutes till all containers up.

This will start the Spring Boot application and any other dependencies in the docker-compose.yml file.
