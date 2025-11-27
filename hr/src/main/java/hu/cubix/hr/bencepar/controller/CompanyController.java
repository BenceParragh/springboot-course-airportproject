package hu.cubix.hr.bencepar.controller;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import hu.cubix.hr.bencepar.dto.CompanyDto;
import hu.cubix.hr.bencepar.dto.EmployeeDto;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private Map<Long, CompanyDto> companies = new HashMap<>();

	{
		List<EmployeeDto> employees = new ArrayList<>();
		employees.add(new EmployeeDto("Parragh Bence", 16018045, "Field Application Specialist", 850000, LocalDate.of(2024, 12, 9)));
		employees.add(new EmployeeDto("Kis Pista", 16010000, "service engineer", 750000, LocalDate.of(2020, 10, 15)));
		
		companies.put(1357L, new CompanyDto(1357L, 2804L, "Biomerieux", "1138 Budapest", employees));
	}

	@GetMapping
	public List<CompanyDto> getCompanies(@RequestParam(required = false, defaultValue = "false") boolean full) {
		if (full) {
			return new ArrayList<>(companies.values());
		} else {
			List<CompanyDto> companiesWithoutEmployees = new ArrayList<>();
			for (CompanyDto company : companies.values()) {
				CompanyDto summary = new CompanyDto(company.getCompanyId(), company.getRegistrationNumber(),
						company.getCompanyName(), company.getAddress(), null);
				companiesWithoutEmployees.add(summary);
			}
			return companiesWithoutEmployees;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> findById(@PathVariable long id) {
		CompanyDto companyDto = companies.get(id);
		if (companyDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(companyDto);
	}

	@PostMapping
	public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto company) {
		if (companies.containsKey(company.getCompanyId()))
			return ResponseEntity.badRequest().build();

		companies.put(company.getCompanyId(), company);
		return ResponseEntity.ok(company);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> update(@PathVariable long id, @RequestBody CompanyDto company) {
		company.setCompanyId(id);
		if (!companies.containsKey(id))
			return ResponseEntity.notFound().build();

		companies.put(id, company);
		return ResponseEntity.ok(company);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		companies.remove(id);
	}

	// new methods to make the API more sophisticated:

}
