package org.zoxweb;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.NVEntity;

public class ClassInstance {

    @Test
    public void testAssignableFrom()
    {
        assert(Enum.class.isAssignableFrom(Const.TimeInMillis.class));
        assert(NVEntity.class.isAssignableFrom(AddressDAO.class));
    }

    public void testInstanceOf()
    {
        assert(new AddressDAO() instanceof NVEntity);
    }

}
