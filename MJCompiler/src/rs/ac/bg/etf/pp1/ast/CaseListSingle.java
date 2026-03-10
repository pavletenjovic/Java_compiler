// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class CaseListSingle extends CaseList {

    private Case Case;

    public CaseListSingle (Case Case) {
        this.Case=Case;
        if(Case!=null) Case.setParent(this);
    }

    public Case getCase() {
        return Case;
    }

    public void setCase(Case Case) {
        this.Case=Case;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Case!=null) Case.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Case!=null) Case.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Case!=null) Case.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseListSingle(\n");

        if(Case!=null)
            buffer.append(Case.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseListSingle]");
        return buffer.toString();
    }
}
