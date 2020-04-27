CREATE TABLE IF NOT EXISTS `location`
(

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100),
    `address_country` varchar(100),
    `address_city` varchar(100),
    `address_county` varchar(100),
    `address_street_address` varchar(100)

);