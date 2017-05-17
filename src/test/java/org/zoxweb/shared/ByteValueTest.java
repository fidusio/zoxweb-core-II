package org.zoxweb.shared;

import org.zoxweb.shared.util.BytesValue;


import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by mnael on 5/9/2017.
 */
public class ByteValueTest
{
    public static void main(String ...args)
    {
        byte result[] = BytesValue.SHORT.toBytes((short) 25999);

        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putShort((short) 25999);
        System.out.println(result.length + " " + Arrays.toString(result) + " " + BytesValue.SHORT.toValue(result) + " " + Arrays.toString(bb.array()));
        result = BytesValue.INT.toBytes(4000000);
        bb.clear();
        bb.putInt(4000000);
        System.out.println(result.length + " " + Arrays.toString(result) + " " + BytesValue.INT.toValue(result) + " " + Arrays.toString(bb.array()));



    }
}
