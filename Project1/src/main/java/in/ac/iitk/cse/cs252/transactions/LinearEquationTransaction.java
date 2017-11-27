package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import static org.bitcoinj.script.ScriptOpCodes.*;
/**
 * Created by bbuenz on 24.09.15.
 */
public class LinearEquationTransaction extends ScriptTransaction {
    // TODO: Problem 2
    public LinearEquationTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend by two numbers x and y such that x+y=first 4 digits of your iitk roll and |x-y|=last 4 digits of your suid (perhaps +1)
    	ScriptBuilder b = new ScriptBuilder();
    	Integer sumint = 150;
    	Integer diffint = 562;
    	BigInteger sum = new BigInteger(ByteBuffer.allocate(4).putInt(sumint).array());
    	BigInteger diff = new BigInteger(ByteBuffer.allocate(4).putInt(diffint).array());
    	//System.out.println(encode(sum));
    	//byte[] sumbytes = ByteBuffer.allocate(4).putInt(sum).array();
    	//byte[] diffbytes = ByteBuffer.allocate(4).putInt(diff).array();
    	b.op(OP_2DUP);
    	b.op(OP_SUB);
    	b.op(OP_ABS);
    	b.data(encode(diff));
    	b.op(OP_EQUAL);
    	b.op(OP_ROT);
    	b.op(OP_ROT);
    	b.op(OP_ADD);
//    	//b.op(OP_12);
//    	b.data(ByteBuffer.allocate(4).putInt(sumint).array());
    	b.data(encode(sum));
    	b.op(OP_EQUAL);    	
    	b.op(OP_BOOLAND);
//    	//b.op(OP_2);
//    	//b.op(OP_EQUAL);

    	
    	System.out.println(b.build());
        return b.build();
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedScript) {
        // TODO: Create a spending script
    	ScriptBuilder b = new ScriptBuilder();
    	Integer xint = 356;
    	Integer yint = -206;
    	BigInteger x = new BigInteger(ByteBuffer.allocate(4).putInt(xint).array());
    	BigInteger y = new BigInteger(ByteBuffer.allocate(4).putInt(yint).array());

   //    	byte[] xbytes = ByteBuffer.allocate(4).putInt(x).array();
//    	byte[] ybytes = ByteBuffer.allocate(4).putInt(y).array();
    	
    	//b.data(ByteBuffer.allocate(4).putInt(yint).array());
    	//b.op(OP_5);
    	//b.op(OP_7);
    	//b.data(ByteBuffer.allocate(4).putInt(xint).array());
    	//b.op(OP_SUB);
    	b.data(encode(x));
    	b.data(encode(y));
    	System.out.println(b.build());

        return b.build();
    }

    private byte[] encode(BigInteger bigInteger) {
        return Utils.reverseBytes(Utils.encodeMPI(bigInteger, false));
        
    }
}
