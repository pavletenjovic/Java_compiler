// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:43


package rs.ac.bg.etf.pp1.ast;

public class UnaryMinusY extends UnaryMinus {

    public UnaryMinusY () {
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
        buffer.append("UnaryMinusY(\n");

        buffer.append(tab);
        buffer.append(") [UnaryMinusY]");
        return buffer.toString();
    }
}
