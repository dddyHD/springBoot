package cn.suancloud.springBoot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import cn.suancloud.springBoot.model.Apply;

public interface ApplyDao extends CrudRepository<Apply,Long> {
  @Query("select a from Apply a")
  Page<Apply> getPageApply(Pageable pageable);
  @Query("from Apply where applicant=?1 and isAgree = false ")
  List<Apply> getApplying(String applicant);
}
