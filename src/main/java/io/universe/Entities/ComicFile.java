package io.universe.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;



@Entity
public class ComicFile extends PanacheEntity {
    public String path;
    public FileType fileType;

    public ComicFile(){}

    public ComicFile(String path, FileType fileType){
        this.path = path;
        this.fileType = fileType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
