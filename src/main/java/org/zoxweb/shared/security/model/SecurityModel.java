package org.zoxweb.shared.security.model;

import org.zoxweb.shared.security.shiro.ShiroPermissionDAO;
import org.zoxweb.shared.security.shiro.ShiroRoleDAO;
import org.zoxweb.shared.util.AppID;
import org.zoxweb.shared.util.GetDescription;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.SharedUtil;

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
		UPTDATE_PERMISSION("update_permission", "Permission to update a permission", "update:permission"),
		ADD_ROLE("add_role", "Permission to add a role", "add:role"),
		DELETE_ROLE("delete_role", "Permission to delete a role", "delete:role"),
		UPDATE_ROLE("update_role", "Permission to update a role", "update:role"),
		CREATE_APP_ID("create_app_id", "Permission to create an app", "create:app:id"),
		DELETE_APP_ID("delete_app_id", "Permission to delete an app", "delete:app:id"),
		CREATE_USER("create_user", "Permission to create a user", "create:user"),
		DELETE_USER("delete_user", "Permission to delete a user", "delete:user"),
		UPDATE_USER("update_user", "Permission to update a user", "update:user"),
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
		
		
		public ShiroPermissionDAO toPermission(String domainID, String appID)
		{
			return toPermission(domainID, appID, getName(), getDescription(), getValue());
		}
		
		
		/**
		 * The name is the name of the permission and value is the patterm
		 * @param gnv
		 * @return
		 */
		public static ShiroPermissionDAO toPermission(String domainID, String appID, GetNameValue<String> gnv)
		{
			return toPermission(domainID, appID, gnv.getName(), null,  gnv.getValue());
		}
		
		public static ShiroPermissionDAO toPermission(String domainID, String appID, String name, String description, String pattern)
		{
			ShiroPermissionDAO ret = new ShiroPermissionDAO();
			ret.setName(name);
			ret.setPermissionPattern(pattern);
			ret.setDescription(description);
			ret.setDomainAppID(domainID, appID);
			return ret;
			
		}
	}
	
	public enum Role
	    implements GetName, GetDescription
	{
		SUPER_ADMIN("super_admin_role", "Super admin role"),
		DOMAIN_ADMIN("domain_admin_role", "domain admin role"),
        APP_ADMIN("app_admin", "App admin role"),
        APP_USER("app_user", "App user role"),
        APP_SERVICE_PROVIDER("app_service_provider", "App service provider role"),
		
		;
		private final String name;
		private final String description;
	
		
		Role(String name, String description)
		{
			this.name = name;
			this.description = description;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
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
	
	
	public static String toSubjectID(String domainID, String appID, GetName gn)
	{
		return toSubjectID(domainID, appID, gn.getName());
	}
	
	
	public static String toSubjectID(String domainID, String appID, String name)
	{
		return SharedUtil.toCanonicalID(':', domainID, appID, name);
	}
}
