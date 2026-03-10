// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class StatementFor extends Statement {

    private ForInit ForInit;
    private DesignatorStatementOptional DesignatorStatementOptional;
    private ForCondStart ForCondStart;
    private ConditionOptional ConditionOptional;
    private ForUpdateStart ForUpdateStart;
    private DesignatorStatementOptional DesignatorStatementOptional1;
    private ForBodyStart ForBodyStart;
    private Statement Statement;

    public StatementFor (ForInit ForInit, DesignatorStatementOptional DesignatorStatementOptional, ForCondStart ForCondStart, ConditionOptional ConditionOptional, ForUpdateStart ForUpdateStart, DesignatorStatementOptional DesignatorStatementOptional1, ForBodyStart ForBodyStart, Statement Statement) {
        this.ForInit=ForInit;
        if(ForInit!=null) ForInit.setParent(this);
        this.DesignatorStatementOptional=DesignatorStatementOptional;
        if(DesignatorStatementOptional!=null) DesignatorStatementOptional.setParent(this);
        this.ForCondStart=ForCondStart;
        if(ForCondStart!=null) ForCondStart.setParent(this);
        this.ConditionOptional=ConditionOptional;
        if(ConditionOptional!=null) ConditionOptional.setParent(this);
        this.ForUpdateStart=ForUpdateStart;
        if(ForUpdateStart!=null) ForUpdateStart.setParent(this);
        this.DesignatorStatementOptional1=DesignatorStatementOptional1;
        if(DesignatorStatementOptional1!=null) DesignatorStatementOptional1.setParent(this);
        this.ForBodyStart=ForBodyStart;
        if(ForBodyStart!=null) ForBodyStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForInit getForInit() {
        return ForInit;
    }

    public void setForInit(ForInit ForInit) {
        this.ForInit=ForInit;
    }

    public DesignatorStatementOptional getDesignatorStatementOptional() {
        return DesignatorStatementOptional;
    }

    public void setDesignatorStatementOptional(DesignatorStatementOptional DesignatorStatementOptional) {
        this.DesignatorStatementOptional=DesignatorStatementOptional;
    }

    public ForCondStart getForCondStart() {
        return ForCondStart;
    }

    public void setForCondStart(ForCondStart ForCondStart) {
        this.ForCondStart=ForCondStart;
    }

    public ConditionOptional getConditionOptional() {
        return ConditionOptional;
    }

    public void setConditionOptional(ConditionOptional ConditionOptional) {
        this.ConditionOptional=ConditionOptional;
    }

    public ForUpdateStart getForUpdateStart() {
        return ForUpdateStart;
    }

    public void setForUpdateStart(ForUpdateStart ForUpdateStart) {
        this.ForUpdateStart=ForUpdateStart;
    }

    public DesignatorStatementOptional getDesignatorStatementOptional1() {
        return DesignatorStatementOptional1;
    }

    public void setDesignatorStatementOptional1(DesignatorStatementOptional DesignatorStatementOptional1) {
        this.DesignatorStatementOptional1=DesignatorStatementOptional1;
    }

    public ForBodyStart getForBodyStart() {
        return ForBodyStart;
    }

    public void setForBodyStart(ForBodyStart ForBodyStart) {
        this.ForBodyStart=ForBodyStart;
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
        if(ForInit!=null) ForInit.accept(visitor);
        if(DesignatorStatementOptional!=null) DesignatorStatementOptional.accept(visitor);
        if(ForCondStart!=null) ForCondStart.accept(visitor);
        if(ConditionOptional!=null) ConditionOptional.accept(visitor);
        if(ForUpdateStart!=null) ForUpdateStart.accept(visitor);
        if(DesignatorStatementOptional1!=null) DesignatorStatementOptional1.accept(visitor);
        if(ForBodyStart!=null) ForBodyStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForInit!=null) ForInit.traverseTopDown(visitor);
        if(DesignatorStatementOptional!=null) DesignatorStatementOptional.traverseTopDown(visitor);
        if(ForCondStart!=null) ForCondStart.traverseTopDown(visitor);
        if(ConditionOptional!=null) ConditionOptional.traverseTopDown(visitor);
        if(ForUpdateStart!=null) ForUpdateStart.traverseTopDown(visitor);
        if(DesignatorStatementOptional1!=null) DesignatorStatementOptional1.traverseTopDown(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForInit!=null) ForInit.traverseBottomUp(visitor);
        if(DesignatorStatementOptional!=null) DesignatorStatementOptional.traverseBottomUp(visitor);
        if(ForCondStart!=null) ForCondStart.traverseBottomUp(visitor);
        if(ConditionOptional!=null) ConditionOptional.traverseBottomUp(visitor);
        if(ForUpdateStart!=null) ForUpdateStart.traverseBottomUp(visitor);
        if(DesignatorStatementOptional1!=null) DesignatorStatementOptional1.traverseBottomUp(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementFor(\n");

        if(ForInit!=null)
            buffer.append(ForInit.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementOptional!=null)
            buffer.append(DesignatorStatementOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondStart!=null)
            buffer.append(ForCondStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionOptional!=null)
            buffer.append(ConditionOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForUpdateStart!=null)
            buffer.append(ForUpdateStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementOptional1!=null)
            buffer.append(DesignatorStatementOptional1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBodyStart!=null)
            buffer.append(ForBodyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementFor]");
        return buffer.toString();
    }
}
