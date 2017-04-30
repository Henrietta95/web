package com.hwt.service;

import java.io.Serializable;
import java.util.List;


public interface BaseService<T, ID extends Serializable> {

	int add(T t);

	int deleteById(ID id);

	int update(T t);

	T getById(ID id);

	List<T> list(T t);
	
	

}
