package com.prs.web;

//import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.User;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RestController
@RequestMapping("/purchaseRequest")
public class PurchaseRequestController {

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr=JsonResponse.getInstance(purchaseRequestRepo.findAll());
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> u = purchaseRequestRepo.findById(id);
			if(u.isPresent())
				jr=JsonResponse.getInstance(u);
			else
				jr=JsonResponse.getInstance("No purchase request found for id: "+id);
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		//May need to enhance exception handling if more than one exception type needs to be caught
		try { 
			jr=JsonResponse.getInstance(purchaseRequestRepo.save(u));
		}
		catch (Exception e ) {
			e.printStackTrace();
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/submit-new")
	public JsonResponse submitNew(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		u.setStatus("new");
		try {
			jr=JsonResponse.getInstance(purchaseRequestRepo.save(u));
		}
		catch (Exception e ) {
			e.printStackTrace();
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/submit-review/{id}")
	public JsonResponse SubmitForReview(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		double costLimit = 50.00;
		if (u.getTotal() < costLimit) {
			u.setStatus("Approved");	
		}
		else {
			u.setStatus("review");
		}
		Date date = new Date();
		u.setSubmittedDate(date);
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				jr=JsonResponse.getInstance(purchaseRequestRepo.save(u));
			}
			else {
				jr=JsonResponse.getInstance("PurchaseRequest id:  "+u.getId()+"does not exist and you are attemping to save it.");
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/reject/{id}")
	public JsonResponse reject(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		u.setStatus("Rejected");
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				jr=JsonResponse.getInstance(purchaseRequestRepo.save(u));
			}
			else {
				jr=JsonResponse.getInstance("PurchaseRequest id:  "+u.getId()+"does not exist and you are attemping to save it.");
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/approve/{id}")
	public JsonResponse approve(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		u.setStatus("Approved");
		u.setReasonForRejection(null);
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				jr=JsonResponse.getInstance(purchaseRequestRepo.save(u));
			}
			else {
				jr=JsonResponse.getInstance("PurchaseRequest id:  "+u.getId()+"does not exist and you are attemping to save it.");
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/{id}")
	public JsonResponse update(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		//May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				jr=JsonResponse.getInstance(purchaseRequestRepo.save(u));
			}
			else {
				jr=JsonResponse.getInstance("PurchaseRequest id:  "+u.getId()+"does not exist and you are attemping to save it.");
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse delete(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		//May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				purchaseRequestRepo.delete(u);
				jr=JsonResponse.getInstance("Purchase Request deleted.");
			}
			else {
				jr=JsonResponse.getInstance("PurchaseRequest id:  "+u.getId()+"does not exist and you are attemping to save it.");
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/list-review")
	public JsonResponse listReview() {
		JsonResponse jr = null;
		try {
			List<PurchaseRequest> pr = purchaseRequestRepo.findByStatusAndUserIDNot("review", 1);
			if(!pr.isEmpty()) {
				jr=JsonResponse.getInstance(pr);
			}	else {
				jr=JsonResponse.getInstance("No purchase request records found for status review and user ID 3.");
			}
		} 
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}
}


