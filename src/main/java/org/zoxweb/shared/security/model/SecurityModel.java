package org.zoxweb.shared.security.model;

import org.zoxweb.shared.security.shiro.ShiroPermissionDAO;
import org.zoxweb.shared.security.shiro.ShiroRoleDAO;
import org.zoxweb.shared.util.AppID;
import org.zoxweb.shared.util.GetDescription;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;

public class SecurityModel
{
	

	public enum Permission
	implements GetNameValue<String>, GetDescription
	{
		NVE_READ_ALL("nve_read_all", "Permission to read all nventities", "nventity:read:*"),
		NVE_UPDATE_ALL("nve_update_all", "Permission to read all nventities", "nventity:update:*"),
		NVE_DELETE_ALL("nve_delete_all", "Permission to delete all nventities", "nventity:delete:*"),
		NVE_CREAD_ALL("nve_create_all", "Permission to create all nventities", "nventity:create:*"),
		ADD_PERMISSION("add_permission", "Permission to add a permission", "add:permission"),
		DELETE_PERMISSION("delete_permission", "Permission to delete a permission", "delete:permission"),
		ADD_ROLE("add_role", "Permission to add a role", "add:role"),
		DELETE_ROLE("delete_role", "Permission to delete a role", "delete:role"),
		CREATE_APP_ID("create_app_id", "Permission to create an app", "create:app:id"),
		DELETE_APP_ID("delete_app_id", "Permission to delete an app", "delete:app:id"),
		;
		private final String name;
		private final String value;
		private final String description;
		
		Permission(String name, String description, String value)
		{
			this.name = name;
			this.value = value;
			this.description = description;
		}
	
		
		public String getName() {
			// TODO Auto-generated method stub
			return name;
		}
		
		public String getDescription() {
			// TODO Auto-generated method stub
			return description;
		}
		public String getValue() {
			// TODO Auto-generated method stub
			return value;
		}
		
		
		public ShiroPermissionDAO toPermission()
		{
			return toPermission(getName(), getDescription(), getValue());
		}
		
		
		/**
		 * The name is the name of the permission and value is the patterm
		 * @param gnv
		 * @return
		 */
		public static ShiroPermissionDAO toPermission(GetNameValue<String> gnv)
		{
			return toPermission(gnv.getName(), null,  gnv.getValue());
		}
		
		public static ShiroPermissionDAO toPermission(String name, String description, String pattern)
		{
			ShiroPermissionDAO ret = new ShiroPermissionDAO();
			ret.setName(name);
			ret.setPermissionPattern(pattern);
			ret.setDescription(description);
			return ret;
			
		}
	}
	
	public enum Role
	implements GetName, GetDescription
	{
		SUPER_ADMIN("super_admin", "Super admin role"),
		DOMAIN_ADMIN("domain_admin", "domain admin role"),
		
		;
		private final String name;
		private final String description;
	
		
		Role(String name, String description)
		{
			this.name = name;
			this.description = description;
		}
		
		public String getName()
		{
			// TODO Auto-generated method stub
			return name;
		}
		
		public String getDescription() {
			// TODO Auto-generated method stub
			return description;
		}
		
		
		
		public ShiroRoleDAO toRole(AppID<String> appID)
		{
			return toRole(appID.getDomainID(), appID.getAppID());
		}
		
		public ShiroRoleDAO toRole(String domainID, String appID)
		{
			return toRole(domainID, appID, name, description);
		}
		
		
		
		public static ShiroRoleDAO toRole(AppID<String> appID, String name, String description)
		{
			return new ShiroRoleDAO(appID.getDomainID(), appID.getAppID(), name, description);
		}
		
		public static ShiroRoleDAO toRole(String domainID, String appID, String name, String description)
		{
			return new ShiroRoleDAO(domainID, appID, name, description);
		}
			
		public static ShiroRoleDAO addPermission(ShiroRoleDAO role, ShiroPermissionDAO permission)
		{
			permission.setDomainAppID(role.getDomainID(), role.getAppID());
			role.getPermissions().add(permission);
			return role;
		}


		
	
	}
}
