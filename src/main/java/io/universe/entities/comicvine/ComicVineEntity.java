package io.universe.entities.comicvine;

/**
 * Common attributes shared by most ComicVine entities.
 * Keeping fields public for simple DTO mapping as per project guidelines.
 */
public class ComicVineEntity {
    public String id; // ComicVine IDs are numeric but we use String for flexibility
    public String name;
    public String aliases; // Newline-separated list per API docs
    public String api_detail_url;
    public String site_detail_url;
    public ComicVineImage image;

    // Metadata
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
}
