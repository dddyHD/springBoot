package cn.suancloud.springBoot.service.impl;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

import cn.suancloud.springBoot.service.BaseService;

/**
 * Created by teruo on 2018/1/31.
 */
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T,ID>{

  CrudRepository<T,ID> dao;

  public BaseServiceImpl() {
  }

  public BaseServiceImpl(CrudRepository<T, ID> dao) {
    this.dao = dao;
  }

  @Override
  public <S extends T> S save(S entity) {
    return dao.save(entity);
  }

  @Override
  public <S extends T> Iterable<S> save(Iterable<S> entities) {
    return dao.saveAll(entities);
  }

  @Override
  public T findOne(ID id) {
    return (T) dao.findById(id);
  }

  @Override
  public boolean exists(ID id) {
    return dao.existsById(id);
  }

  @Override
  public Iterable<T> findAll() {
    return dao.findAll();
  }

  @Override
  public Iterable<T> findAll(Iterable<ID> ids) {
    return dao.findAllById(ids);
  }

  @Override
  public long count() {
    return dao.count();
  }

  @Override
  public void delete(ID id) {
    dao.deleteById(id);
  }

  @Override
  public void delete(T entity) {
    dao.delete(entity);
  }

  @Override
  public void delete(Iterable<? extends T> entities) {
    dao.deleteAll(entities);
  }

  @Override
  public void deleteAll() {
    dao.deleteAll();
  }
}
