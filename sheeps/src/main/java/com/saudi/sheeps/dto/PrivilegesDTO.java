package com.saudi.sheeps.dto;
import java.time.LocalDateTime;
import java.util.List;

import com.saudi.sheeps.entity.Privileges;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PrivilegesDTO extends BaseDTO{

    private Long id;
	
	private LocalDateTime creationDate;
	
	private String code;

	private String arabicName;

	private String englishName;

	private String iconPath;

	private String routerLink;

	private String backendUrl;
	
	private boolean mainPrivilege;

	private boolean adminPrivilege;

	private boolean menuItem;

	private List<PrivilegesDTO> childrenPrivilege;
	
	private Long privilegeorder;

	private Long parentPrivilegeId;
}
