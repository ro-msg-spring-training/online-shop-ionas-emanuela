CREATE TABLE IF NOT EXISTS `stock` (

    `productid` int NOT NULL,
    `locationid` int NOT NULL,
    `quantity` int,
    CONSTRAINT `product_fk` FOREIGN KEY (`productid`)
        REFERENCES `product`(`id`),
    CONSTRAINT `location_fk` FOREIGN KEY (`locationid`)
        REFERENCES `location`(`id`),
    PRIMARY KEY (`productid`, `locationid`)

);