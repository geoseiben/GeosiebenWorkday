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
public void closeticket(String toname,String tomail,int ticketid,String remarks,String issue) throws MessagingException, UnsupportedEncodingException{
    String html = """
    <div style="font-family: Arial, sans-serif; color: #333; line-height: 1.6; max-width: 600px; border: 1px solid #f0f0f0; padding: 20px; border-radius: 8px;">
        <h2 style="color: #059669;">Ticket Closed</h2>
        <p>Hello <strong>%s</strong>,</p>
        <p>Great news! Your IT Ticket has been marked as <strong>Resolved</strong>. Please see the closure summary below:</p>
        
        <div style="background-color: #f9fafb; padding: 15px; border-radius: 5px; border-left: 4px solid #059669;">
            <strong>Ticket Details:</strong><br>
            <span style="color: #666;">Issue:</span> %s <br>
            <span style="color: #666;">Closed On:</span> %s <br>
            <hr style="border:none; border-top:1px solid #ddd; margin: 10px 0;" />
            <strong>Resolution Summary:</strong><br>
            %s
        </div>

        <p>If you feel this issue is not fully resolved, you can reopen it or submit a new request via the portal:</p>
        <p style="margin-top: 20px;">
            <a href="https://www.geosieben.org/itticket" 
               style="background-color: #059669; color: #ffffff; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block; font-weight: bold;">
               Go to Portal
            </a>
        </p>
        
        <p style="margin-top: 30px; border-top: 1px solid #eee; pt-10px;">
            Regards,<br>
            <strong>IT Support Team</strong><br>
            Geosieben Consulting Private Limited
        </p>
        
        <small style="color:#999; display: block; margin-top: 20px; font-size: 11px;">
            &copy; %d Geosieben Consulting Private Limited. This is an automated notification.
        </small>
    </div>
    """.formatted(
        toname,                        // The person who opened the ticket
        issue,                               // Original issue title
        AddonServ.formatDate(LocalDate.now()),  // The date it was closed
        remarks,                     // What was done to fix it
        LocalDate.now().getYear()
    );
            MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from.trim(), fromname);
        helper.setTo(tomail.trim());
        helper.setSubject("IT Ticket Closed For The Reference ID :"+ticketid);
        helper.setText(html, true); // true enables HTML
        mailSender.send(message);

}


}