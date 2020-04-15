CREATE TABLE IF NOT EXISTS `product` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100),
    `description` varchar(300),
    `price` decimal,
    `weight` double,
    `category` int,
    `supplier` int,
    `image_url` varchar(300),
    CONSTRAINT `category_fk` FOREIGN KEY (`category`)
        REFERENCES `productCategory`(`id`),

    CONSTRAINT `supplier_fk` FOREIGN KEY (`supplier`)
        REFERENCES `supplier`(`id`)

);