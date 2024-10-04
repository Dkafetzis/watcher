package io.universe.scheduler;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkus.scheduler.Scheduled;
import io.universe.Entities.ComicFile;
import io.universe.Entities.FileType;
import io.universe.extraction.ExtractionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
@ApplicationScoped
public class ScheduledTaskService {

    private static final Logger LOGGER = Logger.getLogger(ScheduledTaskService.class);

    @ConfigProperty(name = "library.directory", defaultValue = "./library")
    String libraryDirectory;

    @ConfigProperty(name = "watch.directory", defaultValue = "./watch")
    String watchDirectory;

    @Inject
    ExtractionService extractionService;

    @Scheduled(every = "1m")
    @Transactional
    public void convertRarToCbz() {
        List<ComicFile> allfiles = ComicFile.listAll();
        List<ComicFile> files = ComicFile.list("fileType", FileType.RAR);
        for (ComicFile file : files) {
            CompletableFuture.runAsync(() -> {
                extractionService.convertRarToCbz(file);
            });
        }
    }

    @Scheduled(every = "1m")
    @Transactional
    public void detectFiles() {
        File directory = new File(watchDirectory);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        CompletableFuture.runAsync(() -> {
                            FileType fileType;
                            String fileName = file.getName().toLowerCase();
                            if (fileName.endsWith(".zip") || fileName.endsWith(".cbz")) {
                                fileType = FileType.ZIP;
                            } else if (fileName.endsWith(".rar") || fileName.endsWith(".cbr")) {
                                fileType = FileType.RAR;
                            } else {
                                LOGGER.warn("Unsupported file type: " + fileName);
                                return;
                            }
                            ComicFile comicFile = new ComicFile(file.getPath(), fileType, file.getName());
                            // Move file to library directory
                            File destinationFile = new File(libraryDirectory, file.getName());
                            if (file.renameTo(destinationFile)) {
                                comicFile.setPath(destinationFile.getPath());
                                LOGGER.info("Moved file to library: " + destinationFile.getPath());
                            } else {
                                LOGGER.error("Failed to move file to library: " + file.getName());
                                return;
                            }
                            comicFile.persist();
                            LOGGER.info("Created and persisted ComicFile: ");
                        });
                    }
                }
                LOGGER.info("Files detected and FileType entities added.");
            } else {
                LOGGER.info("No files detected in watch directory.");
            }
        } else {
            LOGGER.error("Watch directory does not exist or is not a directory.");
        }
    }
}