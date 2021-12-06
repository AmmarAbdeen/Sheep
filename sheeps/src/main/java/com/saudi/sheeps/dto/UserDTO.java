package com.saudi.sheeps.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseDTO {
	
	private Long id;

	private LocalDateTime creationDate;

	private String fullName;

	private String email;

	private String userName;

	private String password;

	private String mobileNumber;

	private String nationalId;
	
	private String sessionToken;
	
	private List<PrivilegesDTO> privileges;


}
