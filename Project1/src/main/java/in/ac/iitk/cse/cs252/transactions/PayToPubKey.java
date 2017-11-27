package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.math.BigInteger;
import java.security.interfaces.ECKey;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class PayToPubKey extends ScriptTransaction {
    private DeterministicKey key;

    public PayToPubKey(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
        key = getWallet().freshReceiveKey();
        
    }

    @Override
    public Script createInputScript() {
        ScriptBuilder builder = new ScriptBuilder();
       // builder.data(key.getPubKey());
        //builder.op(OP_DUP);
        //builder.op(OP_HASH160);
        builder.data(key.getPubKey());
        //builder.op(OP_EQUALVERIFY);
        //builder.op(OP_VERIFY);
        builder.op(OP_CHECKSIG);
        System.out.println(builder.build());

        return builder.build();
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedTransaction) {
        TransactionSignature txSig = sign(unsignedTransaction, key);
        System.out.println(unsignedTransaction);
        ScriptBuilder builder = new ScriptBuilder();
        builder.data(txSig.encodeToBitcoin());
        Script s = builder.build();
        System.out.println(s);
        return s;
    }
}
