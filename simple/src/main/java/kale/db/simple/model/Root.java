package kale.db.simple.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Kale
 * @date 2016/7/5
 */

public class Root {

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public List<Result> results;
    
}
