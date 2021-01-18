package org.zoxweb.shared.security;

public class AuthToken<T> {
    private String url;
    private T token;

    public AuthToken(String url, T token)
    {
        this.url = url;
        this.token = token;
    }

    public String getURL(){return url;}

    public T getToken(){return token;}
}
