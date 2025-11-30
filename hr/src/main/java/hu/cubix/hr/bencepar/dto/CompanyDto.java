package hu.cubix.hr.bencepar.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyDto {

	private Long companyId;
	private Long registrationNumber;
	private String companyName;
	private String address;

	private List<EmployeeDto> employees = new ArrayList<>();

	public CompanyDto(Long companyId, Long registrationNumber, String companyName, String address,
			List<EmployeeDto> employees) {
		this.companyId = companyId;
		this.registrationNumber = registrationNumber;
		this.companyName = companyName;
		this.address = address;
		this.employees = employees;

	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(Long registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

}
