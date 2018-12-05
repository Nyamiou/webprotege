package edu.stanford.bmir.protege.web.server.inject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import dagger.Module;
import dagger.Provides;
import edu.stanford.bmir.protege.web.server.persistence.DbName;
import edu.stanford.bmir.protege.web.server.persistence.MorphiaDatastoreProvider;
import edu.stanford.bmir.protege.web.server.persistence.MorphiaProvider;
import edu.stanford.bmir.protege.web.shared.inject.ApplicationSingleton;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 04/03/15
 */
@Module
public class DbModule {

    @Provides
    @DbUri
    public String provideDbUri(DbUriProvider dbUriProvider) {
        return dbUriProvider.get();
    }

    @Provides
    @Singleton
    public MongoClient provideMongoClient(MongoClientProvider provider) {
        return provider.get();
    }

    @Provides
    @ApplicationSingleton
    public MongoDatabase provideMongoDatabase(MongoDatabaseProvider provider) {
        return provider.get();
    }

    @Provides
    public Morphia providesMorphia(MorphiaProvider provider) {
        return provider.get();
    }

    @Provides
    public Datastore provideDatastore(MorphiaDatastoreProvider provider) {
        return provider.get();
    }

    @Provides
    @DbName
    public String provideDbName() {
        return "webprotege";
    }
}
