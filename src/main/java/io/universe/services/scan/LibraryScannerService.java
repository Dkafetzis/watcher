package io.universe.services.scan;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses comic file names to extract metadata hints (Milestone 1 step 1).
 * Examples handled:
 * - Title 2021 #05 (of 6)
 * - Title v2 003
 * - Title 003A
 * - Title (2020) #12B
 * - Title #7
 */
@ApplicationScoped
public class LibraryScannerService {

    private static final Logger LOGGER = Logger.getLogger(LibraryScannerService.class);

    @ConfigProperty(name = "library.scanner.strict", defaultValue = "false")
    boolean strict;

    // Precompile patterns for common cases
    private static final Pattern PATTERN_YEAR_HASH = Pattern.compile("^(.*?)\\s*\\(?(\\d{4})\\)?\\s*#?\\s*(\\d{1,4})([A-Za-z])?.*$");
    private static final Pattern PATTERN_VOLUMED = Pattern.compile("^(.*?)\\s*v(\\d+)\\s*(\\d{1,4})([A-Za-z])?.*$", Pattern.CASE_INSENSITIVE);

    public ParsedInfo scan(String baseFileName) {
        String name = baseFileName;

        // Try "Title (Year) #NumberVariant" or "Title Year #NumberVariant" or "Title #NumberVariant"
        Matcher m = PATTERN_YEAR_HASH.matcher(name);
        if (m.matches()) {
            String series = clean(m.group(1));
            Integer year = parseIntSafe(m.group(2));
            String issueStr = m.group(3);
            String variant = m.group(4);
            Integer issue = parseIntSafe(issueStr);
            if (issue != null) {
                return new ParsedInfo(series, year, issue, Optional.ofNullable(variant).map(String::trim).orElse(null), null);
            }
        }

        // Try "Title v2 003A"
        m = PATTERN_VOLUMED.matcher(name);
        if (m.matches()) {
            String series = clean(m.group(1));
            Integer vol = parseIntSafe(m.group(2));
            Integer issue = parseIntSafe(m.group(3));
            String variant = m.group(4);
            return new ParsedInfo(series, null, issue, Optional.ofNullable(variant).map(String::trim).orElse(null), vol);
        }

        if (strict) {
            throw new IllegalArgumentException("Unrecognized filename pattern: " + baseFileName);
        }
        LOGGER.debugf("Filename did not match known patterns (lenient): %s", baseFileName);
        return new ParsedInfo(clean(name), null, null, null, null);
    }

    private static String clean(String s) {
        if (s == null) return null;
        // Trim and collapse multiple spaces
        return s.replaceAll("[._]", " ").replaceAll("\\s+", " ").trim();
    }

    private static Integer parseIntSafe(String s) {
        try {
            if (s == null) return null;
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /** Parsed result for a file name. */
    public record ParsedInfo(String series, Integer year, Integer issue, String variant, Integer volume) {}
}
