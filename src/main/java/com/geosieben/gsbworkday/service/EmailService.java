package com.geosieben.gsbworkday.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.geosieben.gsbworkday.addon.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
 private String fromname="Geosieben! HR Portal";


    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from.trim(), fromname);
        helper.setTo(to.trim());
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true enables HTML

        mailSender.send(message);
    }
    public void leaveApplicationHR(String to, String toname, String employeename, String eid, LocalDate fromDate,LocalDate toDate,String leaveType,String reason,LocalDate appliedOn,double days,String subject,String year) throws MessagingException, UnsupportedEncodingException {
        String html = "        <p>Hello <strong>"+toname+"</strong>,</p>\n" +
                "                <p><strong>"+employeename+"</strong> has submitted a new leave request. Please find the details below for your review:</p>\n" +
                "                <hr style='border:none; border-top:1px solid #eee;' />\n" +
                "                <p>\n" +
                "                    <strong>Employee Name:</strong> "+employeename+" <br>\n" +
                "                    <strong>Employee ID:</strong> "+eid+" <br>\n" +
                "                    <strong>Leave Type:</strong> "+leaveType+" <br>\n" +
                "                    <strong>Duration:</strong> "+AddonServ.formatDate(fromDate)+" to "+AddonServ.formatDate(toDate)+" <br>\n" +
                "                    <strong>Total Days:</strong> "+days+" <br>\n" +
                "                    <strong>Reason:</strong> "+reason+" <br>\n" +
                "                    <strong>Date Applied:</strong> "+AddonServ.formatDate(LocalDate.now())+"\n" +
                "                </p>\n" +
                "                <hr style='border:none; border-top:1px solid #eee;' />\n" +
                "                <p>To approve or reject this request, please log in to the portal:</p>\n" +
                "                <p><a href='https://www.geosieben.org' style='background-color: #2563eb; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Review Application</a></p>\n" +
                "                <p>Regards,<br>\n" +
                "                <strong>Leave Management System</strong><br>\n" +
                "                Geosieben Consulting Private Limited</p>\n" +
                "                <br>\n" +
                "                <small style='color:#777;'>&copy; "+year+" Geosieben Consulting Private Limited. All rights reserved.</small>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from.trim(), fromname);
        helper.setTo(to.trim());
        helper.setSubject(subject);
        helper.setText(html, true); // true enables HTML
        mailSender.send(message);

    }

    public  void raiseTicket(String toname,String tomail,String employeename,String issue,String descriptin,String priority,LocalDate raisedOn) throws MessagingException, UnsupportedEncodingException {
        String subject="IT Ticket submitted By "+employeename;
        String html = "        <p>Hello <strong>"+toname+"</strong>,</p>\n" +
                "        <p><strong>"+employeename+"</strong> has submitted a new IT Ticket. Please find the details below for your review:</p>\n" +
                "        <hr style='border:none; border-top:1px solid #eee;' />\n" +
                "        <p>\n" +
                "            <strong>Employee Name:</strong> "+employeename+" <br>\n" +
                "            <strong>Issue:</strong> "+issue+" <br>\n" +
                "            <strong>Priority:</strong> "+priority+" <br>\n" +
                "            <strong>Description:</strong> "+descriptin+" <br>\n" +
                "            <strong>Date Submitted:</strong> "+AddonServ.formatDate(raisedOn)+"\n" +
                "        </p>\n" +
                "        <hr style='border:none; border-top:1px solid #eee;' />\n" +
                "        <p>To review or update this ticket, please log in to the portal:</p>\n" +
                "        <p><a href='https://www.geosieben.org/itticket' style='background-color: #2563eb; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Review Ticket</a></p>\n" +
                "        <p>Regards,<br>\n" +
                "        <strong>IT Management System</strong><br>\n" +
                "        Geosieben Consulting Private Limited</p>\n" +
                "        <br>\n" +
                "        <small style='color:#777;'>&copy; "+LocalDate.now().getYear()+" Geosieben Consulting Private Limited. All rights reserved.</small>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from.trim(), fromname);
        helper.setTo(tomail.trim());
        helper.setSubject(subject);
        helper.setText(html, true); // true enables HTML
        mailSender.send(message);
    }



}