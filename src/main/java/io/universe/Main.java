package io.universe;

import java.io.File;

import io.universe.services.ScheduledTaskService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.jboss.logging.Logger;

/**
 * The Main class serves as the entry point for the Quarkus application.
 * It contains an inner class MyApp that implements the QuarkusApplication interface.
 * The application reads configuration properties to determine the directories
 * for work and library. If the specified directories exist and are valid,
 * it prints their paths; otherwise, it prints an error message.
 */
@QuarkusMain
public class Main {
    public static void main(String... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {

        private static final Logger LOGGER = Logger.getLogger(ScheduledTaskService.class);

        @ConfigProperty(name = "work.directory", defaultValue = "/work")
        String workDirectory;

        @ConfigProperty(name = "library.directory", defaultValue = "/library")
        String libraryDirectory;

        @ConfigProperty(name = "watch.directory", defaultValue = "/watch")
        String watchDirectory;

        @Override
        public int run(String... args) throws Exception {

            File workFile = new File(workDirectory);
            if (!workFile.exists()) {
                if (workFile.mkdirs()) {
                    LOGGER.info("Created work directory: " + workDirectory);
                } else {
                    LOGGER.info("Failed to create work directory: " + workDirectory);
                    Quarkus.asyncExit();
                    return 1;
                }
            } else if (!workFile.isDirectory()) {
                LOGGER.info("Work directory path is not a directory: " + workDirectory);
                Quarkus.asyncExit();
                return 1;
            } else {
                LOGGER.info("Using existing work directory: " + workDirectory);
            }

            File libFile = new File(libraryDirectory);
            if (!libFile.exists()) {
                if (libFile.mkdirs()) {
                    LOGGER.info("Created library directory: " + libraryDirectory);
                } else {
                    LOGGER.info("Failed to create library directory: " + libraryDirectory);
                    Quarkus.asyncExit();
                    return 1;
                }
            } else if (!libFile.isDirectory()) {
                LOGGER.info("Library directory path is not a directory: " + libraryDirectory);
                Quarkus.asyncExit();
                return 1;
            } else {
                LOGGER.info("Using existing library directory: " + libraryDirectory);
            }

            File watchFile = new File(watchDirectory);
            if (!watchFile.exists()) {
                if (watchFile.mkdirs()) {
                    LOGGER.info("Created watch directory: " + watchDirectory);
                } else {
                    LOGGER.info("Failed to create watch directory: " + watchDirectory);
                    Quarkus.asyncExit();
                    return 1;
                }
            } else if (!watchFile.isDirectory()) {
                LOGGER.info("Watch directory path is not a directory: " + watchDirectory);
                Quarkus.asyncExit();
                return 1;
            } else {
                LOGGER.info("Using existing watch directory: " + watchDirectory);
            }
            Quarkus.waitForExit();
            return 0;
        }
    }
}
