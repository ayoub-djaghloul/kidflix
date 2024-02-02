package com.ayoub.ayoubtv.model;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class AllCategory {

    @SerializedName("categoryId")
    Integer categoryId;

    @SerializedName("categoryTitle")
    String categoryTitle;

    @SerializedName("categoryItemList")
    List<CategoryItemList> categoryItemList;


    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryItemList(List<CategoryItemList> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
    public List<CategoryItemList> getCategoryItemList() {
        return categoryItemList;
    }

}