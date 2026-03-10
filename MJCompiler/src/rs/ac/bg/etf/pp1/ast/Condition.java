// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class Condition implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private CondTermListOptional CondTermListOptional;

    public Condition (CondTermListOptional CondTermListOptional) {
        this.CondTermListOptional=CondTermListOptional;
        if(CondTermListOptional!=null) CondTermListOptional.setParent(this);
    }

    public CondTermListOptional getCondTermListOptional() {
        return CondTermListOptional;
    }

    public void setCondTermListOptional(CondTermListOptional CondTermListOptional) {
        this.CondTermListOptional=CondTermListOptional;
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
        if(CondTermListOptional!=null) CondTermListOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTermListOptional!=null) CondTermListOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTermListOptional!=null) CondTermListOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Condition(\n");

        if(CondTermListOptional!=null)
            buffer.append(CondTermListOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Condition]");
        return buffer.toString();
    }
}
