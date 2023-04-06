package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.config.model.Setting;

import java.util.List;

public interface SettingSyncService {
    public void processSettings(List<Setting> settings);
}
