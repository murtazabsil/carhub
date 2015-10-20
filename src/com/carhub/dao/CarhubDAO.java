package com.carhub.dao;

import java.util.List;
import java.util.Map;

import com.carhub.model.Customer;
import com.carhub.model.Job;
import com.carhub.model.JobType;
import com.carhub.model.User;
import com.carhub.model.Vehicle;
import com.carhub.vo.ParticularVO;

public interface CarhubDAO {

	public void addCustomer(Customer customer);
	
	public List<Customer> listCustomer();
	
	public void addVehicle(Vehicle vehicle);
	
	public List<Vehicle> listVehicle();
	
	public List<Vehicle> listVehicle(long customerId);
	
	public void addJob(Job job);
	
	public void addJob(Customer customer, Vehicle vehicle, List<ParticularVO> particularVOs);
	
	public List<Job> listJobs(long customerId);
	
	public List<Job> listJobs();
	
	public List<Job> listJobs(long customerId, long vehicleId);
	
	public void addUser(User user);
	
	public List<User> listUsers();
	
	public List<String> customerNames();
	
	public Map<Long, List<String>> vehicleNames();
	
	public Customer getCustomer(long customerId);
	
	public List<JobType> listJobTypes();
	
	public void addJob(String customerInfo, String vehicleInfo, List<ParticularVO> particularVOs);

	Vehicle getVehicle(long vehicleId);
	
	public void addJob(String customerInfo, Vehicle vehicle, List<ParticularVO> particularVOs);

	public String listDataItems();
	
	public User login(User user);
	
	public List<Job> getDashboardJobs();
}
