package com.saudi.sheeps.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.LambsDAO;
import com.saudi.sheeps.dao.PlacesDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dto.LambsDTO;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.entity.Lambs;
import com.saudi.sheeps.entity.Places;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class LambsService {
	
	@Autowired
	private LambsDAO lambsDAO;
	@Autowired
	private SheepService sheepService;
	@Autowired
	private SheepDAO sheepDAO;
	@PersistenceContext
	public EntityManager em;
	@Autowired
	private PlacesService placesService;
	@Autowired
	private PlacesDAO placesDAO;
	
	public LambsDTO addNewLamb(LambsDTO lambsRequest)throws BusinessException{
		try {
			log.info("Enter addNewLamb Function..with SheepCode= " + lambsRequest.getCode());
			if (lambsRequest.getCode() == null) {
				throw new BusinessException("Lambs code is required");
			}
			if (lambsRequest.getColor() == null || lambsRequest.getColor().isEmpty()) {
				throw new BusinessException("Lambs color is required");
			}
			if (lambsRequest.getBirthDate() == null || lambsRequest.getBirthDate().isEmpty()) {
				throw new BusinessException("Birth date of Lambs is required");
			}
			if (lambsRequest.getDateOfMating() == null || lambsRequest.getDateOfMating().isEmpty()) {
				throw new BusinessException("Mating Date of Lambs is required ");
			}
			if (lambsRequest.getType() == null || lambsRequest.getType().isEmpty()) {
				throw new BusinessException("Type of Lambs is required ");
			}
			if (lambsRequest.getPlace() == null) {
				throw new BusinessException("PLace of Lambs is required ");
			}
			Sheep sheep = sheepDAO.findByCodeAndColor(lambsRequest.getSheepDTO().getCode(), lambsRequest.getSheepDTO().getColor());
			if (sheep == null) {
				throw new BusinessException("The lamb mother not exist");
			}
			Lambs lambs = lambsDAO.findByCodeAndColor(lambsRequest.getCode(), lambsRequest.getColor());
			if (lambs != null) {
				throw new BusinessException("This Lambs is already added");
			}
			Lambs lambsSaved = mapToEntity(lambsRequest);
			lambsDAO.save(lambsSaved);
			return lambsRequest;
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	public String getLambBySpecificData(LambsDTO lambDTO) throws BusinessException {
		try {
			if(lambDTO.getCode() ==null) {
				throw new BusinessException("Lamb code mustn't be null");
			}
			if(lambDTO.getColor() == null || lambDTO.getColor().isEmpty()) {
				throw new BusinessException("Lamb color mustn't be null or empty");
			}
			Lambs lamb= lambsDAO.findByCodeAndColor(lambDTO.getCode(), lambDTO.getColor());
			return new Gson().toJson(mapToDTO(lamb));
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public LambsDTO updateLamb(LambsDTO lambDTO) throws BusinessException {
		try {
			log.info("Enter updateLamb Function..with LambCode= " + lambDTO.getCode());
			if (lambDTO.getCode() == null) {
				throw new BusinessException("Lambs code is required");
			}
			if (lambDTO.getColor() == null || lambDTO.getColor().isEmpty()) {
				throw new BusinessException("Lambs color is required");
			}
			if (lambDTO.getBirthDate() == null || lambDTO.getBirthDate().isEmpty()) {
				throw new BusinessException("Birth date of Lambs is required");
			}
			if (lambDTO.getDateOfMating() == null || lambDTO.getDateOfMating().isEmpty()) {
				throw new BusinessException("Mating Date of Lambs is required ");
			}
			if (lambDTO.getType() == null || lambDTO.getType().isEmpty()) {
				throw new BusinessException("Type of Lambs is required ");
			}
			if (lambDTO.getPlace() == null) {
				throw new BusinessException("PLace of Lambs is required ");
			}
			Sheep sheep = sheepDAO.findByCodeAndColor(lambDTO.getSheepDTO().getCode(), lambDTO.getSheepDTO().getColor());
			if (sheep == null) {
				throw new BusinessException("The lamb mother not exist");
			}
			Lambs lamb = lambsDAO.findById(lambDTO.getId()).get();
			saveChangedDTOInEntity(lamb,lambDTO);
			lambsDAO.save(lamb);
			return lambDTO;

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	private void saveChangedDTOInEntity(Lambs lamb,LambsDTO lambDTO) throws Exception {
		lamb.setCode(lambDTO.getCode());
		lamb.setColor(lambDTO.getColor());
		lamb.setAdvantages(lambDTO.getAdvantages());
		lamb.setMatingDate(new SimpleDateFormat("yyyy-MM-dd").parse(lambDTO.getDateOfMating()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		lamb.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(lambDTO.getBirthDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		lamb.setDisadvantages(lambDTO.getDisadvantages());
		lamb.setNamed(lambDTO.getNamed());
		lamb.setNotes(lambDTO.getNotes());
		lamb.setShape(lambDTO.getShape());
		lamb.setStatus(lambDTO.getStatus());
		lamb.setWeaningWeight(lambDTO.getWeightOfWeaning());
		lamb.setSellingWeight(lambDTO.getWeightAtSale());
		lamb.setBirthWeight(lambDTO.getWeightOfBirth());
		lamb.setBirthWeightSheep(lambDTO.getWeigthOfSheep());
		lamb.setType(lambDTO.getType());
		Sheep sheep = sheepDAO.findByCodeAndColor(lambDTO.getSheepDTO().getCode(),lambDTO.getSheepDTO().getColor());
		lamb.setSheep(sheep);
		Places places = placesDAO.findById(lambDTO.getPlace().getId()).get();
		lamb.setPlaces(places);
		
	}
	
	public String lambsSearch(LambsDTO lambsRequest) throws BusinessException {
		try {
			Map<String, Object> params = new HashMap();
			StringBuilder query = new StringBuilder();
			query.append("SELECT l FROM Lambs l WHERE l.id > 0 ");
			if (lambsRequest.getCode() != null) {
	            params.put("code", lambsRequest.getCode());
	            query.append(" and l.code = :code ");
	        }
			if (lambsRequest.getColor() != null && !lambsRequest.getColor().isEmpty()) {
	            params.put("color", lambsRequest.getColor());
	            query.append(" and l.color = :color ");
	        }
			if (lambsRequest.getFromBirthDate() != null && !lambsRequest.getFromBirthDate().isEmpty()) {
				LocalDate fromBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(lambsRequest.getFromBirthDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("fromBirthDate", fromBirthDate);
	            query.append(" and l.birthDate >= :fromBirthDate ");
	        }
			if (lambsRequest.getToBirthDate() != null && !lambsRequest.getToBirthDate().isEmpty()) {
				LocalDate toBirthDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lambsRequest.getToBirthDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("toBirthDate", toBirthDate);
	            query.append(" and l.birthDate <= :toBirthDate ");
	        }
			if (lambsRequest.getFromMatingDate() != null && !lambsRequest.getFromMatingDate().isEmpty()) {
				LocalDate fromMatingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lambsRequest.getFromMatingDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("fromMatingDate", fromMatingDate);
	            query.append(" and l.matingDate >= :fromMatingDate ");
	        }
			if (lambsRequest.getToMatingDate() != null && !lambsRequest.getToMatingDate().isEmpty()) {
				LocalDate toMatingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lambsRequest.getToMatingDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("toMatingDate", toMatingDate);
	            query.append(" and l.matingDate <= :toMatingDate ");
	        }
			if (lambsRequest.getWeightOfBirth() != null && !lambsRequest.getWeightOfBirth().isEmpty()) {
	            params.put("weightOfBirth", lambsRequest.getWeightOfBirth());
	            query.append(" and l.birthWeight = :weightOfBirth ");
	        }
			if (lambsRequest.getWeightOfWeaning() != null && !lambsRequest.getWeightOfWeaning().isEmpty()) {
	            params.put("weightOfWeaning", lambsRequest.getWeightOfWeaning());
	            query.append(" and l.weaningWeight = :weightOfWeaning ");
	        }
			if (lambsRequest.getWeightAtSale() != null && !lambsRequest.getWeightAtSale().isEmpty()) {
	            params.put("weightAtSale", lambsRequest.getWeightAtSale());
	            query.append(" and l.sellingWeight = :weightAtSale ");
	        }
			if (lambsRequest.getStatus() != null && !lambsRequest.getStatus().isEmpty()) {
	            params.put("status", lambsRequest.getStatus());
	            query.append(" and l.status = :status ");
	        }
			if (lambsRequest.getSheepDTO() != null ) {
				if(lambsRequest.getSheepDTO().getCode() != null && lambsRequest.getSheepDTO().getColor() != null ) {
	            params.put("sheepCode", lambsRequest.getSheepDTO().getCode());
	            params.put("sheepColor", lambsRequest.getSheepDTO().getColor());
	            query.append(" and l.sheep.code = :sheepCode and l.sheep.color = :sheepColor ");
	        }}
			if (lambsRequest.getAge() != 0 ) {
				LocalDate age = LocalDate.now().minusDays(lambsRequest.getAge());			
	            params.put("age", age);
	            query.append(" and l.birthDate <= :age ");
	        }
            List<Lambs> lambs = getLambs(em, query.toString(),params);
			return new Gson().toJson(mapToDTOList(lambs));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	private List<Lambs> getLambs(EntityManager em, String q,Map<String, Object> params) {
		TypedQuery<Lambs> query = em.createQuery(q, Lambs.class);
		if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
		return query.getResultList();
	}
	
	
	public Lambs mapToEntity(LambsDTO lambDTO) throws ParseException {
		Lambs lamb = new Lambs();
		lamb.setCode(lambDTO.getCode());
		lamb.setColor(lambDTO.getColor());
		lamb.setAdvantages(lambDTO.getAdvantages());
		lamb.setMatingDate(new SimpleDateFormat("yyyy-MM-dd").parse(lambDTO.getDateOfMating()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		lamb.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(lambDTO.getBirthDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		lamb.setDisadvantages(lambDTO.getDisadvantages());
		lamb.setNamed(lambDTO.getNamed());
		lamb.setNotes(lambDTO.getNotes());
		lamb.setShape(lambDTO.getShape());
		lamb.setStatus(lambDTO.getStatus());
		lamb.setWeaningWeight(lambDTO.getWeightOfWeaning());
		lamb.setSellingWeight(lambDTO.getWeightAtSale());
		lamb.setBirthWeight(lambDTO.getWeightOfBirth());
		lamb.setBirthWeightSheep(lambDTO.getWeigthOfSheep());
		lamb.setType(lambDTO.getType());
		Sheep sheep = sheepDAO.findByCodeAndColor(lambDTO.getSheepDTO().getCode(),lambDTO.getSheepDTO().getColor());
		lamb.setSheep(sheep);
		Places places = placesDAO.findById(lambDTO.getPlace().getId()).get();
		lamb.setPlaces(places);
		return lamb;
	}

	public LambsDTO mapToDTO(Lambs lamb) throws ParseException {
		LambsDTO lambDTO = new LambsDTO();
		lambDTO.setId(lamb.getId());
		lambDTO.setCode(lamb.getCode());
		lambDTO.setColor(lamb.getColor());
		lambDTO.setAdvantages(lamb.getAdvantages());
		int birthDate =(int)lamb.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		int now =(int)LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		lambDTO.setAge((((now - birthDate)/60)/60)/24);
		lambDTO.setDateOfMating(lamb.getMatingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		lambDTO.setBirthDate(lamb.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		lambDTO.setDisadvantages(lamb.getDisadvantages());
		lambDTO.setNamed(lamb.getNamed());
		lambDTO.setNotes(lamb.getNotes());
		lambDTO.setShape(lamb.getShape());
		lambDTO.setStatus(lamb.getStatus());
		lambDTO.setType(lamb.getType());
		lambDTO.setWeigthOfSheep(lamb.getBirthWeightSheep());
		lambDTO.setWeightOfBirth(lamb.getBirthWeight());
		lambDTO.setWeightAtSale(lamb.getSellingWeight());
		lambDTO.setWeightOfWeaning(lamb.getWeaningWeight());
		lambDTO.setSheepDTO(sheepService.mapToDTO(lamb.getSheep()));
		lambDTO.setPlace(placesService.mapToDTO(lamb.getPlaces()));
		return lambDTO;
	}
	
	public List<LambsDTO> mapToDTOList(List<Lambs> lambs){
		List<LambsDTO> lambsDTOs = new ArrayList<>();
		for(Lambs lambs2 : lambs) {
			try {
				lambsDTOs.add(mapToDTO(lambs2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return lambsDTOs;
	}

	


}
