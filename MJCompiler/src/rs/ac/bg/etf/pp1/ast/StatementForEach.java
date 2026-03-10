// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:33:19


package rs.ac.bg.etf.pp1.ast;

public class StatementForEach extends Statement {

    private ForeachArrayStart ForeachArrayStart;
    private Designator Designator;
    private Designator Designator1;
    private ForeachBodyStart ForeachBodyStart;
    private Statement Statement;

    public StatementForEach (ForeachArrayStart ForeachArrayStart, Designator Designator, Designator Designator1, ForeachBodyStart ForeachBodyStart, Statement Statement) {
        this.ForeachArrayStart=ForeachArrayStart;
        if(ForeachArrayStart!=null) ForeachArrayStart.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.ForeachBodyStart=ForeachBodyStart;
        if(ForeachBodyStart!=null) ForeachBodyStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForeachArrayStart getForeachArrayStart() {
        return ForeachArrayStart;
    }

    public void setForeachArrayStart(ForeachArrayStart ForeachArrayStart) {
        this.ForeachArrayStart=ForeachArrayStart;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public ForeachBodyStart getForeachBodyStart() {
        return ForeachBodyStart;
    }

    public void setForeachBodyStart(ForeachBodyStart ForeachBodyStart) {
        this.ForeachBodyStart=ForeachBodyStart;
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
        if(ForeachArrayStart!=null) ForeachArrayStart.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(ForeachBodyStart!=null) ForeachBodyStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachArrayStart!=null) ForeachArrayStart.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(ForeachBodyStart!=null) ForeachBodyStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachArrayStart!=null) ForeachArrayStart.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(ForeachBodyStart!=null) ForeachBodyStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementForEach(\n");

        if(ForeachArrayStart!=null)
            buffer.append(ForeachArrayStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachBodyStart!=null)
            buffer.append(ForeachBodyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementForEach]");
        return buffer.toString();
    }
}
