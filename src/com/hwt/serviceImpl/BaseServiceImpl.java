package com.hwt.serviceImpl;

import java.io.Serializable;
import java.util.List;

import com.hwt.mapper.BaseMapper;
import com.hwt.service.BaseService;

public class BaseServiceImpl<T, ID extends Serializable> implements
		BaseService<T, ID> {

	private BaseMapper<T, ID> baseMapper;

	public BaseMapper<T, ID> getBaseMapper() {
		return baseMapper;
	}

	public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public int add(T t) {
		return baseMapper.insert(t);
	}

	@Override
	public int deleteById(ID id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public int update(T t) {
		return baseMapper.update(t);
	}

	@Override
	public T getById(ID id) {
		return baseMapper.getById(id);
	}

	@Override
	public List<T> list(T t) {
		return baseMapper.list(t);
	}


}
