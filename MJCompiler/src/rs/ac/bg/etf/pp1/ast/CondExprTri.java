// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class CondExprTri extends CondExpr {

    private CondFact CondFact;
    private TernaryCondEnd TernaryCondEnd;
    private CondExpr CondExpr;
    private TernaryTrueEnd TernaryTrueEnd;
    private CondExpr CondExpr1;

    public CondExprTri (CondFact CondFact, TernaryCondEnd TernaryCondEnd, CondExpr CondExpr, TernaryTrueEnd TernaryTrueEnd, CondExpr CondExpr1) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.TernaryCondEnd=TernaryCondEnd;
        if(TernaryCondEnd!=null) TernaryCondEnd.setParent(this);
        this.CondExpr=CondExpr;
        if(CondExpr!=null) CondExpr.setParent(this);
        this.TernaryTrueEnd=TernaryTrueEnd;
        if(TernaryTrueEnd!=null) TernaryTrueEnd.setParent(this);
        this.CondExpr1=CondExpr1;
        if(CondExpr1!=null) CondExpr1.setParent(this);
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

    public CondExpr getCondExpr() {
        return CondExpr;
    }

    public void setCondExpr(CondExpr CondExpr) {
        this.CondExpr=CondExpr;
    }

    public TernaryTrueEnd getTernaryTrueEnd() {
        return TernaryTrueEnd;
    }

    public void setTernaryTrueEnd(TernaryTrueEnd TernaryTrueEnd) {
        this.TernaryTrueEnd=TernaryTrueEnd;
    }

    public CondExpr getCondExpr1() {
        return CondExpr1;
    }

    public void setCondExpr1(CondExpr CondExpr1) {
        this.CondExpr1=CondExpr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFact!=null) CondFact.accept(visitor);
        if(TernaryCondEnd!=null) TernaryCondEnd.accept(visitor);
        if(CondExpr!=null) CondExpr.accept(visitor);
        if(TernaryTrueEnd!=null) TernaryTrueEnd.accept(visitor);
        if(CondExpr1!=null) CondExpr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(TernaryCondEnd!=null) TernaryCondEnd.traverseTopDown(visitor);
        if(CondExpr!=null) CondExpr.traverseTopDown(visitor);
        if(TernaryTrueEnd!=null) TernaryTrueEnd.traverseTopDown(visitor);
        if(CondExpr1!=null) CondExpr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(TernaryCondEnd!=null) TernaryCondEnd.traverseBottomUp(visitor);
        if(CondExpr!=null) CondExpr.traverseBottomUp(visitor);
        if(TernaryTrueEnd!=null) TernaryTrueEnd.traverseBottomUp(visitor);
        if(CondExpr1!=null) CondExpr1.traverseBottomUp(visitor);
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

        if(CondExpr!=null)
            buffer.append(CondExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryTrueEnd!=null)
            buffer.append(TernaryTrueEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondExpr1!=null)
            buffer.append(CondExpr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondExprTri]");
        return buffer.toString();
    }
}
