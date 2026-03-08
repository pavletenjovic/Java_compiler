// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:43


package rs.ac.bg.etf.pp1.ast;

public class ActPars implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private CondExpr CondExpr;
    private ExprNext ExprNext;

    public ActPars (CondExpr CondExpr, ExprNext ExprNext) {
        this.CondExpr=CondExpr;
        if(CondExpr!=null) CondExpr.setParent(this);
        this.ExprNext=ExprNext;
        if(ExprNext!=null) ExprNext.setParent(this);
    }

    public CondExpr getCondExpr() {
        return CondExpr;
    }

    public void setCondExpr(CondExpr CondExpr) {
        this.CondExpr=CondExpr;
    }

    public ExprNext getExprNext() {
        return ExprNext;
    }

    public void setExprNext(ExprNext ExprNext) {
        this.ExprNext=ExprNext;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondExpr!=null) CondExpr.accept(visitor);
        if(ExprNext!=null) ExprNext.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondExpr!=null) CondExpr.traverseTopDown(visitor);
        if(ExprNext!=null) ExprNext.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondExpr!=null) CondExpr.traverseBottomUp(visitor);
        if(ExprNext!=null) ExprNext.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActPars(\n");

        if(CondExpr!=null)
            buffer.append(CondExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprNext!=null)
            buffer.append(ExprNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActPars]");
        return buffer.toString();
    }
}
