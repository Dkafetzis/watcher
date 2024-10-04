package io.universe.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.File;

@ApplicationScoped
public class ScheduledTaskService {

    private static final Logger LOGGER = Logger.getLogger(ScheduledTaskService.class);
    @ConfigProperty(name = "work.directory", defaultValue = "./work")
    String workDirectory;

    @ConfigProperty(name = "library.directory", defaultValue = "./library")
    String libraryDirectory;

    @ConfigProperty(name = "watch.directory", defaultValue = "./watch")
    String watchDirectory;

    @Scheduled(every = "10s")
    public void detectFiles() {
        File directory = new File(watchDirectory);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                        z
                }
                System.out.println("Files detected and FileType entities added.");
            } else {
                System.out.println("No files detected in watch directory.");
            }
        } else {
            System.out.println("Watch directory does not exist or is not a directory.");
        }
    }
}