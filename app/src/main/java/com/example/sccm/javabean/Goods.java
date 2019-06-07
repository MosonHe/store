package com.example.sccm.javabean;

import java.io.Serializable;



//使用Bundle.putSerializable()方法 
//数据通过意图进行交互，这是一个key_value方法
//首先Serilizable是一个可序列化对象接口 
//在运用这个putSerializable()时 ,可以先创建一个类,该类实现了Serilizable接口 如下: 
public class Goods implements Serializable {



    private int pid;
    private String pic;
    private String name;
    private float price;
    private int sale;

    public Goods(int pid,String pic, String name, float price,int sale) {
        this.pid = pid;
        this.pic = pic;
        this.name = name;
        this.price = price;
        this.sale=sale;
    }
    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
