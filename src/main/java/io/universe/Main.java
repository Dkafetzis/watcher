package io.universe;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;

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

        @ConfigProperty(name = "work.directory", defaultValue = "./work")
        String workDirectory;

        @ConfigProperty(name = "library.directory", defaultValue = "./library")
        String libraryDirectory;

        @ConfigProperty(name = "watch.directory", defaultValue = "./watch")
        String watchDirectory;

        @Override
        public int run(String... args) throws Exception {

            File file = new File(workDirectory);
            if (file.exists() && file.isDirectory()) {
                System.out.println("Using work directory: " + workDirectory);
            } else {
                System.out.println("Invalid work directory: " + workDirectory);
                Quarkus.asyncExit();
                return 1;
            }

            File libFile = new File(libraryDirectory);
            if (libFile.exists() && libFile.isDirectory()) {
                System.out.println("Using library directory: " + libraryDirectory);
            } else {
                System.out.println("Invalid library directory: " + libraryDirectory);
                Quarkus.asyncExit();
                return 1;
            }
    
            File watchFile = new File(watchDirectory);
            if (watchFile.exists() && watchFile.isDirectory()) {
                System.out.println("Using watch directory: " + watchDirectory);
            } else {
                System.out.println("Invalid watch directory: " + watchDirectory);
                Quarkus.asyncExit();
                return 1;
            }
            Quarkus.waitForExit();
            return 0;
        }
    }
}
