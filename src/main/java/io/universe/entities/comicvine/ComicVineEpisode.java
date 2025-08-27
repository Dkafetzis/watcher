package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineEpisode {
    public String api_detail_url;
    public String aliases;

    // Credits and relations
    public List<ComicVineCharacter> character_credits;
    public List<ComicVineCharacter> characters_died_in;
    public List<ComicVineConcept> concept_credits;
    public List<ComicVineCharacter> first_appearance_characters;
    public List<ComicVineConcept> first_appearance_concepts;
    public List<ComicVineLocation> first_appearance_locations;
    public List<ComicVineObject> first_appearance_objects;
    public List<ComicVineStoryArc> first_appearance_storyarcs;
    public List<ComicVineTeam> first_appearance_teams;
    public List<ComicVineLocation> location_credits;
    public List<ComicVineObject> object_credits;
    public List<ComicVinePerson> person_credits;
    public List<ComicVineStoryArc> story_arc_credits;
    public List<ComicVineTeam> team_credits;

    // Metadata
    public String air_date;
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
    public Boolean has_staff_review;
    public int id;
    public ComicVineImage image;

    public String episode_number; // number within series
    public String name;
    public String site_detail_url;

    public ComicVineSeries series;
}
