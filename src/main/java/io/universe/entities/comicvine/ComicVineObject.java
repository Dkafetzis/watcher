package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineObject {
    public String api_detail_url;
    public String aliases;
    public Integer count_of_issue_appearances;
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
    public ComicVineIssue first_appeared_in_issue;
    public int id;
    public ComicVineImage image;
    public List<ComicVineIssue> issue_credits;
    public List<ComicVineMovie> movies;
    public String name;
    public String site_detail_url;
    public String start_year;
    public List<ComicVineStoryArc> story_arc_credits;
    public List<ComicVineVolume> volume_credits;
}
