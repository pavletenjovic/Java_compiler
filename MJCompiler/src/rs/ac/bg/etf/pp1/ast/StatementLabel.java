// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:33:19


package rs.ac.bg.etf.pp1.ast;

public class StatementLabel extends Statement {

    private String labelName;
    private LabelDecl LabelDecl;
    private Statement Statement;

    public StatementLabel (String labelName, LabelDecl LabelDecl, Statement Statement) {
        this.labelName=labelName;
        this.LabelDecl=LabelDecl;
        if(LabelDecl!=null) LabelDecl.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName=labelName;
    }

    public LabelDecl getLabelDecl() {
        return LabelDecl;
    }

    public void setLabelDecl(LabelDecl LabelDecl) {
        this.LabelDecl=LabelDecl;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LabelDecl!=null) LabelDecl.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LabelDecl!=null) LabelDecl.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LabelDecl!=null) LabelDecl.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementLabel(\n");

        buffer.append(" "+tab+labelName);
        buffer.append("\n");

        if(LabelDecl!=null)
            buffer.append(LabelDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementLabel]");
        return buffer.toString();
    }
}
