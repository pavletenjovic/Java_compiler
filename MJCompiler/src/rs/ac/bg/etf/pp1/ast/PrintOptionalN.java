// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:43


package rs.ac.bg.etf.pp1.ast;

public class PrintOptionalN extends PrintOptional {

    public PrintOptionalN () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintOptionalN(\n");

        buffer.append(tab);
        buffer.append(") [PrintOptionalN]");
        return buffer.toString();
    }
}
