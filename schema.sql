-- Script opcional: solo es necesario si decides usar tu propia base de datos
-- en lugar de la conexión remota que ya viene provista en el enunciado
-- (esa base ya tiene estas tablas creadas).

CREATE DATABASE IF NOT EXISTS almacenitlafinal;
USE almacenitlafinal;

CREATE TABLE IF NOT EXISTS usuarios (
    idUser   INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(140) NOT NULL UNIQUE,
    Nombre   VARCHAR(140) NOT NULL,
    Apellido VARCHAR(140) NOT NULL,
    Telefono VARCHAR(140) NOT NULL,
    Email    VARCHAR(140) NOT NULL,
    Password VARCHAR(140) NOT NULL
);

CREATE TABLE IF NOT EXISTS productos (
    idProducto        INT AUTO_INCREMENT PRIMARY KEY,
    NombreProducto     VARCHAR(140) NOT NULL,
    MarcaProducto       VARCHAR(140) NOT NULL,
    CategoriaProducto   VARCHAR(140) NOT NULL,
    PrecioProducto      INT NOT NULL,
    StockProducto        INT NOT NULL
);
