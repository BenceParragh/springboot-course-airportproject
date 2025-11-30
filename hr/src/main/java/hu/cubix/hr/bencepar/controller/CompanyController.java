package hu.cubix.hr.bencepar.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import hu.cubix.hr.bencepar.dto.CompanyDto;
import hu.cubix.hr.bencepar.dto.EmployeeDto;
import hu.cubix.hr.bencepar.dto.Views;
import hu.cubix.hr.bencepar.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private Map<Long, CompanyDto> companies = new HashMap<>();

	@Autowired
	private EmployeeService employeeService;
	
//	{
//		List<EmployeeDto> employees = new ArrayList<>();
//		employees.add(new EmployeeDto("Parragh Bence", 16018045, "Field Application Specialist", 850000,
//				LocalDate.of(2024, 12, 9)));
//		employees.add(new EmployeeDto("Kis Pista", 16010000, "service engineer", 750000, LocalDate.of(2020, 10, 15)));
//
//		companies.put(1357L, new CompanyDto(1357L, 2804L, "Biomerieux", "1138 Budapest", employees));
//	}

	@GetMapping(params = "full=true")
	public List<CompanyDto> getCompanies() {
		return new ArrayList<>(companies.values());
	}

	@GetMapping
	@JsonView(Views.BaseData.class)
	public List<CompanyDto> getCompaniesWithoutEmployees(@RequestParam Optional<Boolean> full) {
		return new ArrayList<>(companies.values());

	}

	@GetMapping("/{id}")
	public CompanyDto findById(@PathVariable long id) {
		CompanyDto companyDto = getCompnayOrThrowNotFound(id);
		return companyDto;
	}

	@PostMapping
	public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto company) {
		if (companies.containsKey(company.getId()))
			return ResponseEntity.badRequest().build();

		companies.put(company.getId(), company);
		return ResponseEntity.ok(company);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> update(@PathVariable long id, @RequestBody CompanyDto company) {
		company.setId(id);
		if (!companies.containsKey(id))
			return ResponseEntity.notFound().build();

		companies.put(id, company);
		return ResponseEntity.ok(company);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		companies.remove(id);
	}

	@PostMapping("/{id}/employees")
	public CompanyDto addNewEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		CompanyDto companyDto = getCompnayOrThrowNotFound(id);
		companyDto.getEmployees().add(employeeDto);
		return companyDto;

	}

	@DeleteMapping("/{id}/employees/{employeeId}")
	public CompanyDto addNewEmployee(@PathVariable long id, @PathVariable long employeeId) {
		CompanyDto companyDto = getCompnayOrThrowNotFound(id);
		companyDto.getEmployees().removeIf(emp -> emp.getId() == employeeId);
		return companyDto;

	}

	@PutMapping("/{id}/employees")
	public CompanyDto replaceAllEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
		CompanyDto companyDto = getCompnayOrThrowNotFound(id);
		companyDto.setEmployees(employees);
		return companyDto;

	}

	private CompanyDto getCompnayOrThrowNotFound(long id) {
		CompanyDto companyDto = companies.get(id);
		if (companyDto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		}
		return companyDto;
	}

}
