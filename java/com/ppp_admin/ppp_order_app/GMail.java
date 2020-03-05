package com.ppp_admin.ppp_order_app;

/**
 * Created by Admin on 10/11/2015.
 */

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMail
{
    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    final String emailPort = "587";// gmail's smtp port
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";

    String fromEmail;
    String fromPassword;
    List<String> toEmailList;
    String emailSubject;
    String emailBody;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    // create constructor
    public GMail() {}

    // create constructor by passing parameter
    public GMail(String fromEmail, String fromPassword,
                 List<String> toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.host", emailHost);
        emailProperties.put("mail.smtp.starttls.enable", starttls);

        Log.i("GMail", "Mail server properties set.");
    }
    /*********************************************************************************
        Name :- createEmailMessage
        Description :- This class represents a MIME style email message.
                       It implements the Message abstract class and the MimePart interface.
     *************************************************************************************/

    public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException {

        mailSession = Session.getInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        // Set From: header field of the header.
        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
       for (String toEmail : toEmailList) {
            Log.i("GMail", "toEmail: " + toEmail);
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }

         // Set Subject: header field
        emailMessage.setSubject(emailSubject);

        // set content of message
        emailMessage.setContent(emailBody, "text/html");// for a html email
        Log.i("GMail", "Email Message created.");
        return emailMessage;
    }

    /*********************************************************************************************
        Name :- sendEmail
        Description :- send mail function use to establish connection from sender to receiver
                        through transport method.
     ********************************************************************************************/
    public void sendEmail() throws AddressException, MessagingException
    {
        // Send message
        Transport transport = mailSession.getTransport("smtp");

        // set connection to send mail from sender to reciver
        transport.connect(emailHost,fromEmail, fromPassword);
        Log.i("GMail","all recipients: "+emailMessage.getAllRecipients());

        // sendMessage sends message to receiver
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());

        // close connection
        transport.close();
        Log.i("GMail", "Email sent successfully.");
    }
}