package io.universe.conversion;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Webp {

    private final Logger logger = Logger.getLogger(Webp.class.getName());
    private static int quality;
    private final Pattern pattern = Pattern.compile("jpe?g|png|jpeg");
    private static final String CWEBP_BIN_PATH = "." + File.separator + "bin" + File.separator
            + (System.getProperty("os.name").contains("indows") ? "cwebp.exe" : "cwebp");

    public Webp(int quality) {
        logger.info("Starting converter.Webp module...");
        logger.info(System.getProperty("os.name"));
        Webp.quality = quality;
    }

    public void convertToWebP(File imageFile, File targetFile) {
        Process process;
        try {
            process = new ProcessBuilder(
                            CWEBP_BIN_PATH,
                            "-q",
                            String.valueOf(quality),
                            imageFile.getAbsolutePath(),
                            "-o",
                            targetFile.getAbsolutePath())
                    .start();
            process.waitFor(10, TimeUnit.SECONDS);
            if (process.exitValue() == 0) {
                // Success
                printProcessOutput(process.getInputStream(), System.out);
            } else {
                printProcessOutput(process.getErrorStream(), System.err);
            }
            if (!imageFile.delete()) {
                logger.info("Failed to delete file " + imageFile.getName());
            }
        } catch (Exception error) {
            logger.warning("Failed to start process" + error);
        }
    }

    private static void printProcessOutput(InputStream inputStream, PrintStream output) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(isr)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.println(line);
            }
        }
    }

    public void convertDir(File dir) throws IOException {
        logger.info("Starting conversion of directory: " + dir);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                convertDir(file);
            } else {
                Matcher matcher = pattern.matcher(FilenameUtils.getExtension(file.getName()));
                if (matcher.find()) {
                    logger.info("Converting file: " + file.getName());
                    convertToWebP(
                            file,
                            new File(file.getParent() + File.separator + FilenameUtils.getBaseName(file.getName())
                                    + ".webp"));
                } else {
                    logger.info(file.getName() + " Is not an image file, skipping.");
                }
            }
        }
        logger.info("Directory conversion complete.");
    }
}
