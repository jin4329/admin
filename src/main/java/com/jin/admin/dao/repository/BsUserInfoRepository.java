package com.jin.admin.dao.repository;

import com.jin.admin.model.BsUserInfo;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Jin 
 * @create 2019-11-27
 */
@Repository
public interface BsUserInfoRepository extends BaseRepository<BsUserInfo, Long>{
    BsUserInfo getByLoginNameAndType(String loginName, Long type);
}
