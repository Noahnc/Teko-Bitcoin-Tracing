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

    public ArrayList getBitcoinAddresses(String BitcoinAddress, int LayerDepht) {
        BitcoinAddresses = new ArrayList<String>();


        return BitcoinAddresses;
    }
}




