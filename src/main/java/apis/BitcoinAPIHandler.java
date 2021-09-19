
package apis;

import com.google.gson.Gson;
import model.TransactionByBitcoinObject;
import model.TransactionByTxidObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class BitcoinAPIHandler {

    private String BaseURL = "https://api.bitcore.io";
    private URL RequestURL;

    // Sucht nach Transaktionen einer Bitcoin Adresse
    public TransactionByBitcoinObject[] getBitcoinTransactionsByBitcoinID(String BitcoinAddress) throws IOException {

        String APIEndpoint = "/api/BTC/mainnet/address/";
        String url = BaseURL + APIEndpoint + BitcoinAddress;
        RequestURL = new URL(url);
        URLConnection APIrequest = RequestURL.openConnection();
        APIrequest.connect();
        InputStreamReader JsonData = new InputStreamReader((InputStream) APIrequest.getContent());

        Gson gson = new Gson();
        TransactionByBitcoinObject[] Transactions = gson.fromJson(JsonData, model.TransactionByBitcoinObject[].class);

        return Transactions;

    }

    // Sucht nach Transaktionen anhand TXid
    public TransactionByTxidObject[] getBitcoinTransactionsByTxID(String BitcoinTxid) throws IOException {

        String APIEndpoint = "/api/BTC/mainnet/tx/";
        String url = BaseURL + APIEndpoint + BitcoinTxid + "/coins";
        RequestURL = new URL(url);
        URLConnection APIrequest = RequestURL.openConnection();
        APIrequest.connect();
        InputStreamReader JsonData = new InputStreamReader((InputStream) APIrequest.getContent());

        Gson gson = new Gson();
        TransactionByTxidObject[] Transactions = gson.fromJson(JsonData, model.TransactionByTxidObject[].class);

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

        Gson gson = new Gson();
        TransactionByBitcoinObject[] Transactions = gson.fromJson(JsonData, model.TransactionByBitcoinObject[].class);

        if (Transactions.length == 0){
            return false;
        } else {
            return true;
        }

    }


}





