package com.tacticlogistics;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.j256.ormlite.dao.Dao;
import com.tacticlogistics.jda.wms.db.entities.Control;
import com.tacticlogistics.jda.wms.db.entities.PurchaseOrder;
import com.tacticlogistics.jda.wms.db.entities.PurchaseOrderLine;

@SpringBootApplication
@EnableScheduling
public class SateliteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SateliteApplication.class, args);
	}

	@Value("${email.services.sender.host}")
	private String mailSenderHost;

	@Value("${email.services.sender.port}")
	private int mailSenderPort;

	@Value("${email.services.sender.username}")
	private String mailSenderUsername;

	@Value("${email.services.sender.password}")
	private String mailSenderPassword;

	@Bean()
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailSenderHost);
		mailSender.setPort(mailSenderPort);
		mailSender.setUsername(mailSenderUsername);
		mailSender.setPassword(mailSenderPassword);

		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.debug", "false");
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		javaMailProperties.setProperty("mail.mime.encodefilename", "true");
		javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
		javaMailProperties.setProperty("mail.smtp.socketFactory.port", "" + mailSenderPort);

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}
}
