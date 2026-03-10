// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:33:19


package rs.ac.bg.etf.pp1.ast;

public class EnumDecList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String enumElementName;
    private EnumValue EnumValue;
    private EnumListNext EnumListNext;

    public EnumDecList (String enumElementName, EnumValue EnumValue, EnumListNext EnumListNext) {
        this.enumElementName=enumElementName;
        this.EnumValue=EnumValue;
        if(EnumValue!=null) EnumValue.setParent(this);
        this.EnumListNext=EnumListNext;
        if(EnumListNext!=null) EnumListNext.setParent(this);
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

    public EnumListNext getEnumListNext() {
        return EnumListNext;
    }

    public void setEnumListNext(EnumListNext EnumListNext) {
        this.EnumListNext=EnumListNext;
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
        if(EnumValue!=null) EnumValue.accept(visitor);
        if(EnumListNext!=null) EnumListNext.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumValue!=null) EnumValue.traverseTopDown(visitor);
        if(EnumListNext!=null) EnumListNext.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumValue!=null) EnumValue.traverseBottomUp(visitor);
        if(EnumListNext!=null) EnumListNext.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDecList(\n");

        buffer.append(" "+tab+enumElementName);
        buffer.append("\n");

        if(EnumValue!=null)
            buffer.append(EnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumListNext!=null)
            buffer.append(EnumListNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDecList]");
        return buffer.toString();
    }
}
