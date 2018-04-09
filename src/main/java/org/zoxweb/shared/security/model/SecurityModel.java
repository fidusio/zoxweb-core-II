package org.zoxweb.shared.security.model;

import org.zoxweb.shared.security.shiro.ShiroDAO;
import org.zoxweb.shared.security.shiro.ShiroPermissionDAO;
import org.zoxweb.shared.security.shiro.ShiroRoleDAO;
import org.zoxweb.shared.util.AppID;
import org.zoxweb.shared.util.GetDescription;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.SharedUtil;

public class SecurityModel
{
	public final static String TOK_APP_ID = "$$app_id$$";
	public final static String TOK_PRIVATE = "private";
	public final static String TOK_PUBLIC= "public";
	public final static String TOK_REFERENCE_ID = "$$reference_id$$";
	public final static String TOK_RESOURCE_ID = "$$resource_id$$";
	public final static String TOK_SUBJECT_ID = "$$subject_id$$";	
	public final static String TOK_USER_ID = "$$user_id$$";
	
	
	
	public final static String PERM_ADD_PERMISSION = "permission:create";
	public final static String PERM_DELETE_PERMISSION = "permission:delete";
	public final static String PERM_UPDATE_PERMISSION = "permission:update";
	public final static String PERM_ADD_ROLE = "role:create";
	public final static String PERM_DELETE_ROLE = "role:delete";
	public final static String PERM_UPDATE_ROLE = "role:update";
	public final static String PERM_CREATE_APP_ID = "app:create";
	public final static String PERM_DELETE_APP_ID = "app:delete";
	public final static String PERM_UPDATE_APP_ID = "app:delete";
	public final static String PERM_ADD_USER = "user:create";
	public final static String PERM_DELETE_USER = "user:delete";
	public final static String PERM_READ_USER = "user:read";
	public final static String PERM_UPDATE_USER = "user:update";
	public final static String PERM_SELF = "self";
	public final static String PERM_ADD_RESOURCE = "resource:add";
	public final static String PERM_DELETE_RESOURCE = "resource:delete";
	public final static String PERM_UPDATE_RESOURCE = "resource:update";
	public final static String PERM_READ_RESOURCE = "resource:read";
	
	
	
	
	

	
	
	
	public enum PermissionToken
		implements GetValue<String>
	{
		APP_ID(TOK_APP_ID),
		PRIVATE(TOK_PRIVATE),
		PUBLIC(TOK_PUBLIC),
		REFERENCE_ID(TOK_REFERENCE_ID),
		RESOURCE_ID(TOK_RESOURCE_ID),
		SUBJECT_ID(TOK_SUBJECT_ID),
		USER_ID(TOK_USER_ID),
		;

		
		private final String value;
		PermissionToken(String value)
		{
			this.value = value ;
		}
		
		@Override
		public String getValue() {
			// TODO Auto-generated method stub
			return value;
		}
		
	}


	public enum Permission
	implements GetNameValue<String>, GetDescription
	{
		NVE_READ_ALL("nve_read_all", "Permission to read all nventities", "nventity:read:*"),
		NVE_UPDATE_ALL("nve_update_all", "Permission to read all nventities", "nventity:update:*"),
		NVE_DELETE_ALL("nve_delete_all", "Permission to delete all nventities", "nventity:delete:*"),
		NVE_CREAD_ALL("nve_create_all", "Permission to create all nventities", "nventity:create:*"),
		PERMISSION_ADD("permission_add", "Permission to add a permission", PERM_ADD_PERMISSION),
		PERMISSION_DELETE("permission_delete", "Permission to delete a permission", PERM_DELETE_PERMISSION),
		PERMISSION_UPDATE("permission_update", "Permission to update a permission", PERM_UPDATE_PERMISSION),
		ROLE_ADD("role_add", "Permission to add a role", PERM_ADD_ROLE),
		ROLE_DELETE("role_delete", "Permission to delete a role", PERM_DELETE_ROLE),
		ROLE_UPDATE("role_update", "Permission to update a role", PERM_UPDATE_ROLE),
		APP_ID_CREATE("app_id_create", "Permission to create an app", PERM_CREATE_APP_ID),
		APP_ID_DELETE("app_id_delete", "Permission to delete an app", PERM_DELETE_APP_ID),
		APP_ID_UPDATE("app_id_update", "Permission to update an app", PERM_UPDATE_APP_ID),
		USER_CREATE("user_create", "Permission to create a user", PERM_ADD_USER),
		USER_DELETE("user_delete", "Permission to delete a user", PERM_DELETE_USER),
		USER_UPDATE("user_update", "Permission to update a user", PERM_UPDATE_USER),
		USER_READ("user_read", "Permission to update a user", PERM_UPDATE_USER),
		RESOURCE_ADD("resource_add", "Permission to add a resource", PERM_ADD_RESOURCE, TOK_APP_ID),
		RESOURCE_DELETE("resource_delete", "Permission to delete a resource", PERM_DELETE_RESOURCE, TOK_APP_ID),
		RESOURCE_UPDATE("resource_update", "Permission to update a resource", PERM_UPDATE_RESOURCE, TOK_APP_ID),
		RESOURCE_READ_PUBLIC("resource_read_public", "Permission to read a public resource", PERM_READ_RESOURCE, TOK_APP_ID, TOK_RESOURCE_ID, TOK_PUBLIC),
		RESOURCE_READ_PRIVATE("resource_private", "Permission to read  a private resource", PERM_READ_RESOURCE, TOK_APP_ID, TOK_RESOURCE_ID, TOK_PRIVATE),
		SELF("self", "permission granted to all users", PERM_SELF),
	
	
		
		;
		private final String name;
		private final String pattern;
		private final String description;
		
		
		
		
			
		
		Permission(String name, String description, String ...values)
		{
			this.name = name;
			this.pattern = PPEncoder.SINGLETON.encode(values);
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
			return pattern();
		}
		
		public String pattern()
		{
			return pattern;
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
		public static ShiroPermissionDAO toPermission(String domainID,  String appID, GetNameValue<String> gnv)
		{
			return toPermission(domainID, appID, gnv.getName(), null,  gnv.getValue());
		}
		
		public static ShiroPermissionDAO toPermission(String domainID, String appID, String name, String description, String pattern)
		{
			ShiroPermissionDAO ret = new ShiroPermissionDAO();
			ret.setName(name);
			ret.setDescription(description);
			//ret.setEmbedAppIDEnabled(embedAppID);
			ret.setDomainAppID(domainID, appID);
			ret.setPermissionPattern(pattern);
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
		return SharedUtil.toCanonicalID(ShiroDAO.CAN_ID_SEP, domainID, appID, name);
	}
}
