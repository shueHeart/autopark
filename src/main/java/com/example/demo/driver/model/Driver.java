package com.example.demo.driver.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.vehicle.model.Vehicle;

@Entity
public class Driver {
	
	@Id
	@GeneratedValue
	private UUID uuid;
	
	private String firstName;
	
	private String lastName;
	
	private int salary;
	
	private int age;
	
	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@ManyToOne
    @JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	
	
}
