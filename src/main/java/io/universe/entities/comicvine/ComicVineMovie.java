package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineMovie {
    public String api_detail_url;
    public String box_office_revenue;
    public String budget;

    // Relations
    public List<ComicVineCharacter> characters;
    public List<ComicVineConcept> concepts;
    public List<ComicVineLocation> locations;
    public List<ComicVineTeam> teams;
    public List<ComicVineObject> things; // as per docs wording

    // Metadata
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
    public String distributor;
    public Boolean has_staff_review;
    public int id;
    public ComicVineImage image;

    public String name;
    public List<ComicVinePerson> producers;
    public String rating;
    public String release_date;
    public String runtime;
    public String site_detail_url;
    public List<String> studios; // not detailed in docs; keep as names
    public String total_revenue;
    public List<ComicVinePerson> writers;
}
