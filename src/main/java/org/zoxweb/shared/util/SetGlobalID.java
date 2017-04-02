package org.zoxweb.shared.util;

/**
 * Created by mnael on 3/31/2017.
 */
public interface SetGlobalID <T>
    extends GlobalID<T>
{
    /**
     * @param gid the global identifier of the object.
     */
    void setGlobalID(T gid);
}
