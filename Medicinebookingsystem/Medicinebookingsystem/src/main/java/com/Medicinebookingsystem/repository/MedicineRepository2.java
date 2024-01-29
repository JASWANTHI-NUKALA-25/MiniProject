package com.Medicinebookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medicinebookingsystem.entity.Medicine;

@Repository
public interface MedicineRepository2 extends JpaRepository<Medicine, Integer> {
	public List<Medicine> findByMedicineName(String medicineName);
}
