package com.dd.bikes.service.impl;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dd.bikes.model.RequestMailForm;

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
	
	private String CONTENTTYPE = "text/html";
	
	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.username}")
	private String mailFrom;

	@Value("${spring.mail.admin}")
	private String adminMail;

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
			String htmlContent = "<div style='border: 5px solid #9D00FF ; padding:20px;'>" + "Hi!, "
					+ "<h2 style='color: orange;'>" + appUsername + "</h2>"
					+ "<p>Here is your OTP for resetting the password:</p>" + "<h2 style='color: green;'>"
					+ generatedOTP + "</h2>" + "<span style='color:gray;'>-- Thanks and Regards</span>"
					+ "        <h4>Support Team</h4>"
					+ "<p style='color: red;'>Note: This is a system-generated email. Please do not reply.</p>"
					+ "</div>";
			message.setContent(htmlContent, CONTENTTYPE);

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

	public boolean sendFormSubmissionEmail(RequestMailForm formData) {
		Properties props = new Properties();
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
			// Create and send email to admin
			MimeMessage messageToAdmin = new MimeMessage(session);
			messageToAdmin.setFrom(new InternetAddress(mailFrom));
			messageToAdmin.addRecipient(Message.RecipientType.TO, new InternetAddress(adminMail));
			String subjectForAdmin = "New Service Request from Website User";
			messageToAdmin.setSubject(subjectForAdmin);

			// Adding Message (set content for HTML)
			String htmlContentForAdmin = "<div style='border: 3px solid ##C70039 ; padding:20px; border-radius: 15px:'>"
					+ "<h2>New Service Request</h2>" + "<p><strong>Name:</strong> " + formData.getName() + "</p>"
					+ "<p><strong>Email:</strong> " + formData.getEmail() + "</p>" + "<p><strong>Subject:</strong> "
					+ formData.getSubject() + "</p>" + "<p><strong>Message:</strong></p>" + "<p>"
					+ formData.getMessage() + "</p>"
					+ "<p style='color: red;'>Note: This is a system-generated email. Please do not reply.</p>"
					+ "</div>";
			messageToAdmin.setContent(htmlContentForAdmin, CONTENTTYPE);

			// Send email to admin
			Transport.send(messageToAdmin);
			logger.info("Admin mail sent successfully...");

			// Create and send email to requester
			MimeMessage messageToRequester = new MimeMessage(session);
			messageToRequester.setFrom(new InternetAddress(mailFrom));
			messageToRequester.addRecipient(Message.RecipientType.TO, new InternetAddress(formData.getEmail()));
			String subjectForRequester = "Confirmation of Your Service Request";
			messageToRequester.setSubject(subjectForRequester);

			// Adding Message (set content for HTML)
			String htmlContentForRequester = "<div style='border: 3px solid #00FF00 ; padding:20px; border-radius: 15px:'>"
					+ "<p style='color:blue'>Dear " + formData.getName() + ",</p>"
					+ "<p>Thank you for reaching out to us. We have received your service request and our team will get back to you shortly.</p>"
					+ "<p>If you have any further questions, please do not hesitate to contact us at "
					+ "<a href='mailto:" + adminMail + "'>Tech Support Admin</a>.</p>"
					+ "<p style='color: red;'>Note: This is a system-generated email. Please do not reply.</p>"
					+ "</div>";
			messageToRequester.setContent(htmlContentForRequester, CONTENTTYPE);

			// Send email to requester
			Transport.send(messageToRequester);

			// Log info
			logger.info("Requester email sent successfully...");
			isMailSent = true;

		} catch (MessagingException mex) {
			mex.printStackTrace();
			logger.error("Error while sending email: {}", mex.getMessage());
			isMailSent = false;
		}
		return isMailSent;
	}

}