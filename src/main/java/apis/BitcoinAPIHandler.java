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
    private int requestCalls = 0;

    // API Endpoint: "/api/BTC/mainnet/address/"
    public TransactionByBitcoinObject[] getTransactionsForAddress(String btcAddress, int maxRetries, int currentRetry) throws IOException {

        try {
            var apiEndpoint = "/api/BTC/mainnet/address/";
            var url = baseURL + apiEndpoint + btcAddress + "?limit=100";

            var apiRequest = new URL(url).openConnection();
            apiRequest.connect();
            requestCalls++;

            var JsonData = new InputStreamReader((InputStream) apiRequest.getContent());
            return new Gson().fromJson(JsonData, TransactionByBitcoinObject[].class);
        } catch (Exception ex) {
            currentRetry++;
            if(currentRetry > maxRetries)
                throw new IOException("No more retries");

            return getTransactionsForAddress(btcAddress, maxRetries, currentRetry);
        }
    }

    // API Endpoint: "/api/BTC/mainnet/tx/"
    public TransactionByTxidObject getTransactionDetails(String bitcoinTxid, int maxRetries, int currentRetry) throws IOException {

        try {
            var APIEndpoint = "/api/BTC/mainnet/tx/";
            var url = baseURL + APIEndpoint + bitcoinTxid + "/coins";

            var apiRequest = new URL(url).openConnection();
            apiRequest.connect();
            requestCalls++;

            var JsonData = new InputStreamReader((InputStream) apiRequest.getContent());
            return new Gson().fromJson(JsonData, TransactionByTxidObject.class);
        } catch (Exception ex) {
            currentRetry++;
            if(currentRetry > maxRetries)
                throw new IOException("No more retries");

            return getTransactionDetails(bitcoinTxid, maxRetries, currentRetry);
        }
    }

    // Methode zum prüfen, ob eine Bitcoin Adresse gültig ist
    // API Endpoint: "/api/BTC/mainnet/address/"
    public boolean CheckIfAddressIsValid(String BitcoinAddress, int maxRetries, int currentRetry) throws IOException {

        try {
            var APIEndpoint = "/api/BTC/mainnet/address/";
            var url = baseURL + APIEndpoint + BitcoinAddress;

            var apiRequest = new URL(url).openConnection();
            apiRequest.connect();
            requestCalls++;

            var JsonData = new InputStreamReader((InputStream) apiRequest.getContent());
            var Transactions = new Gson().fromJson(JsonData, model.TransactionByBitcoinObject[].class);

            return Transactions.length != 0;
        } catch (Exception ex) {
            currentRetry++;
            if(currentRetry > maxRetries)
                throw new IOException("No more retries");

            return CheckIfAddressIsValid(BitcoinAddress, maxRetries, currentRetry);
        }
    }

    public int getRequestCalls() {
        return requestCalls;
    }
}





