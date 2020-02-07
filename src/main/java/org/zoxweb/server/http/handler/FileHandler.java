package org.zoxweb.server.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.SimpleDocumentDAO;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.SharedStringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

@SuppressWarnings("restriction")
public class FileHandler implements HttpHandler {
    private final static Logger log = Logger.getLogger(FileHandler.class.getName());

    public final String baseDir;
    public FileHandler(String baseDir)
    {
        this.baseDir = baseDir;
    }

    public void handle(HttpExchange he) throws IOException {


        String path = he.getHttpContext().getPath();

        URI uri = he.getRequestURI();
        log.info("user agent:" +  he.getRequestHeaders());
        log.info("path: " + path);
        log.info("URI: " +  uri.getPath());
        log.info("Remote IP: " + he.getRemoteAddress());
        log.info("Thread: " + Thread.currentThread());
        try {
            String filename = uri.getPath().substring(path.length(), uri.getPath().length());
            log.info("filename: " + filename);
            HTTPMimeType mime = HTTPMimeType.lookupByExtenstion(filename);
            log.info("mime type: " + mime);

            if(mime != null)
                he.getResponseHeaders()
                        .add(HTTPHeaderName.CONTENT_TYPE.getName(), mime.getValue());
            File file = new File(baseDir, filename);
            if (!file.exists() || !file.isFile())
                throw new FileNotFoundException();

            he.sendResponseHeaders(HTTPStatusCode.OK.CODE, file.length());
            IOUtil.relayStreams(new FileInputStream(file), he.getResponseBody(), true);
        }
        catch(Exception e)
        {
            e.printStackTrace();

            HTTPStatusCode hsc= HTTPStatusCode.BAD_REQUEST;
            SimpleDocumentDAO sdd = new SimpleDocumentDAO();
            sdd.setContent(hsc.CODE + ", " + hsc.REASON + ", not found.");
            String message = GSONUtil.toJSON(sdd, false, false, false);
            byte buffer[] = SharedStringUtil.getBytes(message);
            he.getResponseHeaders()
                    .add(HTTPHeaderName.CONTENT_TYPE.getName(), HTTPMimeType.APPLICATION_JSON.getValue());
            he.sendResponseHeaders(hsc.CODE, buffer.length);
            he.getResponseBody().write(buffer);
        }
    }
}