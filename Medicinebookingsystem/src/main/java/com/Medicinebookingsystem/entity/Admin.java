package com.Medicinebookingsystem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int adminId;
	@NotBlank(message = "Username is required")
	private String adminName;
	 @NotBlank(message = "Password is required")
	 @Size(min = 6, message = "Password must be at least 6 characters long")
	private String adminPassword;
	
	 @JsonIgnore
	 @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Medicine> medicines = new ArrayList<>();

	
}
