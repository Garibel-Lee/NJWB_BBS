package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Role;
public interface RoleService{


    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}
