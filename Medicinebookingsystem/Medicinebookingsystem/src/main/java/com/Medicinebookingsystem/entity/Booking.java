package com.Medicinebookingsystem.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
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
public class Booking {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int bookingId;

	    //@Temporal(TemporalType.DATE)
	    private Date bookingDate;

	  private Double totalPriceOfBookedMedicines;

	    // Define the relationship with User and Medicine entities
	    @ManyToOne
	    @JoinColumn(name = "Booking_user_id")
	    private User user;
	     

	    public Booking(User user) {
	        this.user = user;
	        this.bookingDate = new Date(System.currentTimeMillis());
	    }
	    
		
	    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<BookedDetails> bookedMedicines = new ArrayList<>();
		 
		/*
		 * @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
		 * 
		 * @JoinTable( name = "booking_medicine", joinColumns = @JoinColumn(name =
		 * "booking_id"), inverseJoinColumns = @JoinColumn(name = "medicine_id") )
		 * private List<Medicine> bookedMedicines = new ArrayList<>();
		 */
}
