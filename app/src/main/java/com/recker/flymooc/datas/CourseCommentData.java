package com.recker.flymooc.datas;

/**
 * Created by recker on 16/6/1.
 *
 * 课程评论
 *
 */
public class CourseCommentData {

    private String id;

    private String uid;

    private String nickname;

    private String img;

    private String description;

    private String createTime;

    private String supportNum;

    private int isSupport;

    private String mediaId;

    private String chapterSeq;

    private String mediaTitle;

    private String mediaSeq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(String supportNum) {
        this.supportNum = supportNum;
    }

    public int getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(int isSupport) {
        this.isSupport = isSupport;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getChapterSeq() {
        return chapterSeq;
    }

    public void setChapterSeq(String chapterSeq) {
        this.chapterSeq = chapterSeq;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaSeq() {
        return mediaSeq;
    }

    public void setMediaSeq(String mediaSeq) {
        this.mediaSeq = mediaSeq;
    }


    @Override
    public String toString() {
        return "CourseCommentData{" +
                "id=" + id +
                ", uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", supportNum='" + supportNum + '\'' +
                ", isSupport=" + isSupport +
                ", mediaId='" + mediaId + '\'' +
                ", chapterSeq='" + chapterSeq + '\'' +
                ", mediaTitle='" + mediaTitle + '\'' +
                ", mediaSeq='" + mediaSeq + '\'' +
                '}';
    }
}
