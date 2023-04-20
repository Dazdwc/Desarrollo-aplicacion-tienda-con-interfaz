-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: onlinestore
-- ------------------------------------------------------
-- Server version	8.0.32
-- Autor: Daniel Zafra
-- Grupo: El Escuadron de las consultas.
-- Borramos la base de datos si existe y despues crea la base de datos.alter
-- Si no quieres borrar la base de datos cambia el valor del nombre y asignale otro, 
-- importante adaptar el nombre en la parte conexión del programa

DROP DATABASE IF EXISTS onlinestore;
CREATE DATABASE onlinestore;

-- Si solo quieres borrar toda la base de datos, comenta las lineas 10 y 11, de esta manera solo borrará y volverá 
-- a crear todas las tablas con sus atributos.

USE onlinestore;

-- Tabla con la estructura de 'articulo'

DROP TABLE IF EXISTS `articulo`;
CREATE TABLE `articulo` (
  `codigoArticulo` varchar(15) NOT NULL COMMENT 'Descripción del codigo articulo que será la pk, deberá ser unico y no puede ser null',
  `descripcion` varchar(45) DEFAULT NULL COMMENT 'descripción del articulo',
  `pvp` float NOT NULL COMMENT 'Precio total del articulo',
  `gastosEnvio` double NOT NULL COMMENT 'Gastos de envio del articulo',
  `preparacionMin` int NOT NULL COMMENT 'Preparación del articulo en minutos',
  PRIMARY KEY (`codigoArticulo`),
  UNIQUE KEY `codigoArticulo_UNIQUE` (`codigoArticulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabla para almacenar todos los datos de los articulos';

-- Tabla con la estructura de 'cliente'

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `mail` varchar(45) NOT NULL COMMENT 'mail del cliente que será la pk, deberá ser unico y no puede ser null',
  `nombre` varchar(20) NOT NULL COMMENT 'nombre del cliente',
  `nif` varchar(9) NOT NULL COMMENT 'nif del cliente',
  `domicilio` varchar(45) NOT NULL COMMENT 'domicilio del cliente',
  `tipoCliente` varchar(7) NOT NULL,
  `calcAnual` double NOT NULL,
  `descuentoEnv` double NOT NULL,
  PRIMARY KEY (`mail`),
  UNIQUE KEY `mail_UNIQUE` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabla para almacenar todos los datos de los clientes';


-- Tabla con la estructura de 'pedido'

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE `pedido` (
  `numeroPedido` int NOT NULL,
  `cantidad` int NOT NULL,
  `fecha` datetime NOT NULL DEFAULT '2023-04-15 03:30:00',
  `Articulo` varchar(15) NOT NULL,
  `Cliente` varchar(45) NOT NULL,
  PRIMARY KEY (`numeroPedido`),
  KEY `codigoArticulo_idx` (`Articulo`),
  KEY `Cliente_idx` (`Cliente`),
  CONSTRAINT `Articulo` FOREIGN KEY (`Articulo`) REFERENCES `articulo` (`codigoArticulo`),
  CONSTRAINT `Cliente` FOREIGN KEY (`Cliente`) REFERENCES `cliente` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabla para almacenar todos los pedidos';

