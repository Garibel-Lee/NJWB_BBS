package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.Question;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.exception.CustomizeException;
import njwb.lcqjoyce.bbs.mapper.QuestionMapper;
import njwb.lcqjoyce.bbs.mapper.UserMapper;
import njwb.lcqjoyce.bbs.service.impl.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Long questionId) {
        return questionMapper.deleteByPrimaryKey(questionId);
    }

    @Override
    public int insert(Question record) {
        return questionMapper.insert(record);
    }

    @Override
    public int insertSelective(Question record) {
        return questionMapper.insertSelective(record);
    }

    @Override
    public QuestionDTO selectByPrimaryKey(Long questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if (question == null || question.equals(null)) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getQuestionCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public Question selectById(Long questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if (question == null || question.equals(null)) {
            return null;
        }

        return question;
    }

    @Override
    public int updateByPrimaryKeySelective(Question record) {
        return questionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Question record) {
        return questionMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageinfoDTO<QuestionDTO> getAll(String section, Integer page, Integer size) {
        PageinfoDTO<QuestionDTO> pageinfoDTO = new PageinfoDTO<>();
        Integer totalCount = 0;
        if (section.equals("index")) {
            totalCount = questionMapper.count(null, null, null);
        } else if (section.equals("top")) {
            totalCount = questionMapper.count(null, null, 1);
        } else if (section.equals("unsolve")) {
            totalCount = questionMapper.count(null, 0, null);
        } else if (section.equals("solved")) {
            totalCount = questionMapper.count(null, 1, null);
        }
        Integer totalPage;
        if (totalCount.equals(0)) {
            return pageinfoDTO;
        }
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageinfoDTO.setPageinfo(totalPage, page);

        Integer offset = size * (page - 1);
        //偏移量
        List<Question> questions = new ArrayList<>();
        if (section.equals("index")) {
            questions = questionMapper.selectAllByQuestionCreator(null, null, null, offset, size);
        } else if (section.equals("top")) {
            questions = questionMapper.selectAllByQuestionCreator(null, null, 1, offset, size);
        } else if (section.equals("unsolve")) {
            questions = questionMapper.selectAllByQuestionCreator(null, 0, null, offset, size);
        } else if (section.equals("solved")) {
            questions = questionMapper.selectAllByQuestionCreator(null, 1, null, offset, size);
        }

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getQuestionCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageinfoDTO.setData(questionDTOS);
        return pageinfoDTO;
    }


    @Override
    public PageinfoDTO listMyQuestion(Long userId, Integer page, Integer size) {

        PageinfoDTO<QuestionDTO> pageinfoDTO = new PageinfoDTO();
        Integer totalCount = questionMapper.count(userId, null, null);
        if (totalCount.equals(0)) {
            return pageinfoDTO;
        }
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageinfoDTO.setPageinfo(totalPage, page);

        Integer offset = size * (page - 1);
        //偏移量
        List<Question> questions = questionMapper.selectAllByQuestionCreator(userId, null, null, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getQuestionCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageinfoDTO.setData(questionDTOS);
        return pageinfoDTO;
    }

    @Override
    public void inView(Long questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);
        questionMapper.updateViewByPrimaryKey(question);
    }

    @Override
    public List<Question> findAll() {
        return questionMapper.selectAll();
    }


    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getQuestionTag())) {
            return new ArrayList<>();
        }

        Question question = new Question();
        question.setQuestionId(queryDTO.getQuestionId());
        question.setQuestionTag(queryDTO.getQuestionTag());

        List<Question> questions = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

    @Override
    public List<QuestionDTO> selectHot(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getQuestionTag())) {
            return new ArrayList<>();
        }

        Question question = new Question();
        question.setQuestionId(queryDTO.getQuestionId());


        List<Question> questions = questionMapper.selectHot(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
