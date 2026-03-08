// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:43


package rs.ac.bg.etf.pp1.ast;

public class CondFactListOptionalY extends CondFactListOptional {

    private CondFactListOptional CondFactListOptional;
    private CondFact CondFact;

    public CondFactListOptionalY (CondFactListOptional CondFactListOptional, CondFact CondFact) {
        this.CondFactListOptional=CondFactListOptional;
        if(CondFactListOptional!=null) CondFactListOptional.setParent(this);
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
    }

    public CondFactListOptional getCondFactListOptional() {
        return CondFactListOptional;
    }

    public void setCondFactListOptional(CondFactListOptional CondFactListOptional) {
        this.CondFactListOptional=CondFactListOptional;
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFactListOptional!=null) CondFactListOptional.accept(visitor);
        if(CondFact!=null) CondFact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFactListOptional!=null) CondFactListOptional.traverseTopDown(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFactListOptional!=null) CondFactListOptional.traverseBottomUp(visitor);
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFactListOptionalY(\n");

        if(CondFactListOptional!=null)
            buffer.append(CondFactListOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFactListOptionalY]");
        return buffer.toString();
    }
}
