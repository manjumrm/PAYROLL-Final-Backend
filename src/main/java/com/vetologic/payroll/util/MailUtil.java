package com.vetologic.payroll.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.vetologic.payroll.entity.ContactUsEntity;
import com.vetologic.payroll.entity.EmployeeEntity;

@Component
public class MailUtil {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendUserEmail(final EmployeeEntity employeeEntity) {
		final String emailId = employeeEntity.getEmailId();
		final String username = employeeEntity.getUsername();
		final String password = employeeEntity.getPassword();

		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(emailId);
				message.setSubject("A new account has been created for you");
				message.setText("Welcome to Payroll!! "
						+ "<br> Your Username is: <b>" + username
						+ "</b> and <br>" + "Password is: <b>" + password
						+ "</b>", true);

			}
		});
	}

	public void changePasswordEmail(String userEmail, String newPassword, String userName) {
			final String userName1 = userName;
			final String email = userEmail;
			final String password = newPassword;
			mailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage)
						throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
							true, "UTF-8");
					message.setTo(email);
					message.setSubject("User" + "  " + userName1 + "  "
							+ "has changed the password");
					message.setText(
							"<br> New Password is: <b>" + password + "</b>", true);

				}
			});
		
	}

	public void sendForgotPasswordEmail(String forgotPasswordEmail, String newPassword) {
		final String emailId = forgotPasswordEmail;
		final String password = newPassword;

		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(emailId);
				message.setSubject("Payroll System Reset Password");
				message.setText("Welcome to Payroll System!! "
						+ "<br> Your new Password is: <b>" + password + "</b>",
						true);
			}
		});
		
	}

	public void sendMailToCompany(ContactUsEntity contactUsEntity) {
		final String firstName = contactUsEntity.getFirstName();
		final String lastName = contactUsEntity.getLastName();
		final String email = contactUsEntity.getEmail();
		final String subject = contactUsEntity.getSubject();
		final String message1 = contactUsEntity.getMessage();
		/*final String emailTo = "info@samarthtechnologies.com";*/
		final String emailTo = "jagadeesha@samarthtechnologies.com";
		
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(emailTo);
				message.setSubject(subject);
				message.setText(
						"<br> FirstName : <b>" + firstName + "</b>"
						+"<br> LastName : <b>" + lastName + "</b>"
						+"<br> Email : <b>" + email + "</b>"
						+ "<br> Subject : <b>" + subject + "</b>"
						+ "<br> Message : <b>" + message1 + "</b>", true);

			}
		});
		
	}
	
}
