package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineCharacter extends ComicVineEntity {
    public String birth;
    public int count_of_issue_appearances;
    public ComicVineIssue first_appeared_in_issue;
    public int gender; // 1=Male, 2=Female, 0=Other/Unknown

    // Commonly used fields per ComicVine documentation
    public String real_name;

    // Linked resources
    public ComicVineOrigin origin;
    public ComicVinePublisher publisher;
    public List<ComicVinePower> powers;
    public List<ComicVineTeam> teams;

    // Relationships per API
    public List<ComicVineCharacter> character_enemies; // List of characters that are enemies with this character
    public List<ComicVineCharacter> character_friends; // List of characters that are friends with this character

    // Credits and appearances
    public List<ComicVinePerson> creators; // Real-life creators of the character
    public List<ComicVineIssue> issue_credits; // Issues this character appears in
    public List<ComicVineIssue> issues_died_in; // Issues this character died in
    public List<ComicVineMovie> movies; // Movies the character was in
    public List<ComicVineStoryArc> story_arc_credits; // Story arcs this character appears in
    public List<ComicVineTeam> team_enemies; // Teams that are enemies of this character
    public List<ComicVineTeam> team_friends; // Teams that are friends with this character
    public List<ComicVineVolume> volume_credits; // Volumes this character appears in
}
