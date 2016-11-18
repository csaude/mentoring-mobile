package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.service.FormsSyncServiceimpl;
import mz.org.fgh.mentoring.service.ProgrammaticAreaSyncServiceImpl;
import mz.org.fgh.mentoring.service.CareerSyncServiceImpl;
import mz.org.fgh.mentoring.service.HealthFacilitySyncServiceImpl;
import mz.org.fgh.mentoring.service.SyncService;
import mz.org.fgh.mentoring.service.TutoredSyncServiceImpl;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public enum Location {

    CAREERS("Carreiras", new CareerSyncServiceImpl()),

    HEALTH_FACILITIES("Unidades Sanitárias", new HealthFacilitySyncServiceImpl()),

    PROGRAMATIC_AREAS("Areas Programáticas", new ProgrammaticAreaSyncServiceImpl()),

    FORMS("Formulários", new FormsSyncServiceimpl()),

    TUTOREDS("Tutorandos", new TutoredSyncServiceImpl());

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
