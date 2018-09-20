package mz.org.fgh.mentoring.util;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public enum ServerConfig {

    ACCOUNT_MANAGER("test.fgh.org.mz", "/account-manager-web/services/"),

    MENTORING("test.fgh.org.mz", "/mentoring-integ/services/");

    private String address;
    private String service;

    ServerConfig(final String address, final String service) {
        this.address = address;
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public String getService() {
        return service;
    }
}