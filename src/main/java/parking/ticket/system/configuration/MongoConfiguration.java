package parking.ticket.system.configuration;


import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static parking.ticket.system.constants.MongoConstants.MONGO_CLIENT_BEAN_IDENTIFIER;
import static parking.ticket.system.constants.MongoConstants.MONGO_CODEC_REGISTRY_BEAN_IDENTIFIER;
import static parking.ticket.system.constants.MongoConstants.MONGO_DATABASE_BEAN_IDENTIFIER;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import parking.ticket.system.constants.MongoConstants;

import java.util.Map;

/**
 * @author anurag.y
 * @since 14/11/18.
 */
@Slf4j
@Configuration
@PropertySource(value = MongoConstants.MONGO_PROPERTIES_FILE_SOURCE)
public class MongoConfiguration {

    @Value("${mongodb.uri}")
    private String mongoClientURI;

    @Value("${mongodb.defaultDatabase}")
    private String defaultDatabase;

    @Bean(name = MONGO_CLIENT_BEAN_IDENTIFIER)
    public MongoClient getMongoClient() {
        final String mongodbUrl = mongoClientURI;
        return MongoClients.create(mongodbUrl);
    }

    @Bean(name = MONGO_CODEC_REGISTRY_BEAN_IDENTIFIER)
    public CodecRegistry getCodecRegistry() {
        return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }

    @Bean(name = MONGO_DATABASE_BEAN_IDENTIFIER)
    public MongoDatabase getMongoDatabase() {
        return getMongoClient()
                .getDatabase(defaultDatabase)
                .withCodecRegistry(getCodecRegistry());
    }

    public MongoDatabase getMongoDatabase(String databaseName) {
        return getMongoClient()
                .getDatabase(databaseName)
                .withCodecRegistry(getCodecRegistry());
    }
}