CREATE TABLE IF NOT EXISTS `customer`
(

    `id`            int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name`    varchar(100),
    `last_name`     varchar(100),
    `username`      varchar(100),
    `password`      varchar(100),
    `email_address` varchar(100)
);