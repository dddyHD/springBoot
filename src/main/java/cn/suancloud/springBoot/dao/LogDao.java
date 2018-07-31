package cn.suancloud.springBoot.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.suancloud.springBoot.model.Log;

@Repository
public interface LogDao extends CrudRepository<Log, Long> {
}
