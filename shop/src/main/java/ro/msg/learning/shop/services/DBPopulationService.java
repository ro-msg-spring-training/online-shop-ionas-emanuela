package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.*;

@Service
@RequiredArgsConstructor
public class DBPopulationService{

    private final StockRepository stockRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public void saveProductCategory(ProductCategory toSave) {
        productCategoryRepository.save(toSave);
    }

    public void deleteAllProductCategories() {
        productCategoryRepository.deleteAll();
    }

    public void saveSupplier(Supplier toSave) {
        supplierRepository.save(toSave);
    }

    public void deleteAllSuppliers() {
        supplierRepository.deleteAll();
    }

    public void saveStock(Stock toSave) {
        stockRepository.save(toSave);
    }

    public void deleteAllStocks() {
        stockRepository.deleteAll();
    }

    public void saveCustomer(Customer toSave) {
        customerRepository.save(toSave);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public void saveLocation(Location toSave) {
        locationRepository.save(toSave);
    }

    public void deleteAllLocations() {
        locationRepository.deleteAll();
    }

    public void saveProduct(Product toSave) {
        productRepository.save(toSave);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public void deleteAllOrderDetails() {
        orderDetailRepository.deleteAll();
    }

}
