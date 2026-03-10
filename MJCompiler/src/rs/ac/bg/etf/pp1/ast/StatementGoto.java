// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class StatementGoto extends Statement {

    private String labelName;

    public StatementGoto (String labelName) {
        this.labelName=labelName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName=labelName;
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
        buffer.append("StatementGoto(\n");

        buffer.append(" "+tab+labelName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementGoto]");
        return buffer.toString();
    }
}
