package io.universe.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity
public class Comic extends PanacheEntityBase {

    @Id
    private long id;

    private String name;
    private String description;
    private String image;
    private int year;
    @OneToMany(mappedBy = "id")
    private List<Issue> issues;

    public Comic(long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Comic() {}

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Comic comic)) return false;

        return id == comic.id && year == comic.year && name.equals(comic.name) && Objects.equals(description, comic.description) && image.equals(comic.image) && Objects.equals(issues, comic.issues);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + image.hashCode();
        result = 31 * result + year;
        result = 31 * result + Objects.hashCode(issues);
        return result;
    }
}
