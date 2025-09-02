package io.universe.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Issue extends PanacheEntityBase {

    @Id
    private long id;

    public double getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Issue issue)) return false;

        return id == issue.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
