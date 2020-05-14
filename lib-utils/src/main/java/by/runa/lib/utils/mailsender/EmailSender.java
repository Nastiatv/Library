package by.runa.lib.utils.mailsender;

import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.utils.IEmailSender;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.Order;
import by.runa.lib.entities.User;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender implements IEmailSender {

    private static final String ADMIN_EMAIL_ADDRESS = "litvinenoknastia@gmail.com";

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUserDao userDao;

    @Async
    public void sendEmailsFromAdminAboutNewBook(Book book) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String text = prepareActivateRequestEmail(book, "mailtemplates/newBookMessage.vm");

        for (User user : userDao.getByDepartment(book.getDepartments().get(0).getName())) {
            configureMimeMessageHelper(helper, ADMIN_EMAIL_ADDRESS, user.getEmail(), text, "New Book in our Library!");
            mailSender.send(message);
        }
    }

    @Async
    public void sendEmailsFromAdminAboutDebts(Order order) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String text = prepareActivateRequestEmail(order.getBook(), "mailtemplates/returnBookMessage.vm");
        configureMimeMessageHelper(helper, ADMIN_EMAIL_ADDRESS, order.getUser().getEmail(), text,
                "Please return book!");
        mailSender.send(message);
    }

    @Async
    public void sendEmailsFromAdminDueDateTomorrow(Order order) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String text = prepareActivateRequestEmail(order.getBook(), "mailtemplates/dueDateBookMessage.vm");
        configureMimeMessageHelper(helper, ADMIN_EMAIL_ADDRESS, order.getUser().getEmail(), text,
                "Due date is tomorrow))");
        mailSender.send(message);
    }

    private String prepareActivateRequestEmail(Book book, String mailtemplates) {
        VelocityContext context = createVelocityContextWithBasicParameters(book);
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate(mailtemplates, "UTF-8", context, stringWriter);
        return stringWriter.toString();
    }

    private VelocityContext createVelocityContextWithBasicParameters(Book book) {
        VelocityContext context = new VelocityContext();
        context.put("name", book.getBookDetails().getName());
        context.put("author", book.getBookDetails().getAuthor());
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