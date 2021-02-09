package org.zoxweb.shared.util;

public abstract class DSR<V> implements DataSizeReader<V>
{
    public static final DSR<byte []> BYTES = new DSR<byte[]>() {
        @Override
        public long size(byte[] bytes) {
            if(bytes != null)
                return bytes.length;
            return 0;
        }
    };
}
