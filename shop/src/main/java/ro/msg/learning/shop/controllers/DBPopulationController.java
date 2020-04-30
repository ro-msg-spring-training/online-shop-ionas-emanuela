package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.services.DBPopulationService;

import java.math.BigDecimal;

@RestController
@Profile("test")
@RequiredArgsConstructor
public class DBPopulationController {

    private final DBPopulationService dbPopulationService;

    @PostMapping("/database")
    public void populateDB() {

        ProductCategory productCategory1 = ProductCategory.builder()
                .id(1)
                .name("vegan")
                .description("good for the planet. good for you").build();
        ProductCategory productCategory2 = ProductCategory.builder()
                .id(2)
                .name("pastry")
                .description("sweet and salty").build();

        dbPopulationService.saveProductCategory(productCategory1);
        dbPopulationService.saveProductCategory(productCategory2);


        Supplier supplier1 = Supplier.builder().id(1).name("Alpro").build();
        Supplier supplier2 = Supplier.builder().id(2).name("LKK Bakery").build();

        dbPopulationService.saveSupplier(supplier1);
        dbPopulationService.saveSupplier(supplier2);

        Product product1 = Product.builder().id(1).category(productCategory1).supplier(supplier1)
                .name("Soy Milk").description("milk from soy")
                .price(new BigDecimal(2)).weight(0.8).imageUrl("image").build();
        Product product2 = Product.builder().id(2).category(productCategory1).supplier(supplier1)
                .name("Almond Milk").description("milk from almond")
                .price(new BigDecimal(3)).weight(1.0).imageUrl("image").build();
        Product product3 = Product.builder().id(3).category(productCategory2).supplier(supplier2)
                .name("White loaf").description("Flour, yeast, salt")
                .price(new BigDecimal(1)).weight(0.8).imageUrl("image").build();

        dbPopulationService.saveProduct(product1);
        dbPopulationService.saveProduct(product2);
        dbPopulationService.saveProduct(product3);

        Customer customer = Customer.builder().id(1)
                .firstName("Rou")
                .lastName("Reynolds")
                .username("roureyno").password("password").emailAddress("roureyno@email.com").build();

        dbPopulationService.saveCustomer(customer);

        Address address1 = Address.builder().country("Romania")
                .county("Maramures").city("Targu Lapus").streetAddress("L. rebreanu, 42").build();
        Address address2 = Address.builder().country("Romania")
                .county("Maramures").city("Targu Lapus").streetAddress("1 Mai, 33").build();

        Location location1 = Location.builder().id(1).name("Warehouse").address(address1).build();
        Location location2 = Location.builder().id(2).name("Other Warehouse").address(address2).build();

        dbPopulationService.saveLocation(location1);
        dbPopulationService.saveLocation(location2);


        Stock stock1 = Stock.builder().id(new StockKey(1,1)).location(location1).product(product1).quantity(100).build();
        Stock stock2 = Stock.builder().id(new StockKey(2,1)).location(location1).product(product2).quantity(150).build();
        Stock stock3 = Stock.builder().id(new StockKey(2,2)).location(location2).product(product2).quantity(300).build();
        Stock stock4 = Stock.builder().id(new StockKey(3,1)).location(location1).product(product3).quantity(200).build();
        Stock stock5 = Stock.builder().id(new StockKey(1,2)).location(location2).product(product1).quantity(120).build();

        dbPopulationService.saveStock(stock1);
        dbPopulationService.saveStock(stock2);
        dbPopulationService.saveStock(stock3);
        dbPopulationService.saveStock(stock4);
        dbPopulationService.saveStock(stock5);

    }

    @DeleteMapping("/database")
    public void emptyDB() {

        dbPopulationService.deleteAllOrderDetails();
        dbPopulationService.deleteAllStocks();
        dbPopulationService.deleteAllOrders();
        dbPopulationService.deleteAllCustomers();
        dbPopulationService.deleteAllLocations();
        dbPopulationService.deleteAllProducts();
        dbPopulationService.deleteAllProductCategories();
        dbPopulationService.deleteAllSuppliers();
    }

}
