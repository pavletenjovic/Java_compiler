// generated with ast extension for cup
// version 0.8
// 8/2/2026 17:3:52


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListN extends VarDeclList {

    public VarDeclListN () {
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
        buffer.append("VarDeclListN(\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListN]");
        return buffer.toString();
    }
}
