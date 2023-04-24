/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.model;

import mz.org.fgh.mentoring.config.model.Setting;

import java.util.List;

public class SettingBeanResource extends BeanResource {

    private List<Setting> settings;

    private Setting setting;

    private List<String> settingUuids;

    public SettingBeanResource() {
    }

    public void setSettings(final List<Setting> settings) {
        this.settings = settings;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public List<String> getSettingsUuids() {
        return settingUuids;
    }

    public void setSettingUuids(List<String> settingUuids) {
        this.settingUuids = settingUuids;
    }
}
