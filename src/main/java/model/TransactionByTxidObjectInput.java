package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionByTxidObjectInput {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("chain")
    @Expose
    public String chain;
    @SerializedName("network")
    @Expose
    public String network;
    @SerializedName("coinbase")
    @Expose
    public Boolean coinbase;
    @SerializedName("mintIndex")
    @Expose
    public Integer mintIndex;
    @SerializedName("spentTxid")
    @Expose
    public String spentTxid;
    @SerializedName("mintTxid")
    @Expose
    public String mintTxid;
    @SerializedName("mintHeight")
    @Expose
    public Integer mintHeight;
    @SerializedName("spentHeight")
    @Expose
    public Integer spentHeight;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("script")
    @Expose
    public String script;
    @SerializedName("value")
    @Expose
    public Integer value;
    @SerializedName("confirmations")
    @Expose
    public Integer confirmations;

}
