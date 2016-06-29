package com.thed.zephyr.cloud.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Kavya on 27-06-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class ZQLFilter {

    public String id;
    public String name;
    public String tenantId;
    public String createdBy;
    public String updatedBy;
    public Boolean favorite;
    public Date createdOn;
    public Date updatedOn;
    public String zql;
    public String description;
    public String sharePerm;
    public Set<String> markedFavoriteBy;
    public Integer totalCount;
    public Integer currentIndex;
    public Integer maxResultAllowed;
    public List<Integer> linksNew;
    public Integer popularity;
    public Long executionCount;

    public ZQLFilter() {

    }

    public ZQLFilter(String id, String tId, String name, String createdBy, Date createdOn, String zql, String sharePerm) {
        this.id = id;
        this.tenantId = tId;
        this.name = name;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.zql = zql;
        this.sharePerm = sharePerm;
    }

    @Override
    public String toString() {
        return "ZQLFilter : {" +
                "id:'" + id + '\'' +
                ", name:'" + name + '\'' +
                ", tenantId:'" + tenantId + '\'' +
                ", createdBy:'" + createdBy + '\'' +
                ", updatedBy:'" + updatedBy + '\'' +
                ", favorite:" + favorite +
                ", createdOn:" + createdOn +
                ", updatedOn:" + updatedOn +
                ", zql:'" + zql + '\'' +
                ", description:'" + description + '\'' +
                ", sharePerm:'" + sharePerm + '\'' +
                ", markedFavoriteBy:" + markedFavoriteBy +
                '}';
    }
}
