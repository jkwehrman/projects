package com.prs.business;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
//	@ManyToOne(fetch=FetchType.EAGER)
  	@ManyToOne
  	@JoinColumn(name="userID")
  	private User user;
	private String description;
	private String justification;
	private Date dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	private java.util.Date LocalDate;
	private String reasonForRejection;
	private int submittedDate;
	
	public PurchaseRequest () {
	id =0;
	user =null;
	description ="";
	justification ="";
	dateNeeded =null;
	deliveryMode ="";
	status ="";
	total =0.0;
	submittedDate =2019-12-12;
	reasonForRejection ="";
	}
	
	


	public PurchaseRequest (int id, User user, String description, String justification, Date dateNeeded,
			String deliveryMode, String status, double total, 
		//	Date submittedDate, 
			String reasonForRejection,
			boolean isActive, Date dateCreated, Date dateUpdated, int updatedByUser) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		//this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
	}

	public PurchaseRequest (User user, String description, String justification, Date dateNeeded,
			String deliveryMode, String status, double total, String reasonForRejection) {
		//super();
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.reasonForRejection = reasonForRejection;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getJustification() {
		return justification;
	}


	public void setJustification(String justification) {
		this.justification = justification;
	}


	public Date getDateNeeded() {
		return dateNeeded;
	}


	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}


	public String getDeliveryMode() {
		return deliveryMode;
	}


	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public java.util.Date getSubmittedDate() {
		return submittedDate;
	}


	public void setSubmittedDate(java.util.Date date) {
		this.submittedDate = date;
	}


	public String getReasonForRejection() {
		return reasonForRejection;
	}


	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}



	@Override
	public String toString() {
		return "PurchaseRequest [id=" + id + ", user=" + user + ", description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status=" + status
				+ ", total=" + total + ", submittedDate=" + submittedDate + ", reasonForRejection=" + reasonForRejection
				+ "]";
	}
}
