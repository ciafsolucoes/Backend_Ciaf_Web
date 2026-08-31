package br.com.ciaf.admin.dtos;

import org.springframework.stereotype.Component;

@Component
public class EmailDto {
	
	private String emailTo;	
	private String subject;	
	private String text;	
	private String attached;	
	
	public EmailDto() {}

	public EmailDto(String emailTo, String subject, String text, String attached) {
		super();
		this.emailTo = emailTo;
		this.subject = subject;
		this.text = text;
		this.attached = attached;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAttached() {
		return attached;
	}

	public void setAttached(String attached) {
		this.attached = attached;
	}
	    
}
