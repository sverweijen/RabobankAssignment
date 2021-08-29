package nl.rabobank.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
@EnableMongoRepositories
@EnableConfigurationProperties(MongoProperties.class)
@Import(EmbeddedMongoAutoConfiguration.class)
@RequiredArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {
    private final MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName()
    {
        return mongoProperties.getMongoClientDatabase();
    }

    @Override
    @Bean(destroyMethod = "close")
    public MongoClient mongoClient()
    {
        return MongoClients.create(mongoProperties.determineUri());
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRepositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("account-data.json")});
        return factory;
    }
}
