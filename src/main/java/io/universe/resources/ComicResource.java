package io.universe.resources;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.universe.entities.Comic;
import io.universe.entities.DTO.ComicRequest;
import io.universe.services.ComicService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@ApplicationScoped
public class ComicResource {

    @CheckedTemplate
    public static class ComicTemplates {
        public static native TemplateInstance resultButton(String result);
        public static native TemplateInstance comicTable(List<Comic> comicList);
    }

    @Inject
    ComicService comicService;

    @GET
    @Path("comics")
    public TemplateInstance getComics() {
        return ComicTemplates.comicTable(Comic.listAll());
    }

    @POST
    @Blocking
    @Path("api/comic")
    public TemplateInstance createComic(ComicRequest request) {
        comicService.createComic(request);
        return ComicTemplates.resultButton("OK");
    }

    @TemplateExtension(namespace = "comic")
    static boolean isAdded(long id){
        Comic comic = Comic.findById(id);
        return comic != null;
    }
}
