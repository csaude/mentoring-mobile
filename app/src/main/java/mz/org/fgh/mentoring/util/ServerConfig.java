package mz.org.fgh.mentoring.util;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public enum ServerConfig {

    ACCOUNT_MANAGER("192.168.56.1:8081", "/account-manager-web/services/"),

    MENTORING("192.168.56.1:8080", "/mentoring-integ/services/");

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
