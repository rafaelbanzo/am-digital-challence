package com.gft.amdc.domain.error;

public class ErrorInfo {

    public final String url;
    public final String method;
    public final String errorMessage;

    public ErrorInfo(String url, String method, Exception ex) {
        this.url = url;
        this.method = method;
        this.errorMessage = ex.getLocalizedMessage();
    }
}
