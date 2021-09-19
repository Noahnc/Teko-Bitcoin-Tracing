package logic;

import apis.BitcoinAPIHandler;

import java.util.ArrayList;

public class BitcoinHandler {

    BitcoinAPIHandler APIHandler = new BitcoinAPIHandler();
    ArrayList<String> BitcoinAddresses;

    public boolean CheckIfBitcoinAddressIsValid(String BitcoinAddress) {
        boolean isValid = true;


        return isValid;
    }

    public ArrayList getBitcoinAddresses(String BitcoinAddress) {
        BitcoinAddresses = new ArrayList<String>();


        return BitcoinAddresses;
    }

}


