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
	 * Updates a subject, usually the password.
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
	 * Delete a role.
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
	 * Updates a role
	 * @param role
	 * @return ShiroRoleDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleDAO updateRole(ShiroRoleDAO role)
        throws NullPointerException, IllegalArgumentException, AccessException;

	/**
	 * Adds a role group.
	 * @param rolegroup
	 * @return ShiroRoleGroupDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleGroupDAO addRoleGroup(ShiroRoleGroupDAO rolegroup)
        throws NullPointerException, IllegalArgumentException, AccessException;

	/**
	 * Delete a role group.
	 * @param rolegroup
	 * @return ShiroRoleGroupDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroRoleGroupDAO deleteRoleGroup(ShiroRoleGroupDAO rolegroup)
        throws NullPointerException, IllegalArgumentException, AccessException;

	/**
	 * Update a role group.
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
	 * Updates a permission.
	 * @param permission 
	 * @return ShiroPermissionDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroPermissionDAO updatePermission(ShiroPermissionDAO permission)
        throws NullPointerException, IllegalArgumentException, AccessException;

	/**
	 * Returns all subjects.
	 * @return list of ShiroSubjectDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroSubjectDAO> getAllShiroSubjects()
        throws AccessException;

	/**
	 * Returns all roles.
	 * @return list ShiroRoleDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroRoleDAO> getAllShiroRoles()
		throws AccessException;

	/**
	 * Returns all roles groups.
	 * @return list ShiroRoleGroupDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroRoleGroupDAO> getAllShiroRoleGroups()
		throws AccessException;

	/**
	 * Returns all permissions.
	 * @return list ShiroPermissionDAO
	 * @throws AccessException
	 */
	public ArrayList<ShiroPermissionDAO> getAllShiroPermissions()
		throws AccessException;
	
	/**
	 * Looks up a subject based on username.
	 * @param userName
	 * @return ShiroSubjectDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroSubjectDAO lookupSubject(String userName)
        throws NullPointerException, IllegalArgumentException, AccessException;

	/**
	 * Looks up association collection.
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
	 * Create an association.
	 * @param association
	 * @return ShiroAssociationDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroAssociationDAO addShiroAssociationDAO(ShiroAssociationDAO association)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	/**
	 * Removes an association.
	 * @param association
	 * @return ShiroAssociationDAO
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 */
	public ShiroAssociationDAO removeShiroAssociationDAO(ShiroAssociationDAO association)
        throws NullPointerException, IllegalArgumentException, AccessException;

}
