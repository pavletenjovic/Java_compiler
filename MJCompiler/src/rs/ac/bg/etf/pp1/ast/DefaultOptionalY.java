// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class DefaultOptionalY extends DefaultOptional {

    private DefaultLabel DefaultLabel;
    private StatementList StatementList;

    public DefaultOptionalY (DefaultLabel DefaultLabel, StatementList StatementList) {
        this.DefaultLabel=DefaultLabel;
        if(DefaultLabel!=null) DefaultLabel.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public DefaultLabel getDefaultLabel() {
        return DefaultLabel;
    }

    public void setDefaultLabel(DefaultLabel DefaultLabel) {
        this.DefaultLabel=DefaultLabel;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DefaultLabel!=null) DefaultLabel.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DefaultLabel!=null) DefaultLabel.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DefaultLabel!=null) DefaultLabel.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DefaultOptionalY(\n");

        if(DefaultLabel!=null)
            buffer.append(DefaultLabel.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DefaultOptionalY]");
        return buffer.toString();
    }
}
