package cn.suancloud.springBoot.util.openshift;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.openshift.client.OpenShiftConfig;
import io.fabric8.openshift.client.OpenShiftConfigBuilder;

public class GetClient {


  public static KubernetesClient getKClient() {
    Config config = new ConfigBuilder()
            .withMasterUrl("https://112.74.27.228:8443")
            .withUsername("admin")
            .withPassword("admin@123")
            .withTrustCerts(true)
            .build();
    return new DefaultKubernetesClient(config);
  }

  public static OpenShiftClient getOClient() {
    OpenShiftConfig shiftConfig = new OpenShiftConfigBuilder()
            .withOpenShiftUrl("https://112.74.27.228:8443")
            .withMasterUrl("https://112.74.27.228:8443")
            .withUsername("fandh")
            .withPassword("fan@123")
            .withTrustCerts(true).build();
    return new DefaultOpenShiftClient(shiftConfig);
  }

  public static OpenShiftClient getAdminOClient() {
    OpenShiftConfig shiftConfig = new OpenShiftConfigBuilder()
            .withMasterUrl("https://112.74.27.228:8443")
            .withUsername("admin")
            .withPassword("admin@123")
            .withTrustCerts(true).build();
    return new DefaultOpenShiftClient(shiftConfig);
  }

  public static void main(String[] args) {
    OpenShiftClient client = getOClient();
    System.out.println(client.namespaces().withName("aa").get());
    client.close();
  }

}
