package io.universe.entities.DTO;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public record SearchRequest(@QueryParam("query") String query,@DefaultValue("0") @QueryParam("offset") Integer offset) {
}
