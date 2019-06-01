package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.PurchaseRequest;
import com.prs.business.User;

public interface PurchaseRequestRepository extends CrudRepository <PurchaseRequest, Integer> {

	public Optional<PurchaseRequest> findByUserID(int ID);

	public Optional<PurchaseRequest> findByStatus(String string);
	
	public List<PurchaseRequest> findByStatusAndUserIDNot(String status, Integer userID);

	public Object save(int i);

	

} 