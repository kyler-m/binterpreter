package crosscompile;

public class BToCConverter {
    public BToCConverter() {
    }

    public String convert(String bprog) {
        StringBuilder cprog = new StringBuilder();

        cprog.append("#include <stdio.h>\n");
        cprog.append("static char p[1024] = {0};");

        //fire up the main
        cprog.append("int main(){");

        for (int i = 0; i < bprog.length(); i++) {
            switch (bprog.charAt(i)) {
                case '>': cprog.append("p++;"); break;
                case '<': cprog.append("p--;"); break;
                case '+': cprog.append("(*p)++;"); break;
                case '-': cprog.append("(*p)--;"); break;
                case '.': cprog.append("putchar(*p);"); break;
                case ',': cprog.append("*p = getchar();"); break;
                case '[': cprog.append("while(*p){"); break;
                case ']': cprog.append("}"); break;
            }
        }

        cprog.append("return 0;}");
        return cprog.toString();
    }
}
