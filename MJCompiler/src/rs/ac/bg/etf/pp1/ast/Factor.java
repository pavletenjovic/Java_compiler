// generated with ast extension for cup
// version 0.8
// 10/2/2026 2:47:2


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private UnaryMinus UnaryMinus;
    private FactorOption FactorOption;

    public Factor (UnaryMinus UnaryMinus, FactorOption FactorOption) {
        this.UnaryMinus=UnaryMinus;
        if(UnaryMinus!=null) UnaryMinus.setParent(this);
        this.FactorOption=FactorOption;
        if(FactorOption!=null) FactorOption.setParent(this);
    }

    public UnaryMinus getUnaryMinus() {
        return UnaryMinus;
    }

    public void setUnaryMinus(UnaryMinus UnaryMinus) {
        this.UnaryMinus=UnaryMinus;
    }

    public FactorOption getFactorOption() {
        return FactorOption;
    }

    public void setFactorOption(FactorOption FactorOption) {
        this.FactorOption=FactorOption;
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
        if(UnaryMinus!=null) UnaryMinus.accept(visitor);
        if(FactorOption!=null) FactorOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(UnaryMinus!=null) UnaryMinus.traverseTopDown(visitor);
        if(FactorOption!=null) FactorOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(UnaryMinus!=null) UnaryMinus.traverseBottomUp(visitor);
        if(FactorOption!=null) FactorOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(UnaryMinus!=null)
            buffer.append(UnaryMinus.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorOption!=null)
            buffer.append(FactorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
