package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.ProcessStage;

import java.util.Arrays;
import java.util.List;

public class ProcessStageEntityProvider {

  public static Department getDepartment() {

    Department department = new Department();
    department.setId(1);
    department.setName("testDepartment");

    return department;
  }

  public static Process getProces() {
    Process process = new Process();
    process.setId(1);
    process.setName("testProcess");

    return process;
  }

  public static ProcessStage getProcessStage(String stageName) {

    ProcessStage prePersist = new ProcessStage();
    prePersist.setName("stageName");
    prePersist.setNextStage("nextStage");
    prePersist.setProcess("testProcess");
    prePersist.setDepartment("testDepartment");

    return prePersist;
  }

  public static ProcessStage getPersistedStage(String stage){

      ProcessStage persistedEntity = new ProcessStage();
      persistedEntity.setName(stage);
      persistedEntity.setDepartmentEntity(getDepartment());
      persistedEntity.setProcessEntity(getProces());
      persistedEntity.setNextStageEntity(persistedEntity);

      return persistedEntity;

  }

  public static List<ProcessStage> getStageList() {
    List<ProcessStage> stages =
        Arrays.asList(
            getProcessStage("first_stage"),
            getProcessStage("last_stage"),
            getProcessStage("middle_stage"));

    for(ProcessStage stage:stages){
        stage.setNextStageEntity(getProcessStage(stage.getNextStage()));
        stage.setDepartmentEntity(getDepartment());
        stage.setProcessEntity(getProces());

    }
    return stages;
  }
}
