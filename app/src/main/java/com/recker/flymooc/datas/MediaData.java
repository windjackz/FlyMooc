package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/31.
 */
public class MediaData {

    private int mediaId;

    private String name;

    private int type;

    private String shareUrl;

    private int chapterId;

    private int chapterSeq;

    private int mediaSeq;

    private int lastTime;

    private long lastDate;

    private int status;

    private int haveQues;

    private int duration;

    private long mediaSize;

    private long mediaDownSize;

    private String mediaUrl;

    private String mediaDownUrl;

    private int courseId;

    private int isFollow;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public long getLastDate() {
        return lastDate;
    }

    public void setLastDate(long lastDate) {
        this.lastDate = lastDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHaveQues() {
        return haveQues;
    }

    public void setHaveQues(int haveQues) {
        this.haveQues = haveQues;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(long mediaSize) {
        this.mediaSize = mediaSize;
    }

    public long getMediaDownSize() {
        return mediaDownSize;
    }

    public void setMediaDownSize(long mediaDownSize) {
        this.mediaDownSize = mediaDownSize;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaDownUrl() {
        return mediaDownUrl;
    }

    public void setMediaDownUrl(String mediaDownUrl) {
        this.mediaDownUrl = mediaDownUrl;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    @Override
    public String toString() {
        return "MediaData{" +
                "mediaId=" + mediaId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", shareUrl='" + shareUrl + '\'' +
                ", chapterId=" + chapterId +
                ", chapterSeq=" + chapterSeq +
                ", mediaSeq=" + mediaSeq +
                ", lastTime=" + lastTime +
                ", lastDate=" + lastDate +
                ", status=" + status +
                ", haveQues=" + haveQues +
                ", duration=" + duration +
                ", mediaSize=" + mediaSize +
                ", mediaDownSize=" + mediaDownSize +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", mediaDownUrl='" + mediaDownUrl + '\'' +
                ", courseId=" + courseId +
                ", isFollow=" + isFollow +
                '}';
    }
}
