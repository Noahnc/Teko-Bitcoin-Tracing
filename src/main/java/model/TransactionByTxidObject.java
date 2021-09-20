package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionByTxidObject {

    @SerializedName("inputs")
    @Expose
    public TransactionByTxidObjectInput[] inputs = null;

    @SerializedName("outputs")
    @Expose
    public TransactionByTxidObjectOutput[] outputs = null;
}
