package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Transaction.SigHash;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERSequenceGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.signers.DSAKCalculator;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.util.BigIntegers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import static org.bitcoinj.script.ScriptOpCodes.*;
import static org.bitcoinj.script.ScriptOpCodes.OP_VERIFY;

/**
 * Created by bbuenz on 24.09.15.
 */
public class PayToPubKeyHash extends ScriptTransaction {
    // TODO: Problem 1
	//public key 1mitrU98assx4VzjZvsNnvY2UxF6AhCKd
	//private key 5JR2V2d3cAiHmix4Bafd5e1ST1Dj17yZZGwLpJ29BwPuKfgzrQq
	
	
	DeterministicKey key;
	//ECKey key;
	//Wallet wallet;
    @SuppressWarnings("deprecation")
	public PayToPubKeyHash(NetworkParameters parameters, File file, String password) throws AddressFormatException {
        super(parameters, file, password);
//        String privkey = "5JR2V2d3cAiHmix4Bafd5e1ST1Dj17yZZGwLpJ29BwPuKfgzrQq";
//        BigInteger privKeyString = Base58.decodeToBigInteger(privkey);
//        key = ECKey.fromPrivate(privKeyString.toByteArray());
        //importKey(key);
        //System.out.println(getWallet().getImportedKeys().get(0).toAddress(parameters));
//       address = key.toAddress(parameters);
        key = getWallet().freshReceiveKey();

    }
    @Override
    public Script createInputScript() {
        // TODO: Create a P2PKH script
        ScriptBuilder builder = new ScriptBuilder();
        builder.op(OP_DUP);
        builder.op(OP_HASH160);
        builder.data(key.getPubKeyHash());
        builder.op(OP_EQUALVERIFY);
        builder.op(OP_CHECKSIG);
        // TODO: be sure to test this script on the mainnet using a vanity address
        Script s =  builder.build();
        //System.out.println(s);
        return s;
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedTransaction) {
    	
        TransactionSignature txSig = sign(unsignedTransaction, key);
    	//ECDSASignature txSig = key.sign(hash);
        ScriptBuilder builder = new ScriptBuilder();
    	builder.data(txSig.encodeToBitcoin());
        builder.data(key.getPubKey());
        Script s2 = builder.build();
        //System.out.println(s2);
        return s2;
    }
//    protected BigInteger calculateE(BigInteger n, byte[] message)
//    {
//        int log2n = n.bitLength();
//        int messageBitLength = message.length * 8;
//
//        BigInteger e = new BigInteger(1, message);
//        if (log2n < messageBitLength)
//        {
//            e = e.shiftRight(messageBitLength - log2n);
//        }
//        return e;
//    }
//    
//    public byte[] sign(byte[] hash,BigInteger priv){
//    	ECPrivateKeyParameters keyparam = new ECPrivateKeyParameters(priv, domain);
//    	//System.out.printl(ECgetN());
//    	//System.out.println(keyparam.getD());
//    	//System.out.println(keyparam.);
//    	final DSAKCalculator kCalculator = new HMacDSAKCalculator(new SHA256Digest());
//        ECDSASigner signer = new ECDSASigner(kCalculator);
//        ECDomainParameters ec = keyparam.getParameters();
//        BigInteger n = ec.getN();
//        //BigInteger es = calculateE(n, hash);
//        BigInteger d = ((ECPrivateKeyParameters)keyparam).getD();
//        byte[] dVal = BigIntegers.asUnsignedByteArray(d);
//        //System.out.println(dVal.hashCode());
//        byte[] x = new byte[(n.bitLength() + 7) / 8];
//        //System.out.println(x.hashCode());
//
//        System.arraycopy(dVal, 0, x, x.length - dVal.length+1, dVal.length);
//        kCalculator.init(n,d, hash);
//        
//        BigInteger[] signature = signer.generateSignature(hash);
//        ByteArrayOutputStream s = new ByteArrayOutputStream();
//
//        try {
//            DERSequenceGenerator seq = new DERSequenceGenerator(s);
//            seq.addObject(new ASN1Integer(signature[0]));
//            seq.addObject(new ASN1Integer(signature[1]));
//            seq.close();
//            return s.toByteArray();
//        }
//        catch(IOException e){
//            return null;
//        }
//    }

  
}
