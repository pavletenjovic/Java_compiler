// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:42


package rs.ac.bg.etf.pp1.ast;

public class ConstN extends Const {

    private Integer numberValue;

    public ConstN (Integer numberValue) {
        this.numberValue=numberValue;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Integer numberValue) {
        this.numberValue=numberValue;
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
        buffer.append("ConstN(\n");

        buffer.append(" "+tab+numberValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstN]");
        return buffer.toString();
    }
}
