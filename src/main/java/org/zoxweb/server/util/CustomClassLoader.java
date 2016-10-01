package org.zoxweb.server.util;


import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.io.InternalBufferAccess;
import org.zoxweb.server.io.UByteArrayOutputStream;

import sun.misc.Resource;
import sun.misc.URLClassPath;
import sun.misc.Unsafe;


public class CustomClassLoader extends ClassLoader 
{
	private static final transient Logger log = Logger.getLogger(CustomClassLoader.class.getName());
    private ChildClassLoader childClassLoader;

    public CustomClassLoader(URL ...urls)
    {
        super(Thread.currentThread().getContextClassLoader());
        childClassLoader = new ChildClassLoader(urls, new DetectClass(this.getParent()));
        log.info("created");
    }

    @Override

    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
    {

        try
        {

            return childClassLoader.findClass(name);

        }

        catch( ClassNotFoundException e )

        {

            return super.loadClass(name, resolve);

        }

    }

    private static class ChildClassLoader extends URLClassLoader

    {

        private DetectClass realParent;

        public ChildClassLoader(URL[] urls, DetectClass realParent )

        {

            super(urls, null);

            this.realParent = realParent;

        }
        
        @Override

        public Class<?> findClass(final String name) throws ClassNotFoundException

        {

            try

            {

            	Class<?> loaded = super.findLoadedClass(name);
            	log.info("Class " + name + " not found.");

                if(loaded != null)
                    return loaded;
               
                String path = name.replace('.', '/').concat(".class");
                URLClassPath ucp = new URLClassPath(getURLs());
                Resource res = ucp.getResource(path, false);
                UByteArrayOutputStream classBytes = IOUtil.inputStreamToByteArray(res.getInputStream(), true);
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                Unsafe unsafe = (Unsafe) f.get(null);
                f.setAccessible(false);

                //Unsafe unsafe = Unsafe.getUnsafe();
                Class<?> clazz = null;
                
                
                try
                {
                	unsafe.ensureClassInitialized(InternalBufferAccess.class);
                	clazz = unsafe.defineClass(name, classBytes.getInternalBuffer(), 0, classBytes.size(), Thread.currentThread().getContextClassLoader(), null);
                	log.info("Class " + name + " loaded");
                }
                catch(IllegalAccessError e)
                {
                	String message = e.getMessage();
                	String tokens[] = message.split(" ");
                	if (tokens != null && tokens.length > 0)
                	{
                		 Class<?> c = Class.forName(tokens[tokens.length -1]);
                		 clazz = unsafe.defineClass(name, classBytes.getInternalBuffer(), 0, classBytes.size(), c.getClassLoader(), null);
                	}
                }
               
                if (clazz != null)
                {
                	log.info("Class " + name + " loaded will return");
                	return clazz;
                }

                return super.findClass(name);

            }

            catch(ClassNotFoundException | IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
            {

            	e.printStackTrace();
                return realParent.loadClass(name);

            }

        }

    }

    private static class DetectClass extends ClassLoader
    {

        public DetectClass(ClassLoader parent)
        {

            super(parent);

        }

        @Override

        public Class<?> findClass(String name) throws ClassNotFoundException
        {
            return super.findClass(name);

        }

    }

}