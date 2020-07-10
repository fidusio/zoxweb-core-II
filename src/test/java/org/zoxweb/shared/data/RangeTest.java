package org.zoxweb.shared.data;


import org.junit.jupiter.api.Test;
import org.zoxweb.server.util.GSONUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void rangeMatch()
    {
        String[] values =
                {
                  "(1,2)",
                  "[3,4]",
                  "(-5,6]",
                  "[ 10,    20.45  )  "
                };


        for (String value : values)
        {
            Range r = Range.toRange(value);
            Range rr = Range.toRange(r.toString());
            System.out.println(value+":" + Range.Inclusive.match(value) + " " + r +","  + rr);

        }
    }

    @Test
    public void rangeFail()
    {

        String[] values =
                {
                        "((1,2)",
                        "3,4",
                        "(toto,6]",
                        "[true, false]"
                };

        for(String val: values)
            assertThrows(IllegalArgumentException.class, ()->Range.toRange(val));
    }

    @Test
    public void testTypes()
    {
        Range<Integer> intRange = Range.toRange("[4, 4]");
        assert(intRange.getStart().getClass() == Integer.class);
        Range<Long> longRange = Range.toRange("(1, 5000000000)");
        assert(longRange.getStart().getClass() == Long.class);
        Range<Float> floatRange = Range.toRange("(1, 50.45)");
        assert(floatRange.getStart().getClass() == Float.class);

        floatRange = Range.toRange("[10, -50.10]");
        assert(floatRange.getStart().getClass() == Float.class);

        intRange = Range.toRange("[4.6, 4.5]", Integer.class);
        assert(intRange.getStart().getClass() == Integer.class);

    }
}
