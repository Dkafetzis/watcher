package io.universe.utilities.extraction;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import net.lingala.zip4j.ZipFile;
import org.jboss.logging.Logger;

public class Zip {

    private static final org.jboss.logging.Logger LOGGER = Logger.getLogger(Zip.class);
    

    public static void zipDir(String librarydir, File dir) {
        LOGGER.info("Zipping directory: " + dir.getName());
        File[] contents = dir.listFiles();
        LOGGER.info("Removing old file if it already exists...");
        if (!new File(librarydir + File.separator + dir.getName() + ".cbz").delete()) {
            LOGGER.info("Failed to delete file");
        }
        try (ZipFile zippedFile = new ZipFile(librarydir + File.separator + dir.getName() + ".cbz")) {
            LOGGER.info("Adding new files...");
            assert contents != null;
            for (File file : contents) {
                if (file.isDirectory()) {
                    zippedFile.addFolder(file);
                } else {
                    zippedFile.addFile(file);
                }
            }
            LOGGER.info("Directory zipped, deleting " + dir.getName());
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            LOGGER.error("Zipping failed, reason: {0}", e);
        }
    }

    public static void unzipFile(String workdir, File file) {
        try {
            ZipFile zf = new ZipFile(file);
            LOGGER.info("Extracting zip file: " + file.getName());
            zf.extractAll(workdir + File.separator + FilenameUtils.getBaseName(file.getName()));
            zf.close();
            LOGGER.info("Extraction completed");
        } catch (IOException e) {
            LOGGER.error("Unzipping failed, reason: " + e);
        }
    }
}
