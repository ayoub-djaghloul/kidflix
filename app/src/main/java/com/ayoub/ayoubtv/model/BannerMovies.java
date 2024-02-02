package com.ayoub.ayoubtv.model;

public class BannerMovies {
     String bannerCategoryId;
     Integer id;
     String movieName;
     String imageUrl;
     String fileUrl;

     public String getBannerCategoryId() {
          return bannerCategoryId;
     }

     public void setBannerCategoryId(String bannerCategoryId) {
          this.bannerCategoryId = bannerCategoryId;
     }

     public BannerMovies(Integer id, String movieName, String imageUrl, String fileUrl) {
          this.id = id;
          this.movieName = movieName;
          this.imageUrl = imageUrl;
          this.fileUrl = fileUrl;
     }

     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public String getMovieName() {
          return movieName;
     }

     public void setMovieName(String movieName) {
          this.movieName = movieName;
     }

     public String getImageUrl() {
          return imageUrl;
     }

     public void setImageUrl(String imageUrl) {
          this.imageUrl = imageUrl;
     }

     public String getFileUrl() {
          return fileUrl;
     }

     public void setFileUrl(String fileUrl) {
          this.fileUrl = fileUrl;
     }
}
