package com.hwt.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwt.mapper.LinkManMapper;
import com.hwt.po.LinkMan;
import com.hwt.service.LinkManService;

@Service
public class LinkManServiceImpl extends BaseServiceImpl<LinkMan, String> implements
LinkManService {

	@Autowired
	private LinkManMapper linkManMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(linkManMapper);
	}

	@Override
	public List<LinkMan> selectLinkMan(String userId, String account, String tel) {
		
		return linkManMapper.selectLinkMan(userId,account,tel);
	}

	@Override
	public List<LinkMan> selectLinkManByTel(String tel, String userId) {
		return linkManMapper.selectLinkManByTel(tel,userId);
	}
}
