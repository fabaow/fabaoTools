package com.fabao.task;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@ComponentScan("cn.fabao.task")
@EnableScheduling //通过@EnableScheduling注解开启对计划任务的支持
public class TaskScheduleConfig {
}