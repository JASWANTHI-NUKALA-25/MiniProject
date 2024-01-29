package com.Medicinebookingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medicinebookingsystem.entity.Medicine;
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer>{
public Medicine findByMedicineId(int medicineId);
//public List<Medicine> findByMedicineName(String medicineName);
public Medicine findByMedicineName(String medicineName);
}
