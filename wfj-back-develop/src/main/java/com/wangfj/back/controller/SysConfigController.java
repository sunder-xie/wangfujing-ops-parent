package com.wangfj.back.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wangfj.back.entity.po.SysConfig;
import com.wangfj.back.entity.vo.SysConfigVO;
import com.wangfj.back.service.ISysConfigService;

/**
 * @Class Name SysConfigController
 * @Author zhangdl
 * @Create In 2016-8-30
 */

@Controller
@RequestMapping(value="sysConfig")
public class SysConfigController {
	
	@Autowired
	ISysConfigService sysConfigService;
	
	@ResponseBody
	@RequestMapping(value = "/findAll", method = {RequestMethod.GET,RequestMethod.POST})
	public String findAll(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysConfig> list = sysConfigService.selectAll();
		if(list == null || list.size() == 0){
			resultMap.put("success", false);
		} else {
			resultMap.put("data", list);
			resultMap.put("success", true);
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/findSysConfigByKeys", method = {RequestMethod.GET,RequestMethod.POST})
	public String findSysConfigByKeys(HttpServletRequest request, HttpServletResponse response,
			String keys) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(keys != null && !"".equals(keys)){
			List<String> paramKeys = new ArrayList<String>();
			
			String[] ks = keys.split(",");
			for(String s : ks){
				paramKeys.add(s);
			}
			
			List<SysConfig> list = sysConfigService.selectByKeys(paramKeys);
			if(list == null || list.size() == 0){
				resultMap.put("msg", "查询为空");
				resultMap.put("success", false);
			} else {
				List<SysConfigVO> listVO = new ArrayList<SysConfigVO>();
				for(SysConfig sc : list){
					SysConfigVO vo = new SysConfigVO();
					vo.setSysKey(sc.getSysKey());
					vo.setSysValue(sc.getSysValue());
					listVO.add(vo);
				}
				resultMap.put("data", listVO);
				resultMap.put("success", true);
			}
		} else {
			resultMap.put("msg", "查询为空");
			resultMap.put("success", false);
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/editSysConfigByKey", method = {RequestMethod.GET,RequestMethod.POST})
	public String editSysConfigByKey(HttpServletRequest request, HttpServletResponse response,
			String sid, String sysKey, String sysValue, String sysDesc) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysConfig cof = new SysConfig();
		if(sid!= null && !"".equals(sid)){
			cof.setSid(Integer.valueOf(sid));
		}
		cof.setSysKey(sysKey);
		cof.setSysValue(sysValue);
		cof.setSysDesc(sysDesc);
		try {
			boolean success = sysConfigService.saveOrEditSysConfigByKey(cof);
			if(success){
				resultMap.put("msg", "修改成功");
				resultMap.put("success", true);
			} else {
				resultMap.put("msg", "修改失败");
				resultMap.put("success", true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("msg", "系统异常");
			resultMap.put("success", false);
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveSysConfig", method = {RequestMethod.GET,RequestMethod.POST})
	public String saveSysConfigByKey(HttpServletRequest request, HttpServletResponse response,
			String sysKey, String sysValue, String sysDesc) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysConfig cof = new SysConfig();
		cof.setSysKey(sysKey);
		cof.setSysValue(sysValue);
		cof.setSysDesc(sysDesc);
		try {
			boolean success = sysConfigService.saveOrEditSysConfigByKey(cof);
			if(success){
				resultMap.put("msg", "添加成功");
				resultMap.put("success", true);
			} else {
				resultMap.put("msg", "添加失败");
				resultMap.put("success", true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("msg", "系统异常");
			resultMap.put("success", false);
		}
		return JSONObject.toJSONString(resultMap);
	}

}
