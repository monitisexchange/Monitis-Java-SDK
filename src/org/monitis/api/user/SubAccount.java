package org.monitis.api.user;

import java.util.HashMap;

import org.json.JSONObject;
import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.beans.URLObject;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

public class SubAccount extends APIObject {

	public enum SubAccountAction{
		subAccounts,
		subAccountPages,
		addSubAccount,
		deleteSubAccount,
		addPagesToSubAccount,
		deletePagesFromSubAccount;
	}
	
	public SubAccount(){
		super();
	}
	
	public SubAccount(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public Response getSubAccounts(OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(SubAccountAction.subAccounts, params);
	}
	
	public Response getSubAccountPages(OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(SubAccountAction.subAccountPages, params);
	}
	
	public Response addSubAccount(String firstName, String lastName, String email, String password, String group) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		params.put("email", email);
		params.put("password", password);
		params.put("group", group);
		return makePostRequest(SubAccountAction.addSubAccount, params);
	}
	
	public Response deleteSubAccount(int userId) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return makePostRequest(SubAccountAction.deleteSubAccount, params);
	}
	
	public Response addPagesToSubAccount(int userId, String [] pageNames) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("pageNames", StringUtils.arrayToString(pageNames, URLObject.DATA_SEPARATOR, 0, pageNames.length));
		return makePostRequest(SubAccountAction.addPagesToSubAccount, params);
	}
	
	public Response deletePagesFromSubAccount(int userId, String [] pageNames) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("pageNames", StringUtils.arrayToString(pageNames, URLObject.DATA_SEPARATOR, 0, pageNames.length));
		return makePostRequest(SubAccountAction.deletePagesFromSubAccount, params);
	}
	
	public static void main(String[] args) {
		try {
			
			SubAccount subaccount = new SubAccount();
			Response resp;
			resp = subaccount.addSubAccount("narika", "gasp", "narika@gmailik.com", "qqqqqq", "group1");
			System.out.println(resp);
			Integer subaccountId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("userId");
			resp = subaccount.getSubAccounts(OutputType.JSON);
			System.out.println(resp);
			resp = subaccount.getSubAccountPages(OutputType.JSON);
			System.out.println(resp);
			resp = subaccount.deleteSubAccount(subaccountId);
			System.out.println(resp);
			resp = subaccount.addPagesToSubAccount(subaccountId, new String[]{"Util"});
			System.out.println(resp);
			resp = subaccount.deletePagesFromSubAccount(subaccountId, new String[]{"Mysql"});
			System.out.println(resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
