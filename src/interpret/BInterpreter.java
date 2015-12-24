package interpret;

import java.util.HashMap;
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
    private int ptr;
    /**
     * The data buffer for the program space.
     */
    private Buffer buf;
    /**
     * Maps characters to their corresponding tokens, e.g. '>' : BToken.INCPTR.
     */
    private Map<Character, BToken> tokMap;
    /**
     * Scanner to get byte input (for ',').
     */
    private Scanner scanner;

    /**
     * Constructor. Initialize the buffer, data pointer and token map and strip out the program of non-token characters.
     * @param program the program to interpret
     */
    public BInterpreter(String program) {
        this.buf = new Buffer();
        this.ptr = 0;
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
        int pcHigh = -1; //Saves the highest pc reached in the interpretation.
        for (int i = 0; i < program.length(); i++) {
            BToken tok = tokMap.get(program.charAt(i));
            switch (tok) {
                case INCPTR: ptr++; break;
                case DECPTR: ptr--; break;
                case INCVAL: buf.inc(ptr); break;
                case DECVAL: buf.dec(ptr); break;
                case OUTPUT: System.out.printf("%c", buf.get(ptr)); break;
                case INPUT: buf.set(ptr, scanner.nextByte()); break;
                case LOOPSTART: {
                    //We've been here before, so this case is not necessary.
                    if (i <= pcHigh)
                        break;

                    //Advance pointer to the matching ].
                    int bf = 1;
                    while (bf > 0) {
                        i++;
                        if (tokMap.get(program.charAt(i)) == BToken.LOOPSTART)
                            bf++;
                        else if (tokMap.get(program.charAt(i)) == BToken.LOOPEND)
                            bf--;
                    }
                    i--;

                } break;
                case LOOPEND: {
                    //Loop is finished.
                    if (buf.get(ptr) == 0)
                        break;

                    //Using balance factor (similar to parentheses balance), decrement pointer back to matching [.
                    int balanceFactor = -1;
                    while (balanceFactor < 0) {
                        i--;
                        if (tokMap.get(program.charAt(i)) == BToken.LOOPSTART)
                            balanceFactor++;
                        else if (tokMap.get(program.charAt(i)) == BToken.LOOPEND)
                            balanceFactor--;
                    }
                } break;
                default: throw new RuntimeException("malformed program"); //Note since we strip this won't happen ever
            }
            pcHigh = Math.max(pcHigh, i);
        }
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
     * Enumerates the 8 different Brainfuck operators.
     */
    enum BToken {
        INCPTR, DECPTR, INCVAL, DECVAL, OUTPUT, INPUT, LOOPSTART, LOOPEND;
    }
}
