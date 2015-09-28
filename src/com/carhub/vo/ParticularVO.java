package com.carhub.vo;

public class ParticularVO {

	private Double partPrice;
	private Double labourPrice;
	private Double totalPrice;
	private String itemData;
	private Long jobType;

	public Double getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(Double partPrice) {
		this.partPrice = partPrice;
	}

	public Double getLabourPrice() {
		return labourPrice;
	}

	public void setLabourPrice(Double labourPrice) {
		this.labourPrice = labourPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getItemData() {
		return itemData;
	}

	public void setItemData(String itemData) {
		this.itemData = itemData;
	}

	public Long getJobType() {
		return jobType;
	}

	public void setJobType(Long jobType) {
		this.jobType = jobType;
	}
}
