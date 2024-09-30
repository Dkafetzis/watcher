package io.universe.extraction;

import com.github.junrar.Junrar;
import com.github.junrar.exception.RarException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Rar {

    private final Logger logger = Logger.getLogger(Rar.class.getName());
    private final String tmpdir;

    public Rar(String tmpdir) {
        logger.info("Starting converter.Rar module...");
        this.tmpdir = tmpdir;
    }

    public void extractRAR(File file) {
        try {
            File dest = new File(tmpdir + File.separator + FilenameUtils.getBaseName(file.getName()));
            if (!dest.mkdir()) {
                logger.info("Failed to make directory");
                return;
            }
            Junrar.extract(file, dest);
            logger.info("Extraction completed");
        } catch (RarException | IOException error) {
            logger.warning("converter.Rar failed with error" + error);
        }
    }
}
