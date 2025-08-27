package io.universe.services.metadata;

import java.util.List;
import java.util.Optional;

/**
 * Abstraction for metadata providers (Milestone 4). This keeps us flexible to add more providers later
 * while starting with ComicVine. Methods intentionally minimal for early milestones.
 */
public interface MetadataProvider {

    /**
     * Search volumes by series title and optional start year.
     * @param title series title (required)
     * @param year optional year, pass null if unknown
     * @return provider-specific volume search result DTOs in a lightweight form
     */
    List<VolumeRef> searchVolumes(String title, Integer year);

    /**
     * Fetch a single volume by provider-specific id.
     */
    Optional<VolumeRef> getVolume(String providerVolumeId);

    /**
     * Fetch a single issue by provider-specific id.
     */
    Optional<IssueRef> getIssue(String providerIssueId);

    /**
     * Light-weight representation of a Volume for selection purposes.
     */
    record VolumeRef(String id, String name, Integer startYear, String publisher) {}

    /**
     * Light-weight representation of an Issue for linking purposes.
     */
    record IssueRef(String id, String number, String title, String coverDate, String volumeId) {}
}
