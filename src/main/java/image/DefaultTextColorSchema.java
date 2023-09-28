package image;
public class DefaultTextColorSchema implements TextColorSchema {
    public char convert(int color) {
        char[] symbols = {'#', '$', '@', '%', '*', '+', '-', ' '};
        int stepSize = 255 / (symbols.length - 1);
        int index = Math.round(color / stepSize);
        return symbols[index];
    }
}