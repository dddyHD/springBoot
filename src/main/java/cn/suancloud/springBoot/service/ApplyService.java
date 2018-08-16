package cn.suancloud.springBoot.service;

import java.util.List;
import java.util.Map;

import cn.suancloud.springBoot.model.Apply;

public interface ApplyService extends BaseService<Apply,Long> {
  /**
   *  分页返回数据
   * @param page 页数
   * @param size 一页多少条数据
   * @return
   */
  Map<String,Object> getPageApply(int page, int size);
  List<Apply> getApplying(String applicant);

  /**
   * 是否项目正在申请
   */
  boolean isApplying(String applicant,String project);
}
