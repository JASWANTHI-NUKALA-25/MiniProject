package com.Medicinebookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medicinebookingsystem.entity.BookedDetails;

@Repository
public interface BookedDetailsRepository extends JpaRepository<BookedDetails, Integer>{

}
