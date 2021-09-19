package model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TransactionByTxidObject {

    @SerializedName("inputs")
    @Expose
    public List<TransactionByTxidObjectInput> inputs = null;
    @SerializedName("outputs")
    @Expose
    public List<TransactionByTxidObjectOutput> outputs = null;

}
