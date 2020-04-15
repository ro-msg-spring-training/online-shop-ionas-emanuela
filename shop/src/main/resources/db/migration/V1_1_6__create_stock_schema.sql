CREATE TABLE IF NOT EXISTS `stock` (

    `product` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `location` int,
    `quantity` int,
    CONSTRAINT `product_fk` FOREIGN KEY (`product`)
        REFERENCES `product`(`id`),
    CONSTRAINT `location_fk` FOREIGN KEY (`location`)
        REFERENCES `location`(`id`)

);