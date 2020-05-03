package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    int deleteByPrimaryKey(Long questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(Question record);

    List<Question> selectAllByQuestionCreator(@Param("questionCreator") Long questionCreator, @Param("offset")Integer offset,@Param("size") Integer size);

    int updateByPrimaryKey(Question record);

    Integer count();

    Integer countByQuestionCreator(@Param("questionCreator")Long questionCreator);



}