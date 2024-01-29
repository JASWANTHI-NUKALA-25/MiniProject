package com.Medicinebookingsystem.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int medicineId;

	private String medicineName;
	private String medicineDescription;
	private int medicineQuantity;
	private int medicinePrice;
	
	/*
	 * @JsonIgnore
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "user_id") private User user;
	 */
	  @ManyToOne
	  @JsonIgnore
	    @JoinColumn(name = "admin_id")
	    private Admin admin;
	
	
}
