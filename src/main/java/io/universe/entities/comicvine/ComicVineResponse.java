package io.universe.entities.comicvine;

import java.util.List;

public class ComicVineResponse<T> {
    public int status_code;
    public String error;
    public int number_of_total_results;
    public int number_of_page_results;
    public int limit;
    public int offset;
    public List<T> results;
}
