package com.saudi.sheeps.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.ErrorsDAO;
import com.saudi.sheeps.dao.LookupsDAO;
import com.saudi.sheeps.dto.LookupsDTO;
import com.saudi.sheeps.entity.BugsAndError;
import com.saudi.sheeps.entity.Lookups;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class LookupsService {
	
	@Autowired
	private LookupsDAO lookupsDAO;
	@Autowired
	private ErrorsDAO errorsDAO ;
	
	public String getAllLookupsByType(String type) throws BusinessException{
		try {
			return new Gson().toJson(mapToDTOList(lookupsDAO.findAllByType(type)));
		}catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllLookupsByType",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public LookupsDTO mapToDTO( Lookups lookup ) throws ParseException {
		LookupsDTO dto = new LookupsDTO();
		dto.setNameEN(lookup.getNameEN());
		dto.setNameAR(lookup.getNameAR());
		dto.setType(lookup.getType());
		dto.setCode(lookup.getCode());
		return dto;
	}
	
	public List<LookupsDTO> mapToDTOList(List<Lookups> lookups){
		List<LookupsDTO> LookupsDTO = new ArrayList<>();
		for(Lookups lookup : lookups) {
			try {
				LookupsDTO.add(mapToDTO(lookup));
			} catch (ParseException e) {
				errorsDAO.save(new BugsAndError(LocalDateTime.now(),"mapToLookupsDTOList",e.getMessage()));
				e.printStackTrace();
			}
		}
		return LookupsDTO;
	}

}
