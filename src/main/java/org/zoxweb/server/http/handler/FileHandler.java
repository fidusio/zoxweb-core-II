/*
 * Copyright (c) 2012-2020 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.http.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

/**
 * File handler for the built in http server that is shipped with java jre and jdk.
 * This class will automatically upload files to the http client if it exist on the file system.
 * The context is set during context initialization.
 * The baseDir is the main folder entry point, any file within the baseDir/filename or baseDir/
 */
@SuppressWarnings("restriction")
public class FileHandler implements HttpHandler {
    private final static Logger log = Logger.getLogger(FileHandler.class.getName());

    public final String baseDir;
    public FileHandler(String baseDir)
            throws IOException
    {
        baseDir = SharedStringUtil.trimOrNull(baseDir);
        SharedUtil.checkIfNulls("Null baseDir ", baseDir);
        File folder = new File(baseDir);
        if (!folder.exists() || !folder.isDirectory() || !folder.canRead())
            throw new IOException("Invalid folder: " + baseDir);
        this.baseDir = baseDir;
    }

    public void handle(HttpExchange he) throws IOException {
        String path = he.getHttpContext().getPath();
        URI uri = he.getRequestURI();
        log.info("path: " + path);
        log.info("URI: " +  uri.getPath());
        log.info("Remote IP: " + he.getRemoteAddress());
        //log.info("Thread: " + Thread.currentThread());
        try {
            String filename = uri.getPath().substring(path.length(), uri.getPath().length());
            log.info("filename: " + filename);
            HTTPMimeType mime = HTTPMimeType.lookupByExtenstion(filename);
            log.info("mime type: " + mime);

            if(mime != null)
                he.getResponseHeaders()
                        .add(HTTPHeaderName.CONTENT_TYPE.getName(), mime.getValue());
            File file = new File(baseDir, filename);
            if (!file.exists() || !file.isFile() || !file.canRead())
                throw new FileNotFoundException();

            he.sendResponseHeaders(HTTPStatusCode.OK.CODE, file.length());
            IOUtil.relayStreams(new FileInputStream(file), he.getResponseBody(), true);
        }
        catch(FileNotFoundException e)
        {
          HTTPHandlerUtil.sendErrorMessage(he, HTTPStatusCode.NOT_FOUND, "Resource NOT FOUND");
        }
        catch(Exception e)
        {
            e.printStackTrace();            
            HTTPHandlerUtil.sendErrorMessage(he, HTTPStatusCode.BAD_REQUEST, "System error");

        }
    }
}