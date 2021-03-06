package ru.glosav.gais.gateway.repo;

import org.springframework.data.repository.CrudRepository;
import ru.glosav.gais.gateway.dto.Application;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    List<Application> findByNumber(String number);
    Application findBySessionId(String number);
}
