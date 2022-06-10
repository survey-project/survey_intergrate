package com.JEB.survey.service;

import java.util.List;

import com.JEB.survey.model.Pagination;
import com.JEB.survey.model.SurveyDto;

public interface SurvListService {
	public List<SurveyDto> getSurvList(Pagination pagination);
	public int getListCnt();
}
