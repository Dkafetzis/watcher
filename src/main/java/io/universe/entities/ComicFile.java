package io.universe.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.io.File;
import java.util.Objects;

@Entity
public class ComicFile extends PanacheEntity {
    private String path;
    private String fileName;
    private FileType fileType;
    private boolean converted;

    public ComicFile() {}

    public ComicFile(String path, FileType fileType, String fileName, boolean converted) {
        this.path = path;
        this.fileType = fileType;
        this.fileName = fileName;
        this.converted = converted;
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

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    public String getFileExtension() {
        return switch (fileType) {
            case ZIP -> "cbz";
            case RAR -> "cbr";
            case SEVENZ -> "cb7";
            case UNKNOWN -> "unknown";
        };
    }

    public String getFullPathAndFileName() {
        return path + File.separator + fileName + "." + getFileExtension();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ComicFile comicFile = (ComicFile) o;
        return converted == comicFile.converted
                && Objects.equals(path, comicFile.path)
                && Objects.equals(fileName, comicFile.fileName)
                && fileType == comicFile.fileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, fileName, fileType, converted);
    }
}
