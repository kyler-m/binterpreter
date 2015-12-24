package interpret;

/**
 * Dynamically sized byte buffer with a primitive array backing.
 */
public class Buffer {
    /**
     * The default capacity for the buffer.
     */
    public static final int DEFAULT_CAPACITY = 1024;
    /**
     * The primitive buffer backing.
     */
    private byte[] buf;

    /**
     * Constructor. Sets up a buffer with default capacity.
     */
    public Buffer() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor.
     * @param capacity the capacity to start with
     */
    public Buffer(int capacity) {
        buf = new byte[capacity];
    }

    /**
     * Get the value at the index.
     * @param i the index of the value to get
     * @return the value at the index
     */
    public byte get(int i) {
        return buf[i];
    }

    /**
     * Increment the value at the given index, expanding the buffer if necessary.
     * @param i the index of the value to increment.
     */
    public void inc(int i) {
        if (i >= buf.length)
            expandCapacity();
        buf[i]++;
    }

    /**
     * Decrement the value at the given index, expanding the buffer if necessary.
     * @param i the index of the value to decrement.
     */
    public void dec(int i) {
        if (i >= buf.length)
            expandCapacity();
        buf[i]--;
    }

    /**
     * Set the value at the given index to the given value.
     * @param i the index to set
     * @param val the value to put
     */
    public void set(int i, byte val) {
        if (i >= buf.length)
            expandCapacity();
        buf[i] = val;
    }

    /**
     * Double the capacity of the array backing for the buffer.
     */
    private void expandCapacity() {
        byte newBuf[] = new byte[buf.length * 2];
        for (int i = 0; i < buf.length; i++)
            newBuf[i] = buf[i];
        buf = newBuf;
    }
}
