package com.stackroute.auth.controller;

import com.stackroute.auth.EmailTemplate;
import com.stackroute.auth.exception.InvalidOtpException;
import com.stackroute.auth.service.EmailService;
import com.stackroute.auth.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

// In this Controller class OTP authentication API's are called when user is going to update their details

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class OTPController {

    public OTPService otpService;
    public EmailService emailService;

    @Autowired
    public OTPController(OTPService otpService, EmailService emailService) {
        this.otpService = otpService;
        this.emailService = emailService;
    }

    // In this API getting email from user and returning OTP on email
    @GetMapping("/otp/{email}")
    public ResponseEntity<?> generateOtp(@PathVariable String email) throws MessagingException {

        try{
            int otp = otpService.generateOTP(email);
            //Generate The Template to send OTP
            EmailTemplate template = new EmailTemplate("SendOtp.html");
            Map<String, String> replacements = new HashMap<String, String>();
            replacements.put("user", email);
            replacements.put("otpNumber", Integer.toString(otp));
            String message = template.getTemplate(replacements);

            emailService.sendOtpMessage(email, "Quantum OTP", message);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    // Validating the OTP entered by the User and then give the authorisation
    @GetMapping("/validate/{email}/{otpnum}")
    public ResponseEntity<?> validationOtp(@PathVariable int otpnum,
                                           @PathVariable String email) throws InvalidOtpException {
        //Validating the Otp
        if (otpnum >= 0) {

            int serverOtp = otpService.getOtp(email);
            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(email);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
