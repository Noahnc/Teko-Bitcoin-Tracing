package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionByBitcoinObject {
    @SerializedName("spentTxid")
    @Expose
    public String spentTxid;

    @SerializedName("mintTxid")
    @Expose
    public String received;
}
