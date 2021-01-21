package org.zoxweb.shared.security;

import org.zoxweb.shared.security.SecurityConsts.*;

public class AuthToken<T> {
    private String url;
    private T token;
    private AuthenticationType type;

    public AuthToken(String url, AuthenticationType type, T token)
    {
        this.url = url;
        this.type = type;
        this.token = token;
    }

    public String getURL(){return url;}

    public T getToken(){return token;}

    public AuthenticationType getType()
    {
        return type;
    }


}
