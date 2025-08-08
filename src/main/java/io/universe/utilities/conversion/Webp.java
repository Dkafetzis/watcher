package io.universe.utilities.conversion;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;

public class Webp {

    private static final org.jboss.logging.Logger LOGGER = Logger.getLogger(Webp.class);

    private static int quality = 80;

    private static final Pattern pattern = Pattern.compile("jpe?g|png|jpeg");

    private static void convertToWebP(File imageFile, File targetFile) {
        Process process;
        try {
            process = new ProcessBuilder(
                            "cwebp",
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
                LOGGER.info("Failed to delete file " + imageFile.getName());
            }
        } catch (Exception error) {
            LOGGER.error("Failed to start process" + error);
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

    public static void convertDir(File dir) {
        LOGGER.info("Starting conversion of directory: " + dir);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                convertDir(file);
            } else {
                Matcher matcher = pattern.matcher(FilenameUtils.getExtension(file.getName()));
                if (matcher.find()) {
                    LOGGER.info("Converting file: " + file.getName());
                    convertToWebP(
                            file,
                            new File(file.getParent() + File.separator + FilenameUtils.getBaseName(file.getName())
                                    + ".webp"));
                } else {
                    LOGGER.info(file.getName() + " Is not an image file, skipping.");
                }
            }
        }
        LOGGER.info("Directory conversion complete.");
    }
}
