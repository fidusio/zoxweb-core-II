package org.zoxweb.shared.util;

/**
 * The subject ID intercace.
 * Created by mnael on 4/8/2017.
 */
public interface SubjectID<T>
{

    /**
     * Returns the subject ID.
     * @return
     */
    T getSubjectID();

    /**
     * Sets the subject ID.
     * @param id
     */
    void setSubjectID(T id);

}