package by.runa.lib.api.utils;

import javax.mail.MessagingException;

import by.runa.lib.api.dto.OrderDto;

public interface IEmailSender {

	void sendEmailToAdmin(OrderDto dto) throws MessagingException;
}