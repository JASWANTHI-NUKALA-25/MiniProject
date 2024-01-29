package com.Medicinebookingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.Medicinebookingsystem.entity.Medicine;
@Service
public interface MedicineService {
	

    public List<Medicine> createMedicine(List<Medicine> medicines,int adminId);
    public abstract  Medicine updateMedicine(int medicineId,Medicine medicine,int adminId);
    public List<Medicine> getMedicinesByUserId(int userId) ;
    public List<Medicine> findAll(int adminId);
    public List<Medicine> findByName(String medicineName);
    public List<Medicine> findAll();
}
