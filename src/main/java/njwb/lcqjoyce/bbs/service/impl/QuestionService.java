package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.Question;

import java.util.List;

public interface QuestionService {


    int deleteByPrimaryKey(Long questionId);

    int insert(Question record);

    int insertSelective(Question record);

    QuestionDTO selectByPrimaryKey(Long questionId);

    Question selectById(Long questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    PageinfoDTO<QuestionDTO> getAll(String section, Integer page, Integer size,String search);

    PageinfoDTO listMyQuestion(Long userId, Integer page, Integer size);

    void inView(Long questionId);

    List<Question> findAll();

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);


    List<QuestionDTO> selectHot(QuestionDTO questionDTO);

}
