package cn.suancloud.springBoot.util;

import java.util.Iterator;

/**
 * Created by Administrator on 2018/3/19.
 */
public class ProfitUtil {

  public static <T> T getLatestProfit(Iterator<T> iterator) {
    iterator.next();iterator.next();iterator.next();iterator.next();
    return iterator.next();
  }
}
