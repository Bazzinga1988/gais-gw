package ru.glosav.gais.gateway.repo;

import org.springframework.data.repository.CrudRepository;
import ru.glosav.gais.gateway.dto.Session;

import java.util.List;

public interface SessionRepository  extends CrudRepository<Session, String> {
    List<Session> findByHandled(boolean handled);
}
