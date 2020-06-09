package by.runa.lib.utils.mappers;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.entities.Feedback;
import by.runa.lib.entities.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Objects;

@Component
public class FeedbackMapper extends AMapper<Feedback, FeedbackDto> {

    public FeedbackMapper(IUserDao userDao, ModelMapper mapper, IBookDao bookDao) {
        super(Feedback.class, FeedbackDto.class);
        this.userDao = userDao;
        this.bookDao = bookDao;
        this.mapper = mapper;
    }

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IBookDao bookDao;

    @Autowired
    private AMapper<User, UserDto> userMapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Feedback.class, FeedbackDto.class).addMappings(m -> {
            m.skip(FeedbackDto::setUserDto);
            m.skip(FeedbackDto::setBookId);
        }).setPostConverter(toDtoConverter());
        mapper.createTypeMap(FeedbackDto.class, Feedback.class).addMappings(m -> {
            m.skip(Feedback::setUser);
            m.skip(Feedback::setBook);
        }).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Feedback source, FeedbackDto destination) {
        destination.setUserDto(getUser(source));
        destination.setBookId(getBookId(source));
    }

    private Long getBookId(Feedback source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getBook().getId();
    }

    private UserDto getUser(Feedback source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : userMapper.toDto(source.getUser());
    }

    @Override
    void mapSpecificFields(FeedbackDto source, Feedback destination) {
        destination.setUser(userDao.get(source.getUserDto().getId()));
        destination.setBook(bookDao.get(source.getBookId()));
    }

}
