package com.pepper.business.core.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.pepper.core.BaseDao;
import com.pepper.core.BaseService;
import com.pepper.core.exception.BusinessException;
import com.pepper.persistence.BaseSearch;
import com.pepper.vo.Pager;

public abstract class BaseServiceImpl<T> extends BaseSearch implements BaseService<T> {

	@Autowired
	private BaseDao<T> baseDao;

	/**
	 * 新增修改
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public T save(T entity) {
		return baseDao.save(entity);
	}

	/**
	 *
	 */
	@Override
	public T update(T entity) {
		return baseDao.update(entity);
	}

	/**
	 * 批量新增修改
	 *
	 * @param entities
	 * @return
	 */
	@Override
	public List<T> saveAll(List<T> entities) {
		return baseDao.saveAll(entities);
	}

	/**
	 * 根据id查询
	 *
	 * @param id
	 * @return
	 */
	@Override
	public T findById(String id) {
		Optional<T> t = baseDao.findById(id);
		if (t.isPresent()) {
			return t.get();
		}
		return null;
	}

	/**
	 * 获取所有
	 *
	 * @return
	 */
	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	/**
	 * 根据id删除
	 *
	 * @param id
	 */
	@Override
	public void deleteById(String id) {
		baseDao.deleteById(id);
	}

	/**
	 * 删除指定列表中的所有对象
	 *
	 * @param entities
	 */
	@Override
	public void deleteAll(List<? extends T> entities) {
		baseDao.deleteAll(entities);
	}

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param pageSize
	 * @param searchParameter
	 * @param sortParameter
	 * @return
	 */
	@Override
	public Pager<T> list(Integer page, Integer pageSize, Map<String, Object> searchParameter,
			Map<String, Object> sortParameter, Class<T> returnType) {
		return baseDao.queryPage(page, pageSize, searchParameter, sortParameter);
	}

	/**
	 * 不分页查询
	 *
	 * @param searchParameter
	 * @param sortParameter
	 * @return
	 */
	@Override
	public List<T> list(Map<String, Object> searchParameter, Map<String, Object> sortParameter, Class<T> returnType) {
		return baseDao.list(searchParameter, sortParameter);
	}

	/**
	 * 简单排序查询
	 *
	 * @param sort
	 * @return
	 */
	@Override
	public List<T> findAll(Sort sort) {
		return baseDao.findAll(sort);
	}

	/**
	 * 根据id数组获取对象
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public List<T> findAllById(Iterable<String> ids) {
		return baseDao.findAllById(ids);
	}

	/**
	 * 插入对象的子类
	 *
	 * @param entities
	 * @return
	 */
	@Override
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return baseDao.saveAll(entities);
	}

	@Override
	public T load(String id) {
		T t = findById(id);
		if (t == null) {
			notFindObjectMessage();
		}
		return t;
	}

	protected void notFindObjectMessage() {
		new BusinessException("对象不存在");
	}

}
