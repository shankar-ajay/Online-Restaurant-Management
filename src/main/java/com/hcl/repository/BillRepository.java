package com.hcl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hcl.entity.Bill;

public interface BillRepository extends CrudRepository<Bill,Integer> {

	public List<Bill> findByDate(LocalDate date);
}
