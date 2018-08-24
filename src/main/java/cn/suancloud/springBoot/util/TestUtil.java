package cn.suancloud.springBoot.util;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;

import cn.suancloud.springBoot.util.openshift.GetClient;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.openshift.client.OpenShiftClient;

public class TestUtil {
  /**
   * 平台用户和项目初始化
   */

  protected static void initOpenShfitData() {
    OpenShiftClient client = GetClient.getAdminOClient();
    //判断user用户是否存在不存在则新建用户
    if (client.users().withName("user1").get() == null) ;
    io.fabric8.openshift.api.model.User user = new io.fabric8.openshift.api.model.User();

    ObjectMeta meta = new ObjectMeta();
    user.setApiVersion("v1");
    user.setKind("User");
    user.setIdentities(Arrays.asList("custom:user1"));
    user.setGroups(Arrays.asList("user"));
    meta.setName("user1");
    meta.setAnnotations(new HashMap<>());
    user.setMetadata(meta);
    user.setAdditionalProperty("password", "user1@123");
    user.setAdditionalProperty("qqq", "user1@123");
    io.fabric8.openshift.api.model.User res = client.users().create(user);
    System.out.println(res.toString());
  }

  public static void main(String[] args) throws MalformedURLException {
    initOpenShfitData();
  }
}
