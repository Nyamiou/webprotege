package edu.stanford.bmir.protege.web.server.inject;

import edu.stanford.bmir.protege.web.server.app.WebProtegeProperties;

import javax.inject.Inject;
import javax.inject.Provider;

public class DbUriProvider implements Provider<String> {

    private WebProtegeProperties webProtegeProperties;

    @Inject
    public DbUriProvider(WebProtegeProperties webProtegeProperties) {
        this.webProtegeProperties = webProtegeProperties;
    }

    @Override
    public String get() {
        return webProtegeProperties.getDBUri().get();
    }
}
