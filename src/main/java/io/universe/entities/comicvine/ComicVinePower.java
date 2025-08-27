package io.universe.entities.comicvine;

import java.util.List;

public class ComicVinePower {
    public String api_detail_url;
    public String aliases;
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
    public int id;
    public String name;
    public String site_detail_url;
    public ComicVineImage image;

    // Relations per API
    public List<ComicVineCharacter> characters;
}
