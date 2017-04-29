package com.hwt.mapper;




import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hwt.po.LinkMan;

@Repository
public interface LinkManMapper extends BaseMapper<LinkMan, String> {

	List<LinkMan> selectLinkMan(@Param("userId")String userId,@Param("account") String account,@Param("tel") String tel);
	
}
