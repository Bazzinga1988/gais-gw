package ru.glosav.gais.gateway.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glosav.gais.gateway.dto.Application;
import ru.glosav.gais.gateway.dto.Session;
import ru.glosav.gais.gateway.repo.ApplicationRepository;
import ru.glosav.gais.gateway.repo.SessionRepository;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/application")
@Api(description = "REST-сервисы для обработки информации о заявках перевозчиков")
public class ApplicationController {
    Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    SessionRepository sessionRepository;

    @ApiOperation(value = "Сервис регистрации заявки в ГАИС")
    @PostMapping(value = { "/register"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value=HttpStatus.CONFLICT,
            reason="Data integrity violation")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Session> register(
            @ApiParam(value = "Объект заявки", required = true)
            @NotNull
            @RequestBody Application application) throws ApplicationException {
        log.debug("ApplicationController.register: {}", application);
        Session session = new Session();
        application.setSessionId(session.getId());
        try {
            sessionRepository.save(session);
            applicationRepository.save(application);
        } catch (Exception e) {
            log.warn("Error persist application: {}", e.getMessage());
        }
        return ResponseEntity.ok(session);
    }

    @ApiOperation(value = "Список заявок")
    @GetMapping(value = {"/list"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> list() throws ApplicationException {
        log.debug("ApplicationController.list:");
        Iterable<Application> apps = applicationRepository.findAll();
        return ResponseEntity.ok(apps);
    }

}
