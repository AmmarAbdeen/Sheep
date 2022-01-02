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
import com.saudi.sheeps.dao.ErrorsDAO;
import com.saudi.sheeps.dao.LookupsDAO;
import com.saudi.sheeps.dao.PlacesDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.entity.BugsAndError;
import com.saudi.sheeps.entity.Lookups;
import com.saudi.sheeps.entity.Places;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class SheepService {

	@Autowired
	private SheepDAO sheepDAO;
	@Autowired
	private PlacesService placesService;
	@Autowired
	private LookupsDAO lookupsDAO;
	@Autowired
	private PlacesDAO placesDAO;
	@Autowired
	private ErrorsDAO errorsDAO ;
	@PersistenceContext
	public EntityManager em;

	public SheepDTO addNewSheep(SheepDTO sheepRequest) throws BusinessException {
		try {
			log.info("Enter addNewSheep Function..with SheepCode= " + sheepRequest.getCode());
			if (sheepRequest.getCode() == null) {
				throw new BusinessException("Sheep code is required");
			}
			if (sheepRequest.getColor() == null || sheepRequest.getColor().isEmpty()) {
				throw new BusinessException("Sheep color is required");
			}
			if (sheepRequest.getWeight() == null || sheepRequest.getWeight().isEmpty()
					|| sheepRequest.getWeight().equals("0")) {
				throw new BusinessException("Sheep color is required and mustn't be zero");
			}
			if (sheepRequest.getBirthDate() == null || sheepRequest.getBirthDate().isEmpty()) {
				throw new BusinessException("Birth date of sheep is required");
			}
			if (sheepRequest.getArrivalDate() == null || sheepRequest.getArrivalDate().isEmpty()) {
				throw new BusinessException("Arrival Date of sheep is required ");
			}
			if (sheepRequest.getType() == null || sheepRequest.getType().isEmpty()) {
				throw new BusinessException("Type of sheep is required ");
			}
			if (sheepRequest.getPlace() == null) {
				throw new BusinessException("place of sheep is required ");
			}
			Sheep sheep = sheepDAO.findByCodeAndColor(sheepRequest.getCode(), sheepRequest.getColor());
			if (sheep != null) {
				throw new BusinessException("This sheep is already added");
			}
			Sheep sheepSaved = mapToEntity(sheepRequest);
			sheepDAO.save(sheepSaved);
			return sheepRequest;

		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"addNewSheep",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	public SheepDTO updateSheep(SheepDTO sheepRequest) throws BusinessException {
		try {
			log.info("Enter updateSheep Function..with SheepCode= " + sheepRequest.getCode());
			if (sheepRequest.getCode() == null) {
				throw new BusinessException("Sheep code is required");
			}
			if (sheepRequest.getColor() == null || sheepRequest.getColor().isEmpty()) {
				throw new BusinessException("Sheep color is required");
			}
			if (sheepRequest.getWeight() == null || sheepRequest.getWeight().isEmpty()
					|| sheepRequest.getWeight().equals("0")) {
				throw new BusinessException("Sheep color is required and mustn't be zero");
			}
			if (sheepRequest.getBirthDate() == null || sheepRequest.getBirthDate().isEmpty()) {
				throw new BusinessException("Birth date of sheep is required");
			}
			if (sheepRequest.getArrivalDate() == null || sheepRequest.getArrivalDate().isEmpty()) {
				throw new BusinessException("Arrival Date of sheep is required ");
			}
			if (sheepRequest.getType() == null || sheepRequest.getType().isEmpty()) {
				throw new BusinessException("Type of sheep is required ");
			}
			if (sheepRequest.getPlace() == null) {
				throw new BusinessException("place of sheep is required ");
			}
			Sheep sheep = sheepDAO.findById(sheepRequest.getId()).get();
			saveChangedDTOInEntity(sheep,sheepRequest);
			sheepDAO.save(sheep);
			return sheepRequest;

		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"updateSheep",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	private void saveChangedDTOInEntity(Sheep sheep,SheepDTO sheepDTO) throws Exception {
		sheep.setCode(sheepDTO.getCode());
		sheep.setColor(sheepDTO.getColor());
		sheep.setAdvantages(sheepDTO.getAdvantages());
		sheep.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd").parse(sheepDTO.getArrivalDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		sheep.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(sheepDTO.getBirthDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		sheep.setDisadvantages(sheepDTO.getDisadvantages());
		sheep.setNamed(sheepDTO.getNamed());
		sheep.setNotes(sheepDTO.getNotes());
		sheep.setShape(sheepDTO.getShape());
		sheep.setStatus(sheepDTO.getStatus());
		sheep.setType(sheepDTO.getType());
		sheep.setWeight(sheepDTO.getWeight());
		Places places = placesDAO.findById(sheepDTO.getPlace().getId()).get();
		sheep.setPlaces(places);
		
	}
	
	
	public String getSheepBySpecificData(SheepDTO sheepDTO) throws BusinessException {
		try {
			if(sheepDTO.getCode() ==null) {
				throw new BusinessException("Sheep code mustn't be null");
			}
			if(sheepDTO.getColor() == null || sheepDTO.getColor().isEmpty()) {
				throw new BusinessException("Sheep color mustn't be null or empty");
			}
			Sheep sheep = sheepDAO.findByCodeAndColor(sheepDTO.getCode(), sheepDTO.getColor());
			return new Gson().toJson(mapToDTO(sheep));
			
		}catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getSheepBySpecificData",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public String sheepsSearch(SheepDTO sheepDTO) throws BusinessException {
		try {
			Map<String, Object> params = new HashMap();
			StringBuilder query = new StringBuilder();
			query.append("SELECT s FROM Sheep s WHERE s.id > 0 ");
			if (sheepDTO.getCode() != null) {
	            params.put("code", sheepDTO.getCode());
	            query.append(" and s.code = :code ");
	        }
			if (sheepDTO.getColor() != null && !sheepDTO.getColor().isEmpty()) {
	            params.put("color", sheepDTO.getColor());
	            query.append(" and s.color = :color ");
	        }
			if (sheepDTO.getFromBirthDate() != null && !sheepDTO.getFromBirthDate().isEmpty()) {
				LocalDate fromBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(sheepDTO.getFromBirthDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("fromBirthDate", fromBirthDate);
	            query.append(" and s.birthDate >= :fromBirthDate ");
	        }
			if (sheepDTO.getToBirthDate() != null && !sheepDTO.getToBirthDate().isEmpty()) {
				LocalDate toBirthDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sheepDTO.getToBirthDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("toBirthDate", toBirthDate);
	            query.append(" and s.birthDate <= :toBirthDate ");
	        }
			if (sheepDTO.getFromArrivalDate() != null && !sheepDTO.getFromArrivalDate().isEmpty()) {
				LocalDate fromArrivalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sheepDTO.getFromArrivalDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("fromArrivalDate", fromArrivalDate);
	            query.append(" and s.arrivalDate >= :fromArrivalDate ");
	        }
			if (sheepDTO.getToArrivalDate() != null && !sheepDTO.getToArrivalDate().isEmpty()) {
				LocalDate toArrivalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sheepDTO.getToArrivalDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
	            params.put("toArrivalDate", toArrivalDate);
	            query.append(" and s.arrivalDate <= :toArrivalDate ");
	        }
			if (sheepDTO.getWeight() != null && !sheepDTO.getWeight().isEmpty()) {
	            params.put("weight", sheepDTO.getWeight());
	            query.append(" and s.weight = :weight ");
	        }
			if (sheepDTO.getShape() != null && !sheepDTO.getShape().isEmpty()) {
	            params.put("shape", sheepDTO.getShape());
	            query.append(" and s.shape = :shape ");
	        }
			if (sheepDTO.getStatus() != null && !sheepDTO.getStatus().isEmpty()) {
	            params.put("status", sheepDTO.getStatus());
	            query.append(" and s.status = :status ");
	        }
			if (sheepDTO.getAge() != 0 ) {
				LocalDate age = LocalDate.now().minusDays(sheepDTO.getAge());			
	            params.put("age", age);
	            query.append(" and s.birthDate <= :age ");
	        }
            List<Sheep> sheeps = getSheeps(em, query.toString(),params);
			return new Gson().toJson(mapToDTOList(sheeps));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"sheepsSearch",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	private List<Sheep> getSheeps(EntityManager em, String q,Map<String, Object> params) {
		TypedQuery<Sheep> query = em.createQuery(q, Sheep.class);
		if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
		return query.getResultList();
	}

	public String getEwes() {
		List<Sheep> sheeps = sheepDAO.findAllByType("ewe");
		return new Gson().toJson(mapToDTOList(sheeps));
	}
	
	public String getAllSheeps() {
		List<Sheep> sheeps = sheepDAO.findAll();
		return new Gson().toJson(mapToDTOList(sheeps));
	}
	
	public Sheep mapToEntity(SheepDTO sheepDTO) throws ParseException {
		Sheep sheep = new Sheep();
		sheep.setCode(sheepDTO.getCode());
		sheep.setColor(sheepDTO.getColor());
		sheep.setAdvantages(sheepDTO.getAdvantages());
		sheep.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd").parse(sheepDTO.getArrivalDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		sheep.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(sheepDTO.getBirthDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		sheep.setDisadvantages(sheepDTO.getDisadvantages());
		sheep.setNamed(sheepDTO.getNamed());
		sheep.setNotes(sheepDTO.getNotes());
		sheep.setShape(sheepDTO.getShape());
		sheep.setStatus(sheepDTO.getStatus());
		sheep.setType(sheepDTO.getType());
		sheep.setWeight(sheepDTO.getWeight());
		Places places = placesDAO.findById(sheepDTO.getPlace().getId()).get();
		sheep.setPlaces(places);
		return sheep;
	}

	public SheepDTO mapToDTO(Sheep sheep) throws ParseException {
		SheepDTO sheepDTO = new SheepDTO();
		sheepDTO.setId(sheep.getId());
		sheepDTO.setCode(sheep.getCode());
		Lookups lookups = lookupsDAO.findByNameAR(sheep.getColor());
		sheepDTO.setColor(lookups.getNameAR());
		sheepDTO.setAdvantages(sheep.getAdvantages());
		int birthDate =(int)sheep.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		int now =(int)LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		sheepDTO.setAge((((now - birthDate)/60)/60)/24);
		sheepDTO.setArrivalDate(sheep.getArrivalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		sheepDTO.setBirthDate(sheep.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		sheepDTO.setDisadvantages(sheep.getDisadvantages());
		sheepDTO.setNamed(sheep.getNamed());
		sheepDTO.setNotes(sheep.getNotes());
		sheepDTO.setShape(sheep.getShape());
		sheepDTO.setStatus(sheep.getStatus());
		sheepDTO.setType(sheep.getType());
		sheepDTO.setWeight(sheep.getWeight());
		sheepDTO.setPlace(placesService.mapToDTO(sheep.getPlaces()));
		return sheepDTO;
	}
	
	public List<SheepDTO> mapToDTOList(List<Sheep> sheeps){
		List<SheepDTO> sheepDTOs = new ArrayList<>();
		for(Sheep sheep : sheeps) {
			try {
				sheepDTOs.add(mapToDTO(sheep));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return sheepDTOs;
	}

}
