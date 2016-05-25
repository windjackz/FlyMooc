package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/25.
 */
public class ClassifyData {

    private int id;

    private String name;

    private String pic;

    private int numbers;

    private boolean isTitle = false;


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

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    @Override
    public String toString() {
        return "ClassifyData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", numbers=" + numbers +
                ", isTitle=" + isTitle +
                '}';
    }
}
