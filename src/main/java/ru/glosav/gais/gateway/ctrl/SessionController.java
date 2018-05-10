package ru.glosav.gais.gateway.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.glosav.gais.gateway.dto.Session;
import ru.glosav.gais.gateway.repo.ApplicationRepository;
import ru.glosav.gais.gateway.repo.SessionRepository;

import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/session")
@Api(description = "REST-сервисы для информации о сессиях")
public class SessionController {
    Logger log = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    SessionRepository sessionRepository;

    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Список сессий")
    @GetMapping(value = {"/list"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> list() throws ApplicationException {
        log.debug("SessionController.list:");
        List<Session> target = new ArrayList<>();
        sessionRepository.findAll().forEach(target::add);
        return ResponseEntity.ok(target);
    }


}
