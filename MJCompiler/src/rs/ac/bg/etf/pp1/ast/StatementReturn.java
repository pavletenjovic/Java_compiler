// generated with ast extension for cup
// version 0.8
// 8/2/2026 17:3:52


package rs.ac.bg.etf.pp1.ast;

public class StatementReturn extends Statement {

    private ExprOptional ExprOptional;

    public StatementReturn (ExprOptional ExprOptional) {
        this.ExprOptional=ExprOptional;
        if(ExprOptional!=null) ExprOptional.setParent(this);
    }

    public ExprOptional getExprOptional() {
        return ExprOptional;
    }

    public void setExprOptional(ExprOptional ExprOptional) {
        this.ExprOptional=ExprOptional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprOptional!=null) ExprOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprOptional!=null) ExprOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprOptional!=null) ExprOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementReturn(\n");

        if(ExprOptional!=null)
            buffer.append(ExprOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementReturn]");
        return buffer.toString();
    }
}
