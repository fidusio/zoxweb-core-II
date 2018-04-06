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
	
	
	
	public final static String PERM_ADD_PERMISSION = "add:permission";
	public final static String PERM_DELETE_PERMISSION = "delete:permission";
	public final static String PERM_UPDATE_PERMISSION = "update:permission";
	public final static String PERM_ADD_ROLE = "add:role";
	public final static String PERM_DELETE_ROLE = "delete:role";
	public final static String PERM_UPDATE_ROLE = "update:role";
	public final static String PERM_CREATE_APP_ID = "create:app:id";
	public final static String PERM_DELETE_APP_ID = "delete:app:id";
	public final static String PERM_ADD_USER = "create:user";
	public final static String PERM_DELETE_USER = "delete:user";
	public final static String PERM_READ_USER = "read:user";
	public final static String PERM_UPDATE_USER = "update:user";
	public final static String PERM_SELF = "self";
	public final static String PERM_ADD_RESOURCE = "add:resource";
	public final static String PERM_DELETE_RESOURCE = "delete:resource";
	public final static String PERM_UPDATE_RESOURCE = "update:resource";
	public final static String PERM_READ_RESOURCE = "read:resource";
	
	
	
	
	

	
	
	
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
		ADD_PERMISSION("add_permission", "Permission to add a permission", PERM_ADD_PERMISSION),
		DELETE_PERMISSION("delete_permission", "Permission to delete a permission", PERM_DELETE_PERMISSION),
		UPTDATE_PERMISSION("update_permission", "Permission to update a permission", PERM_UPDATE_PERMISSION),
		ADD_ROLE("add_role", "Permission to add a role", PERM_ADD_ROLE),
		DELETE_ROLE("delete_role", "Permission to delete a role", PERM_DELETE_ROLE),
		UPDATE_ROLE("update_role", "Permission to update a role", PERM_UPDATE_ROLE),
		CREATE_APP_ID("create_app_id", "Permission to create an app", PERM_CREATE_APP_ID),
		DELETE_APP_ID("delete_app_id", "Permission to delete an app", PERM_DELETE_APP_ID),
		CREATE_USER("create_user", "Permission to create a user", PERM_ADD_USER),
		DELETE_USER("delete_user", "Permission to delete a user", PERM_DELETE_USER),
		UPDATE_USER("update_user", "Permission to update a user", PERM_UPDATE_USER),
		READ_USER("read_user", "Permission to update a user", PERM_UPDATE_USER),
		ADD_RESOURCE("add_resource", "Permission to add a resource", PERM_ADD_RESOURCE, TOK_APP_ID),
		DELETE_RESOURCE("delete_resource", "Permission to delete a resource", PERM_DELETE_RESOURCE, TOK_APP_ID),
		UPDATE_RESOURCE("update_resource", "Permission to update a resource", PERM_UPDATE_RESOURCE, TOK_APP_ID),
		READ_PUBLIC_RESOURCE("read_public_resource", "Permission to read a public resource", PERM_READ_RESOURCE, TOK_PUBLIC, TOK_APP_ID, TOK_RESOURCE_ID),
		READ_PRIVATE_RESOURCE("read_private_resource", "Permission to read  a private resource", PERM_READ_RESOURCE, TOK_PRIVATE, TOK_APP_ID, TOK_RESOURCE_ID),
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
