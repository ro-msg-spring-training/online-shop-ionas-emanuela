package ro.msg.learning.services.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StrategyFactory {

    @Value("${sbpg.init.chosen-strategy}")
    private String chosenStrategy;

    @Bean
    public IStrategy getStrategy() {

        switch(chosenStrategy){

            case "single_location": return new SingleLocationStrategy();
            case "most_abundant": return new MostAbundantStrategy();
        }

        return null;
    }
}
