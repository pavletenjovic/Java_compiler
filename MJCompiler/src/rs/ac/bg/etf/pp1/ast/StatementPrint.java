// generated with ast extension for cup
// version 0.8
// 10/2/2026 2:47:2


package rs.ac.bg.etf.pp1.ast;

public class StatementPrint extends Statement {

    private CondExpr CondExpr;
    private PrintOptional PrintOptional;

    public StatementPrint (CondExpr CondExpr, PrintOptional PrintOptional) {
        this.CondExpr=CondExpr;
        if(CondExpr!=null) CondExpr.setParent(this);
        this.PrintOptional=PrintOptional;
        if(PrintOptional!=null) PrintOptional.setParent(this);
    }

    public CondExpr getCondExpr() {
        return CondExpr;
    }

    public void setCondExpr(CondExpr CondExpr) {
        this.CondExpr=CondExpr;
    }

    public PrintOptional getPrintOptional() {
        return PrintOptional;
    }

    public void setPrintOptional(PrintOptional PrintOptional) {
        this.PrintOptional=PrintOptional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondExpr!=null) CondExpr.accept(visitor);
        if(PrintOptional!=null) PrintOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondExpr!=null) CondExpr.traverseTopDown(visitor);
        if(PrintOptional!=null) PrintOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondExpr!=null) CondExpr.traverseBottomUp(visitor);
        if(PrintOptional!=null) PrintOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrint(\n");

        if(CondExpr!=null)
            buffer.append(CondExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintOptional!=null)
            buffer.append(PrintOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrint]");
        return buffer.toString();
    }
}
