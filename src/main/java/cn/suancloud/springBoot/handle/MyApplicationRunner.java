package cn.suancloud.springBoot.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.suancloud.springBoot.util.IgnoreCertificates;

/**
 * Created by gin on 2018/08/13 忽略SSL验证
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
  protected static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

  @Override
  public void run(ApplicationArguments var1) throws Exception {
    logger.info("忽略SSL认证。。。");
    try {
      IgnoreCertificates.ignoreCertificates();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}