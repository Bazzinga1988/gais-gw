package ru.glosav.gais.gateway.cfg;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.glosav.gais.gateway.jobs.GaisTransferJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class JobsConfig {

    @Value("${gaisclient.transfer.job.interval}")
    private int interval;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(GaisTransferJob.class)
                .storeDurably()
                .withIdentity("Gais_Transfer_Job")
                .withDescription("Invoke Gais Transfer Job service...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Gais_Transfer_Trigger")
                .withDescription("Gais Transfer Job trigger")
                .withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever())
                .build();
    }


}
