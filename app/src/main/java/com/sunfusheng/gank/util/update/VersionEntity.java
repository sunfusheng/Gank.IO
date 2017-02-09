package com.sunfusheng.gank.util.update;

import java.io.Serializable;

/**
 * Created by sunfusheng on 15/8/19.
 */
public class VersionEntity implements Serializable {

    public String name;
    public String version;
    public String changelog;
    public String versionShort;
    public String build;
    public String installUrl;
    public String install_url;
    public String update_url;
    public Binary binary;

}
