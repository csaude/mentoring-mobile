package mz.org.fgh.mentoring.util;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public enum ServerConfig {

    ACCOUNT_MANAGER("https", "prod.fgh.org.mz", "/account-manager-web/services/"),

    MENTORING("https", "prod.fgh.org.mz", "/mentoring-integ/services/");

    private String protocol;
    private String address;
    private String service;

    ServerConfig(final String protocol, final String address, final String service) {
        this.protocol = protocol;
        this.address = address;
        this.service = service;
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

    public String getBaseUrl() {
        return new StringBuilder(getProtocol())
                .append("://")
                .append(getAddress())
                .append(getService())
                .toString();
    }
}