package edu.stanford.bmir.protege.web.server.inject;

import com.mongodb.MongoClient;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import com.mongodb.MongoClientURI;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 1 Oct 2016
 */
public class MongoClientProvider implements Provider<MongoClient> {

    @Nonnull
    private final String uri;

    @Inject
    public MongoClientProvider(@DbUri String dbUri) {
        this.uri = checkNotNull(dbUri);
    }

    @Override
    public MongoClient get() {
        return new MongoClient(new MongoClientURI(uri));
    }
}
