#include <stdio.h>
#include <stdlib.h>

static char data[1024] = {0};
static char *p = data;

int loop_match(int *, int, char *, int);

int main(int argc, char **args)
{
    char *fname;
    char *prog;
    FILE *f;
    int c;
    int len;
    int i;

    if (argc != 2) {
        printf("Usage: bf <filename>.b");
        return -1;
    }

    fname = args[1];

    f = fopen(fname, "r");

    len = 0;
    while ((c = fgetc(f)) != EOF)
        len++;
    rewind(f);

    prog = (char *)malloc(len * sizeof(char));

    i = 0;
    while ((c = fgetc(f)) != EOF)
        prog[i++] = c;

    fclose(f);

    i = 0;
    for (; i < len; i++) {
        switch (prog[i]) {
        case '>': p++; break;
        case '<': p--; break;
        case '+': (*p)++; break;
        case '-': (*p)--; break;
        case '.': putchar(*p); break;
        case ',': *p = getchar(); break;
        case '[': if (loop_match(&i, 1, prog, len) < 0) return -1; i--; break;
        case ']': if (*p) if (loop_match(&i, -1, prog, len) < 0) return -1; break;
        default: return -1;
        }
    }

    return 0;
}

int loop_match(int *i, int bf, char *prog, int len)
{
    int delta;
    delta = bf;
    while (bf) {
        (*i) += delta;
        if (*i < 0 || *i >= len)
            return -1;
        if (prog[*i] == '[')
            bf++;
        if (prog[*i] == ']')
            bf--;
    }
    return 0;
}