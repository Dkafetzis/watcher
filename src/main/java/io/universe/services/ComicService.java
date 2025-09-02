package io.universe.services;

import io.universe.entities.Comic;
import io.universe.entities.DTO.ComicRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ComicService {

    @Transactional
    public void createComic(ComicRequest request) {
        Comic comic = new Comic(request.id(), request.name(), request.year());
        comic.persist();
    }
}
