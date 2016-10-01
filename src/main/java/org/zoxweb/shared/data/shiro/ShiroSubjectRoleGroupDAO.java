/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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
package org.zoxweb.shared.data.shiro;

@SuppressWarnings("serial")
public class ShiroSubjectRoleGroupDAO
extends ShiroAssociationDAO
{

	public ShiroSubjectRoleGroupDAO()
	{
		super( ShiroAssociationType.ROLEGROUP_TO_SUBJECT, null,null);
	}
	
	public ShiroSubjectRoleGroupDAO( ShiroSubjectDAO subject, ShiroRoleGroupDAO roleGroup)
	{
		
		super( ShiroAssociationType.ROLEGROUP_TO_SUBJECT, subject, roleGroup);
	
	}
	
	
	
	public ShiroSubjectDAO getSubject() {
		return (ShiroSubjectDAO) getAssociatedTo();
	}
	
	public void setSubject(ShiroSubjectDAO subject) {
		setAssociatedTo( subject);
	}

	public ShiroRoleGroupDAO getRoleGroup() {
		return (ShiroRoleGroupDAO) getAssociation();
	}

	public void setRoleGroup(ShiroRoleGroupDAO roleGroup) {
		setAssociation(roleGroup);
	}

}
