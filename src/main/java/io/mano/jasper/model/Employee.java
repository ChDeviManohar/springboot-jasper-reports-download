package io.mano.jasper.model;

public class Employee {

	private Integer id;
	private String name;
	private String designation;
	private String department;

	public Employee(Integer id, String name, String designation, String department) {
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesignation() {
		return designation;
	}

	public String getDepartment() {
		return department;
	}
}
