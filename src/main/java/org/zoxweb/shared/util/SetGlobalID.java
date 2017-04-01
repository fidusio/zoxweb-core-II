package org.zoxweb.shared.util;

/**
 * Created by mnael on 3/31/2017.
 */
public interface SetGlobalID <T>
    extends GlobalID<T>
{
    void setUUID(T uuid);
}
