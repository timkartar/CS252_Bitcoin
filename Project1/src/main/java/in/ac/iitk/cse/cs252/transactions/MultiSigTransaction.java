package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;
import java.math.BigInteger;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class MultiSigTransaction extends ScriptTransaction {
    // TODO: Problem 3
	ECKey party1_Key = new ECKey();
	ECKey party2_Key = new ECKey();
	ECKey party3_Key = new ECKey();
	ECKey bank_Key = new ECKey();

    public MultiSigTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend using signatures from the bank and one of the customers
    	ScriptBuilder sb = new ScriptBuilder();
    	
//    	sb.op(OP_ROT);
//    	sb.op(OP_ROT);
//    	sb.op(OP_ROT);
//    	sb.op(OP_ROT);
    	sb.op(OP_1);
    	sb.data(party1_Key.getPubKey());
    	sb.data(party2_Key.getPubKey());
    	sb.data(party3_Key.getPubKey());
    	sb.op(OP_3);
    	sb.op(OP_CHECKMULTISIG);
    	sb.op(OP_SWAP);
    	sb.data(bank_Key.getPubKey());
    	sb.op(OP_CHECKSIG);
    	sb.op(OP_BOOLAND);
    	return sb.build();
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedTransaction) {
        // Please be aware of the CHECK_MULTISIG bug!
        // TODO: Create a spending script
    	TransactionSignature banksig = sign(unsignedTransaction, bank_Key);
    	TransactionSignature party1sig = sign(unsignedTransaction, party1_Key);
    	TransactionSignature party2sig = sign(unsignedTransaction, party2_Key);
    	TransactionSignature party3sig = sign(unsignedTransaction, party3_Key);
//    	
    	
    	// Wrong Keys For Testing
    	
//    	ECKey wrong1 = new ECKey();
//    	ECKey wrong2 = new ECKey();
//    	ECKey wrong3 = new ECKey();
//    	ECKey wrong4 = new ECKey();
//
//    	TransactionSignature pw1sig = sign(unsignedTransaction, wrong1);
//    	TransactionSignature pw2sig = sign(unsignedTransaction, wrong2);
//    	TransactionSignature pw3sig = sign(unsignedTransaction, wrong3);
//    	TransactionSignature pw4sig = sign(unsignedTransaction, wrong4);

    	ScriptBuilder b = new ScriptBuilder();
    	b.data(banksig.encodeToBitcoin());
    	b.op(OP_1);
    	b.data(party1sig.encodeToBitcoin());

//    	b.data(pw3sig.encodeToBitcoin());
//    	b.data(pw4sig.encodeToBitcoin());
        return b.build();
    }
}
