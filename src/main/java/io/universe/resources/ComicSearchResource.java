package io.universe.resources;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.universe.entities.DTO.SearchRequest;
import io.universe.entities.comicvine.ComicVineEntity;
import io.universe.entities.comicvine.ComicVineResponse;
import io.universe.entities.comicvine.ComicVineVolume;
import io.universe.services.ComicVineService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("/api/search")
public class ComicSearchResource {

    @CheckedTemplate
    public static class ComicSearchTemplates {
        public static native TemplateInstance listResults(List<ComicVineVolume> volumeList, int offset, int pageCount, int totalCount, String query, int limit, int currentPage, int totalPages, java.util.List<java.lang.Integer> pageWindow, int lastOffset, java.util.List<java.lang.Integer> pageOffsets);
    }

    @Inject
    ComicVineService comicVineService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ComicVineResponse<ComicVineEntity> search(@QueryParam("query") String query) {
        return comicVineService.search(query);
    }

    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Blocking
    public TemplateInstance searchForm(@BeanParam SearchRequest request) {
        ComicVineResponse<ComicVineVolume> re = comicVineService.searchVolumes(request.query(), request.offset());
        int limit = re.limit > 0 ? re.limit : 100;
        int offset = re.offset;
        int totalCount = re.number_of_total_results;
        int currentPage = offset / limit + 1;
        int totalPages = (totalCount + limit - 1) / limit;
        java.util.List<java.lang.Integer> pageWindow = new java.util.ArrayList<>();
        int window = 5; // number of pages around current
        int start = Math.max(1, currentPage - window);
        int end = Math.min(totalPages, currentPage + window);
        for (int p = start; p <= end; p++) {
            pageWindow.add(p);
        }
        int lastOffset = totalPages > 0 ? (totalPages - 1) * limit : 0;
        java.util.List<java.lang.Integer> pageOffsets = new java.util.ArrayList<>();
        for (Integer p : pageWindow) {
            pageOffsets.add((p - 1) * limit);
        }
        return ComicSearchTemplates.listResults(re.results, offset, re.number_of_page_results, totalCount, request.query(), limit, currentPage, totalPages, pageWindow, lastOffset, pageOffsets);
    }
}
