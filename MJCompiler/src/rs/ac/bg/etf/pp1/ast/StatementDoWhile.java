// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class StatementDoWhile extends Statement {

    private DoStart DoStart;
    private Statement Statement;
    private DoCondStart DoCondStart;
    private Condition Condition;
    private DoEnd DoEnd;

    public StatementDoWhile (DoStart DoStart, Statement Statement, DoCondStart DoCondStart, Condition Condition, DoEnd DoEnd) {
        this.DoStart=DoStart;
        if(DoStart!=null) DoStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.DoCondStart=DoCondStart;
        if(DoCondStart!=null) DoCondStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.DoEnd=DoEnd;
        if(DoEnd!=null) DoEnd.setParent(this);
    }

    public DoStart getDoStart() {
        return DoStart;
    }

    public void setDoStart(DoStart DoStart) {
        this.DoStart=DoStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public DoCondStart getDoCondStart() {
        return DoCondStart;
    }

    public void setDoCondStart(DoCondStart DoCondStart) {
        this.DoCondStart=DoCondStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public DoEnd getDoEnd() {
        return DoEnd;
    }

    public void setDoEnd(DoEnd DoEnd) {
        this.DoEnd=DoEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoStart!=null) DoStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(DoCondStart!=null) DoCondStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(DoEnd!=null) DoEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoStart!=null) DoStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(DoCondStart!=null) DoCondStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(DoEnd!=null) DoEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoStart!=null) DoStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(DoCondStart!=null) DoCondStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(DoEnd!=null) DoEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDoWhile(\n");

        if(DoStart!=null)
            buffer.append(DoStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoCondStart!=null)
            buffer.append(DoCondStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoEnd!=null)
            buffer.append(DoEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDoWhile]");
        return buffer.toString();
    }
}
