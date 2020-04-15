CREATE TABLE IF NOT EXISTS `orderDetail` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order` int,
    `product` int,
    `quantity` int,

    CONSTRAINT `product_order_detail_fk` FOREIGN KEY (`product`)
        REFERENCES `product`(`id`),
    CONSTRAINT `order_fk` FOREIGN KEY (`order`)
        REFERENCES `order`(`id`)

);