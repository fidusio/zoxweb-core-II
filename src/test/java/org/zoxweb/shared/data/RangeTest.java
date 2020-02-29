package org.zoxweb.shared.data;

import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;

import java.io.IOException;

public class RangeTest {
    @Test
    public void simpleRange() throws IOException {
        Range<Integer> intRange = new Range<>(1, 100, Range.Inclusive.BOTH);
        String json = GSONUtil.toJSON(intRange, true);
        System.out.println(json);
        intRange = GSONUtil.fromJSON(json, Range.class);
        json = GSONUtil.toJSON(intRange, true);
        System.out.println(json);
        assert(intRange.getStart() instanceof Integer);
        assert(intRange.getEnd() instanceof Integer);
        assert(!intRange.contains(500));
        assert(!intRange.contains(0));
        assert(intRange.contains(50));
        assert(intRange.contains(99));
        assert(intRange.contains(1));

        System.out.println(intRange);
    }
}
