package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            var store = new ArrayList<>();
            var scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            var data = new JobDataMap();
            data.put("store", store);
            var job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            var times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            var trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(5000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        private Connection connection;

        private Properties properties;

        public Rabbit(Properties properties) {
            this.properties = properties;
            initConnection();
        }

        private void initConnection() {
            try {
                connection = getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static Properties getAppProperties() {
            var result = new HashMap<String, String>();
            var properties = new Properties();
            var path = "src/main/resources/rabbit.properties";
            try (var reader = new BufferedReader(new FileReader(path))) {
                reader.lines().map(line -> line.split("="))
                        .forEach(i -> result.put(i[0], i[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            properties.setProperty("url", result.get("url"));
            properties.setProperty("login", result.get("login"));
            properties.setProperty("password", result.get("password"));
            return properties;
        }

        private Connection getConnection() throws Exception {
            var appProperties = getAppProperties();
            var url = appProperties.getProperty("url");
            properties.setProperty("login", appProperties.getProperty("login"));
            properties.setProperty("password", appProperties.getProperty("password"));
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, properties);
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            var store = (List<Long>) context.getJobDetail().getJobDataMap().get("store");
            store.add(System.currentTimeMillis());
        }
    }
}
