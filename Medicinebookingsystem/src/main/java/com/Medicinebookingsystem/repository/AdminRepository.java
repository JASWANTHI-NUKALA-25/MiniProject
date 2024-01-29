package com.Medicinebookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.User;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	public Admin findByAdminName(String adminName);

}
