package logic;

import apis.BitcoinAPIHandler;
import model.MarkedBitcoin;
import model.TransactionByBitcoinObject;
import model.TransactionByTxidObjectInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MarkedBitcoinService {

    private final BitcoinAPIHandler bitcoinAPIHandler;
    private final int maxRetries = 3;

    public MarkedBitcoinService() {
        bitcoinAPIHandler = new BitcoinAPIHandler();
    }

    public int getRequestCount() {
        return bitcoinAPIHandler.getRequestCalls();
    }

    // Startet die Berechnung der markierten Bitcoins
    public void calculateMarkedBitcoinsRecursive(String address, MarkedBitcoin markedBitcoin, int maxLevel, int currentLevel, String[] txId) throws IOException {
        if (currentLevel == maxLevel)
            return;

        currentLevel++;

        var markedTransactions = Arrays.stream(bitcoinAPIHandler.getTransactionsForAddress(address, maxRetries, 0))
                .filter(transactionByBitcoinObject -> transactionByBitcoinObject.spentTxid != null && !transactionByBitcoinObject.spentTxid.isEmpty());

        if (txId != null && txId.length > 0)
            markedTransactions = markedTransactions.filter(m -> Arrays.stream(txId).anyMatch(t -> t.equals(m.received)));

        var dict = new HashMap<String, ArrayList<String>>();
        for (var markedTransaction : markedTransactions.toArray(TransactionByBitcoinObject[]::new)) {
            var addressesToContinue = getNextAddressesToTrackWithLiFo(markedTransaction, address);
            for (var next : addressesToContinue) {
                if (!dict.containsKey(next.getAddress()))
                    dict.put(next.getAddress(), new ArrayList<>());

                dict.get(next.getAddress()).add(markedTransaction.spentTxid);
            }
        }

        // Recursive call: Berechnet den naechsten layer
        for (Map.Entry<String, ArrayList<String>> entry : dict.entrySet()) {
            var mb = new MarkedBitcoin(entry.getKey());
            markedBitcoin.getChildren().add(mb);
            calculateMarkedBitcoinsRecursive(entry.getKey(), mb, maxLevel, currentLevel, entry.getValue().toArray(String[]::new));
        }
    }

    // Checkt ob die Bitcoin Adresse gueltig ist und transaktionen beinhaltet
    public boolean CheckIfBitcoinAddressIsValid(String BitcoinAddress) throws IOException {
        if (bitcoinAPIHandler.CheckIfAddressIsValid(BitcoinAddress, maxRetries, 0)) {
            return true;
        } else {
            return false;
        }
    }

    // Gibt die naechsten Adressen zurueck,die ueberpruft werden muessen (LIFO Prinzip)
    private MarkedBitcoin[] getNextAddressesToTrackWithLiFo(TransactionByBitcoinObject btcAddressTransaction, String markedBtcAddress) throws IOException {
        var toTrackAddresses = new ArrayList<MarkedBitcoin>();
        var transactionDetails = bitcoinAPIHandler.getTransactionDetails(btcAddressTransaction.spentTxid, maxRetries, 0);

        var input = getNextInput(transactionDetails.inputs);
        if (input == null)
            return new MarkedBitcoin[0];

        // Iteriert durch alle Inputs / Outputs und berechnet daraus die naechsten Adressen
        for (var i = 0; i < transactionDetails.outputs.length; ) {
            var output = transactionDetails.outputs[i];
            var initialInput = input.value;
            var initialOutput = output.value;

            input.value -= initialOutput;
            output.value -= initialInput;

            if (input.address.equals(markedBtcAddress)) {
                toTrackAddresses.add(new MarkedBitcoin(output.address));
            }

            input = getNextInput(transactionDetails.inputs);
            if (input == null)
                break;

            if (output.value <= 0)
                i++;
        }

        return toTrackAddresses.stream().filter(a -> !a.getAddress().equals(markedBtcAddress)).toArray(MarkedBitcoin[]::new);
    }

    private TransactionByTxidObjectInput getNextInput(TransactionByTxidObjectInput[] inputs) {
        for (var i = inputs.length - 1; i >= 0; i--) {
            if (inputs[i].value >= 0)
                return inputs[i];
        }

        return null;
    }
}
