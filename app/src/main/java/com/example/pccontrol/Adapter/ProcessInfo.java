package com.example.pccontrol.Adapter;

public class ProcessInfo {

    private String name;
    private int pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public ProcessInfo(String name, int pid) {
        this.name = name;
        this.pid = pid;
    }
}
