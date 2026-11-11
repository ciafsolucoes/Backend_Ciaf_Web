package br.com.ciaf.admin.services;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.ciaf.admin.entities.Emitente;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final static org.jboss.logging.Logger LOGGER = LoggerFactory.logger(EmailService.class);

    private final JavaMailSender mailSender;

    private final EmitenteServices emitenteService;

    public EmailService(EmitenteServices emitenteService, JavaMailSender mailSender) {
        this.emitenteService = emitenteService;
        this.mailSender = mailSender;
    }
    @Async
    public void sendBasic(String to, String subject, String text, String fileToAttach) throws MessagingException, IOException {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setText(text);
            mimeMessage.setSubject(subject);
			mimeMessage.setFrom("financeiro@ciaf.com.br");

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(fileToAttach);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachPart);

            mimeMessage.setContent(multipart);

            mailSender.send(mimeMessage);


        } catch (MailException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Async
    public void send(Long emitente_id, String to, String subject, String text, String fileToAttach) throws MessagingException, IOException {
         Emitente emitente = emitenteService.findById(emitente_id);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setText(text);
            mimeMessage.setSubject(subject);
//			mimeMessage.setFrom("gilvan@ciaf.com.br");
            if(emitente.getEmit_email() != null) {
                mimeMessage.setFrom(emitente.getEmit_email());
            }else{
                mimeMessage.setFrom("financeiro@ciaf.com.br");
            }

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(fileToAttach);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachPart);

            mimeMessage.setContent(multipart);

            mailSender.send(mimeMessage);


        } catch (MailException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Async
    public void sendWithoutAttach(String to, String subject, String text) throws MessagingException, IOException {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setText(text);
            mimeMessage.setSubject(subject);
			mimeMessage.setFrom("financeiro@ciaf.com.br");

            mailSender.send(mimeMessage);

        } catch (MailException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }


}
