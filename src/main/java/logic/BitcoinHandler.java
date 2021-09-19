package logic;

import apis.BitcoinAPIHandler;
import model.TransactionByBitcoinObject;

import java.io.IOException;
import java.util.ArrayList;

public class BitcoinHandler {

    BitcoinAPIHandler APIHandler = new BitcoinAPIHandler();
    ArrayList<String> BitcoinAddresses;

    public boolean CheckIfBitcoinAddressIsValid(String BitcoinAddress) throws IOException {
        if (APIHandler.CheckIfAddressIsValid(BitcoinAddress)) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList getBitcoinAddresses(String BitcoinAddress) {
        BitcoinAddresses = new ArrayList<String>();


        return BitcoinAddresses;
    }


    public void OutputAllAddressesForTesting(String BitcoinAddress) throws IOException {
        TransactionByBitcoinObject[] Transactions = APIHandler.getBitcoinTransactionsByBitcoinID(BitcoinAddress);

        for (var transaction : Transactions) {

            System.out.println(transaction.spentTxid);

        }


    }
}




