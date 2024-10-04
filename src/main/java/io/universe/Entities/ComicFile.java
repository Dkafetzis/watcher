package io.universe.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;



@Entity
public class ComicFile extends PanacheEntity {
    public String path;
    public String fileName;
    public FileType fileType;

    public ComicFile(){}

    public ComicFile(String path, FileType fileType, String fileName){
        this.path = path;
        this.fileType = fileType;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
