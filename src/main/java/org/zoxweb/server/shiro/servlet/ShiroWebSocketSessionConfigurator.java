package org.zoxweb.server.shiro.servlet;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class ShiroWebSocketSessionConfigurator
        extends ServerEndpointConfig.Configurator {

    public static final String HTTP_SESSION = "http_session";

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put(HTTP_SESSION, request.getHttpSession());
    }

}