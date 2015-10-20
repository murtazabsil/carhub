package com.carhub.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carhub.dao.CarhubDAO;
import com.carhub.model.Customer;
import com.carhub.model.Job;
import com.carhub.model.JobType;
import com.carhub.model.Particular;
import com.carhub.model.User;
import com.carhub.model.Vehicle;
import com.carhub.vo.DashboardVO;
import com.carhub.vo.ParticularVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
@ManagedBean(name = "carhubService")
@SessionScoped
public class CarhubServiceImpl implements CarhubService {

	private CarhubDAO carhubDAO;

	public String jobParticularData;

	public String customerInfo;

	public String vehicleInfo;

	public User sessionUser;

	public void setCarhubDAO(CarhubDAO carhubDAO) {
		this.carhubDAO = carhubDAO;
	}

	@Override
	@Transactional
	public void addCustomer(Customer customer) {
		this.carhubDAO.addCustomer(customer);
	}

	@Override
	public User getSessionUser() {
		return (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("loggedInUser");
	}

	@Override
	@Transactional
	public List<Customer> listCustomer() {
		return this.carhubDAO.listCustomer();
	}

	@Override
	@Transactional
	public void addVehicle(Vehicle vehicle) {
		this.carhubDAO.addVehicle(vehicle);
	}

	@Override
	@Transactional
	public List<Vehicle> listVehicle() {
		return this.carhubDAO.listVehicle();
	}

	@Override
	@Transactional
	public List<Vehicle> listVehicle(long customerId) {
		if (customerId != 0)
			return this.carhubDAO.listVehicle(customerId);
		else
			return this.carhubDAO.listVehicle();
	}

	@Override
	@Transactional
	public void addJob(Job job) {
		this.carhubDAO.addJob(job);
	}

	@Override
	@Transactional
	public List<Job> listJobs(long customerId) {
		return this.carhubDAO.listJobs(customerId);
	}

	@Override
	@Transactional
	public List<Job> listJobs(long customerId, long vehicleId) {
		if (customerId != 0 && vehicleId != 0)
			return this.carhubDAO.listJobs(customerId, vehicleId);
		else if (customerId != 0 && vehicleId == 0)
			return this.carhubDAO.listJobs(customerId);
		else
			return this.carhubDAO.listJobs();
	}

	@Override
	@Transactional
	public void addUser(User user) {
		this.carhubDAO.addUser(user);
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.carhubDAO.listUsers();
	}

	@Override
	@Transactional
	public List<String> customerNames() {
		// TODO Auto-generated method stub
		return this.carhubDAO.customerNames();
	}

	@Override
	@Transactional
	public Customer getCustomer(long customerId) {
		// TODO Auto-generated method stub
		return this.carhubDAO.getCustomer(customerId);
	}

	@Override
	@Transactional
	public List<JobType> listJobTypes() {
		return this.carhubDAO.listJobTypes();
	}

	public String getJobParticularData() {
		return jobParticularData;
	}

	public void setJobParticularData(String jobParticularData) {
		this.jobParticularData = jobParticularData;
	}

	@Override
	public String sampleParticularVOData() {
		Gson gson = new Gson();
		ParticularVO particularVO = new ParticularVO();
		particularVO.setItemData(null);
		particularVO.setJobType(0l);
		particularVO.setLabourPrice(0.0);
		particularVO.setPartPrice(0.0);
		particularVO.setTotalPrice(0.0);
		return gson.toJson(particularVO);
	}

	@Override
	@Transactional
	public void addJob() {
		List<ParticularVO> particularVOs = new Gson().fromJson(
				this.jobParticularData, new TypeToken<List<ParticularVO>>() {
				}.getType());
		this.carhubDAO.addJob(this.customerInfo, this.vehicleInfo,
				particularVOs);
	}

	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String getVehicleInfo() {
		return vehicleInfo;
	}

	public void setVehicleInfo(String vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}

	@Override
	@Transactional
	public void addJob(Customer customer, Vehicle vehicle) {
		List<ParticularVO> particularVOs = new Gson().fromJson(
				this.jobParticularData, new TypeToken<List<ParticularVO>>() {
				}.getType());
		this.carhubDAO.addJob(customer, vehicle, particularVOs);
	}

	@Override
	@Transactional
	public String vehicleNames() {
		return new Gson().toJson(this.carhubDAO.vehicleNames());
	}

	@Override
	@Transactional
	public void addJob(Vehicle vehicle) {
		List<ParticularVO> particularVOs = new Gson().fromJson(
				this.jobParticularData, new TypeToken<List<ParticularVO>>() {
				}.getType());
		this.carhubDAO.addJob(this.customerInfo, vehicle, particularVOs);
	}

	@Override
	@Transactional
	public String listDataItems() {
		return this.carhubDAO.listDataItems();
	}

	@Override
	@Transactional
	public List<Job> listJobs() {
		return this.carhubDAO.listJobs();
	}

	@Override
	@Transactional
	public String listJobsInJSON(long customerId, long vehicleId) {
		List<Job> listJob = listJobs(customerId, vehicleId);
		Map<Long, List<ParticularVO>> map = new HashMap();
		for (Job job : listJob) {
			List<ParticularVO> particularVOList = new ArrayList<ParticularVO>();
			for (Particular particular : job.getParticulars()) {
				ParticularVO particularVO = new ParticularVO();
				particularVO
						.setItemData(particular.getItemData().getItemName());
				particularVO.setPartPrice(particular.getPartPrice());
				particularVO.setLabourPrice(particular.getLabourPrice());
				particularVO.setTotalPrice(particular.getTotalPrice());
				particularVO.setJobType(job.getJobType().getJobTypeId());
				particularVOList.add(particularVO);
			}
			map.put(job.getJobId(), particularVOList);
		}
		return new Gson().toJson(map);
	}

	@Override
	@Transactional
	public String login(User user) {
		User loggedInUser = this.carhubDAO.login(user);
		if (loggedInUser != null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("loggedInUser", loggedInUser);
			return "dashboard";
		} else
			return null;
	}

	@Override
	@Transactional
	public List<DashboardVO> getDashboardContent() {
		List<Job> jobList = this.carhubDAO.getDashboardJobs();
		List<DashboardVO> dashboardVOList = new ArrayList<DashboardVO>();
		for (Job job : jobList) {
			DashboardVO dashboardVO = new DashboardVO();
			dashboardVO.setCustomerName(job.getCustomer().getCustomerName());
			dashboardVO.setServiceDate(new SimpleDateFormat().format(job
					.getJobDate()));
			dashboardVO.setServiceProvided(job.getJobType().getJobTypeName());
			dashboardVO.setContactEmail(job.getCustomer().getEmailId());
			dashboardVO
					.setContactNumber(job.getCustomer().getContact() != null ? job
							.getCustomer().getContact().toString()
							: "");
			dashboardVOList.add(dashboardVO);
		}
		return dashboardVOList;
	}
}
