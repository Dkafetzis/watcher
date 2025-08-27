package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineLocation extends ComicVineEntity {
    public Integer count_of_issue_appearances;
    public ComicVineIssue first_appeared_in_issue;
    public List<ComicVineIssue> issue_credits;
    public List<ComicVineMovie> movies;
    public String start_year;
    public List<ComicVineStoryArc> story_arc_credits;
    public List<ComicVineVolume> volume_credits;
}
