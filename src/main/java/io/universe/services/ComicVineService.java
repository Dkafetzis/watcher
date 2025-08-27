package io.universe.services;

import io.universe.entities.comicvine.ComicVineEntity;
import io.universe.entities.comicvine.ComicVineIssue;
import io.universe.entities.comicvine.ComicVineResponse;
import io.universe.entities.comicvine.ComicVineVolume;
import io.universe.integrations.comicvine.ComicVineClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ComicVineService {

    private static final Logger LOGGER = Logger.getLogger(ComicVineService.class);

    @Inject
    @RestClient
    ComicVineClient client;

    @ConfigProperty(name = "comicvine.apikey", defaultValue = "")
    String apiKey;

    public boolean hasApiKey() {
        return apiKey != null && !apiKey.isBlank();
    }

    public ComicVineResponse<ComicVineIssue> getIssues() {
        if (!hasApiKey()) {
            LOGGER.warn("comicvine.apikey is not set; skipping ComicVine issues fetch.");
            return null;
        }
        return client.getIssues();
    }

    public ComicVineResponse<ComicVineEntity> search(String query) {
        if (!hasApiKey()) {
            LOGGER.warn("comicvine.apikey is not set; skipping ComicVine search.");
            return null;
        }
        return client.search(query);
    }

    public ComicVineResponse<ComicVineVolume> searchVolumes(String query, int offset) {
        if (!hasApiKey()) {
            LOGGER.warn("comicvine.apikey is not set; skipping ComicVine search.");
            return null;
        }
        return client.searchVolumes("name:" + query, offset);
    }

    // TODO(mylar3-parity): Add search endpoints (volumes, series) and a thin provider abstraction
    // that supports searchVolumes(title, year), getVolume(cvid), getIssue(cvid) with caching and
    // simple rate limiting (see docs/proposals/mylar3-feature-roadmap.md Milestones 1 & 4).
}
