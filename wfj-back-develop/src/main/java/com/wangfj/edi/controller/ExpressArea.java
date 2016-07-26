package com.wangfj.edi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wangfj.edi.bean.ExpressVO;
import com.wangfj.edi.util.HttpClients;
import com.wangfj.edi.util.PropertiesUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/express")
public class ExpressArea {

	@ResponseBody
	@RequestMapping(value = "/queryExpress", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryAddress(String parentId,String channel) {
		String json = "";
		String url = "";
		if("M4".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_yz_express_Tree")+parentId;
		}else if("C8".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_jm_express_Tree")+parentId;
		}else if("OB".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_hlm_express_Tree")+parentId;
		}else{
			url = (String) PropertiesUtil.getContextProperty("edi_express_Tree")+parentId;
		}
		try {
			json = HttpClients.httpdoGet(url);
			System.out.println("-------------------" + json);
		} catch (Exception e) {
			return "{success:false}";
		} finally {
			if (null == json || "".equals(json) || "false".equals(json)) {
				json = "{success:false}";
			}
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryExpressInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryExpressInfo(String id,String channel) {
		String json = "";
		String url = "";
		if("M4".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_yz_express_Info")+id;
		}else if("C8".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_jm_express_Info")+id;
		}else if("OB".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_hlm_express_Info")+id;
		}else{
			url = (String) PropertiesUtil.getContextProperty("edi_express_Info")+id;
		}
		try {
			json = HttpClients.httpdoGet(url);
			System.out.println("-------------------json" + json);
			
		} catch (Exception e) {
			return "{success:false}";
		} finally {
			if (null == json || "".equals(json) || "false".equals(json)) {
				json = "{success:false}";
			}
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateExpress", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateExpress(ExpressVO expressVO,String channel,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map =new HashMap<String,Object>();
		String username = request.getParameter("username");
		expressVO.setOperaterLoginid(username);
		String json = "";
		JSONObject jsonData = JSONObject.fromObject(expressVO);
		String url = "";
		if("M4".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_yz_express_Update");
		}else if("C8".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_jm_express_Update");
		}else if("OB".equals(channel)){
			url = (String) PropertiesUtil.getContextProperty("edi_hlm_express_Update");
		}else{
			url = (String) PropertiesUtil.getContextProperty("edi_express_Update");
		}
		try {
			json = HttpClients.httpPost(url, jsonData);
			System.out.println("-------------------json++++" + json);
			if("1".equals(json)){
				json = "修改成功";
			} else{
				json = "修改失败";
			}
			map.put("success",true);
			map.put("msg",json);
//			HttpClients.sendResult(response,JSON.toJSONString(map));
			
			
		} catch (Exception e) {
			e.getLocalizedMessage() ;
		} 
		return JSON.toJSONString(map);
	}
}
