package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.Question;

public interface QuestionService{


    int deleteByPrimaryKey(Long questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    PageinfoDTO<QuestionDTO> getAll(int page, int size);

    PageinfoDTO listMyQuestion(Long userId, Integer page, Integer size);
}
