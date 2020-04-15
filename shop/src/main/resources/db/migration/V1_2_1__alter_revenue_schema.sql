ALTER TABLE `revenue`
ADD FOREIGN KEY (`location`)
REFERENCES `location`(`id`);