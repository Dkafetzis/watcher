package io.universe.utilities.extraction;

import com.github.junrar.Junrar;
import com.github.junrar.exception.RarException;
import io.universe.services.ScheduledTaskService;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;

public class Rar {

    private static final org.jboss.logging.Logger LOGGER = Logger.getLogger(Rar.class);

    public static void extractRAR(String workDirectory, File file) {
        try {
            File dest = new File(workDirectory + File.separator + FilenameUtils.getBaseName(file.getName()));
            if (!dest.exists() && !dest.mkdir()) {
                LOGGER.info("Failed to make directory");
                return;
            }
            Junrar.extract(file, dest);
            LOGGER.info("Extraction completed");
        } catch (RarException | IOException error) {
            LOGGER.error("converter.Rar failed with error" + error);
        }
    }
}
