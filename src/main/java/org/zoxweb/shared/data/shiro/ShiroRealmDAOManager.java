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

import java.util.ArrayList;

import org.zoxweb.shared.security.AccessException;

public interface ShiroRealmDAOManager 
{
	/**
	 * Add a subject
	 * @param subject
	 * @return ShiroSubjectDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroSubjectDAO addSubject(ShiroSubjectDAO subject)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Delete a subject
	 * @param subject
	 * @param withRoles
	 * @return ShiroSubjectDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroSubjectDAO deleteSubject(ShiroSubjectDAO subject, boolean withRoles)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Update a subject, usually the password
	 * @param subject
	 * @return ShiroSubjectDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroSubjectDAO updateSubject(ShiroSubjectDAO subject)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	
	
	/**
	 * Add a role
	 * @param role 
	 * @return ShiroRoleDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleDAO addRole(ShiroRoleDAO role)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Delete a role
	 * @param role
	 * @param withPermissions
	 * @return ShiroRoleDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleDAO deleteRole(ShiroRoleDAO role, boolean withPermissions)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Update a role
	 * @param role
	 * @return ShiroRoleDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleDAO updateRole(ShiroRoleDAO role)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	
	
	/**
	 * Add a rolegroup
	 * @param rolegroup
	 * @return ShiroRoleGroupDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleGroupDAO addRoleGroup(ShiroRoleGroupDAO rolegroup)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Delete a rolegroup
	 * @param rolegroup

	 * @return ShiroRoleGroupDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleGroupDAO deleteRoleGroup(ShiroRoleGroupDAO rolegroup)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Update a rolegroup
	 * @param rolegroup
	 * @return ShiroRoleGroupDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleGroupDAO updateRoleGroup(ShiroRoleGroupDAO rolegroup)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	
	
	
	
	
	/**
	 * Add a permission
	 * @param permission
	 * @return ShiroPermissionDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroPermissionDAO addPermission(ShiroPermissionDAO permission)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Delete a permission
	 * @param permission
	 * @return ShiroPermissionDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroPermissionDAO deletePermission(ShiroPermissionDAO permission)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * update a permission
	 * @param permission 
	 * @return ShiroPermissionDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroPermissionDAO updatePermission(ShiroPermissionDAO permission)
			throws NullPointerException, IllegalArgumentException, AccessException;
	/**
	 * Get a list of all the subjects
	 * @return list of ShiroSubjectDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroSubjectDAO> getAllShiroSubjects()
			throws AccessException;
	/**
	 * Get a list of all the roles
	 * @return list ShiroRoleDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroRoleDAO> getAllShiroRoles()
		throws AccessException;
	
	
	/**
	 * Get a list of all the roles
	 * @return list ShiroRoleGroupDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroRoleGroupDAO> getAllShiroRoleGroups()
		throws AccessException;
	/**
	 * Get a list of all permissions
	 * @return list ShiroPermissionDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroPermissionDAO> getAllShiroPermissions()
		throws AccessException;
	
	/**
	 * Lookup a subject based on his userName
	 * @param userName
	 * @return ShiroSubjectDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroSubjectDAO lookupSubject(String userName)
			throws NullPointerException, IllegalArgumentException, AccessException;
	

	
	
	/**
	 * 
	 * @param shiroDao
	 * @param sat
	 * @return ShiroCollectionAssociationDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroCollectionAssociationDAO lookupShiroCollection(ShiroDAO shiroDao, ShiroAssociationType sat)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	/**
	 * create an association
	 * @param association
	 * @return ShiroAssociationDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroAssociationDAO addShiroAssociationDAO(ShiroAssociationDAO association)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	/**
	 * remove association
	 * @param association
	 * @return ShiroAssociationDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroAssociationDAO removeShiroAssociationDAO(ShiroAssociationDAO association)
			throws NullPointerException, IllegalArgumentException, AccessException;
	

	
	

}
