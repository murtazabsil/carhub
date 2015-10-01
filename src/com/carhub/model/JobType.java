package com.carhub.model;

// Generated Sep 22, 2015 9:30:19 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * JobType generated by hbm2java
 */
@Entity
@Table(name = "job_type", catalog = "carhub")
@ManagedBean(name="jobType")
public class JobType implements java.io.Serializable {

	private Long jobTypeId;
	private String jobTypeName;
	private Set jobs = new HashSet(0);

	public JobType() {
	}

	public JobType(String jobTypeName, Set jobs) {
		this.jobTypeName = jobTypeName;
		this.jobs = jobs;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "job_type_id", unique = true, nullable = false)
	public Long getJobTypeId() {
		return this.jobTypeId;
	}

	public void setJobTypeId(Long jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	@Column(name = "job_type_name", length = 100)
	public String getJobTypeName() {
		return this.jobTypeName;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "jobType")
	public Set<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(Set jobs) {
		this.jobs = jobs;
	}

}
