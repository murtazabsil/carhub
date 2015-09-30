package com.carhub.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.carhub.model.Customer;
import com.carhub.model.ItemData;
import com.carhub.model.Job;
import com.carhub.model.JobType;
import com.carhub.model.Particular;
import com.carhub.model.User;
import com.carhub.model.Vehicle;
import com.carhub.vo.ParticularVO;
import com.google.gson.Gson;

@Repository
public class CarhubDAOImpl implements CarhubDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(CarhubDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		if (customer.getCustomerId() != null)
			session.update(customer);
		else
			session.persist(customer);
		logger.info("Customer added successfully with customer details as :"
				+ customer);
	}

	@Override
	public List<Customer> listCustomer() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Customer> customers = session.createQuery("from Customer").list();
		return customers;
	}

	@Override
	public void addVehicle(Vehicle vehicle, String customerInfo) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		if (vehicle.getVehicleId() != null) {
			session.update(vehicle);
		} else {
			if (customerInfo.indexOf("|") != -1) {
				String splittedCustomer[] = customerInfo.split("|");
				vehicle.setCustomer(getCustomer(Long
						.parseLong(splittedCustomer[0])));
			} else {
				Customer customer = new Customer();
				customer.setCustomerName(customerInfo);
				session.persist(customer);
				vehicle.setCustomer(customer);
			}
			session.persist(vehicle);
		}
		logger.info("Customer added successfully with customer details as :"
				+ vehicle);
	}

	@Override
	public List<Vehicle> listVehicle() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Vehicle> vehicles = session.createQuery("from Vehicle").list();
		return vehicles;
	}

	@Override
	public List<Vehicle> listVehicle(long customerId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Vehicle> vehicles = session
				.createQuery(
						"Select v from Vehicle v where v.customer.customerId = :customerId")
				.setLong("customerId", customerId).list();
		return vehicles;
	}

	@Override
	public void addJob(Job job) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(job);
		logger.info("Customer added successfully with customer details as :"
				+ job);
	}

	@Override
	public List<Job> listJobs(long customerId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Job> jobs = session
				.createQuery(
						"Select j from Job v where j.customer.customerId = :customerId")
				.setLong("customerId", customerId).list();
		return jobs;
	}

	@Override
	public List<Job> listJobs(long customerId, long vehicleId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Job> jobs = session
				.createQuery(
						"Select j from Job v where j.customer.customerId = :customerId and j.vehicle.vehicleId")
				.setLong("customerId", customerId)
				.setLong("vehicleId", vehicleId).list();
		return jobs;
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(user);
		logger.info("Customer added successfully with customer details as :"
				+ user);
	}

	@Override
	public List<User> listUsers() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> users = session.createQuery("from User").list();
		return users;
	}

	@Override
	public List<String> customerNames() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Customer> customers = session.createQuery("from Customer").list();
		List<String> customerNames = new ArrayList<String>();
		for (Customer customer : customers)
			customerNames.add("'" + customer.getCustomerId() + "|"
					+ customer.getCustomerName() + "'");
		return customerNames;
	}

	@Override
	public Customer getCustomer(long customerId) {
		Session session = this.sessionFactory.getCurrentSession();
		Customer customer = (Customer) session
				.createQuery("from Customer where customerId = :customerId")
				.setLong("customerId", customerId).uniqueResult();
		return customer;
	}

	@Override
	public Vehicle getVehicle(long vehicleId) {
		Session session = this.sessionFactory.getCurrentSession();
		Vehicle vehicle = (Vehicle) session
				.createQuery("from Vehicle where vehicleId = :vehicleId")
				.setLong("vehicleId", vehicleId).uniqueResult();
		return vehicle;
	}

	@Override
	public List<JobType> listJobTypes() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<JobType> jobTypes = session.createQuery("from JobType").list();
		return jobTypes;
	}

	@Override
	public void addJob(String customerInfo, String vehicleInfo,
			List<ParticularVO> particularVOs) {
		Session session = this.sessionFactory.getCurrentSession();
		Customer customer = null;
		Vehicle vehicle = null;
		if (customerInfo.indexOf("|") != -1) {
			String splittedCustomer[] = customerInfo.split("|");
			customer = getCustomer(Long.parseLong(splittedCustomer[0]));
		}

		if (vehicleInfo.indexOf("|") != -1) {
			String splittedVehicle[] = vehicleInfo.split("|");
			vehicle = getVehicle(Long.parseLong(splittedVehicle[0]));
		}
		savingJobData(customer, vehicle, session, particularVOs);
	}

	@Override
	public void addJob(Customer customer, Vehicle vehicle,
			List<ParticularVO> particularVOs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(customer);
		vehicle.setCustomer(customer);
		session.persist(vehicle);
		savingJobData(customer, vehicle, session, particularVOs);
	}

	private void savingJobData(Customer customer, Vehicle vehicle,
			Session session, List<ParticularVO> particularVOs) {
		// Adding Job to Database
		Job job = new Job();
		job.setCustomer(customer);
		job.setVehicle(vehicle);
		job.setJobDate(new Date());
		job.setJobType((JobType) session
				.createQuery("From JobType where jobTypeId = :jobTypeId")
				.setLong("jobTypeId", particularVOs.get(0).getJobType())
				.uniqueResult());
		session.persist(job);

		// Adding Particulars to Database.
		for (ParticularVO particularVO : particularVOs) {
			Particular particular = new Particular();
			ItemData itemData = null;
			if (particularVO.getItemData().indexOf("|") != -1) {
				String splittedItemData[] = particularVO.getItemData().split(
						"|");
				itemData = (ItemData) session
						.createQuery("From ItemData where itemId = :itemId")
						.setLong("itemId", Long.parseLong(splittedItemData[0]))
						.uniqueResult();
			} else {
				itemData = new ItemData();
				itemData.setItemName(particularVO.getItemData());
				session.persist(itemData);
			}
			particular.setItemData(itemData);
			particular.setJob(job);
			particular.setLabourPrice(particularVO.getLabourPrice());
			particular.setPartPrice(particularVO.getPartPrice());
			particular.setTotalPrice(particularVO.getTotalPrice());
			session.persist(particular);
		}
	}

	@Override
	public Map<Long, List<String>> vehicleNames() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Customer> customers = session.createQuery("from Customer").list();
		Map<Long, List<String>> map = new HashMap<Long, List<String>>();
		for (Customer customer : customers) {
			List<String> vehicleNames = new ArrayList<String>();
			for (Vehicle vehicle : customer.getVehicles())
				vehicleNames.add(vehicle.getVehicleNumber());
			map.put(customer.getCustomerId(), vehicleNames);
		}
		return map;
	}

	@Override
	public void addJob(String customerInfo, Vehicle vehicle,
			List<ParticularVO> particularVOs) {
		Session session = this.sessionFactory.getCurrentSession();
		Customer customer = null;
		if (customerInfo.indexOf("|") != -1) {
			String splittedCustomer[] = customerInfo.split("|");
			customer = getCustomer(Long.parseLong(splittedCustomer[0]));
		}
		vehicle.setCustomer(customer);
		session.persist(vehicle);
		savingJobData(customer, vehicle, session, particularVOs);
	}

	@Override
	public String listDataItems() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ItemData> itemDatas = session.createQuery("FROM ItemData").list();
		List<String> listDataItems = new ArrayList<String>();
		for (ItemData itemData : itemDatas)
			listDataItems.add(itemData.getItemId() + "|"
					+ itemData.getItemName());
		return new Gson().toJson(listDataItems);
	}
}
