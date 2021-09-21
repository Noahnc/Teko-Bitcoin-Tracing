package apis;

import com.google.gson.Gson;
import model.TransactionByBitcoinObject;
import model.TransactionByTxidObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class BitcoinAPIHandler {

    private final String baseURL = "https://api.bitcore.io";

    public TransactionByBitcoinObject[] getTransactionsForAddress(String btcAddress) throws IOException {

        var apiEndpoint = "/api/BTC/mainnet/address/";
        var url = baseURL + apiEndpoint + btcAddress + "?limit=1000";

        var apiRequest = new URL(url).openConnection();
        apiRequest.connect();

        var JsonData = new InputStreamReader((InputStream) apiRequest.getContent());
        return new Gson().fromJson(JsonData, TransactionByBitcoinObject[].class);
    }

    public TransactionByTxidObject getTransactionDetails(String bitcoinTxid) throws IOException {

        var APIEndpoint = "/api/BTC/mainnet/tx/";
        var url = baseURL + APIEndpoint + bitcoinTxid + "/coins";

        var apiRequest = new URL(url).openConnection();
        apiRequest.connect();

        var JsonData = new InputStreamReader((InputStream) apiRequest.getContent());
        return new Gson().fromJson(JsonData, TransactionByTxidObject.class);
    }

    // Methode zum überprüfen, ob eine Bitcoin Adresse gültig ist
    public boolean CheckIfAddressIsValid(String BitcoinAddress) throws IOException {

        var APIEndpoint = "/api/BTC/mainnet/address/";
        var url = baseURL + APIEndpoint + BitcoinAddress;

        var apiRequest = new URL(url).openConnection();
        apiRequest.connect();

        var JsonData = new InputStreamReader((InputStream) apiRequest.getContent());
        var Transactions = new Gson().fromJson(JsonData, model.TransactionByBitcoinObject[].class);

        return Transactions.length != 0;
    }
}





