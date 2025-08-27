package io.universe.services.metadata;

import io.universe.entities.comicvine.ComicVineIssue;
import io.universe.entities.comicvine.ComicVineResponse;
import io.universe.services.ComicVineService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import org.jboss.logging.Logger;

/**
 * ComicVine-backed provider implementation. At this stage, we only have an issues fetch stub in ComicVineService.
 * We provide minimal, placeholder implementations to exercise the abstraction and enable future enrichment wiring.
 */
@ApplicationScoped
public class ComicVineProvider implements MetadataProvider {

    private static final Logger LOGGER = Logger.getLogger(ComicVineProvider.class);

    @Inject
    ComicVineService comicVineService;

    @Override
    public List<VolumeRef> searchVolumes(String title, Integer year) {
        // TODO: Wire to ComicVineClient volumes search endpoint when available.
        // For now, return empty list to avoid misleading matches.
        LOGGER.debugf("searchVolumes(title=%s, year=%s) not implemented yet, returning empty list", title, year);
        return List.of();
    }

    @Override
    public Optional<VolumeRef> getVolume(String providerVolumeId) {
        // TODO: implement via comic vine volume endpoint.
        return Optional.empty();
    }

    @Override
    public Optional<IssueRef> getIssue(String providerIssueId) {
        // As a temporary placeholder, we can opportunistically scan the last issues fetch (if any) to map.
        ComicVineResponse<ComicVineIssue> resp = comicVineService.getIssues();
        if (resp == null || resp.results == null) {
            return Optional.empty();
        }
        return resp.results.stream()
                .filter(i -> providerIssueId.equals(i.id))
                .findFirst()
                .map(i -> new IssueRef(i.id, i.issue_number, i.name, i.cover_date,
                        (i.volume != null) ? String.valueOf(i.volume.id) : null));
    }
}
