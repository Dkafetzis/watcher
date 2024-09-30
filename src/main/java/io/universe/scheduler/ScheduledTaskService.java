package io.universe.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ScheduledTaskService {

    @ConfigProperty(name = "work.directory", defaultValue = "./work")
    String workDirectory;

    @ConfigProperty(name = "library.directory", defaultValue = "./library")
    String libraryDirectory;

    @Scheduled(every = "10s")
    public void detectFiles(){
    }
}