package ro.msg.learning.shop.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration
public class StrategyFactory {

    @Value("${sbpg.init.chosen-strategy}")
    private String chosenStrategy;

    @Bean
    public IStrategy getStrategy(SingleLocationStrategy singleLocationStrategy, MostAbundantStrategy mostAbundantStrategy) {

        switch(chosenStrategy){

            case "single_location": return singleLocationStrategy;
            case "most_abundant": return mostAbundantStrategy;
        }

        return null;
    }
}
