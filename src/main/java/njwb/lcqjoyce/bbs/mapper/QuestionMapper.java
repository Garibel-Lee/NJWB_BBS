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

    List<Question> selectAllByQuestionCreator(@Param("search") String search,@Param("questionCreator") Long questionCreator,@Param("questionStatus") Integer questionStatus, @Param("questionTop") Integer questionTop,@Param("offset")Integer offset,@Param("size") Integer size);

    int updateByPrimaryKey(Question record);

    Integer count(@Param("search") String search,@Param("questionCreator") Long questionCreator,@Param("questionStatus") Integer questionStatus, @Param("questionTop") Integer questionTop);

    Integer countByQuestionCreator(@Param("questionCreator")Long questionCreator);


    int updateViewByPrimaryKey(Question question);

    List<Question> selectAll();


    int addCommentCount(Question question);

    List<Question> selectRelated(Question question);

    List<Question> selectHot(Question question);

    List<Question> findInId(@Param("questionIds") List<Long> questionIds);

}