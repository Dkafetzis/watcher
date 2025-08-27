package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineStoryArc extends ComicVineEntity {

    // Story arc specific
    public Integer count_of_issue_appearances;
    public ComicVineIssue first_appeared_in_issue;
    public ComicVinePublisher publisher;

    public List<ComicVineIssue> issues;
    public List<ComicVineMovie> movies;
}
