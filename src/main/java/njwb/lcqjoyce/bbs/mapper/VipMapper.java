package njwb.lcqjoyce.bbs.mapper;
import njwb.lcqjoyce.bbs.entity.Vip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VipMapper {
    int deleteByPrimaryKey(Long vipId);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(Long vipId);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);

    List<Vip> selectByVipUseid(@Param("vipUseid")Long vipUseid);


}