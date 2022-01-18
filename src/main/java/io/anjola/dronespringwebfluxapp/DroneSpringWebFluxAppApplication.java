package io.anjola.dronespringwebfluxapp;

import io.anjola.dronespringwebfluxapp.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class DroneSpringWebFluxAppApplication {

    private final Integer connectionPoolSize;

    public DroneSpringWebFluxAppApplication(@Value("${spring.datasource.maximum-pool-size:10}")Integer connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(DroneSpringWebFluxAppApplication.class, args);
        String mysqlUrl = context.getEnvironment().getProperty("spring.datasource.url");
        log.info("connected to mysql at {}", mysqlUrl);
        DroneService droneService = context.getBean(DroneService.class);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(droneService::droneBatteryLevelPeriodicTask, 1, 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(droneService::drainBatteryPeriodically, 1, 10, TimeUnit.SECONDS);


    }

    @Bean
    public Scheduler jdbcScheduler() {
        log.info("mysql connectionPoolSize {}", connectionPoolSize);
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
    }

}
