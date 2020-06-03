package main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class Spring {

    public static void main(String[] args) {
        SpringApplication.run(Spring.class, args);
    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    @Bean
//    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//
//
//        return args -> {
//
//
//            String quote = restTemplate.getForObject(
//                    "https://cscinterns2020.atlassian.net//rest/api/3/issue/JIRARD-4", String.class);
//           System.out.println(quote);
//        };
//    }
}