package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.service.*;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public enum Location {

    CAREERS("Carreiras", new CareerSyncServiceImpl()),

    HEALTH_FACILITIES("Unidades Sanitárias", new HealthFacilitySyncServiceImpl()),

    FORMS("Formulários", new FormQuestionSyncServiceImpl()),

    SETTINGS("Configurações", new SettingSyncServiceImpl());

    private String name;
    private SyncService syncService;

    Location(String name, SyncService syncService) {
        this.name = name;
        this.syncService = syncService;
    }

    public String getName() {
        return name;
    }

    public SyncService getSyncService() {
        return syncService;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
