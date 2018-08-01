package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import cn.suancloud.springBoot.dao.LogDao;
import cn.suancloud.springBoot.model.Log;
import cn.suancloud.springBoot.service.LogService;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {
  LogDao dao;

  @Autowired
  public LogServiceImpl(LogDao dao) {
    super(dao);
    this.dao = dao;
  }

  @Override
  public Map<String, Object> getPageLog(int page, int size) {
    Map map = new HashMap();
    Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "id");
    Page<Log> logPage = dao.getPageLog(pageable);
    map.put("logList", logPage.getContent());
    map.put("total", logPage.getTotalPages());
    return map;
  }
}
