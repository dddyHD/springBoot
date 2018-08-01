package cn.suancloud.springBoot.service;

import java.util.Map;

import cn.suancloud.springBoot.model.Log;

/**
 * 日志接口
 */
public interface LogService extends BaseService<Log, Long> {
  Map<String, Object> getPageLog(int page, int size);
}
