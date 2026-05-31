package com.example.pccontrol.Utils;

public class ActionItem {


    private String title;
    private String sshCommand;
    private String ActionCommand;

    private String filepath;

    private boolean runAsAdmin;
    private boolean startExecutableFile;
    private boolean useSSHCommand;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSshCommand() {
        return sshCommand;
    }

    public void setSshCommand(String sshCommand) {
        this.sshCommand = sshCommand;
    }

    public String getActionCommand() {
        return ActionCommand;
    }

    public void setActionCommand(String actionCommand) {
        ActionCommand = actionCommand;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public boolean isRunAsAdmin() {
        return runAsAdmin;
    }

    public void setRunAsAdmin(boolean runAsAdmin) {
        this.runAsAdmin = runAsAdmin;
    }

    public boolean isStartExecutableFile() {
        return startExecutableFile;
    }

    public void setStartExecutableFile(boolean startExecutableFile) {
        this.startExecutableFile = startExecutableFile;
    }

    public boolean isUseSSHCommand() {
        return useSSHCommand;
    }

    public void setUseSSHCommand(boolean useSSHCommand) {
        this.useSSHCommand = useSSHCommand;
    }

    public ActionItem(String title, String sshCommand, String actionCommand, String filepath, boolean runAsAdmin, boolean startExecutableFile, boolean useSSHCommand) {
        this.title = title;
        this.sshCommand = sshCommand;
        ActionCommand = actionCommand;
        this.filepath = filepath;
        this.runAsAdmin = runAsAdmin;
        this.startExecutableFile = startExecutableFile;
        this.useSSHCommand = useSSHCommand;
    }
}
