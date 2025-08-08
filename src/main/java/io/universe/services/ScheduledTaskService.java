package io.universe.services;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.universe.entities.ComicFile;
import io.universe.entities.FileType;
import io.universe.utilities.conversion.Webp;
import io.universe.utilities.extraction.Rar;
import io.universe.utilities.extraction.Zip;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduledTaskService {

    private static final Logger LOGGER = Logger.getLogger(ScheduledTaskService.class);


    @ConfigProperty(name = "library.directory", defaultValue = "./library")
    String libraryDirectory;

    @ConfigProperty(name = "work.directory", defaultValue = "./work")
    String workDirectory;

    @ConfigProperty(name = "watch.directory", defaultValue = "./watch")
    String watchDirectory;

    @Scheduled(every = "1m", delayed = "4m", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    @Transactional
    public void converRarToCbz() {
        List<ComicFile> files = ComicFile.list("fileType", FileType.RAR);
        for (ComicFile file : files) {
            File oldfile = new File(file.getFullPathAndFileName());
            Rar.extractRAR(workDirectory, oldfile);
            Zip.zipDir(libraryDirectory, new File(workDirectory + File.separator + file.getFileName()));
            oldfile.delete();
            file.setFileType(FileType.ZIP);
            file.setPath(libraryDirectory);
            file.persist();
        }
    }

    @Scheduled(every = "1m", delayed = "3m", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    @Transactional
    public void convertImagesToWebp() {
        List<ComicFile> files = ComicFile.list("fileType = ?1 AND converted = ?2", FileType.ZIP, false);
        for (ComicFile file : files) {
            File oldfile = new File(file.getFullPathAndFileName());
            Zip.unzipFile(workDirectory, oldfile);
            Webp.convertDir(new File(workDirectory + File.separator + file.getFileName()));
            Zip.zipDir(libraryDirectory, new File(workDirectory + File.separator + file.getFileName()));
            file.setConverted(true);
            file.persist();
        }
    }

    @Scheduled(every = "1m")
    @Transactional
    public void detectFiles() {
        File directory = new File(watchDirectory);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                LOGGER.info("Files detected adding to library.");
                for (File file : files){
                    FileType fileType = null;
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".zip") || fileName.endsWith(".cbz")) {
                        fileType = FileType.ZIP;
                    } else if (fileName.endsWith(".rar") || fileName.endsWith(".cbr")) {
                        fileType = FileType.RAR;
                    } else {
                        LOGGER.warn("Unsupported file type: " + fileName);
                    }
                    ComicFile comicFile = new ComicFile(file.getParent(), fileType, file.getName().substring(0, file.getName().lastIndexOf(".")), false);
                    // Move file to library directory
                    File destinationFile = new File(libraryDirectory, comicFile.getFileName() + "." + comicFile.getFileExtension());
                    if (file.renameTo(destinationFile)) {
                        comicFile.setPath(destinationFile.getParent());
                        LOGGER.info("Moved file to library: " + destinationFile.getPath());
                    } else {
                        LOGGER.error("Failed to move file to library: " + file.getName());
                    }
                    comicFile.persist();
                    LOGGER.info("Created and persisted ComicFile: " + comicFile.fileName);
                }
            } else {
                LOGGER.info("No files detected in watch directory.");
            }
        } else {
            LOGGER.error("Watch directory does not exist or is not a directory.");
        }
    }
}