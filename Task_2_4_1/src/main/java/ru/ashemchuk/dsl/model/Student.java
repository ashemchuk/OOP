package ru.ashemchuk.dsl.model;

public class Student {
    private String name;
    private String nicknameGH;
    private String repoURL;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicknameGH() {
        return nicknameGH;
    }

    public void setNicknameGH(String nicknameGH) {
        this.nicknameGH = nicknameGH;
    }

    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }
}
