package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/24.
 *
 * 广告栏数据
 *
 */
public class BannerData {

    private int id;

    private int type;

    private int typeId;

    private String name;

    private String pic;

    private String links;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "BannerData{" +
                "id=" + id +
                ", type=" + type +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", links='" + links + '\'' +
                '}';
    }
}
