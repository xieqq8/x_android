package de.greenrobot.testdao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ADDRESSE".
 */
public class Addresse {

    private Long id;
    private Integer count;
    private int dummy;

    public Addresse() {
    }

    public Addresse(Long id) {
        this.id = id;
    }

    public Addresse(Long id, Integer count, int dummy) {
        this.id = id;
        this.count = count;
        this.dummy = dummy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getDummy() {
        return dummy;
    }

    public void setDummy(int dummy) {
        this.dummy = dummy;
    }

}
