package ru.glosav.gais.gateway.repo;

import org.springframework.data.repository.CrudRepository;
import ru.glosav.gais.gateway.dto.Company;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Long> {
        List<Company> findByName(String name);
}
