package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.entity.Comment;
import njwb.lcqjoyce.bbs.mapper.CommentMapper;
import njwb.lcqjoyce.bbs.service.impl.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int deleteByPrimaryKey(Long commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    @Override
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    @Override
    public Comment selectByPrimaryKey(Long commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

}
