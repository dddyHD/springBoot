package cn.suancloud.springBoot.service;

import java.util.List;
import java.util.Map;

import cn.suancloud.springBoot.model.Apply;

public interface ApplyService extends BaseService<Apply,Long> {
  Map<String,Object> getPageApply(int page, int size);
  List<Apply> getApplying(String applicant);
}
