// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:33:19


package rs.ac.bg.etf.pp1.ast;

public class ConstList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constName;
    private Const Const;
    private ConstListNext ConstListNext;

    public ConstList (String constName, Const Const, ConstListNext ConstListNext) {
        this.constName=constName;
        this.Const=Const;
        if(Const!=null) Const.setParent(this);
        this.ConstListNext=ConstListNext;
        if(ConstListNext!=null) ConstListNext.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Const getConst() {
        return Const;
    }

    public void setConst(Const Const) {
        this.Const=Const;
    }

    public ConstListNext getConstListNext() {
        return ConstListNext;
    }

    public void setConstListNext(ConstListNext ConstListNext) {
        this.ConstListNext=ConstListNext;
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
        if(Const!=null) Const.accept(visitor);
        if(ConstListNext!=null) ConstListNext.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Const!=null) Const.traverseTopDown(visitor);
        if(ConstListNext!=null) ConstListNext.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Const!=null) Const.traverseBottomUp(visitor);
        if(ConstListNext!=null) ConstListNext.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstList(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Const!=null)
            buffer.append(Const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstListNext!=null)
            buffer.append(ConstListNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstList]");
        return buffer.toString();
    }
}
