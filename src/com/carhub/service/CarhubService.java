package com.carhub.service;

import java.util.List;
import com.carhub.model.Customer;
import com.carhub.model.Job;
import com.carhub.model.JobType;
import com.carhub.model.User;
import com.carhub.model.Vehicle;

public interface CarhubService {

	public void addCustomer(Customer customer);

	public List<Customer> listCustomer();

	void addVehicle(Vehicle vehicle);

	public List<Vehicle> listVehicle();

	public List<Vehicle> listVehicle(long customerId);
	
	public String vehicleNames();

	public void addJob(Job job);
	
	public void addJob();
	
	public void addJob(Customer customer, Vehicle vehicle);
	
	public void addJob(Vehicle vehicle);

	public List<Job> listJobs(long customerId);
	
	public List<Job> listJobs();

	public List<Job> listJobs(long customerId, long vehicleId);

	public void addUser(User user);

	public List<User> listUsers();
	
	public List<String> customerNames();
	
	public Customer getCustomer(long customerId);
	
	public List<JobType> listJobTypes();
	
	public String getJobParticularData();
	
	public void setJobParticularData(String jobParticularData);
	
	public String sampleParticularVOData();

	public String getCustomerInfo();

	public void setCustomerInfo(String customerInfo);

	public String getVehicleInfo();

	public void setVehicleInfo(String vehicleInfo);

	public String listDataItems();
}
