public class Driver {
    //hello world
    static String hw1 = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
    //another hello world
    static String hw2 = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
    //more complex hello world
    static String hw3 = ">++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<]>-----.>->\n" +
            "+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+.";
    //echo routine
    static String echo = ",[.[-],]";
    //infinite fibonacci
    static String fib = ">++++++++++>+>+[\n" +
            "    [+++++[>++++++++<-]>.<++++++[>--------<-]+<<<]>.>>[\n" +
            "        [-]<[>+<-]>>[<<+>+>-]<[>+<-[>+<-[>+<-[>+<-[>+<-[>+<-\n" +
            "            [>+<-[>+<-[>+<-[>[-]>+>+<<<-[>+<-]]]]]]]]]]]+>>>\n" +
            "    ]<<<\n" +
            "]";
    public static void main(String args[]) {
        //new BInterpreter(hw1).execute();
        new BInterpreter(echo).execute();
    }
}
