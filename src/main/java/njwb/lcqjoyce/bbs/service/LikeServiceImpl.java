package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Like;
import njwb.lcqjoyce.bbs.mapper.LikeMapper;
import njwb.lcqjoyce.bbs.service.impl.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeMapper likeMapper;

    @Override
    public int deleteByPrimaryKey(Long likeId) {
        return likeMapper.deleteByPrimaryKey(likeId);
    }

    @Override
    public int insert(Like record) {
        return likeMapper.insert(record);
    }

    @Override
    public int insertSelective(Like record) {
        return likeMapper.insertSelective(record);
    }

    @Override
    public Like selectByPrimaryKey(Long likeId) {
        return likeMapper.selectByPrimaryKey(likeId);
    }

    @Override
    public int updateByPrimaryKeySelective(Like record) {
        return likeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Like record) {
        return likeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Like> selectByCommentId(Long commentId) {
        return likeMapper.selectAllByLikePostid(commentId);

    }

    @Override
    public Like selectByPostIDandReplyId(Long postId, Long replyId) {

        List<Like> likes = likeMapper.selectAllByLikePostidAndLikeReplyid(postId, replyId);
        if (likes.size() == 0) {
            return null;
        } else {
            return likes.get(0);
        }

    }

    @Override
    public int updateByByPostIDandReplyId(Like record) {
        return likeMapper.updateByPrimaryKeySelective(record);
    }

}
