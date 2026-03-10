// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:50:8


package rs.ac.bg.etf.pp1.ast;

public class FormParsNextY extends FormParsNext {

    private FormValue FormValue;
    private FormParsNext FormParsNext;

    public FormParsNextY (FormValue FormValue, FormParsNext FormParsNext) {
        this.FormValue=FormValue;
        if(FormValue!=null) FormValue.setParent(this);
        this.FormParsNext=FormParsNext;
        if(FormParsNext!=null) FormParsNext.setParent(this);
    }

    public FormValue getFormValue() {
        return FormValue;
    }

    public void setFormValue(FormValue FormValue) {
        this.FormValue=FormValue;
    }

    public FormParsNext getFormParsNext() {
        return FormParsNext;
    }

    public void setFormParsNext(FormParsNext FormParsNext) {
        this.FormParsNext=FormParsNext;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormValue!=null) FormValue.accept(visitor);
        if(FormParsNext!=null) FormParsNext.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormValue!=null) FormValue.traverseTopDown(visitor);
        if(FormParsNext!=null) FormParsNext.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormValue!=null) FormValue.traverseBottomUp(visitor);
        if(FormParsNext!=null) FormParsNext.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsNextY(\n");

        if(FormValue!=null)
            buffer.append(FormValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsNext!=null)
            buffer.append(FormParsNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsNextY]");
        return buffer.toString();
    }
}
