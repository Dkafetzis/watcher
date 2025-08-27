package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineIssue extends ComicVineEntity {
    public String issue_number;
    public String cover_date; // YYYY-MM-DD
    public String store_date; // first sold date
    public ComicVineVolume volume;

    public Boolean has_staff_review;

    // Credits and appearances
    public List<ComicVineCharacter> character_credits;
    public List<ComicVineCharacter> characters_died_in;
    public List<ComicVineConcept> concept_credits;
    public List<ComicVineLocation> location_credits;
    public List<ComicVineObject> object_credits;
    public List<ComicVinePerson> person_credits;
    public List<ComicVineStoryArc> story_arc_credits;
    public List<ComicVineTeam> team_credits;
    public List<ComicVineTeam> disbanded_teams; // as per docs
    public List<ComicVineTeam> teams_disbanded_in; // alternate naming observed in docs

    // First appearances
    public List<ComicVineCharacter> first_appearance_characters;
    public List<ComicVineConcept> first_appearance_concepts;
    public List<ComicVineLocation> first_appearance_locations;
    public List<ComicVineObject> first_appearance_objects;
    public List<ComicVineStoryArc> first_appearance_storyarcs;
    public List<ComicVineTeam> first_appearance_teams;
}
