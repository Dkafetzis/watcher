package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineTeam extends ComicVineEntity {

    // Team-specific fields
    public Integer count_of_issue_appearances;
    public Integer count_of_team_members;
    public ComicVineIssue first_appeared_in_issue;
    public ComicVinePublisher publisher;

    // Relationships
    public List<ComicVineCharacter> character_enemies;
    public List<ComicVineCharacter> character_friends;
    public List<ComicVineCharacter> characters;

    // Credits and appearances
    public List<ComicVineIssue> issue_credits;
    public List<ComicVineIssue> issues_disbanded_in;
    public List<ComicVineIssue> disbanded_in_issues; // sometimes provided under this key
    public List<ComicVineStoryArc> story_arc_credits;
    public List<ComicVineVolume> volume_credits;
    public List<ComicVineMovie> movies;
}
