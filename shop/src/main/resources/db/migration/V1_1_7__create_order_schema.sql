CREATE TABLE IF NOT EXISTS `orders` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `shipped_from` int,
    `customerid` int,
    `created_at` date,
    `address_country` varchar(100),
    `address_city` varchar(100),
    `address_county` varchar(100),
    `address_street_address` varchar(100),

    CONSTRAINT `shipped_from_fk` FOREIGN KEY (`shipped_from`)
        REFERENCES `location`(`id`),
    CONSTRAINT `customer_fk` FOREIGN KEY (`customerid`)
        REFERENCES `customer`(`id`)

);