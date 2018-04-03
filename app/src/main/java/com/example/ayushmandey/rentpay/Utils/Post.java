package com.example.ayushmandey.rentpay.Utils;


public class Post {

    String title;
    String productAge;
    String image;
    String price;
    String timeStamp;
    String desc;
    String locality;
    String pid;

    public Post(String title, String productAge, String image, String price, String timeStamp, String desc, String locality, String pid) {
        this.title = title;
        this.productAge = productAge;
        this.image = image;
        this.price = price;
        this.timeStamp = timeStamp;
        this.desc = desc;
        this.locality = locality;
        this.pid = pid;
    }

    public String getPid() {

        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Post() {

    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductAge() {
        return productAge;
    }

    public void setProductAge(String productAge) {
        this.productAge = productAge;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


}
