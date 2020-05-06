package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.entity.Violation;
import njwb.lcqjoyce.bbs.mapper.ViolationMapper;
import njwb.lcqjoyce.bbs.service.impl.ViolationService;

@Service
public class ViolationServiceImpl implements ViolationService {

    @Resource
    private ViolationMapper violationMapper;

    @Override
    public int deleteByPrimaryKey(Long violationId) {
        return violationMapper.deleteByPrimaryKey(violationId);
    }

    @Override
    public int insert(Violation record) {
        return violationMapper.insert(record);
    }

    @Override
    public int insertSelective(Violation record) {
        return violationMapper.insertSelective(record);
    }

    @Override
    public Violation selectByPrimaryKey(Long violationId) {
        return violationMapper.selectByPrimaryKey(violationId);
    }

    @Override
    public int updateByPrimaryKeySelective(Violation record) {
        return violationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Violation record) {
        return violationMapper.updateByPrimaryKey(record);
    }

}

