package ru.glosav.gais.gateway.jobs;

import org.apache.thrift.transport.TSocket;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.gais.gateway.dto.Application;
import ru.glosav.gais.gateway.dto.Session;
import ru.glosav.gais.gateway.repositories.ApplicationRepository;
import ru.glosav.gais.gateway.repositories.SessionRepository;

import java.util.List;
import java.util.Optional;

@Component
public class GaisTransferJob implements Job {
    Logger log = LoggerFactory.getLogger(GaisTransferJob.class);

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    TSocket tSocket;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("GaisTransferJob.execute, tsocket: {}", tSocket);
        sessionRepository.findByHandled(false).stream().forEach(
                session -> {
                    log.debug("Handle session  with id: {}", session.getId());
                    try {
                        applicationRepository.findById(
                                session.getAppId()
                        ).ifPresent(
                                application -> {
                                    log.debug("Handle application with id: {}", application.getId());
                                });
                    } catch (Exception e) {
                        log.warn("Error");
                    }
                }
        );

    }
}
