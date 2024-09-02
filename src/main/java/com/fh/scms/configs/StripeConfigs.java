package com.fh.scms.configs;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfigs {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Bean
    public void stripeConfig() {
        Stripe.apiKey = stripeApiKey;
    }
}
