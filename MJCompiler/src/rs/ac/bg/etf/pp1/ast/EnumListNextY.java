// generated with ast extension for cup
// version 0.8
// 8/2/2026 17:3:52


package rs.ac.bg.etf.pp1.ast;

public class EnumListNextY extends EnumListNext {

    private EnumListNext EnumListNext;
    private String enumElementName;
    private EnumValue EnumValue;

    public EnumListNextY (EnumListNext EnumListNext, String enumElementName, EnumValue EnumValue) {
        this.EnumListNext=EnumListNext;
        if(EnumListNext!=null) EnumListNext.setParent(this);
        this.enumElementName=enumElementName;
        this.EnumValue=EnumValue;
        if(EnumValue!=null) EnumValue.setParent(this);
    }

    public EnumListNext getEnumListNext() {
        return EnumListNext;
    }

    public void setEnumListNext(EnumListNext EnumListNext) {
        this.EnumListNext=EnumListNext;
    }

    public String getEnumElementName() {
        return enumElementName;
    }

    public void setEnumElementName(String enumElementName) {
        this.enumElementName=enumElementName;
    }

    public EnumValue getEnumValue() {
        return EnumValue;
    }

    public void setEnumValue(EnumValue EnumValue) {
        this.EnumValue=EnumValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumListNext!=null) EnumListNext.accept(visitor);
        if(EnumValue!=null) EnumValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumListNext!=null) EnumListNext.traverseTopDown(visitor);
        if(EnumValue!=null) EnumValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumListNext!=null) EnumListNext.traverseBottomUp(visitor);
        if(EnumValue!=null) EnumValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumListNextY(\n");

        if(EnumListNext!=null)
            buffer.append(EnumListNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+enumElementName);
        buffer.append("\n");

        if(EnumValue!=null)
            buffer.append(EnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumListNextY]");
        return buffer.toString();
    }
}
