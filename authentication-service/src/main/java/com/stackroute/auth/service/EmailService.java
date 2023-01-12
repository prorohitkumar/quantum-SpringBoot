package com.stackroute.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

// Sending the OTP on mail in this class
@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	// Sending the OTP through mail
	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
	
		 MimeMessage msg = javaMailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

	        helper.setTo(to);

	        helper.setSubject(subject);

	        helper.setText(message, true);

	        javaMailSender.send(msg);
   }
	
}
	
