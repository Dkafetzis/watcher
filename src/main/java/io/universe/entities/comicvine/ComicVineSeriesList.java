package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineSeriesList {
    public String api_detail_url;
    public String aliases;

    // Relations
    public ComicVineEpisode first_episode;
    public ComicVineEpisode last_episode;
    public ComicVinePublisher publisher;

    // Metadata
    public Integer count_of_episodes;
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
    public int id;
    public ComicVineImage image;

    public String name;
    public String site_detail_url;
    public String start_year;
}
