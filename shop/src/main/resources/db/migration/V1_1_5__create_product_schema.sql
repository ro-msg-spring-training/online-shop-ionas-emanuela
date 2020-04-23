CREATE TABLE IF NOT EXISTS `product` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100),
    `description` varchar(300),
    `price` decimal,
    `weight` double,
    `categoryid` int,
    `supplierid` int,
    `image_url` varchar(300),
    CONSTRAINT `category_fk` FOREIGN KEY (`categoryid`)
        REFERENCES `product_category`(`id`),

    CONSTRAINT `supplier_fk` FOREIGN KEY (`supplierid`)
        REFERENCES `supplier`(`id`)

);