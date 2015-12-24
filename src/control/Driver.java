package control;

import crosscompile.BToCConverter;
import interpret.BInterpreter;

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

    static String fact = ">++++++++++>>>+>+[>>>+[-[<<<<<[+<<<<<]>>[[-]>[<<+>+>-]<[>+<-]<[>+<-[>+<-[>\n" +
            "+<-[>+<-[>+<-[>+<-[>+<-[>+<-[>+<-[>[-]>>>>+>+<<<<<<-[>+<-]]]]]]]]]]]>[<+>-\n" +
            "]+>>>>>]<<<<<[<<<<<]>>>>>>>[>>>>>]++[-<<<<<]>>>>>>-]+>>>>>]<[>++<-]<<<<[<[\n" +
            ">+<-]<<<<]>>[->[-]++++++[<++++++++>-]>>>>]<<<<<[<[>+>+<<-]>.<<<<<]>.>>>>]";

    public static void main(String args[]) {
        //new interpret.BInterpreter(fact).execute();
        //new interpret.BInterpreter(fib).execute();
        new BInterpreter(hw3).execute();

        //System.out.println((new BToCConverter().convert(echo)));
    }
}
