package org.zoxweb.shared.data.events;



import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.util.SharedUtil;

import java.util.EventObject;

public class InetSocketAddressEvent extends EventObject {

    private final long timeStamp;
    private final InetSocketAddressDAO addressDAO;

    public InetSocketAddressEvent(Object source, InetSocketAddressDAO addressDAO) {
        super(source);
        SharedUtil.checkIfNulls("addressDAO can't be null",  addressDAO);
        this.addressDAO = addressDAO;
        timeStamp = System.currentTimeMillis();
    }

    /**
     * @return the timestamp in millis when the event was created
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * The InetSocketAddressDAO will return the remote  IP address and the local port it is trying to access
     * @return
     */
    public InetSocketAddressDAO getAddressDAO() {
        return addressDAO;
    }
}



