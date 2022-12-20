package com.user.bindings;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
@Data
public class UserRegForm {

	private Integer userId;

	private String fname;

	private String lname;

	private String email;

	private String password;

	private Long phno;

	private LocalDate dob;

	private String gender;

	private Integer cityId;

	private Integer stateId;

	private Integer countryId;

}
