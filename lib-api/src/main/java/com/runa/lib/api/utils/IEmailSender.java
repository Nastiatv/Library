package com.runa.lib.api.utils;

import javax.mail.MessagingException;

import com.runa.lib.api.dto.OrderDto;

public interface IEmailSender {

	void sendEmailToAdmin(OrderDto dto) throws MessagingException;
}