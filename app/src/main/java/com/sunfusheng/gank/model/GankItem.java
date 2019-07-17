package com.sunfusheng.gank.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author by sunfusheng on 2017/1/17.
 */
public class GankItem implements Serializable {

    public String _id;
    public String type;
    public String desc;
    public String who;
    public String url;
    public ArrayList<String> images;
    public String createdAt;
    public String publishedAt;


}
