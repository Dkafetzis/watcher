package io.universe.entities.comicvine;

import java.util.List;

public class ComicVinePublisher extends ComicVineEntity {
    // Address fields
    public String location_address;
    public String location_city;
    public String location_state;

    // Relations
    public List<ComicVineCharacter> characters;
    public List<ComicVineStoryArc> story_arcs;
    public List<ComicVineTeam> teams;
    public List<ComicVineVolume> volumes;
}
