package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
