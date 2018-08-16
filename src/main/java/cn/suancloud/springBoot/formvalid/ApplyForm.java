package cn.suancloud.springBoot.formvalid;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class ApplyForm implements Serializable {

  private String applicant;
  @NotNull(message = "项目名不能为空")
  private String project;
  @NotNull(message = "类型不能为空！")
  private String type;

  private Date applicationTime = new Date();
  @NotNull(message = "申请原因不能为空")
  private String reason;
  private String remark;
  private Boolean status = true;

  public String getApplicant() {
    return applicant;
  }

  public void setApplicant(String applicant) {
    this.applicant = applicant;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public Date getApplicationTime() {
    return applicationTime;
  }

  public void setApplicationTime(Date applicationTime) {
    this.applicationTime = applicationTime;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }
}
