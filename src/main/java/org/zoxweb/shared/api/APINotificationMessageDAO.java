/*
 * Copyright (c) 2012-May 27, 2014 ZoxWeb.com LLC.
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
package org.zoxweb.shared.api;

import java.util.ArrayList;
import java.util.List;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class defines the APINotificationMessage data access object used to
 * create messages.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class APINotificationMessageDAO 
	extends SetNameDescriptionDAO
	implements APINotificationMessage
{
	/**
	 * This enum contains message notification parameters.
	 * @author mzebib
	 *
	 */
	public enum Params
		implements GetNVConfig
	{	
		SENDER_ID(NVConfigManager.createNVConfig("sender_id", "The id of the sender.", "SenderID", false, true, String.class)),
		RECIPIENT_IDS(NVConfigManager.createNVConfig("recipient_ids", "The ids of the recipients.", "RecipientIDs", true, true, String[].class)),
		TITLE(NVConfigManager.createNVConfig("title", "The title or subject of the email.", "Title", false, true, String.class)),
		PRE_TOKEN_TAG(NVConfigManager.createNVConfig("pre_token_tag", "The pre token tag.", "PreTokenTag", false, true, String.class)),
		POST_TOKEN_TAG(NVConfigManager.createNVConfig("post_token_tag", "The post token tag.", "PostTokenTag", false, true, String.class)),
		MESSAGE(NVConfigManager.createNVConfig("message", "The body message.", "Message", false, true, String.class)),
		TAGS(NVConfigManager.createNVConfig("tags", "Email tags.", "Tags", false, true, true, String[].class, null)),
		MESSAGE_TYPE(NVConfigManager.createNVConfig("message_type", "The type of message.", "MessageType", true, true, APIServiceType.class)),
		EXTRA_ATTRIBUTES(NVConfigManager.createNVConfig("extra_attributes", "Extra attributes.", "ExtraAttributes", false, true, String[].class)),
		
		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	
	}
	
	/**
	 * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on APINotificationMessageDAO.
	 */
	public static final NVConfigEntity NVC_API_NOTIFICATION_MESSAGE_DAO = new NVConfigEntityLocal
																			(
																				"api_notification_message_dao",
																				null,
																				"APINotificationMessageDAO",
																				true,
																				false,
																				false,
																				false,
																				APINotificationMessageDAO.class,
																				SharedUtil.extractNVConfigs(Params.values()),
																				null,
																				false,
																				SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																			);
	
	
	/**
	 * This is the default constructor.
	 */
	public APINotificationMessageDAO()
	{
		super(NVC_API_NOTIFICATION_MESSAGE_DAO);
	}
	
	/**
	 * This constructor instantiates APINotificationMessageDAO based on list of NVConfigEntity type.
	 * @param list
	 */
