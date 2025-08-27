package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineVolume extends ComicVineEntity {
    public String start_year;

    // Volume-specific
    public Integer count_of_issues;
    public ComicVineIssue first_issue;
    public ComicVineIssue last_issue;
    public ComicVinePublisher publisher;

    // Credits
    public List<ComicVineCharacter> character_credits;
    public List<ComicVineConcept> concept_credits;
    public List<ComicVineLocation> location_credits;
    public List<ComicVineObject> object_credits;
    public List<ComicVinePerson> person_credits;
    public List<ComicVineTeam> team_credits;
}
