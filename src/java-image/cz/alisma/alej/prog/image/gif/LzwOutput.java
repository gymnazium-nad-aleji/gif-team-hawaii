package cz.alisma.alej.prog.image.gif;

public interface LzwOutput {
    public void add(int code, int bitSize);
    public void done();
}