//	protected APINotificationMessageDAO(List<NVConfigEntity> list)
//	{
//		super(SharedUtil.merge(list, NVC_API_NOTIFICATION_MESSAGE_DAO));
//	}
	
	/**
	 * This method returns the sender ID.
	 * @return
	 */
	@Override
	public String getSenderID()
	{
		return lookupValue(Params.SENDER_ID);
	}

	/**
	 * This method sets the sender ID.
	 * @param senderID
	 */
	@Override
	public void setSenderID(String senderID) 
			throws NullPointerException, IllegalArgumentException
	{
		if (SharedStringUtil.isEmpty(senderID))
		{
			throw new NullPointerException("Null or empty value: " + senderID);
		}
		
		setValue(Params.SENDER_ID, senderID);
	}

	/**
	 * This method returns the recipient IDs.
	 * @return
	 */
	@Override
	public String[] getRecipientIDs() 
	{
		List<NVPair> list = lookupValue(Params.RECIPIENT_IDS);
		
		String[] IDs = new String [list.size()];
		
		for (int i = 0; i < list.size(); i++)
		{
			IDs[i] = list.get(i).getValue();
		}
		
		return IDs;
	}

	/**
	 * This method sets the recipients IDs.
	 * @param recipients
	 */
	@Override
	public void setRecipientIDs(String... recipients)
			throws NullPointerException, IllegalArgumentException 
	{
		SharedUtil.checkIfNulls("Null or empty array", (Object[]) recipients);
		
		List<NVPair> list = new ArrayList<NVPair>();
		
		for (String str : recipients)
		{
			list.add(new NVPair("recipient", str));
		}
		
		setValue(Params.RECIPIENT_IDS, list);
		
	}
	
	/**
	 * This method returns the subject of the message.
	 * @return
	 */
	@Override
	public String getTitle() 
	{
		return lookupValue(Params.TITLE);
	}

	/**
	 * This method sets the subject of the message.
	 * @param title
	 */
	@Override
	public void setTitle(String title) 
			throws NullPointerException, IllegalArgumentException 
	{
		if (SharedStringUtil.isEmpty(title))
		{
			throw new NullPointerException("Null or empty value: " + title);
		}
		
		setValue(Params.TITLE, title);
	}

	
	/**
	 * This method returns the message body.
	 */
	@Override
	public String getBodyTemplate() 
	{
		return lookupValue(Params.MESSAGE);
	}

	/**
	 * This method sets the message body.
	 * @param messageBody
	 */
	@Override
	public void setBodyTemplate(String messageBody) 
			throws NullPointerException, IllegalArgumentException 
	{
		if (SharedStringUtil.isEmpty(messageBody))
		{
			throw new NullPointerException("Null or empty value: " + messageBody);
		}
		
		setValue(Params.MESSAGE, messageBody);
	}

	/**
	 * This method returns message body tags.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayValues<NVPair> getBodyTags() 
	{
		return (ArrayValues<NVPair>) lookup(Params.TAGS);
	}

	/**
	 * This method sets message body tags.
	 * @param tags
	 */
	@Override
	public void setBodyTags(ArrayValues<NVPair> tags) 
	{
		getBodyTags().add(tags.values(), true);
	}
	
	/**
	 * This method sets message body tags.
	 * @param tags
	 */
	@Override
	public void setBodyTags(List<NVPair> tags) 
	{
		getBodyTags().add(tags.toArray(new NVPair[0]), true);
	}

	/**
	 * This method returns extra attributes.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayValues<NVPair> getExtraAttribues() 
	{
		return (ArrayValues<NVPair>) lookup(Params.EXTRA_ATTRIBUTES);
	}

	/**
	 * This method sets extra attributes.
	 * @param attributes
	 */
	@Override
	public void setExtraAttributes(ArrayValues<NVPair> attributes)
	{
		getExtraAttribues().add(attributes.values(), true);
	}	

	/**
	 * This method sets extra attributes.
	 * @param attributes
	 */
	@Override
	public void setExtraAttributes(List<NVPair> attributes) 
	{
		getExtraAttribues().add(attributes.toArray(new NVPair[0]), true);
	}

	/**
	 * This method returns the message type.
	 */
	@Override
	public APIServiceType getMessageType() 
	{
		return lookupValue(Params.MESSAGE_TYPE);
	}

	/**
	 * This method sets the message type.
	 * @param type
	 */
	@Override
	public void setMessageType(APIServiceType type) 
			throws NullPointerException, IllegalArgumentException 
	{
		SharedUtil.checkIfNulls("Null value: ", type);
		setValue(Params.MESSAGE_TYPE, type);
	}

	/**
	 * This method returns the pre-token tag.
	 * @return
	 */
	public String getPreTag() 
	{
		return  lookupValue(Params.PRE_TOKEN_TAG);
	}

	/**
	 * This method sets the pre-token tag.
	 * @param preTokenTag
	 */
	public void setPreTag(String preTokenTag) 
	{
		setValue(Params.PRE_TOKEN_TAG, preTokenTag);
	}
	
	/**
	 * This method returns the post-token tag.
	 * @return
	 */
	public String getPostTag() 
	{
		return  lookupValue(Params.POST_TOKEN_TAG);
	}

	/**
	 * This method sets the post-token tag.
	 * @param postTokenTag
	 */
	public void setPostTag(String postTokenTag)
	{
		setValue(Params.POST_TOKEN_TAG, postTokenTag);
	}

}
