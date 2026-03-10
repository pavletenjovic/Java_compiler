// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class EnumValueY extends EnumValue {

    private Integer enumValueName;

    public EnumValueY (Integer enumValueName) {
        this.enumValueName=enumValueName;
    }

    public Integer getEnumValueName() {
        return enumValueName;
    }

    public void setEnumValueName(Integer enumValueName) {
        this.enumValueName=enumValueName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumValueY(\n");

        buffer.append(" "+tab+enumValueName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumValueY]");
        return buffer.toString();
    }
}
