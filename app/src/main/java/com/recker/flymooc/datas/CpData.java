package com.recker.flymooc.datas;

import java.util.List;

/**
 * Created by recker on 16/5/31.
 */
public class CpData {


    private int id;

    private String chapterName;

    private int cid;

    private int seq;

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

    private boolean isTitle = false;//是否是标题

    private boolean isSeleted = false;//是否被选中

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CpData{" +
                "id=" + id +
                ", chapterName='" + chapterName + '\'' +
                ", cid=" + cid +
                ", seq=" + seq +
                ", mediaId=" + mediaId +
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
                ", isTitle=" + isTitle +
                '}';
    }

    //    private Chapter chapter;
//
//    private List<MediaData> mediaDataList;
//
//    public Chapter getChapter() {
//        return chapter;
//    }
//
//    public void setChapter(Chapter chapter) {
//        this.chapter = chapter;
//    }
//
//    public List<MediaData> getMediaDataList() {
//        return mediaDataList;
//    }
//
//    public void setMediaDataList(List<MediaData> mediaDataList) {
//        this.mediaDataList = mediaDataList;
//    }
}
