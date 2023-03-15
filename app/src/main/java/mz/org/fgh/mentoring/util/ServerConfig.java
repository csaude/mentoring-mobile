package mz.org.fgh.mentoring.util;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public enum ServerConfig {

    ACCOUNT_MANAGER("https", "mentdev.fgh.org.mz", "/account-manager-web/services/"),

    //MENTORING("https", "mentdev.fgh.org.mz", "/mentoring-integ/services/");


    MENTORING("http", "10.0.2.2:8080", "/mentoring_integ_war/services/");

    private String protocol;
    private String address;
    private String service;

    ServerConfig(final String protocol, final String address, final String service) {
        this.protocol = protocol;
        this.address = address;
        this.service = service;
    }

    public String getBaseUrl() {
        return new StringBuilder(protocol).append("://")
                .append(address)
                .append(service)
                .toString();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAddress() {
        return address;
    }

    public String getService() {
        return service;
    }
}