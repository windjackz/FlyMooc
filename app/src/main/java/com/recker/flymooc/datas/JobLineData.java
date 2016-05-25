package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/25.
 */
public class JobLineData {

    private int id;

    private String name;

    private int studyPersons;//学习人数

    private String description;//描述

    private int courses;//课程数量

    private int status;

    private String pathPicFmt;

    private String share;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudyPersons() {
        return studyPersons;
    }

    public void setStudyPersons(int studyPersons) {
        this.studyPersons = studyPersons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourses() {
        return courses;
    }

    public void setCourses(int courses) {
        this.courses = courses;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPathPicFmt() {
        return pathPicFmt;
    }

    public void setPathPicFmt(String pathPicFmt) {
        this.pathPicFmt = pathPicFmt;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }


    @Override
    public String toString() {
        return "JobLineData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studyPersons=" + studyPersons +
                ", description='" + description + '\'' +
                ", courses=" + courses +
                ", status=" + status +
                ", pathPicFmt='" + pathPicFmt + '\'' +
                ", share='" + share + '\'' +
                '}';
    }
}
