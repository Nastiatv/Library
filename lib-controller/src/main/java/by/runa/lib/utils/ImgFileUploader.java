package by.runa.lib.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.dao.BookDao;
import by.runa.lib.dao.BookDetailsDao;

@Component
public class ImgFileUploader {

	@Autowired
	BookDao bookDao;
	@Autowired
	BookDetailsDao bookDetailsDao;

	private static final String IMAGE_EXTENSION = ".png";

	private static final String FOLDER_PATH = "classpath:static/img/";

	public void createOrUpdate(UserDto dto, MultipartFile file) throws IOException {
		if (file != null && !file.isEmpty()) {
			String username = dto.getUsername();
			String filePath = new StringBuilder(FOLDER_PATH).append(username).append(IMAGE_EXTENSION).toString();
			File userImage;
			try {
				userImage = ResourceUtils.getFile(filePath);
			} catch (FileNotFoundException e) {
				URL fileUrl = ResourceUtils.getURL(FOLDER_PATH);
				userImage = new File(
						new StringBuilder(fileUrl.getPath()).append(username).append(IMAGE_EXTENSION).toString());
			}
			Path path = Paths.get(userImage.getPath());
			byte[] bytes = file.getBytes();
			Files.write(path, bytes);
		}
	}

	public void createOrUpdate(BookDto dto, MultipartFile file) throws IOException {
		if (file != null && !file.isEmpty()) {
			Long bookId = dto.getId();
			String filePath = new StringBuilder(FOLDER_PATH).append(bookId).append(IMAGE_EXTENSION).toString();
			File bookImage;
			try {
				bookImage = ResourceUtils.getFile(filePath);
			} catch (FileNotFoundException e) {
				URL fileUrl = ResourceUtils.getURL(FOLDER_PATH);
				bookImage = new File(
						new StringBuilder(fileUrl.getPath()).append(bookId).append(IMAGE_EXTENSION).toString());
			}
			Path path = Paths.get(bookImage.getPath());
			byte[] bytes = file.getBytes();
			Files.write(path, bytes);
		}
	}
}
