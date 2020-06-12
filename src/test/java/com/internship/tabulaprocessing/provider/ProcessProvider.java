package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Process;

import java.util.Collections;
import java.util.List;

public class ProcessProvider {
    public static Process getProcessInstance() {
        Process process = new Process();

        process.setId(1);
        process.setName("shipping");

        return process;
    }

    public static List<Process> getProcessesInstance() {
        return Collections.singletonList(getProcessInstance());
    }
}
