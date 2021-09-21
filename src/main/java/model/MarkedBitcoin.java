package model;

import java.util.ArrayList;

public class MarkedBitcoin
{
    private String address;
    private ArrayList<MarkedBitcoin> children;

    public MarkedBitcoin(String address) {
        this.address = address;
        this.children = new ArrayList<>();
    }

    public ArrayList<MarkedBitcoin> getChildren() {
        return children;
    }

    public String getAddress() {
        return address;
    }
}