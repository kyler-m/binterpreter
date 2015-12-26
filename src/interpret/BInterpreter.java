package interpret;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.Scanner;

/**
 * Class to interpret Brainfuck programs. Construct with a String representation of the Brainfuck program and call
 * ::execute to begin interpreting.
 */
public class BInterpreter {
    /**
     * The held Brainfuck program.
     */
    private String program;
    /**
     * The data pointer, i.e. where the program is currently pointing to in the data buffer.
     */
    private int dPtr;
    /**
     * The data buffer for the program space.
     */
    private Buffer data;
    /**
     * Maps characters to their corresponding tokens, e.g. '>' : BToken.INCPTR.
     */
    private Map<Character, BToken> tokMap;
    /**
     * Scanner to get byte input (for ',').
     */
    private Scanner scanner;
    /**
     * The character buffer from standard input.
     */
    private Buffer buf;
    /**
     * Pointer to the current item on the character buffer.
     */
    private int bPtr;

    /**
     * Constructor. Initialize the buffer, data pointer and token map and strip out the program of non-token characters.
     * @param program the program to interpret
     */
    public BInterpreter(String program) {
        this.data = new Buffer();
        this.buf = new Buffer(24);
        this.dPtr = 0;
        this.bPtr = -1;
        this.tokMap = new HashMap<>();
        tokMap.put('>', BToken.INCPTR);
        tokMap.put('<', BToken.DECPTR);
        tokMap.put('+', BToken.INCVAL);
        tokMap.put('-', BToken.DECVAL);
        tokMap.put('.', BToken.OUTPUT);
        tokMap.put(',', BToken.INPUT);
        tokMap.put('[', BToken.LOOPSTART);
        tokMap.put(']', BToken.LOOPEND);
        this.program = strip(program);
        System.out.println("Interpreting: " + this.program);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Execute (interpret) the program).
     */
    public void execute() {
        preprocess();
        for (int i = 0; i < program.length(); i++) {
            BToken tok = tokMap.get(program.charAt(i));
            switch (tok) {
                case INCPTR: dPtr++; break;
                case DECPTR: dPtr--; break;
                case INCVAL: data.inc(dPtr); break;
                case DECVAL: data.dec(dPtr); break;
                case OUTPUT: handleOutput(); break;
                case INPUT: handleInput(); break;
                case LOOPSTART: i = loopMatch(i, 1) - 1; break;
                case LOOPEND: i = (data.get(dPtr) == 0) ? i : loopMatch(i, -1); break;
                default: throw new RuntimeException("malformed program"); //Note since we strip this won't happen ever
            }
        }
        scanner.close();
    }

    /**
     * Handle the input routine ",". To emulate the implicit buffering in getchar(), use a buffer for multiple
     * characters entered at the same time so whole lines can be consumed.
     */
    private void handleInput() {
        //If we have stuff in the input buffer, then just consume it
        if (bPtr != -1) {
            data.set(dPtr, buf.get(bPtr--));
            return;
        }
        String input = scanner.nextLine() + "\n";
        try {
            //If the input is a literal byte, try to parse that in
            int intVal = Integer.parseInt(input);
            if (intVal >= Byte.MIN_VALUE && intVal <= Byte.MAX_VALUE)
                data.set(dPtr, (byte)intVal);
        } catch (NumberFormatException e) {
            //Otherwise, populate the buffer character-by-character
            if (input.length() > 0)
                data.set(dPtr, (byte)input.charAt(0));
            else
                data.set(dPtr, (byte)0);
            for (int k = input.length() - 1; k > 0; k--)
                buf.set(++bPtr, (byte)input.charAt(k));
        }
    }

    /**
     * Output a single character (represented as a byte in the data buffer).
     */
    private void handleOutput() {
        try {
            System.out.printf("%c", data.get(dPtr));
        } catch (IllegalFormatCodePointException e) {

        }
    }

    /**
     * Advance the program pointer from a [ to a ] or vice versa. Note that each [ counts as +1 and each ] counts as -1,
     * thus to advance the pointer to a ] bf should initially be +1 and for the other case it should be -1.
     * @param i the initial program pointer
     * @param bf the initial balance factor of the loops, e.g. [ is +1 and ] is -1
     * @return the pointer to the corresponding loop token
     */
    private int loopMatch(int i, int bf) {
        int delta = bf;
        while (bf != 0) {
            i += delta;
            if (tokMap.get(program.charAt(i)) == BToken.LOOPSTART)
                bf++;
            else if (tokMap.get(program.charAt(i)) == BToken.LOOPEND)
                bf--;
        }
        return i;
    }

    /**
     * Return a string with every non-BToken character stripped out.
     * This is just used for ease of testing; it is helpful to be able to be able to just paste in large Brainfuck
     * programs (IntelliJ automatically inserts "\n") and this allows us to do that without affecting the program.
     * @param toStrip the string from which to strip tokens
     * @return the resultant string
     */
    private String strip(String toStrip) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < toStrip.length(); i++)
            if (tokMap.get(toStrip.charAt(i)) != null)
                builder.append(toStrip.charAt(i));
        return builder.toString();
    }

    /**
     * Preprocess the program. Currently just checks to see if the program has balanced loop tokens.
     */
    private void preprocess() {
        //Check loop balance
        int bf = 0;
        for (int i = 0; i < program.length(); i++) {
            char ch = program.charAt(i);
            if (tokMap.get(ch) == BToken.LOOPSTART)
                bf++;
            if (tokMap.get(ch) == BToken.LOOPEND)
                bf--;
            if (bf < 0)
                throw new RuntimeException("] without matching [");
        }
        if (bf != 0)
            throw new RuntimeException("program has unbalanced loop tokens");
    }

    public void printData() {
        System.out.print("Dptr=" + dPtr + " ");
        data.printBuffer();
    }

    /**
     * Enumerates the 8 different Brainfuck operators.
     */
    enum BToken {
        INCPTR, DECPTR, INCVAL, DECVAL, OUTPUT, INPUT, LOOPSTART, LOOPEND;
    }
}
