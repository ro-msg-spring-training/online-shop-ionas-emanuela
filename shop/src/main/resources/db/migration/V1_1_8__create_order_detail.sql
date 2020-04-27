CREATE TABLE IF NOT EXISTS `order_detail` (

    `orderid` int NOT NULL ,
    `productid` int NOT NULL ,
    `quantity` int,

    CONSTRAINT `product_order_detail_fk` FOREIGN KEY (`productid`)
        REFERENCES `product`(`id`),
    CONSTRAINT `order_fk` FOREIGN KEY (`orderid`)
        REFERENCES `orders`(`id`),
    PRIMARY KEY (`orderid`,`productid`)

);