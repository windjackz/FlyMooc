package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/26.
 */
public class ArticleListData {

    private int id;

    private String title;

    private int view;

    private int typeId;

    private String type;

    private int push;

    private int comment;

    private String desc;

    private String img;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ArticleListData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", view=" + view +
                ", typeId=" + typeId +
                ", type='" + type + '\'' +
                ", push=" + push +
                ", comment=" + comment +
                ", desc='" + desc + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
