package com.audicin.backend.track.api.config.env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        Dotenv dotenv = Dotenv.configure().directory("../").filename(".env")
                .ignoreIfMissing().load();

        dotenv.entries().forEach(
                entry->System.setProperty(entry.getKey(), entry.getValue()));
        dotenv.entries().forEach(
                dotenvEntry->System.out.println(dotenvEntry.getValue()));
        return dotenv;
    }
}

