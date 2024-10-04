package io.universe.extraction;

import java.io.File;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.universe.Entities.ComicFile;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExtractionService {

    private static final Logger LOGGER = Logger.getLogger(ExtractionService.class.getName());

    @ConfigProperty(name = "work.directory", defaultValue = "./work")
    String workDirectory;

    @ConfigProperty(name = "library.directory", defaultValue = "./library")
    String libraryDirectory;


    public void convertRarToCbz(ComicFile file) {
        LOGGER.info("Converting RAR to CBZ: " + file.getPath());
        var rarFile = new File(file.getPath());
    }
    
}
