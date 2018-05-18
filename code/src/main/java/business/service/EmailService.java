package business.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;

public class EmailService extends Service {

    public EmailService() {
    }

    public void sendMail(String recipient, String sbj, String msg) throws Exception {
        String protocol = "smtp";
        String from = "sdproj1@outlook.com";
        String pass = "parolalaMAIL";
        String host = "smtp.live.com";
        String port = "25";

        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties);
        try {
            Transport trans = session.getTransport(protocol);
            trans.connect(host, from, pass);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(sbj);
            message.setText(msg);
            trans.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            throw new Exception("Mail exception: " + e.toString(), e);
        }
    }
}
