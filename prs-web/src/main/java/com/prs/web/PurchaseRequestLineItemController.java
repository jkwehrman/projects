package com.prs.web;

import java.util.Date;
import java.util.Optional;

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
import com.prs.business.Product;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.business.User;
import com.prs.db.PurchaseRequestLineItemRepository;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.ProductRepository;

@RestController
@RequestMapping("/purchaseRequestLineItem")
public class PurchaseRequestLineItemController {

	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepo;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	@Autowired
	private ProductRepository productRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr=JsonResponse.getInstance(purchaseRequestLineItemRepo.findAll());
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
			Optional<PurchaseRequestLineItem> u = purchaseRequestLineItemRepo.findById(id);
			if(u.isPresent())
				jr=JsonResponse.getInstance(u);
			else
				jr=JsonResponse.getInstance("No purchase request line item found for id: "+ id);
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		try {
			jr=JsonResponse.getInstance(purchaseRequestLineItemRepo.save(u));
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/{id}")
	public JsonResponse update(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		//May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestLineItemRepo.existsById(u.getId())) {
				jr=JsonResponse.getInstance(purchaseRequestLineItemRepo.save(u));
			}
			else {
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse delete(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		//May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestLineItemRepo.existsById(u.getId())) {
				purchaseRequestLineItemRepo.delete(u);
				jr=JsonResponse.getInstance("Purchase Request Line Item deleted.");
			}
			else {
				jr=JsonResponse.getInstance("PurchaseRequestLineItem id:  "+u.getId()+"does not exist and you are attemping to save it.");
			}
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/lines-for-pr/{id}")
	public JsonResponse getByPRid(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr=JsonResponse.getInstance(purchaseRequestLineItemRepo.findByPurchaseRequestID(id));
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/purchase-request-line-items")
	public JsonResponse addPRLI(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		try {
			
			jr=JsonResponse.getInstance(purchaseRequestLineItemRepo.save(u));
			int prid = u.getPurchaseRequestID();
			int q = u.getQuantity();
			int pid = u.getProductID();
			Optional<PurchaseRequest> opr = purchaseRequestRepo.findById(prid);
			PurchaseRequest pr = opr.get();
			double t = pr.getTotal();
			Optional<Product> op = productRepo.findById(pid);
			Product p = op.get();
			double itemp = p.getPrice();			
			Double newT = t+(q*itemp);  
			pr.setTotal(newT);
			purchaseRequestRepo.save(pr);
			jr=JsonResponse.getInstance("Line Item was added and the total $" + newT + " was updated.");
		}
		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping("/purchase-request-line-items/{id}")
	public JsonResponse deletePRLI(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		try {
			// 1st step get parent purchase request 
			int prid = u.getPurchaseRequestID();
			Optional<PurchaseRequest> opr = purchaseRequestRepo.findById(prid);
			PurchaseRequest pr = opr.get();
			// 2nd step get old total
			double oldT = pr.getTotal();
			// 3rd step get product
			int pid = u.getProductID();
			Optional<Product> op = productRepo.findById(pid);
			// 4th step get product/price
			Product p = op.get();
			double itemP = p.getPrice();
			// 5th step calculate new total
			int q = u.getQuantity();
			double newT = oldT - (q * itemP  );
			// 6th step delete prli

			purchaseRequestLineItemRepo.delete(u);
			// 7th step update pr
			pr.setTotal(newT);
			purchaseRequestRepo.save(pr);
			jr=JsonResponse.getInstance("Line Item was deleted and the total $" + newT + " was updated.");

		}

		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/purchase-request-line-items/{id}")
	public JsonResponse changePRLIQ(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		try {
			// step 5 get old PRLI
			Optional<PurchaseRequestLineItem> optional_oldPRLI = purchaseRequestLineItemRepo.findById(u.getId());
			PurchaseRequestLineItem oldPRLI = optional_oldPRLI.get();
			// 1st step get PRLIQ 
			int newQ = u.getQuantity();
			// 2nd step get PRid
			int prid = u.getPurchaseRequestID();
			// 3rd step get total
			Optional<PurchaseRequest> opr = purchaseRequestRepo.findById(prid);
			PurchaseRequest pr = opr.get();
			double t = pr.getTotal();
			// 4th step get product
			int pid = u.getProductID();
			Optional<Product> op = productRepo.findById(pid);
			// 4th step get product/price
			Product p = op.get();
			double itemP = p.getPrice();
			// 6th step setQuantity
			purchaseRequestLineItemRepo.save(u);
			// 7th step calculate new total
			double newT = pr.getTotal() - (itemP * oldPRLI.getQuantity()) + (itemP * newQ);
			pr.setTotal(newT);
			purchaseRequestRepo.save(pr);
			jr=JsonResponse.getInstance("The quantity of line Item was changed and the total $" + newT + " was updated.");

		}

		catch (Exception e ) {
			jr=JsonResponse.getInstance(e);
		}
		return jr;
	}

	
}
