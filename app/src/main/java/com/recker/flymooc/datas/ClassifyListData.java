package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/26.
 *
 * 分类列表数据
 *
 */
public class ClassifyListData {

    private int id;

    private String name;

    private String pic;

    private String desc;

    private int isLearned;

    private int companyId;

    private int numbers;

    private long updateTime;

    private int coursetype;

    private long duration;

    private int finished;

    private int isFollow;

    private int maxChapterSeq;

    private int maxMediaSeq;

    private long lastTime;

    private int chapterSeq;

    private int mediaSeq;


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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIsLearned() {
        return isLearned;
    }

    public void setIsLearned(int isLearned) {
        this.isLearned = isLearned;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(int coursetype) {
        this.coursetype = coursetype;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getMaxChapterSeq() {
        return maxChapterSeq;
    }

    public void setMaxChapterSeq(int maxChapterSeq) {
        this.maxChapterSeq = maxChapterSeq;
    }

    public int getMaxMediaSeq() {
        return maxMediaSeq;
    }

    public void setMaxMediaSeq(int maxMediaSeq) {
        this.maxMediaSeq = maxMediaSeq;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public int getChapterSeq() {
        return chapterSeq;
    }

    public void setChapterSeq(int chapterSeq) {
        this.chapterSeq = chapterSeq;
    }

    public int getMediaSeq() {
        return mediaSeq;
    }

    public void setMediaSeq(int mediaSeq) {
        this.mediaSeq = mediaSeq;
    }

    @Override
    public String toString() {
        return "ClassifyListData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", desc='" + desc + '\'' +
                ", isLearned=" + isLearned +
                ", companyId=" + companyId +
                ", numbers=" + numbers +
                ", updateTime=" + updateTime +
                ", coursetype=" + coursetype +
                ", duration=" + duration +
                ", finished=" + finished +
                ", isFollow=" + isFollow +
                ", maxChapterSeq=" + maxChapterSeq +
                ", maxMediaSeq=" + maxMediaSeq +
                ", lastTime=" + lastTime +
                ", chapterSeq=" + chapterSeq +
                ", mediaSeq=" + mediaSeq +
                '}';
    }
}
