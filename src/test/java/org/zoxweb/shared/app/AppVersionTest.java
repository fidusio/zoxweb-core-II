package org.zoxweb.shared.app;

import java.util.Date;
import org.junit.Test;

public class AppVersionTest {

  @Test
  public void build()
  {
    AppVersionDAO appVersionDAO = new AppVersionDAO();
    appVersionDAO.setNano(0);
    appVersionDAO.setReleaseDate(new Date());

    Date dt = appVersionDAO.getReleaseDate();
    assert dt!= null;
  }

}
