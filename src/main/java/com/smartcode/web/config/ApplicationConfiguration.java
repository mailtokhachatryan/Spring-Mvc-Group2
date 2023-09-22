package com.smartcode.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ApplicationConfiguration {

    @Bean("dataSource")
    public DataSource dataSource() {
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
        singleConnectionDataSource.setSuppressClose(true);
        singleConnectionDataSource.setUrl("jdbc:postgresql://localhost:5432/spring_start");
        singleConnectionDataSource.setUsername("postgres");
        singleConnectionDataSource.setPassword("postgres");
        singleConnectionDataSource.setDriverClassName("org.postgresql.Driver");
        return singleConnectionDataSource;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("testfortest891@gmail.com");
        mailSender.setPassword("iabxmmbebumtizqt");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }


}
