package org.zoxweb.server.security;

import org.zoxweb.server.logging.LoggerUtil;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.cache.JWTTokenCache;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SecurityConsts;
import org.zoxweb.shared.util.Const;

public class JWTCacheTest {

    public static void main(String ...args)
    {
        try
        {
            int index = 0;
            String subject = args[index++];
            String password = args[index++];
            int count = Integer.parseInt(args[index++]);

            long ttl = index < args.length ? Const.TimeInMillis.toMillis(args[index++]) : Const.TimeInMillis.SECOND.MILLIS*45;
            TaskUtil.getDefaultTaskProcessor();

            LoggerUtil.enableDefaultLogger("org.zoxweb");
            JWTTokenCache cache = new JWTTokenCache(ttl, TaskUtil.getDefaultTaskScheduler());
            long sizeOfAllTockens = 0;
            long ts = System.currentTimeMillis();
            for(int i=0; i < count; i++)
            {
                JWT jwt = JWT.createJWT(SecurityConsts.JWTAlgorithm.HS256, subject, "xlogistx.io", "test");
                sizeOfAllTockens += CryptoUtil.encodeJWT(password, jwt, true).length();
                cache.map(jwt);
            }

            int actualSize = cache.size();
            long creationTS = System.currentTimeMillis() - ts;

            TaskUtil.waitIfBusyThenClose(250);
            System.out.println("It took " + Const.TimeInMillis.toString(creationTS) + " to create " + actualSize + " JWT token");
            System.out.println("It took " + Const.TimeInMillis.toString(System.currentTimeMillis()-ts) + " to finish " + actualSize + " JWT token cache size " + cache.size() +
                               " average size " + (sizeOfAllTockens/actualSize) + " total size: " + sizeOfAllTockens + " default expiration: " + Const.TimeInMillis.toString(cache.defaultExpirationPeriod()));


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
