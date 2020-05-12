package by.runa.lib.api.utils;

import javax.mail.MessagingException;

import by.runa.lib.entities.Book;
import by.runa.lib.entities.Order;

public interface IEmailSender {

	void sendEmailsFromAdminAboutNewBook(Book book) throws MessagingException;

	void sendEmailsFromAdminAboutDebts(Order order) throws MessagingException;

	void sendEmailsFromAdminDueDateTomorrow(Order order) throws MessagingException;
}