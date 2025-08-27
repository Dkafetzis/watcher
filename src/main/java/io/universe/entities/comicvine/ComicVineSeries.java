package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineSeries extends ComicVineEntity {

    // Relations
    public List<ComicVineCharacter> character_credits;
    public List<ComicVineLocation> location_credits;
    public ComicVineEpisode first_episode;
    public ComicVineEpisode last_episode;
    public ComicVinePublisher publisher;

    // Metadata
    public Integer count_of_episodes;
    public String start_year;
}
