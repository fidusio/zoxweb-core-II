package org.zoxweb.shared.data;


import org.zoxweb.shared.util.*;

public class Range<T extends Comparable<T>>
    extends SetNameDescriptionDAO
{

    /**
     * Include start, end in {@link Range}
     */
    public enum Inclusive {

        /**
         * {@link Range} inclusive of start, exclusive of end
         */
        START,

        /**
         * {@link Range} inclusive of end, exclusive of start
         */
        END,

        /**
         * {@link Range} inclusive of start and end
         */
        BOTH,

        /**
         * {@link Range} exclusive of start and end
         */
        NONE
    }



    public enum Param
            implements GetNVConfig {
        START(NVConfigManager.createNVConfig("start", "Start range", "Start", true, true, Number.class)),
        END(NVConfigManager.createNVConfig("end", "End range", "End", true, true, Number.class)),
        INCLUSIVE(NVConfigManager
                .createNVConfig("inclusive", "Inclusive (default) or exclusive", "inclusive", false, true,
                        Inclusive.class)),

        ;

        private final NVConfig nvc;

        Param(NVConfig nvc) {
            this.nvc = nvc;
        }

        @Override
        public NVConfig getNVConfig() {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_RANGE = new NVConfigEntityLocal(
            "range",
            null,
            Range.class.getSimpleName(),
            true,
            false,
            false,
            false,
            Range.class,
            SharedUtil.extractNVConfigs(Range.Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public Range() {
        super(NVC_RANGE);
    }

    /**
     * {@link Range} start and end values
     */
    //protected T start, end;

    /**
     *
     */
    //private Inclusive inclusive;

    /**
     * Auto switch if start > end
     * <br>It effects {@link #setStart(Comparable)} and {@link #setEnd(Comparable)}
     * <br>It does not effect constructor
     * <br>Default is set to false.
     */
    private boolean isAutoSwitch = false;

    // ///////////////////////////////////////////////////////////
    // ////////////////// Constructor ////////////////////////////
    // ///////////////////////////////////////////////////////////

    /**
     * Create a range with {@link Inclusive#START}
     *
     * @param start
     *<br/> Not null safe
     * @param end
     *<br/> Not null safe
     *<br/>Auto switched if start > end
     */
    public Range(T start, T end) {

        this(start, end, null);
    }

    /**
     * @param start
     *<br/> Not null safe
     * @param end
     *<br/> Not null safe
     *<br/>Auto switched if start > end
     *@param inclusive
     *<br/>If null {@link Inclusive#START} used
     */
    public Range(T start, T end, Inclusive inclusive) {
        this();

        if((start == null) || (end == null)) {

            throw new NullPointerException("Invalid null start / end value");
        }
        setInclusive(inclusive);

        if( isBigger(start, end) ) {
            setStart(end);
            setEnd(start);
        }else {
            setStart(start);
            setEnd(end);
        }

    }

    // ///////////////////////////////////////////////////////////
    // ///////////////////// Methods /////////////////////////////
    // ///////////////////////////////////////////////////////////

    /**
     * Check if this {@link Range} contains t
     *
     *@param t
     *<br/>Not null safe
     *@return
     *false for any value of t, if this.start equals this.end
     */
    public boolean contains(T t) {

        return contains(t, getInclusive());
    }

    /**
     * Check if this {@link Range} contains t
     *
     *@param t
     *<br/>Not null safe
     *@param inclusive
     *<br/>If null  used
     *@return
     *false for any value of t, if this.start equals this.end
     */
    public boolean contains(T t, Inclusive inclusive) {

        if(t == null) {

            throw new NullPointerException("Invalid null value");
        }

        inclusive = (inclusive == null) ? getInclusive() : inclusive;

        switch (inclusive) {

            case NONE:
                return ( isBigger(t, getStart()) && isSmaller(t, getEnd()) );

            case BOTH:
                return ( ! isBigger(getStart(), t)  && ! isBigger(t, getEnd()) ) ;

            case START: default:
                return ( ! isBigger(getStart(), t)  &&  isBigger(getEnd(), t) ) ;

            case END:
                return ( isBigger(t, getStart())  &&  ! isBigger(t, getEnd()) ) ;
        }
    }

    /**
     * Check if this {@link Range} contains other range
     *
     * @return
     * false for any value of range, if this.start equals this.end
     */
    public boolean contains(Range<T> range) {

        return contains(range.getStart()) && contains(range.getEnd());
    }

    /**
     * Check if this {@link Range} intersects with other range
     *
     * @return
     * false for any value of range, if this.start equals this.end
     */
    public boolean intersects(Range<T> range) {

        return contains(range.getStart()) || contains(range.getEnd());
    }

    /**
     * Convenience method
     */
    public static <T extends Comparable<T>> boolean isBigger(T t1, T t2) {

        return t1.compareTo(t2) > 0;
    }

    /**
     * Convenience method
     */
    public static <T extends Comparable<T>> boolean isSmaller(T t1, T t2) {

        return t1.compareTo(t2) < 0;
    }

    /**
     * Modifies range, if needed, so
     * range.getStart() >= intoOtherRange.getStart() and <br/>
     * range.getEnd() <= intoOtherRange.getEnd()
     * It does not guarantee into.contains(range)==true which depends also on
     * {@link Range#inclusive}
     * @param range
     * @param into
     * <br>Both not null safe
     */
    public static <T extends Comparable<T>>	Range<T> fit(Range<T> range,
                                                            Range<T> into) {
        if(isBigger(into.getStart(), range.getStart()) //start too small
                || isBigger(range.getStart(),into.getEnd())) { //start too big
            range.setStart(into.getStart());
        }

        if(isBigger(into.getStart(), range.getEnd()) //end too small
                || isBigger(range.getEnd(),into.getEnd())) { //start too big
            range.setEnd(into.getEnd());
        }

        return range;
    }

    /**
     * Modifies range, if needed, so
     * range.getStart() <= intoOtherRange.getStart() and <br/>
     * range.getEnd() >= intoOtherRange.getEnd() <br/>
     * It does not guarantee range.contains(toContain)==true which depends also on
     * {@link Range#inclusive}
     * @param range
     * @param toContain
     * <br>Both not null safe
     */
    public static <T extends Comparable<T>>	Range<T> expand(Range<T> range,
                                                               Range<T> toContain) {
        if(isBigger(range.getStart(), toContain.getStart()) //start too big
                || isBigger(range.getStart(),toContain.getEnd())) { //start too big
            range.setStart(toContain.getStart());
        }

        if(isBigger(toContain.getStart(), range.getEnd()) //end too small
                || isBigger(toContain.getEnd(), range.getEnd())) { //end too small
            range.setEnd(toContain.getEnd());
        }

        return range;
    }

    /**
     * Returns T which is within min (inclusive) , max (inclusive)
     *
     *@param value, min, max
     * <br/>Not null safe
     * <br/> if min > max they are switched
     * @return
     * min if value is smaller than min
     * <br/>max if value is bigger than max
     * <br/>value otherwise
     */
    public static <T extends Comparable<T>>	T setWithin(T value, T min, T max) {
        Range<T> range = new Range<>(min, max, Inclusive.BOTH);
        if( Range.isBigger(value, range.getEnd())){

            value = range.getEnd();

        }else if(Range.isBigger(range.getStart(), value)) {

            value = range.getStart();
        }

        return value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (getInclusive())
        {

            case START:
                sb.append('[');
                sb.append(getStart());
                sb.append(", ");
                sb.append(getEnd());
                sb.append(')');
                break;
            case END:
                sb.append('(');
                sb.append(getStart());
                sb.append(", ");
                sb.append(getEnd());
                sb.append(']');
                break;
            case BOTH:
                sb.append('[');
                sb.append(getStart());
                sb.append(", ");
                sb.append(getEnd());
                sb.append(']');
                break;
            case NONE:
                sb.append('(');
                sb.append(getStart());
                sb.append(", ");
                sb.append(getEnd());
                sb.append(')');
                break;
        }

//        if (getStart() != null) {
//            if (getStart().isExclusive()
//                    || getStart().getLimitType() == LimitValueDAO.LimitType.OPEN_VALUE) {
//                sb.append("(");
//            } else {
//                sb.append("[");
//            }
//
//            if (getStart().getValue() > 0) {
//                sb.append(getStart().getValue());
//            }
//        }
//
//        if (getEnd() != null) {
//            sb.append(", ");
//
//            if (getEnd().getValue() > 0) {
//                sb.append(getEnd().getValue());
//            }
//
//            if (getEnd().isExclusive()
//                    || getEnd().getLimitType() == LimitValueDAO.LimitType.OPEN_VALUE) {
//                sb.append(")");
//            } else {
//                sb.append("]");
//            }
//        }

        return sb.toString();
    }

    // ///////////////////////////////////////////////////////////
    // ////////////////// Getters, Setters ///////////////////////
    // ///////////////////////////////////////////////////////////

    /**
     * Get {@link #start}
     */
    public T getStart() { return lookupValue(Param.START); }

    /**
     * Set {@link #start}
     * <br/>Not null safe
     * <br/>If {@link #isAutoSwitch} is set to true, and  start > end
     * they are switched
     */
    public Range<T> setStart(T start) {

        if(isAutoSwitch && (start.compareTo(getEnd())>0)) {
            setValue(Param.START, getEnd());
            setValue(Param.END, start);
            //this.start = end;
            //this.end  = start;
        }else {
            //this.start = start;
            setValue(Param.START, start);
        }
        return this;
    }

    /**
     * Get {@link #end}
     */
    public T getEnd() {  return lookupValue(Param.END);  }

    /**
     * Set {@link #end}
     * <br/>Not null safe
     * <br/>If {@link #isAutoSwitch} is set to true, and  start > end
     * they are switched
     */
    public  Range<T> setEnd(T end) {

        if(isAutoSwitch && (getStart().compareTo(end)>0)) {
            setValue(Param.END, getStart());
            setValue(Param.START, end);
            //this.end  = start;
            //this.start = end;
        }else {
            setValue(Param.END, end);
            //this.end = end;
        }
        return this;
    }

    /**
     * Get {@link #inclusive}
     */
    public Inclusive getInclusive() { return lookupValue(Param.INCLUSIVE); }

    /**
     * Set {@link #inclusive}
     * @param inclusive
     *<br/>If null {@link Inclusive#START} used
     */
    public  Range<T> setInclusive(Inclusive inclusive) {

        inclusive = (inclusive == null) ? Inclusive.BOTH : inclusive;
        setValue(Param.INCLUSIVE, inclusive);
        return this;
    }

    /**
     * Get {@link #isAutoSwitch}
     */
    public boolean isAutoSwitch() { return isAutoSwitch; }

    /**
     * Set {@link #isAutoSwitch}
     */
    public Range<T> setAutoSwitch(boolean isAutoSwitch) {

        this.isAutoSwitch = isAutoSwitch;
        return this;
    }
}
