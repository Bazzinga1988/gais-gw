package ru.glosav.gais.gateway.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glosav.gais.gateway.dto.Application;
import ru.glosav.gais.gateway.dto.Company;
import ru.glosav.gais.gateway.dto.Session;
import ru.glosav.gais.gateway.repositories.ApplicationRepository;
import ru.glosav.gais.gateway.repositories.SessionRepository;

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
    public ResponseEntity<Session> register(
            @ApiParam(value = "Объект заявки", required = true)
            @NotNull
            @RequestBody Application application) {
        log.debug("ApplicationController.register: {}", application);
        Session session = new Session();
        application.setSessionId(session.getId());
        sessionRepository.save(session);
        applicationRepository.save(application);
        return ResponseEntity.ok(session);
    }

    @ApiOperation(value = "Тестовый метод")
    @GetMapping(value = { "/test"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> test() {
        log.debug("ApplicationController.test:");
        Application application = new Application();
        Company c = new Company();
        c.setBik("432543");
        return ResponseEntity.ok(application);
    }

}
