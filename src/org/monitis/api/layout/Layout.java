package org.monitis.api.layout;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;

public class Layout extends APIObject {

	public enum LayoutAction{
		pages,
		pageModules,
		addPage,
		addPageModule,
		deletePage,
		deletePageModule,
	}
	
	public Layout(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public Layout(){
		super();
	}
	
	public Response getPages(OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(LayoutAction.pages, params);
	}
	
	public Response getPageModules(String pageName, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("pageName", pageName);
		return makeGetRequest(LayoutAction.pageModules, params);
	}
	
	public Response addPage(String title, int columnCount) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("columnCount", columnCount);
		return makePostRequest(LayoutAction.addPage, params);
	}
	
	public Response deletePage(Integer pageId) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageId", pageId);
		return makePostRequest(LayoutAction.deletePage, params);
	}
	
	public Response deletePageModule(Integer pageModuleId) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageModuleId", pageModuleId);
		return makePostRequest(LayoutAction.deletePageModule, params);
	}
	
	public Response addPageModule(String moduleName, Integer dataModuleId, int pageId, int row, int column, int height) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("moduleName", moduleName);
		params.put("pageId", pageId);
		params.put("row", row);
		params.put("column", column);
		params.put("height", height);
		if(dataModuleId != null) params.put("dataModuleId", dataModuleId);
		return makePostRequest(LayoutAction.addPageModule, params);
	}
	
	public static void main(String[] args) {
		try {
			Layout layout = new Layout();
			Response resp;
			String pageName = "Page api testikik";
			resp = layout.addPage(pageName, 2);
			System.out.println(resp);
			Integer pageId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("pageId");
			resp = layout.getPages(OutputType.JSON);
			System.out.println(resp);
			resp = layout.addPageModule("CPU", 446956, pageId, 1, 1, 200);
			System.out.println(resp);
			resp = layout.getPageModules(pageName, OutputType.JSON);
			System.out.println(resp);
			JSONArray pageModules = new JSONObject(resp.getResponseText()).getJSONArray("data");
			Integer pageModuleId = pageModules.getJSONObject(0).getInt("id");
			resp = layout.deletePage(pageId);
			System.out.println(resp);
			resp = layout.deletePageModule(pageModuleId);
			System.out.println(resp);			

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
