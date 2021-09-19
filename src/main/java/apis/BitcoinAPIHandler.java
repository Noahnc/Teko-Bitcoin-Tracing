
package apis;

import com.google.gson.Gson;
import model.TransactionsByBitcoin;
import model.TransactionsByTxid;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class BitcoinAPIHandler {

    private String BaseURL = "https://api.bitcore.io";
    private URL RequestURL;

    // Sucht nach Transaktionen einer Bitcoin Adresse
    public TransactionsByBitcoin getBitcoinTransactionsByBitcoinID(String BitcoinAddress) throws IOException {

        String APIEndpoint = "/api/BTC/mainnet/address/";
        String url = BaseURL + APIEndpoint + BitcoinAddress;
        RequestURL = new URL(url);
        URLConnection APIrequest = RequestURL.openConnection();
        APIrequest.connect();
        InputStreamReader JsonData = new InputStreamReader((InputStream) APIrequest.getContent());

        Gson gson = new Gson();
        TransactionsByBitcoin Transactions = gson.fromJson(JsonData, model.TransactionsByBitcoin.class);

        return Transactions;

    }

    // Sucht nach Transaktionen anhand TXid
    public TransactionsByTxid getBitcoinTransactionsByTxID(String BitcoinTxid) throws IOException {

        String APIEndpoint = "/api/BTC/mainnet/tx/";
        String url = BaseURL + APIEndpoint + BitcoinTxid + "/coins";
        RequestURL = new URL(url);
        URLConnection APIrequest = RequestURL.openConnection();
        APIrequest.connect();
        InputStreamReader JsonData = new InputStreamReader((InputStream) APIrequest.getContent());

        Gson gson = new Gson();
        TransactionsByTxid Transactions = gson.fromJson(JsonData, model.TransactionsByTxid.class);

        return Transactions;

    }

    // Methode zum überprüfen, ob eine Bitcoin Adresse gültig ist
    public boolean CheckIfAddressIsValid(String BitcoinAddress) throws IOException {

        String APIEndpoint = "/api/BTC/mainnet/address/";
        String url = BaseURL + APIEndpoint + BitcoinAddress;
        RequestURL = new URL(url);
        URLConnection APIrequest = RequestURL.openConnection();
        APIrequest.connect();
        InputStreamReader JsonData = new InputStreamReader((InputStream) APIrequest.getContent());

        if (JsonData.equals("[]")) {
            return false;
        } else {
            return true;
        }
    }


}





