package org.monitis.api.user;

import java.util.HashMap;

import org.json.JSONObject;
import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;

public class Contact extends APIObject {

	public enum ContactAction{
		contactsList,
		contactGroupList,
		recentAlerts,
		addContact,
		editContact,
		deleteContact,
		confirmContact,
		contactActivate,
		contactDeactivate;
	}
	
	public Contact(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public Contact(){
		super();
	}
	
	public Response getContacts(OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(ContactAction.contactsList, params);
	}
	
	public Response getContactGroups(OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(ContactAction.contactGroupList, params);
	}
	
	public Response getRecentAlerts(Integer timezone, Long startDate, Long endDate, Integer limit, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(timezone != null) params.put("timezone", timezone);
		if(startDate != null) params.put("startDate", startDate);
		if(endDate != null) params.put("endDate", endDate);
		if(limit != null) params.put("limit", limit);
		params.put("output", output);
		return makeGetRequest(ContactAction.recentAlerts, params);
	}
	
	public Response addContact(String firstName, String lastName, String account, String group, String country,
			Integer contactType,  Integer timezone, Boolean textType, Boolean portable) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		params.put("account", account);
		params.put("contactType", contactType);
		params.put("group", group);
		params.put("timezone", timezone);
		if(country!= null) params.put("country", country);
		if(textType!= null) params.put("textType", textType);
		if(portable!= null) params.put("portable", portable);
		return makePostRequest(ContactAction.addContact, params);
	}
	
	public Response editContact(Integer contactId, String firstName, String lastName, String account, String group, String country,
			Integer contactType,  Integer timezone, Boolean textType, Boolean portable, String code) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("contactId", contactId);
		if(firstName!= null) params.put("firstName", firstName);
		if(lastName!= null) params.put("lastName", lastName);
		if(account!= null) params.put("account", account);
		if(contactType!= null) params.put("contactType", contactType);
		if(group!= null) params.put("group", group);
		if(timezone!= null) params.put("timezone", timezone);
		if(country!= null) params.put("country", country);
		if(textType!= null) params.put("textType", textType);
		if(portable!= null) params.put("portable", portable);
		if(code!= null) params.put("code", code);
		return makePostRequest(ContactAction.editContact, params);
	}
	
	public Response deleteContact(Integer contactId, Integer contactType, String account) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(contactId!= null) params.put("contactId", contactId);
		if(contactType!= null) params.put("contactType", contactType);
		if(account!= null) params.put("account", account);
		return makePostRequest(ContactAction.deleteContact, params);
	}
	
	public Response confirmContact(Integer contactId, String confirmationKey ) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("contactId", contactId);
		params.put("confirmationKey", confirmationKey );
		return makePostRequest(ContactAction.confirmContact, params);
	}
	
	public Response activateContact(Integer contactId) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("contactId", contactId);
		return makePostRequest(ContactAction.contactActivate, params);
	}
	
	public Response deActivateContact(Integer contactId) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("contactId", contactId);
		return makePostRequest(ContactAction.contactDeactivate, params);
	}
	
	public static void main(String[] args) {
		try {
			Response resp;
			Contact contact = new Contact();
			resp = contact.addContact("firstName", "lastName", "accounik@mail.com", "group", null, 1, 240, null, null);
			System.out.println(resp);
			JSONObject json = new JSONObject(resp.getResponseText()).getJSONObject("data");
			Integer id = json.getInt("contactId");
			resp = contact.editContact(id, "account", "gmail", "account@mail.com", "alisa", null, 1, 240, false, false, "");
			System.out.println(resp);
			resp = contact.confirmContact(id, json.getString("confirmationKey"));
			System.out.println(resp);
			resp = contact.activateContact(id);
			System.out.println(resp);
			resp = contact.deActivateContact(id);
			System.out.println(resp);
			resp = contact.getContacts(OutputType.XML);
			System.out.println(resp);
			resp = contact.getContactGroups(OutputType.XML);
			System.out.println(resp);
			resp = contact.getRecentAlerts(240, null, null, 10, OutputType.XML);
			System.out.println(resp);
			resp = contact.deleteContact(id, null, null);
			System.out.println(resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
