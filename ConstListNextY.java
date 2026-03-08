// generated with ast extension for cup
// version 0.8
// 8/2/2026 15:22:42


package rs.ac.bg.etf.pp1.ast;

public class ConstListNextY extends ConstListNext {

    private ConstListNext ConstListNext;
    private String constName;
    private Const Const;

    public ConstListNextY (ConstListNext ConstListNext, String constName, Const Const) {
        this.ConstListNext=ConstListNext;
        if(ConstListNext!=null) ConstListNext.setParent(this);
        this.constName=constName;
        this.Const=Const;
        if(Const!=null) Const.setParent(this);
    }

    public ConstListNext getConstListNext() {
        return ConstListNext;
    }

    public void setConstListNext(ConstListNext ConstListNext) {
        this.ConstListNext=ConstListNext;
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstListNext!=null) ConstListNext.accept(visitor);
        if(Const!=null) Const.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstListNext!=null) ConstListNext.traverseTopDown(visitor);
        if(Const!=null) Const.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstListNext!=null) ConstListNext.traverseBottomUp(visitor);
        if(Const!=null) Const.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstListNextY(\n");

        if(ConstListNext!=null)
            buffer.append(ConstListNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Const!=null)
            buffer.append(Const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstListNextY]");
        return buffer.toString();
    }
}
