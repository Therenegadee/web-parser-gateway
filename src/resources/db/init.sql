-- user-service
CREATE USER user_service WITH PASSWORD 'user_service';
CREATE DATABASE user_service WITH OWNER = user_service;
CREATE SCHEMA user_service;
-- docker run --name user -e POSTGRES_PASSWORD=user_service -e POSTGRES_DB=user_service -e POSTGRES_USER=user_service -p 5432:5432 -d postgres


-- scrapper-service
CREATE USER scrapper_service WITH PASSWORD 'scrapper_service';
CREATE DATABASE scrapper_service WITH OWNER = scrapper_service;
CREATE SCHEMA scrapper_service;
-- docker run --name scrapper -e POSTGRES_PASSWORD=scrapper_service -e POSTGRES_DB=scrapper_service -e POSTGRES_USER=scrapper_service -p 5433:5432 -d postgres


-- telegram-service
CREATE USER telegram_service WITH PASSWORD 'telegram_service';
CREATE DATABASE telegram_service WITH OWNER = telegram_service;
CREATE SCHEMA telegram_service;
-- docker run --name telegram -e POSTGRES_PASSWORD=telegram_service -e POSTGRES_DB=telegram_service -e POSTGRES_USER=telegram_service -p 5434:5432 -d postgres
