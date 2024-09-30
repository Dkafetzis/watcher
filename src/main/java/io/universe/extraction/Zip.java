package io.universe.extraction;

import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Zip {

    private final Logger logger = Logger.getLogger(Zip.class.getName());
    private final String outdir;
    private final String tmpdir;

    public Zip(String outdir, String tmpdir) {

        logger.info("Starting converter.Zip module...");
        this.outdir = outdir;
        this.tmpdir = tmpdir;
    }

    public void zipDir(File dir) {
        logger.info("Zipping directory: " + dir.getName());
        File[] contents = dir.listFiles();
        logger.info("Removing old file if it already exists...");
        if (!new File(outdir + File.separator + dir.getName() + ".cbz").delete()) {
            logger.info("Failed to delete file " + outdir + File.separator + dir.getName() + ".cbz");
        }
        try (ZipFile zippedFile = new ZipFile(outdir + File.separator + dir.getName() + ".cbz")) {
            logger.info("Adding new files...");
            assert contents != null;
            for (File file : contents) {
                if (file.isDirectory()) {
                    zippedFile.addFolder(file);
                } else {
                    zippedFile.addFile(file);
                }
            }
            logger.info("Directory zipped, deleting " + dir.getName());
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            logger.warning("Zipping failed, reason: " + e);
        }
    }

    public void unzipFile(File file) {
        try {
            ZipFile zf = new ZipFile(file);
            logger.info("Extracting zip file: " + file.getName());
            zf.extractAll(tmpdir + File.separator + FilenameUtils.getBaseName(file.getName()));
            zf.close();
            logger.info("Extraction completed");
        } catch (IOException e) {
            logger.warning("Unzipping failed, reason: " + e);
        }
    }
}
