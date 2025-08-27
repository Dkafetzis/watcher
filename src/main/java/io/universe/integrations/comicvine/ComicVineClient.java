package io.universe.integrations.comicvine;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.universe.entities.comicvine.ComicVineEntity;
import io.universe.entities.comicvine.ComicVineIssue;
import io.universe.entities.comicvine.ComicVineResponse;
import io.universe.entities.comicvine.ComicVineVolume;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "comicvine")
@Path("/api")
@ClientQueryParam(name = "api_key", value = "${comicvine.apikey}")
@ClientQueryParam(name = "format", value = "json")
public interface ComicVineClient {

    // ComicVine search endpoint. Callers should pass resources="issue" and format="json".
    @GET
    @Path("/issues")
    ComicVineResponse<ComicVineIssue> getIssues();

    @GET
    @Path("/volumes")
    ComicVineResponse<ComicVineVolume> getVolumes();

    @GET
    @Path("/search")
    ComicVineResponse<ComicVineEntity> search(@QueryParam("query") String query);

    @GET
    @Path("/volumes")
    ComicVineResponse<ComicVineVolume> searchVolumes(@QueryParam("filter") String filter, @QueryParam("offset") int offset);


}
