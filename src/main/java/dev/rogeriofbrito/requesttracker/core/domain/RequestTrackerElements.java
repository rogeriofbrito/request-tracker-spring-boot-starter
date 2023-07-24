package dev.rogeriofbrito.requesttracker.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class RequestTrackerElements {

    public RequestTrackerElements() {
    }

    public RequestTrackerElements(String url, String queryString, String method, Integer status, String remoteHost,
                                  int remotePort, String protocol, LocalDateTime timestamp) {
        this.url = url;
        this.queryString = queryString;
        this.method = method;
        this.status = status;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.protocol = protocol;
        this.timestamp = timestamp;
    }

    private String url;
    private String queryString;
    private String method;
    private Integer status;
    private String remoteHost;
    private int remotePort;
    private String protocol;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
