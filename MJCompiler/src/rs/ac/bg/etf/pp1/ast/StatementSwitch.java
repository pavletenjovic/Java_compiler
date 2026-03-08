// generated with ast extension for cup
// version 0.8
// 8/2/2026 17:3:52


package rs.ac.bg.etf.pp1.ast;

public class StatementSwitch extends Statement {

    private SwitchStart SwitchStart;
    private CondExpr CondExpr;
    private CaseList CaseList;

    public StatementSwitch (SwitchStart SwitchStart, CondExpr CondExpr, CaseList CaseList) {
        this.SwitchStart=SwitchStart;
        if(SwitchStart!=null) SwitchStart.setParent(this);
        this.CondExpr=CondExpr;
        if(CondExpr!=null) CondExpr.setParent(this);
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
    }

    public SwitchStart getSwitchStart() {
        return SwitchStart;
    }

    public void setSwitchStart(SwitchStart SwitchStart) {
        this.SwitchStart=SwitchStart;
    }

    public CondExpr getCondExpr() {
        return CondExpr;
    }

    public void setCondExpr(CondExpr CondExpr) {
        this.CondExpr=CondExpr;
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchStart!=null) SwitchStart.accept(visitor);
        if(CondExpr!=null) CondExpr.accept(visitor);
        if(CaseList!=null) CaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchStart!=null) SwitchStart.traverseTopDown(visitor);
        if(CondExpr!=null) CondExpr.traverseTopDown(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchStart!=null) SwitchStart.traverseBottomUp(visitor);
        if(CondExpr!=null) CondExpr.traverseBottomUp(visitor);
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementSwitch(\n");

        if(SwitchStart!=null)
            buffer.append(SwitchStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondExpr!=null)
            buffer.append(CondExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementSwitch]");
        return buffer.toString();
    }
}
