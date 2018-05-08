package ru.glosav.gais.gateway.repo;

import org.springframework.data.repository.CrudRepository;
import ru.glosav.gais.gateway.dto.TransportUnit;

import java.util.List;

public interface TransportUnitRepository  extends CrudRepository<TransportUnit, Long> {
    List<TransportUnit> findByGrn(String grn);
}
