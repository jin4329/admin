package com.jin.admin.dao.repository;

import com.jin.admin.model.SysAdminUser;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Jin 
 * @create 2019-11-27
 */
@Repository
public interface SysAdminUserRepository extends BaseRepository<SysAdminUser, Long>{
    SysAdminUser getByUserId(Long userId);
}
