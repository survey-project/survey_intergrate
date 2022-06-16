package com.JEB.survey.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.JEB.survey.model.SurveyDto;
import com.JEB.survey.model.SurvqustDto;
import com.JEB.survey.service.RegSurvService;
import com.JEB.survey.service.RegSurvServiceImpl;

@Controller
public class SurvController {
	
	@Autowired
	private RegSurvService regSurvService;
	
	/*
	 * 설문 만들기 View
	 */
	@RequestMapping("/regSurvForm")
	public String regSurvForm(@AuthenticationPrincipal UserDetails userInfo, Model model) {
		String memNick = regSurvService.getUserInfo(userInfo.getUsername());
		System.out.println("==memNick ==> "+memNick);
		model.addAttribute("memNick",memNick);
		return "survey/regSurv";
	}

	/*
	 * 설문 만들기 
	*/
	@RequestMapping("/regSurv")
	@ResponseBody
	public String regSurv(@RequestBody SurveyDto surveyDto
			, @AuthenticationPrincipal UserDetails userInfo) throws Exception {	
		System.out.println("===regSurv Controller START");
		
		surveyDto.setRegId(userInfo.getUsername());
		System.out.println("surveyDto ==> "+surveyDto.toString());
		regSurvService.insertSurv(surveyDto);
		
		System.out.println("===regSurv Controller END===");
		return "survey/regSurv";
	}
	
	/*
	 * My Survey > 설문 수정하기 View
	 */
	@RequestMapping("/modSurvForm")
	public String modSurv(@RequestParam(value="survNo") Integer survNo
						 , @AuthenticationPrincipal UserDetails userInfo
						 , Model model) {
		System.out.println("===modSurv Controller START===");
		String memNick = regSurvService.getUserInfo(userInfo.getUsername());
		
		System.out.println("수정할 survNo : "+survNo+" memNick :"+memNick);
		
		SurveyDto surveyDto = regSurvService.getSurvey(survNo);
		System.out.println("뿌려줄 surveyDto ==> "+surveyDto.toString());
		
		surveyDto.setMemNick(memNick);
		
		model.addAttribute("surveyDto",surveyDto);
		
		System.out.println("===modSurv Controller END===");
		return "survey/modSurv";
	}
	
	/*
	 * 설문 삭제하기
	 */
	@RequestMapping("/delSurv")
	public String delSurv(@AuthenticationPrincipal UserDetails userInfo
						  ,@RequestParam Map<String,String> param) {
		System.out.println("delSurv Controller START");
		
		int survNo = Integer.parseInt(param.get("survNo"));
		
		String memNick = regSurvService.getUserInfo(userInfo.getUsername());
		System.out.println("survNo : "+survNo+"  memNick : "+memNick);

		regSurvService.delOneSurvey(survNo);
		
		return "redirect:myList";
	}
	
	/*
	 * 설문 수정하기
	 */
	@RequestMapping("/updateSurv")
	@ResponseBody
	public void updateSurv(@AuthenticationPrincipal UserDetails userInfo
						  ,@RequestBody SurveyDto surveyDto) {
		System.out.println("updateSurv controller START");
		surveyDto.setRegId(userInfo.getUsername());
		
		int survNo = surveyDto.getSurvNo();
		regSurvService.delOldSurvey(survNo);
		regSurvService.insertSurv(surveyDto);
		
		System.out.println("updateSurv controller END");
	}
	
	/*
	 * 작성자 : Bonnie 리스트 응답 폼 보여주기 View
	 * */
	@RequestMapping("/resForm")
	public String resForm(@RequestParam(value="survNo") Integer survNo
			, @AuthenticationPrincipal UserDetails userInfo
			 , Model model) {
		String memNick = regSurvService.getUserInfo(userInfo.getUsername());
		SurveyDto surveyDto = regSurvService.getSurvey(survNo);
		surveyDto.setMemNick(memNick);
		model.addAttribute("surveyDto",surveyDto);		
		return "/survey/resSurv";
	}
}
