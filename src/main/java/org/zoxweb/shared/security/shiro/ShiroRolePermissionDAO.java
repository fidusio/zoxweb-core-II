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
package org.zoxweb.shared.security.shiro;

import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ShiroRolePermissionDAO
    extends ShiroAssociationDAO
{

	public ShiroRolePermissionDAO()
	{
		super( ShiroAssociationType.PERMISSION_TO_ROLE, null, null);
	}
	
	public ShiroRolePermissionDAO(ShiroRoleDAO role, ShiroPermissionDAO permission)
	{
		super( ShiroAssociationType.PERMISSION_TO_ROLE, role, permission);
	}

	@Override
	public String toString()
	{
		return toCanonicalID();
	}
	
	@Override
	public String toCanonicalID() 
	{
		return SharedUtil.toCanonicalID(CAN_ID_SEP, getAssociatedTo().getDomainID(), getAssociatedTo().getName(), getPermission().getName(), getPermission().getPermissionPattern());
	}

	public ShiroPermissionDAO getPermission()
    {
		return (ShiroPermissionDAO) getAssociation();
	}

	public void setPermission(ShiroPermissionDAO permission)
	{
		setAssociation( permission);
	}

	public ShiroRoleDAO getRole()
    {
		return (ShiroRoleDAO) getAssociatedTo();
	}

	public void setRole(ShiroRoleDAO role)
    {
		setAssociatedTo( role);
	}

}