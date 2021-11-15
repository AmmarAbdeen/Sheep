package com.saudi.sheeps.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.LambsDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dao.StoredFeedDAO;
import com.saudi.sheeps.dto.LabelsDataMapper;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class DashbordService {

	@Autowired
	private SheepDAO sheepDAO;
	@Autowired
	private LambsDAO lambsDAO;
	@Autowired
	private StoredFeedDAO feedDAO;

	public String getAllSheepGroupByType() throws BusinessException {
		try {
			List<List> sheepsGroupByType = sheepDAO.getAllSheepsMaleAndFemale();
			return new Gson().toJson(LabelsDataMapper.toLabelsDataMap(sheepsGroupByType));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllAmountOfStoredFeed() throws BusinessException {
		try {
			List<List> amounts = feedDAO.getAllAmountOfStoredFeed();
			return new Gson().toJson(LabelsDataMapper.toLabelsDataMap(amounts));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllLambsGroupByType() throws BusinessException {
		try {

			List<List> lambsGroupByType = lambsDAO.getAllLambsMaleAndFemale();
			return new Gson().toJson(LabelsDataMapper.toLabelsDataMap(lambsGroupByType));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}


}
