// generated with ast extension for cup
// version 0.8
// 8/2/2026 17:3:52


package rs.ac.bg.etf.pp1.ast;

public class CondTerm implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private CondFactListOptional CondFactListOptional;

    public CondTerm (CondFactListOptional CondFactListOptional) {
        this.CondFactListOptional=CondFactListOptional;
        if(CondFactListOptional!=null) CondFactListOptional.setParent(this);
    }

    public CondFactListOptional getCondFactListOptional() {
        return CondFactListOptional;
    }

    public void setCondFactListOptional(CondFactListOptional CondFactListOptional) {
        this.CondFactListOptional=CondFactListOptional;
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
        if(CondFactListOptional!=null) CondFactListOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFactListOptional!=null) CondFactListOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFactListOptional!=null) CondFactListOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTerm(\n");

        if(CondFactListOptional!=null)
            buffer.append(CondFactListOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTerm]");
        return buffer.toString();
    }
}
