package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        var path = "src/main/resources/rabbit.properties";
        var result = new HashMap<String, Integer>();

        try {
            var reader = new BufferedReader(new FileReader(path));
            reader.lines().map(i -> i.split("="))
                    .forEach(i -> result.put(i[0], Integer.parseInt(i[1])));

            var scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            var job = newJob(Rabbit.class).build();
            var times = simpleSchedule()
                    .withIntervalInSeconds(result.get("rabbit.interval"))
                    .repeatForever();
            var trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            reader.close();
        } catch (SchedulerException | IOException e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here...");
        }
    }
}
