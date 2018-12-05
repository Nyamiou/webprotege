package edu.stanford.bmir.protege.web.server.init;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;
import edu.stanford.bmir.protege.web.server.inject.DbUri;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 10/04/2013
 */
public class CheckMongoDBConnectionTask implements ConfigurationTask {

    private final String dbUri;

    @Inject
    public CheckMongoDBConnectionTask(@DbUri String dbUri) {
        this.dbUri = checkNotNull(dbUri);
    }

    @Override
    public void run(ServletContext servletContext) throws WebProtegeConfigurationException {
        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI(dbUri));
            mongoClient.getDatabaseNames();
            mongoClient.close();
        } catch (MongoTimeoutException e) {
            throw new WebProtegeConfigurationException(getUnknownHostErrorMessage());
        }
    }

    private String getUnknownHostErrorMessage() {
        return String.format(
                "Could not connect to database on %s.  " +
                        "Please make sure the mongod daemon is running at this address.",
                dbUri
        );
    }

}
