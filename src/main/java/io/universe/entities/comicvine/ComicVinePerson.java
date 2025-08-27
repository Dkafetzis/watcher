package io.universe.entities.comicvine;

import java.util.List;

public class ComicVinePerson extends ComicVineEntity {
    public String birth;

    // Additional fields per API
    public Integer count_of_issue_appearances;
    public String country;
    public List<ComicVineCharacter> created_characters;
    public String death;
    public String email;
    public int gender; // 1=Male, 2=Female, 0=Other/Unknown
    public String hometown;
    public List<ComicVineIssue> issue_credits;
    public List<ComicVineStoryArc> story_arc_credits;
    public List<ComicVineVolume> volume_credits;
    public String website;
}
