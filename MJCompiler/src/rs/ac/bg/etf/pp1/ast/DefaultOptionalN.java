// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:33:19


package rs.ac.bg.etf.pp1.ast;

public class DefaultOptionalN extends DefaultOptional {

    public DefaultOptionalN () {
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
        buffer.append("DefaultOptionalN(\n");

        buffer.append(tab);
        buffer.append(") [DefaultOptionalN]");
        return buffer.toString();
    }
}
