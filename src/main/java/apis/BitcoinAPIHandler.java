
package apis;

import com.google.gson.Gson;
import model.TransactionsByBitcoin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class BitcoinAPIHandler {

    private String BaseURL = "https://api.bitcore.io";
    private URL RequestURL;


    public TransactionsByBitcoin[] getBitcoinTransactions(String BitcoinAddress) throws IOException {

        String APIEndpoint = "/api/BTC/mainnet/address/";
        String url = BaseURL + APIEndpoint + BitcoinAddress;
        RequestURL = new URL(url);
        URLConnection APIrequest = RequestURL.openConnection();
        APIrequest.connect();
        InputStreamReader JsonData = new InputStreamReader((InputStream) APIrequest.getContent());


        Gson gson = new Gson();
        TransactionsByBitcoin[] Transactions = gson.fromJson(JsonData, model.TransactionsByBitcoin[].class);

        return Transactions;

    }


}





