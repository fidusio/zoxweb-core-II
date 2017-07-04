/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.shared.data;

import org.zoxweb.shared.util.CanonicalID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * The ApplicationInfoDAO class defines information about a system application.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class ApplicationVersionDAO
	extends SetNameDescriptionDAO
	implements CanonicalID
{
	
	public enum Param
		implements GetNVConfig
	{
		MAJOR(NVConfigManager.createNVConfig("major", "The major number of the version", "Minor", false, true, int.class)),
		MINOR(NVConfigManager.createNVConfig("minor", "The minor number of the version", "Major", true, true, int.class)),
		NANO(NVConfigManager.createNVConfig("nano", "The nano number of the version", "Nano", true, true, int.class))
		
		;

        private final NVConfig nvc;

        Param(NVConfig nvc)
        {
            this.nvc = nvc;
        }

        public NVConfig getNVConfig()
        {
            return nvc;
        }
	}
	
	public static final NVConfigEntity NVC_APPLICATION_VERSION_DAO = new NVConfigEntityLocal(
            "application_version_dao",
            null,
            "ApplicationVersionDAO",
            true,
            false,
            false,
            false,
            ApplicationVersionDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );
	
	
	/**
	 * The default constructor.
	 */
	public ApplicationVersionDAO()
	{
		super(NVC_APPLICATION_VERSION_DAO);
	}

	/**
	 * This constructor instantiates UserInfoDAO based on list of NVConfigEntity type.
	 * @param list
	 */
//	protected ApplicationInfoDAO(List<NVConfigEntity> list)
//	{
//		super(SharedUtil.merge(list, NVC_USER_INFO_DAO));
//	}
	
	/**
	 * Returns the major number of the version.
	 * @return the major value
	 */
	public int getMajor()
	{
		return lookupValue(Param.MAJOR);
	}
	
	/**
	 * Sets the major number of the version.
	 * @param major
	 */
	public void setMajor(int major)
	{
		setValue(Param.MAJOR, major);
	}
	
	/**
	 * Returns the minor number of the version.
	 * @return the minor value
	 */
	public int getMinor()
	{
		return lookupValue(Param.MINOR);
	}

	/**
	 * Sets the minor number of the version.
	 * @param minor
	 */
	public void setMinor(int minor)
	{
		if (minor < 0 || minor > 9)
		{
			throw new IllegalArgumentException(minor + " invalid minor value valid [0-9]");
		}
		
		setValue(Param.MINOR, minor);
	}
	
	/**
	 * Gets the nano number of the version.
	 * @return the nano value
	 */
	public int getNano()
	{
		return lookupValue(Param.NANO);
	}
	
	/**
	 * Sets the nano number of the version.
	 * @param nano
	 */
	public void setNano(int nano)
	{
		if (nano < 0 || nano > 99)
		{
			throw new IllegalArgumentException(nano + " invalid nano value valid [0-9]");
		}
		
		setValue(Param.NANO, nano);
	}
	
	/**
	 * @see org.zoxweb.shared.util.CanonicalID#toCanonicalID()
	 */
	@Override
	public String toCanonicalID()
	{
		String min =  ""+getMinor();
		String nan =  ""+getNano();
		return getName() + "-" + getMajor() + "." + min + "." + nan;
	}
	
}