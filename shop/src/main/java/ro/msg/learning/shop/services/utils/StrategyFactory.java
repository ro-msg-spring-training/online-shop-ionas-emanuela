package ro.msg.learning.shop.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;


@RequiredArgsConstructor
@Configuration
public class StrategyFactory {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Value("${sbpg.init.chosen-strategy}")
    private String chosenStrategy;

    @Bean
    public IStrategy getStrategy() {

        switch(chosenStrategy){

            case "single_location": return new SingleLocationStrategy(productRepository, productCategoryRepository, stockRepository, locationRepository);
            case "most_abundant": return new MostAbundantStrategy(stockRepository, productRepository, productCategoryRepository, locationRepository);
        }

        return null;
    }
}
