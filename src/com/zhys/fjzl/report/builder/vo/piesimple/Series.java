/**
  * Copyright 2017 bejson.com 
  */
package com.zhys.fjzl.report.builder.vo.piesimple;
import java.util.List;

/**
 * Auto-generated: 2017-07-05 18:23:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Series {

    private List<String> center;
    private List<Data> data;
    private ItemStyle itemStyle;
    private String name;
    private String radius;
    private String type;
    public void setCenter(List<String> center) {
         this.center = center;
     }
     public List<String> getCenter() {
         return center;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public void setItemStyle(ItemStyle itemStyle) {
         this.itemStyle = itemStyle;
     }
     public ItemStyle getItemStyle() {
         return itemStyle;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setRadius(String radius) {
         this.radius = radius;
     }
     public String getRadius() {
         return radius;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

}