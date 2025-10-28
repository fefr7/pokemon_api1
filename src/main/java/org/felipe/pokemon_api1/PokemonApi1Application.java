package org.felipe.pokemon_api1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class PokemonApi1Application {

    public static void main(String[] args) {
        SpringApplication.run(PokemonApi1Application.class, args);
    }

}
