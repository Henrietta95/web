package com.hwt.service;

import java.util.List;

import com.hwt.po.LinkMan;

public interface LinkManService extends BaseService<LinkMan, String> {

	List<LinkMan> selectLinkMan(String userId, String account, String tel);
}
