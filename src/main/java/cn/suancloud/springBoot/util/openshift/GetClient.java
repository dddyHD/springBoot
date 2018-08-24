package cn.suancloud.springBoot.util.openshift;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.openshift.client.OpenShiftConfig;
import io.fabric8.openshift.client.OpenShiftConfigBuilder;

import static cn.suancloud.springBoot.util.Constant.*;

public class GetClient {


  public static KubernetesClient getKClient() {
    Config config = new ConfigBuilder()
            .withMasterUrl(OPENSHIFT_URL)
            .withUsername("admin")
            .withPassword("admin@123")
            .withTrustCerts(true)
            .build();
    return new DefaultKubernetesClient(config);
  }

  public static OpenShiftClient getOClient(String token) {
    OpenShiftConfig shiftConfig = new OpenShiftConfigBuilder()
            .withMasterUrl(OPENSHIFT_URL)
            .withOpenShiftUrl(OPENSHIFT_URL)
            .withOauthToken(token)
            .withTrustCerts(true).build();
    return new DefaultOpenShiftClient(shiftConfig);
  }

  public static OpenShiftClient getAdminOClient() {
    OpenShiftConfig shiftConfig = new OpenShiftConfigBuilder()
            .withMasterUrl(OPENSHIFT_URL)
            .withUsername(OPENSHIFT_ADMIN_USERNAME)
            .withPassword(OPENSHIFT_ADMIN_PASSWORD)
            .withTrustCerts(true).build();
    return new DefaultOpenShiftClient(shiftConfig);
  }

  public static void main(String[] args) {
    OpenShiftClient client = getOClient("t9iN-dMutcdxkG8iRk7CzOhh7G-kusFDqzKKcylRVfk");
    client.namespaces().withName("unittest1").get();
//    System.out.println(client.namespaces().withName("aa").get());
//    client.close();
  }

}
