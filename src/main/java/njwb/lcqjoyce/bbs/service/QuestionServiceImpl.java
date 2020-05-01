package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.entity.Question;
import njwb.lcqjoyce.bbs.mapper.QuestionMapper;
import njwb.lcqjoyce.bbs.service.impl.QuestionService;
@Service
public class QuestionServiceImpl implements QuestionService{

    @Resource
    private QuestionMapper questionMapper;

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
    public Question selectByPrimaryKey(Long questionId) {
        return questionMapper.selectByPrimaryKey(questionId);
    }

    @Override
    public int updateByPrimaryKeySelective(Question record) {
        return questionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Question record) {
        return questionMapper.updateByPrimaryKey(record);
    }

}
