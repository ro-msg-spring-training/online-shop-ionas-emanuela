INSERT INTO `product_category` VALUES (1,'Vegan','good for the planet. good for you');
INSERT INTO `product_category` VALUES (2,'Alcohol','good for a good time');
INSERT INTO `product_category` VALUES (3,'Pastry','sweet and salty');

INSERT INTO `supplier` VALUES (1, 'Alpro');
INSERT INTO `supplier` VALUES (2, 'Biertceva');
INSERT INTO `supplier` VALUES (3, 'LKK Bakery');
INSERT INTO `supplier` VALUES (4, 'NoMeat');

INSERT INTO `product` VALUES(1, 'Soy Milk', 'Milk from soy beans', '3', '0.4', 1, 1, 'image_url');
INSERT INTO `product` VALUES(2, 'Beer', 'Milk from hop', '2', '0.1', 2, 2, 'image_url');
INSERT INTO `product` VALUES(3, 'Almond Milk', 'Milk from almond beans', '4', '0.4', 1, 1, 'image_url');
INSERT INTO `product` VALUES(4, 'Burger', 'Beans, beetroot, sweet potato', '7', '1', 1, 4, 'image_url');
INSERT INTO `product` VALUES(5, 'Sausage', 'Soy beans', '3.5', '1', 1, 4, 'image_url');
INSERT INTO `product` VALUES(6, 'White loaf', 'Flour, yeast, salt', '1', '0.8', 3, 3, 'image_url');
INSERT INTO `product` VALUES(7, 'Cheesecake', 'vegan cheese, blueberries', '10', '1', 3, 3, 'image_url');

INSERT INTO `customer` VALUES(1, 'John', 'Johnson', 'heres_johnny', 'pass', 'john_johnson@yahoo.com');

INSERT INTO `location` VALUES(1, 'Warehouse', 'Romania', 'Targu Lapus', 'Maramures', 'L. Rebreanu, 42');
INSERT INTO `location` VALUES(2, 'Other_Warehouse', 'Romania', 'Targu Lapus', 'Maramures', 'L. Rebreanu, 43');

INSERT INTO `stock` VALUES(1, 1, 100);
INSERT INTO `stock` VALUES(2, 1, 350);
INSERT INTO `stock` VALUES(4, 2, 300);
INSERT INTO `stock` VALUES(3, 1, 200);
INSERT INTO `stock` VALUES(4, 1, 150);
INSERT INTO `stock` VALUES(5, 2, 150);
INSERT INTO `stock` VALUES(6, 2, 150);

INSERT INTO `orders`
VALUES(1, 1, 1, '2020-04-21', 'Romania', 'Targu Lapus', 'Maramures', 'L. Rebreanu, 41');

INSERT INTO `order_detail` VALUES(1, 1, 10);
INSERT INTO `order_detail` VALUES(1, 2, 10);