package com.swvl.swvlchallenge.data.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class FlickrResponse extends BaseObservable implements Serializable {

    @SerializedName("photos")
    @Expose
    private FlickrData data;
    @SerializedName("stat")
    @Expose
    private String stat;

    public FlickrData getPhotos() {
        return data;
    }

    public void setPhotos(FlickrData flickrData) {
        this.data = flickrData;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }


    public class FlickrData {
        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("pages")
        @Expose
        private Integer pages;
        @SerializedName("perpage")
        @Expose
        private Integer perpage;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("photo")
        @Expose
        private List<Photo> photo = null;

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public Integer getPerpage() {
            return perpage;
        }

        public void setPerpage(Integer perpage) {
            this.perpage = perpage;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(List<Photo> photo) {
            this.photo = photo;
        }
    }


    public class Photo {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("secret")
        @Expose
        private String secret;
        @SerializedName("server")
        @Expose
        private String server;
        @SerializedName("farm")
        @Expose
        private Integer farm;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("ispublic")
        @Expose
        private Integer ispublic;
        @SerializedName("isfriend")
        @Expose
        private Integer isfriend;
        @SerializedName("isfamily")
        @Expose
        private int isfamily;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public Integer getFarm() {
            return farm;
        }

        public void setFarm(Integer farm) {
            this.farm = farm;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getIspublic() {
            return ispublic;
        }

        public void isPublic(Integer ispublic) {
            this.ispublic = ispublic;
        }

        public Integer getIsfriend() {
            return isfriend;
        }

        public void setIsfriend(Integer isfriend) {
            this.isfriend = isfriend;
        }

        public Integer isfamily() {
            return isfamily;
        }

        public void isfamily(Integer isfamily) {
            this.isfamily = isfamily;
        }

    }
}