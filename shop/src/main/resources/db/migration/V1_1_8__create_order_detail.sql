CREATE TABLE IF NOT EXISTS `orderDetail` (

    `order` int NOT NULL ,
    `product` int NOT NULL ,
    `quantity` int,

    CONSTRAINT `product_order_detail_fk` FOREIGN KEY (`product`)
        REFERENCES `product`(`id`),
    CONSTRAINT `order_fk` FOREIGN KEY (`order`)
        REFERENCES `order`(`id`),
    PRIMARY KEY (`order`,`product`)

);