CREATE TABLE IF NOT EXISTS `stock` (

    `product` int NOT NULL,
    `location` int NOT NULL,
    `quantity` int,
    CONSTRAINT `product_fk` FOREIGN KEY (`product`)
        REFERENCES `product`(`id`),
    CONSTRAINT `location_fk` FOREIGN KEY (`location`)
        REFERENCES `location`(`id`),
    PRIMARY KEY (`product`, `location`)

);