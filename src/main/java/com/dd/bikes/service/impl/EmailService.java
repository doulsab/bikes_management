package com.dd.bikes.service.impl;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private static final Logger logger = LogManager.getLogger(EmailService.class);
	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.username}")
	private String mailFrom;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.port}")
	private String port;

	public boolean sendMail(String to, int generatedOTP, String appUsername) {
		Properties props = System.getProperties();
		boolean isMailSent = false;

		// Setting the properties
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true"); // Use SSL for port 465

		// Session Object
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);

			// From address
			message.setFrom(new InternetAddress(mailFrom));

			// To address
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Adding Subject
			String subject = "Password Reset Request";
			message.setSubject(subject);

			// Adding Message (set content for HTML)
			String htmlContent = "<div style='border:1px solid #054663 ; padding:20px;'>" + "Hello "
					+ "<h2 style='color: orange;'>" + appUsername + ",</h2>"
					+ "<p>Here is your OTP for resetting the password:</p>" + "<h2 style='color: green;'>"
					+ generatedOTP + "</h2>"
					+ "<p style='color: red;'>Note: This is a system-generated email. Please do not reply.</p>"
					+ "</div>";
			message.setContent(htmlContent, "text/html");

			// Send message
			Transport.send(message);

			// log info
			logger.info("OTP sent to mail successfully....................");
			isMailSent = true;

		} catch (MessagingException mex) {
			mex.printStackTrace();
			isMailSent = false;
			logger.error("Error while sending email: {}", mex.getMessage());
		}
		return isMailSent;
	}

}
