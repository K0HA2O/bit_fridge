package com.bit.mini.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDto {

	private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private Date birthdate;
    private Gender gender;
    private String healthinfo; 
    private String allergies;
    private boolean is_admin;
    private Timestamp created_at;
    
    public enum Gender {
        MALE, FEMALE
    }

    public UserDto() {
    	
    }

	public UserDto(int id, String username, String password, String email, String name, Date birthdate, Gender gender,
			String healthinfo, String allergies) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.birthdate = birthdate;
		this.gender = gender;
		this.healthinfo = healthinfo;
        this.allergies = allergies;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getHealthinfo() {
        return healthinfo;
    }

    public void setHealthinfo(String healthinfo) {
        this.healthinfo = healthinfo;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", name=" + name + ", birthdate=" + birthdate + ", gender=" + gender + ", healthinfo=" + healthinfo
				+ ", allergies=" + allergies + "]";
	}
}
