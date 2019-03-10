package org.zoxweb.shared.util;

public class ArrayQueue<O>
implements SimpleQueueInterface<O>{
    private Object[] array;
    private int head = 0, end = 0;
    private int size = 0;
    private long totalDequeued=0, totalQueued=0;

    public ArrayQueue(int capacity)
    {
        array = new Object[capacity];
    }


    @Override
    public void clear() {

    }

    public int size()
    {
        return size;
    }

    public synchronized boolean queue(O o)
    {

        if (size != array.length) {
            if (end == array.length) {
                end = 0;
            }
         array[end] = o;
         end++;
         size++;
         totalQueued++;
         return true;
        }

        return false;
    }

    public synchronized O dequeue()
    {
        if(size != 0)
        {
            O ret = (O)array[head];
            array[head] = null;
            head++;
            if(head == array.length)
            {
                head = 0;
            }
            size--;
            totalDequeued++;
            return ret;
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long totalQueued() {
        return totalQueued;
    }

    @Override
    public long totalDequeued() {
        return totalDequeued;
    }

    @Override
    public int getCapacity() {
        return array.length;
    }

    @Override
    public boolean contains(O o) {
        return false;
    }

    public int capacity()
    {
        return array.length;
    }


    public String toString()
    {
        return "[" + SharedUtil.toCanonicalID(',', array.length, head, end, size()) + "]";
    }


}
