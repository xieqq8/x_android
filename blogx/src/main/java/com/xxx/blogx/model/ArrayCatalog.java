package com.xxx.blogx.model;

import java.util.List;

/**
 * Created by kuaX on 2017/8/25.
 */

public class ArrayCatalog {

    /**
     * id : java
     * label : Java
     * path : 0
     * order : 10
     * type : 0
     * style : null
     * disabled : false
     * childNodes : []
     * url : /catalog/Java
     * level : 1
     */

    private String id;
    private String label;
    private String path;
    private int order;
    private int type;
    private Object style;
    private boolean disabled;
    private String url;
    private int level;
    private List<?> childNodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getStyle() {
        return style;
    }

    public void setStyle(Object style) {
        this.style = style;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<?> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<?> childNodes) {
        this.childNodes = childNodes;
    }
}
