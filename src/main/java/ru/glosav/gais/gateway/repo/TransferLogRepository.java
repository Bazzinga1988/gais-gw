package ru.glosav.gais.gateway.repo;

import org.springframework.data.repository.CrudRepository;
import ru.glosav.gais.gateway.dto.TransferLog;

import java.util.List;

public interface TransferLogRepository  extends CrudRepository<TransferLog, Long> {
    List<TransferLog> findBySessionId(String sessionId);
}