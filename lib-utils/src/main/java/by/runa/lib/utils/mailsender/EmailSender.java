package by.runa.lib.utils.mailsender;

import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.utils.IEmailSender;
import by.runa.lib.entities.User;

@Component
public class EmailSender implements IEmailSender {

	private static final String ADMIN_FROM_EMAIL_ADDRESS = "litvinenoknastia@gmail.com";

	private static final String ADMIN_TO_EMAIL_ADDRESS = "litvinenoknastia@gmail.com";

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private IBookDao bookDao;

	@Async
	public void sendEmailToAdmin(OrderDto dto) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		String text = prepareActivateRequestEmail(dto);
		configureMimeMessageHelper(helper, ADMIN_FROM_EMAIL_ADDRESS, ADMIN_TO_EMAIL_ADDRESS, text, "New Book!");
		mailSender.send(message);
	}

	@Async
	public void sendEmailsFromAdmin(BookDto dto) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		String text = prepareActivateRequestEmail(dto);
		for (User user : dto.getDepartments().get(0).getUsers()) {
			configureMimeMessageHelper(helper, ADMIN_FROM_EMAIL_ADDRESS, user.getEmail(), text,
					"New Book in your department!");
			mailSender.send(message);
		}
	}

	private String prepareActivateRequestEmail(BookDto dto) {
		VelocityContext context = createVelocityContextWithBasicParameters(dto);
		StringWriter stringWriter = new StringWriter();
		velocityEngine.mergeTemplate("mailtemplates/newBookMessage.vm", "UTF-8", context, stringWriter);
		return stringWriter.toString();
	}

	private VelocityContext createVelocityContextWithBasicParameters(BookDto dto) {
		VelocityContext context = new VelocityContext();
		context.put("name", bookDao.getByIsbn(dto.getIsbn()).getBookDetails().getName());
		context.put("author", bookDao.getByIsbn(dto.getIsbn()).getBookDetails().getAuthor());
		return context;
	}

	private String prepareActivateRequestEmail(OrderDto dto) {
		VelocityContext context = createVelocityContextWithBasicParameters(dto);
		StringWriter stringWriter = new StringWriter();
		velocityEngine.mergeTemplate("mailtemplates/newBookMessage.vm", "UTF-8", context, stringWriter);
		return stringWriter.toString();
	}

	private VelocityContext createVelocityContextWithBasicParameters(OrderDto dto) {
		VelocityContext context = new VelocityContext();
		context.put("UserId", dto.getUser().getId());

		return context;
	}

	private void configureMimeMessageHelper(MimeMessageHelper helper, String mailFrom, String mailTo, String mailText,
			String mailSubject) throws MessagingException {
		helper.setFrom(mailFrom);
		helper.setTo(mailTo);
		helper.setText(mailText, true);
		helper.setSubject(mailSubject);
	}
}