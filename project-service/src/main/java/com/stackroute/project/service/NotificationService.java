package com.stackroute.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Service
public class NotificationService
{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    public QRGenerationService qrService;

    public void sendNotificationEmail(String to, String subject, String message) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

       // byte[] qrImage = Base64.getDecoder().decode(qrmessage);

        // byte[] qrImage = qrService.generateQR(qrmessage, 200, 200);

       // ByteArrayDataSource qrImageDataSource = new ByteArrayDataSource(qrImage, "image/png");

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(to);

        helper.setSubject(subject);

        //helper.addAttachment( "qrCodeAttachment", qrImageDataSource);

        helper.setText(message, true);

        javaMailSender.send(msg);
    }
}
