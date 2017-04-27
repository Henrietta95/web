package com.hwt.mapper;



import org.springframework.stereotype.Repository;

import com.hwt.po.User;

@Repository
public interface UserMapper extends BaseMapper<User, String> {
	User getbyUsername(String username);
}
