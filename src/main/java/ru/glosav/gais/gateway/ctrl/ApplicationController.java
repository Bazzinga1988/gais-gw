package ru.glosav.gais.gateway.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glosav.gais.gateway.cfg.GaisClientConfig;
import ru.glosav.gais.gateway.dto.Application;
import ru.glosav.gais.gateway.dto.Session;
import ru.glosav.gais.gateway.repo.ApplicationRepository;
import ru.glosav.gais.gateway.repo.SessionRepository;
import ru.glosav.gais.gateway.svc.GaisConnectorService;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/application")
@Api(description = "REST-сервисы для обработки информации о заявках перевозчиков")
public class ApplicationController {
    Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    ApplicationContext ctx;

    @Autowired
    private GaisClientConfig cfg;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @ApiOperation(value = "Сервис регистрации заявки в ГАИС")
    @PostMapping(value = { "/register"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value=HttpStatus.CONFLICT,
            reason="Data integrity violation")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Session> register(
            @ApiParam(value = "Объект заявки", required = true)
            @NotNull
            @RequestBody Application application) {
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
    public ResponseEntity<?> list() {
        log.debug("ApplicationController.list:");
        List<Application> target = new ArrayList<>();
        applicationRepository.findAll().forEach(target::add);
        return ResponseEntity.ok(target);
    }

    @ApiOperation(value = "Список заявок в глонас")
    @GetMapping(value = {"/rlist"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> rlist() {
        log.debug("ApplicationController.rlist:");
        List<Application> target = new ArrayList<>();
        applicationRepository.findAll().forEach(target::add);
        GaisConnectorService gcs = ctx.getBean(GaisConnectorService.class);
        try {
            gcs.connect();
            gcs.list();
        } catch (Exception e) {
            log.warn("Error {}",e);
        } finally {
            gcs.disconnect();
        }
        return ResponseEntity.ok("Ok");
    }

}
