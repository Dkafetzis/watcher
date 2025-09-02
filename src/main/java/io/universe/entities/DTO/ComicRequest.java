package io.universe.entities.DTO;

import jakarta.ws.rs.QueryParam;

public record ComicRequest(@QueryParam("id") long id,@QueryParam("name") String name,@QueryParam("year") int year) {
}
