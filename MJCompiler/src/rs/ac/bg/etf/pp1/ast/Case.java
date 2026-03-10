// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class Case implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Integer caseValue;
    private CaseLabel CaseLabel;
    private StatementList StatementList;

    public Case (Integer caseValue, CaseLabel CaseLabel, StatementList StatementList) {
        this.caseValue=caseValue;
        this.CaseLabel=CaseLabel;
        if(CaseLabel!=null) CaseLabel.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public Integer getCaseValue() {
        return caseValue;
    }

    public void setCaseValue(Integer caseValue) {
        this.caseValue=caseValue;
    }

    public CaseLabel getCaseLabel() {
        return CaseLabel;
    }

    public void setCaseLabel(CaseLabel CaseLabel) {
        this.CaseLabel=CaseLabel;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
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
        if(CaseLabel!=null) CaseLabel.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseLabel!=null) CaseLabel.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseLabel!=null) CaseLabel.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Case(\n");

        buffer.append(" "+tab+caseValue);
        buffer.append("\n");

        if(CaseLabel!=null)
            buffer.append(CaseLabel.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Case]");
        return buffer.toString();
    }
}
