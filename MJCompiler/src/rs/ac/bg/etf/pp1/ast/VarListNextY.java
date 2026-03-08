// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:42


package rs.ac.bg.etf.pp1.ast;

public class VarListNextY extends VarListNext {

    private VarListNext VarListNext;
    private String varName;
    private Brackets Brackets;

    public VarListNextY (VarListNext VarListNext, String varName, Brackets Brackets) {
        this.VarListNext=VarListNext;
        if(VarListNext!=null) VarListNext.setParent(this);
        this.varName=varName;
        this.Brackets=Brackets;
        if(Brackets!=null) Brackets.setParent(this);
    }

    public VarListNext getVarListNext() {
        return VarListNext;
    }

    public void setVarListNext(VarListNext VarListNext) {
        this.VarListNext=VarListNext;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public Brackets getBrackets() {
        return Brackets;
    }

    public void setBrackets(Brackets Brackets) {
        this.Brackets=Brackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarListNext!=null) VarListNext.accept(visitor);
        if(Brackets!=null) Brackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarListNext!=null) VarListNext.traverseTopDown(visitor);
        if(Brackets!=null) Brackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarListNext!=null) VarListNext.traverseBottomUp(visitor);
        if(Brackets!=null) Brackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarListNextY(\n");

        if(VarListNext!=null)
            buffer.append(VarListNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(Brackets!=null)
            buffer.append(Brackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarListNextY]");
        return buffer.toString();
    }
}
