package com.Medicinebookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Medicine;
@Service
public interface MedicineService {
	

    public List<Medicine> createMedicine(List<Medicine> medicines,int adminId);
    public abstract  Medicine updateMedicine(int medicineId,Medicine medicine,int adminId);
    public List<Medicine> getMedicinesByUserId(int userId) ;
    public List<Medicine> findAll();
    public List<Medicine> findByName(String medicineName);
    public void deleteMedicine(int medicineId);
    public Optional<Medicine> getMedicineById(int medicineId);
     
  
}