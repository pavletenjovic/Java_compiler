// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class MethodDecListY extends MethodDecList {

    private MethodDecList MethodDecList;
    private MethodDec MethodDec;

    public MethodDecListY (MethodDecList MethodDecList, MethodDec MethodDec) {
        this.MethodDecList=MethodDecList;
        if(MethodDecList!=null) MethodDecList.setParent(this);
        this.MethodDec=MethodDec;
        if(MethodDec!=null) MethodDec.setParent(this);
    }

    public MethodDecList getMethodDecList() {
        return MethodDecList;
    }

    public void setMethodDecList(MethodDecList MethodDecList) {
        this.MethodDecList=MethodDecList;
    }

    public MethodDec getMethodDec() {
        return MethodDec;
    }

    public void setMethodDec(MethodDec MethodDec) {
        this.MethodDec=MethodDec;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDecList!=null) MethodDecList.accept(visitor);
        if(MethodDec!=null) MethodDec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDecList!=null) MethodDecList.traverseTopDown(visitor);
        if(MethodDec!=null) MethodDec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDecList!=null) MethodDecList.traverseBottomUp(visitor);
        if(MethodDec!=null) MethodDec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecListY(\n");

        if(MethodDecList!=null)
            buffer.append(MethodDecList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDec!=null)
            buffer.append(MethodDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecListY]");
        return buffer.toString();
    }
}
