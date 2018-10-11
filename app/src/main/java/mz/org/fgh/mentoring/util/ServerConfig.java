package mz.org.fgh.mentoring.util;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public enum ServerConfig {

    ACCOUNT_MANAGER("10.0.2.2:8081", "/account-manager-web/services/"),

    MENTORING("10.0.2.2:8080", "/mentoring-integ/services/");

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