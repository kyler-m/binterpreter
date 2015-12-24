package crosscompile;

/**
 * Created by mejia_000 on 12/24/2015.
 */
public class BToCConverter {
    public BToCConverter() {

    }

    public String convert(String bprog) {
        StringBuilder cprog = new StringBuilder();

        //necessary imports
        cprog.append("#include <stdio.h>\n");

        //start off with global variables
        cprog.append("static char buf[1024] = {0};");
        cprog.append("static char *ptr = buf;");

        //fire up the main
        cprog.append("int main(){");

        for (int i = 0; i < bprog.length(); i++) {
            switch (bprog.charAt(i)) {
                case '>': cprog.append("ptr++;"); break;
                case '<': cprog.append("ptr--;"); break;
                case '+': cprog.append("(*ptr)++;"); break;
                case '-': cprog.append("(*ptr)--;"); break;
                case '.': cprog.append("putchar(*ptr);"); break;
                case ',': cprog.append("*ptr = getchar();"); break;
                case '[': cprog.append("while(*ptr){"); break;
                case ']': cprog.append("}"); break;
            }
        }

        //all done!
        cprog.append("return 0;}");
        return cprog.toString();
    }
}
