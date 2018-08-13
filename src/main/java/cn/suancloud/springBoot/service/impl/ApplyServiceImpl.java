package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.suancloud.springBoot.dao.ApplyDao;
import cn.suancloud.springBoot.model.Apply;
import cn.suancloud.springBoot.service.ApplyService;

@Service
public class ApplyServiceImpl extends BaseServiceImpl<Apply, Long> implements ApplyService {
  ApplyDao dao;

  @Autowired
  public ApplyServiceImpl(ApplyDao dao) {
    super(dao);
    this.dao = dao;
  }

  public Map<String,Object> getPageApply(int page,int size){
    Map map = new HashMap();
    Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "id");
    Page<Apply> applyPage = dao.getPageApply(pageable);
    map.put("applyList",applyPage.getContent());
    map.put("total",applyPage.getTotalPages());
    return map;
  }

  @Override
  public List<Apply> getApplying(String applicant) {
    return dao.getApplying(applicant);
  }


}
