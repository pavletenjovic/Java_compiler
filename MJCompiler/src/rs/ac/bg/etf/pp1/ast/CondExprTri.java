// generated with ast extension for cup
// version 0.8
// 8/2/2026 17:3:52


package rs.ac.bg.etf.pp1.ast;

public class CondExprTri extends CondExpr {

    private CondFact CondFact;
    private TernaryCondEnd TernaryCondEnd;
    private Expr Expr;
    private TernaryTrueEnd TernaryTrueEnd;
    private Expr Expr1;

    public CondExprTri (CondFact CondFact, TernaryCondEnd TernaryCondEnd, Expr Expr, TernaryTrueEnd TernaryTrueEnd, Expr Expr1) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.TernaryCondEnd=TernaryCondEnd;
        if(TernaryCondEnd!=null) TernaryCondEnd.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.TernaryTrueEnd=TernaryTrueEnd;
        if(TernaryTrueEnd!=null) TernaryTrueEnd.setParent(this);
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public TernaryCondEnd getTernaryCondEnd() {
        return TernaryCondEnd;
    }

    public void setTernaryCondEnd(TernaryCondEnd TernaryCondEnd) {
        this.TernaryCondEnd=TernaryCondEnd;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public TernaryTrueEnd getTernaryTrueEnd() {
        return TernaryTrueEnd;
    }

    public void setTernaryTrueEnd(TernaryTrueEnd TernaryTrueEnd) {
        this.TernaryTrueEnd=TernaryTrueEnd;
    }

    public Expr getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr Expr1) {
        this.Expr1=Expr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFact!=null) CondFact.accept(visitor);
        if(TernaryCondEnd!=null) TernaryCondEnd.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(TernaryTrueEnd!=null) TernaryTrueEnd.accept(visitor);
        if(Expr1!=null) Expr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(TernaryCondEnd!=null) TernaryCondEnd.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(TernaryTrueEnd!=null) TernaryTrueEnd.traverseTopDown(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(TernaryCondEnd!=null) TernaryCondEnd.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(TernaryTrueEnd!=null) TernaryTrueEnd.traverseBottomUp(visitor);
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondExprTri(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryCondEnd!=null)
            buffer.append(TernaryCondEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryTrueEnd!=null)
            buffer.append(TernaryTrueEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondExprTri]");
        return buffer.toString();
    }
}
