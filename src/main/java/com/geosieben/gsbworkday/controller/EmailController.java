package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    @ResponseBody
    public String sendMail() {
        String name="Pavan";
        String html = "        <p>Hello <strong>"+name+"</strong>,</p>\n" +
                "                <p><strong>$ename</strong> has submitted a new leave request. Please find the details below for your review:</p>\n" +
                "                <hr style='border:none; border-top:1px solid #eee;' />\n" +
                "                <p>\n" +
                "                    <strong>Employee Name:</strong> $ename <br>\n" +
                "                    <strong>Employee ID:</strong> $eid <br>\n" +
                "                    <strong>Leave Type:</strong> $ltype <br>\n" +
                "                    <strong>Duration:</strong> $fromm to $too <br>\n" +
                "                    <strong>Total Days:</strong> $days <br>\n" +
                "                    <strong>Reason:</strong> $reason <br>\n" +
                "                    <strong>Date Applied:</strong> $appliedOn\n" +
                "                </p>\n" +
                "                <hr style='border:none; border-top:1px solid #eee;' />\n" +
                "                <p>To approve or reject this request, please log in to the portal:</p>\n" +
                "                <p><a href='https://www.geosieben.org' style='background-color: #2563eb; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Review Application</a></p>\n" +
                "                <p>Regards,<br>\n" +
                "                <strong>Leave Management System</strong><br>\n" +
                "                Geosieben Consulting Private Limited</p>\n" +
                "                <br>\n" +
                "                <small style='color:#777;'>&copy; $yearr Geosieben Consulting Private Limited. All rights reserved.</small>";
        try {
            emailService.sendHtmlEmail("pavanaca28@gmail.com", "Test HTML Email", html);
            return "Email sent successfully!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}


