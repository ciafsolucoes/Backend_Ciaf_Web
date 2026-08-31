package br.com.ciaf.admin.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.ciaf.admin.dtos.EmailDto;
import br.com.ciaf.admin.services.EmailService;

@RestController
@RequestMapping({"/sendemail"})
public class EmailController {
	
	private final EmailService emailService;
	
	private static String response;

	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@CrossOrigin
	@PostMapping
	public String sendMailBasic(@RequestBody EmailDto emailDto) throws MessagingException, IOException {

		try {
			System.out.println(emailDto.getEmailTo() + " - " + emailDto.getSubject());
			emailService.sendBasic(
					emailDto.getEmailTo(),
					emailDto.getSubject(),
					emailDto.getText(),
					emailDto.getAttached());
			response = "Mensagem Enviada com sucesso para " + emailDto.getEmailTo();
		}
		catch (Exception e) {
			response = "Tivemos um Erro no Envio do seu email. \n - Você pode tentar novamente mais tarde";
		}
		return response;
	}

	@CrossOrigin
	@PostMapping("/{emitente_id}")
	public String sendMail(@PathVariable Long emitente_id, @RequestBody EmailDto emailDto) throws MessagingException, IOException {
		
		try {
		    System.out.println(emailDto.getEmailTo() + " - " + emailDto.getSubject());
			emailService.send(
					emitente_id,
					emailDto.getEmailTo(), 
					emailDto.getSubject(), 
					emailDto.getText(),
					emailDto.getAttached());
			response = "Mensagem Enviada com sucesso para " + emailDto.getEmailTo();
		}
		catch (Exception e) {			
			response = "Tivemos um Erro no Envio do seu email. - Você pode tentar novamente mais tarde";
		}		
		return response;
	}
	@CrossOrigin
	@PostMapping(value = "/register")
	public String sendMailWithoutAttach(@RequestBody EmailDto emailDto) throws MessagingException, IOException {
		try {
			emailService.sendWithoutAttach(
					emailDto.getEmailTo(),
					emailDto.getSubject(),
					emailDto.getText()
			);
			response = "Mensagem Enviada com sucesso para " + emailDto.getEmailTo();
		}
		catch (Exception e) {
			response = "Tivemos um Erro no Envio do seu email. - Você pode tentar novamente mais tarde";
		}
		return response;
	}
	@CrossOrigin
	@PostMapping(value = "/register-adm")
	public String sendMailAdm(@RequestBody List<EmailDto> emailDto) throws MessagingException, IOException {
		final String[] clientEmail = {""};
		String suporteEmail;
		int count = 0;
		try {
			emailDto.forEach(email -> {
                try {
                    emailService.sendWithoutAttach(
                            email.getEmailTo(),
                            email.getSubject(),
                            email.getText()
                    );
                } catch (MessagingException | IOException e) {
                    throw new RuntimeException(e);
                }
                response = "Mensagem Enviada com sucesso para " + emailDto.get(0).getEmailTo() + " e " + email.getEmailTo();
			});

		}
		catch (Exception e) {
			response = "Tivemos um Erro no Envio do seu email. - Você pode tentar novamente mais tarde";
		}
		return response;
	}

}
