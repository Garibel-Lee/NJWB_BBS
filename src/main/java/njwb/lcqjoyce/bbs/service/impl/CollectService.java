package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Collect;

import java.util.List;

public interface CollectService {


    int deleteByPrimaryKey(Long collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Long collectId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    Integer selectByQuestionIdandUserId(Long questionId, Long userId);

    Collect selectByQuestIdandUId(Long questionId, Long userId);

    List<Collect> listMyCollsections(Long userId);

    void createNotify(Collect comment, String loginuserName, String outerTitle);

}
