package com.saudi.sheeps.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


public class LabelsDataMapper {
	
	public static Map<String, List<?>> toLabelsDataMap(List<List> table) {
		Map<String, List<?>> labelsDataMap = new HashMap<>();
		List labels = new ArrayList<>();
		List data = new ArrayList<>();
		
		labelsDataMap.put("labels", labels);
		labelsDataMap.put("data", data);
		
		for (List row : table) {
			labels.add(row.get(1));
			data.add(row.get(0));
		}
		
		return labelsDataMap;
	}
	

}
