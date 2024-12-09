package org.levon24.ht2000.configurations;

/*
 * User: levon
 * Date: 12.07.2022
 * Time: 16:12
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableScheduling
@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskExecutor());
  }

  @Bean(destroyMethod = "shutdown")
  public Executor taskExecutor() {
    return Executors.newScheduledThreadPool(2);
  }
}
