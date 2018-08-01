package cn.suancloud.springBoot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import cn.suancloud.springBoot.model.Log;

@Repository
public interface LogDao extends CrudRepository<Log, Long> {
  @Query("select l from Log l")
  Page<Log> getPageLog(Pageable pageable);
}
