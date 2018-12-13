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
public class PieSimpleVo {

    private Legend legend;
    private List<Series> series;
    private Title title;
    private Tooltip tooltip;
    public void setLegend(Legend legend) {
         this.legend = legend;
     }
     public Legend getLegend() {
         return legend;
     }

    public void setSeries(List<Series> series) {
         this.series = series;
     }
     public List<Series> getSeries() {
         return series;
     }

    public void setTitle(Title title) {
         this.title = title;
     }
     public Title getTitle() {
         return title;
     }

    public void setTooltip(Tooltip tooltip) {
         this.tooltip = tooltip;
     }
     public Tooltip getTooltip() {
         return tooltip;
     }

}