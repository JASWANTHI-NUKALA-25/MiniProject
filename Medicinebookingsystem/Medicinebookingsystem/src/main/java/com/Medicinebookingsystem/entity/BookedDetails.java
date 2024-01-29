package com.Medicinebookingsystem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

 

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

//    (cascade = CascadeType.ALL) 

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

 

    private int medicineQuantity;

    //private int price;

    

}
