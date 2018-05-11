package ru.glosav.gais.gateway.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.glosav.gais.gateway.cfg.GaisClientConfig;
import ru.glosav.gais.gateway.dto.Application;
import ru.glosav.gais.gateway.repo.ApplicationRepository;
import ru.glosav.gais.gateway.repo.SessionRepository;
import ru.glosav.gais.gateway.svc.GaisConnectorService;

import java.time.Instant;

@Component
public class GaisTransferJob implements Job {
    Logger log = LoggerFactory.getLogger(GaisTransferJob.class);

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private GaisClientConfig cfg;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("GaisTransferJob.execute");
        if(!sessionRepository.findByHandled(false).isEmpty()){
        GaisConnectorService gcs = ctx.getBean(GaisConnectorService.class);
            try {
                gcs.connect();
                sessionRepository.findByHandled(false).stream().forEach(
                        session -> {
                            log.debug("Handle session  with id: {}", session.getId());
                            try {
                                Application application = applicationRepository.findBySessionId(
                                        session.getId()
                                );
                                if (application != null) {
                                    log.debug("Handle application with id: {}", application.getId());
                                    try {
                                        gcs.save(cfg.getGroup(), application);
                                        session.setHandled(true);
                                        session.setAttempts( session.getAttempts() + 1 );
                                        session.setHandledDate(Instant.now());
                                        sessionRepository.save(session);

                                    } catch (Exception e) {
                                        log.warn("Error send application with id: {}", application.getId(), e);
                                    }
                                }
                            } catch (Exception e) {
                                log.warn("Error {}", e);
                            }
                        }
                );
            } catch (Exception e) {
                log.warn("Error {}",e);
            } finally {
                gcs.disconnect();
            }
        } else
            log.info("No applications for transfer");
    }
}
