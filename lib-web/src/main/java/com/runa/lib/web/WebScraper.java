package com.runa.lib.web;
import java.io.IOException;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.runa.lib.api.dao.IBookDetailsDao;
import com.runa.lib.entities.BookDetails;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebScraper {

	@Autowired
	private IBookDetailsDao bookDetailsDao;
	
	private WebClient webclient = WebClientProvider.getDefaultWebClient();

	private static final String SEARCH_URL = "https://www.bookfinder.com/search/?author=&title=&lang=en&isbn=%s&new_used=*&destination=by&currency=USD&mode=basic&st=sr&ac=qr";

	private static final String IMAGE_URL = "https://pictures.abebooks.com/isbn/%s-us-300.jpg";

	public BookDetails getBookDetailsFromWeb(String isbn) {
		isbn = RegExUtils.replaceAll(isbn, "-", StringUtils.EMPTY);
		BookDetails bookDetails = new BookDetails();
		try {
			String url = String.format(SEARCH_URL, isbn);
			HtmlPage bookPage=webclient.getPage(url);

			HtmlElement description = (HtmlElement) bookPage.getByXPath("//div[@id='bookSummary']").get(0);
			HtmlElement name = (HtmlElement) bookPage.getByXPath("//span[@id='describe-isbn-title']").get(0);
			HtmlElement author = (HtmlElement) bookPage.getByXPath("//span[@itemprop='author']").get(0);

			bookDetails.setDescription(description.getTextContent());
			bookDetails.setPicture(String.format(IMAGE_URL, isbn));
			bookDetails.setName(name.getTextContent());
			bookDetails.setAuthor(author.getTextContent());
			bookDetailsDao.update(bookDetails);
			return bookDetails;
		} catch (FailingHttpStatusCodeException | IOException e) {
			log.info("Bad url response!", e);
			return bookDetails;
		}
	}
}