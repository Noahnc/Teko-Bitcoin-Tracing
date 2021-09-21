package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionByTxidObjectInput {
    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("value")
    @Expose
    public Long value;
}
