package org.zoxweb.shared.data.events;

import org.zoxweb.shared.net.InetSocketAddressDAO;

@SuppressWarnings("serial")
public class InetSocketAddressEvent extends BaseEventObject<InetSocketAddressDAO> {   
    public InetSocketAddressEvent(Object source, InetSocketAddressDAO addressDAO) {
       super(source, addressDAO);
    }
}



