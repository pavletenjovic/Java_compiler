package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private Map<String, List<Integer>> patchAddrs = new HashMap<>();
	private final static int fieldSize = 4;

	
	public int getMainPc() { 
		return this.mainPc;
	}
	
	private void initializePredeclaredMethods() {
        // 'ord' and 'chr' are the same code.
        Obj ordMethod = Tab.find("ord");
        Obj chrMethod = Tab.find("chr");
        ordMethod.setAdr(Code.pc);
        chrMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
 
        Obj lenMethod = Tab.find("len");
        lenMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }
	
	CodeGenerator() {
		this.initializePredeclaredMethods();
	}

	
	@Override
	public void visit(TypeOptionY typeOptionY) {
		typeOptionY.obj.setAdr(Code.pc);
		if(typeOptionY.getMethodName().equalsIgnoreCase("main"))
			this.mainPc = Code.pc;
		
		Code.put(Code.enter);
		Code.put(typeOptionY.obj.getLevel()); //b1
		Code.put(typeOptionY.obj.getLocalSymbols().size()); //b2
	}
	
	@Override
	public void visit(TypeOptionN typeOptionN) {
		typeOptionN.obj.setAdr(Code.pc);
		if(typeOptionN.getMethodName().equalsIgnoreCase("main"))
			this.mainPc = Code.pc;
		
		Code.put(Code.enter);
		Code.put(typeOptionN.obj.getLevel()); //b1
		Code.put(typeOptionN.obj.getLocalSymbols().size()); //b2
	}
	
	@Override
	public void visit(MethodDec methodDec) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	@Override
	public void visit(DesignatorSignatureAssign designatorSignatureAssign) {
		Code.store(designatorSignatureAssign.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorSignatureAct designatorSignatureAct) {
		int offset = designatorSignatureAct.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(designatorSignatureAct.getDesignator().obj.getType() != Tab.noType)
			Code.put(Code.pop);
	}
	
	@Override
	public void visit(DesignatorSignatureInc designatorSignatureInc) {
		if(designatorSignatureInc.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		else if(designatorSignatureInc.getDesignator().obj.getKind() == Obj.Fld)
			Code.put(Code.dup);
		Code.load(designatorSignatureInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorSignatureInc.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorSignatureDec designatorSignatureDec) {
		if(designatorSignatureDec.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		else if(designatorSignatureDec.getDesignator().obj.getKind() == Obj.Fld)
			Code.put(Code.dup);
		Code.load(designatorSignatureDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorSignatureDec.getDesignator().obj);
	}
	
	@Override
	public void visit(StatementPrint statementPrint) {
	    int width = 0;

	    if (statementPrint.getPrintOptional() instanceof PrintOptionalY) {
	        width = ((PrintOptionalY) statementPrint.getPrintOptional()).getN1();
	    }

	    Code.loadConst(width);

	    Struct type = statementPrint.getCondExpr().struct;
	    if (type.equals(Tab.charType)) {
	        Code.put(Code.bprint);
	    } else {
	        Code.put(Code.print);
	    }
	}
	
	@Override
	public void visit(ExprOptionalY exprOptionalY) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ExprOptionalN exprOptionalN) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(StatementRead statementRead) {
		if(statementRead.getDesignator().obj.getType().equals(Tab.charType))
			Code.put(Code.bread);
		else
			Code.put(Code.read);
		Code.store(statementRead.getDesignator().obj);
	}
	
	@Override
	public void visit(AddopTermListAdd addopTermListAdd) {
		if(addopTermListAdd.getAddop() instanceof AddopPlus)
			Code.put(Code.add);
		else if(addopTermListAdd.getAddop() instanceof AddopMinus)
			Code.put(Code.sub);
	}
	
	@Override
	public void visit(MulopFactorListMul mulopFactorListMul) {
		if(mulopFactorListMul.getMulop() instanceof MulopMul)
			Code.put(Code.mul);
		else if(mulopFactorListMul.getMulop() instanceof MulopDiv)
			Code.put(Code.div);
		else if(mulopFactorListMul.getMulop() instanceof MulopMod)
			Code.put(Code.rem);
	}
	
	@Override
	public void visit(Factor factor) {
		if(factor.getUnaryMinus() instanceof UnaryMinusY)
			Code.put(Code.neg);
	}
	
	
	@Override
	public void visit(FactorOptionNumber factorOptionNumber) {
		Code.loadConst(factorOptionNumber.getN1());
	}
	
	@Override
	public void visit(FactorOptionChar factorOptionChar) {
		Code.loadConst(factorOptionChar.getC1());
	}
	
	@Override
	public void visit(FactorOptionBoll factorOptionBoll) {
		Code.loadConst(factorOptionBoll.getB1());
	}
	
	@Override
	public void visit(FactorOptionDesignator factorOptionDesignator) {
		Obj obj = factorOptionDesignator.getDesignator().obj;

	    if (obj.getKind() == Obj.Con) {
	        Code.loadConst(obj.getAdr());
	    } else {
	        Code.load(obj);
	    }
	}
	
	@Override
	public void visit(FactorOptionNewY factorOptionNew) {
		Code.put(Code.newarray);
		if(factorOptionNew.getType().struct.equals(Tab.charType))
			Code.put(0);
		else
			Code.put(1);
	}
	
	@Override
	public void visit(FactorOptionFuncall factorOptionFuncall) {
		int offset = factorOptionFuncall.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
//	@Override
//	public void visit(DesignatorVar designatorVar) {
//	    Code.load(designatorVar.obj);
//	}

//	@Override
//	public void visit(DesignatorLen designatorLen) {
//	    Obj arrayObj = designatorLen.obj;
//
//	    if(arrayObj == Tab.noObj) return;
//
//	    Code.load(arrayObj);  
//	    Code.put(Code.arraylength);
//	}

	@Override
	public void visit(DesignatorEnum designatorEnum) {
	    Code.load(designatorEnum.obj);
	}

	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Code.load(designatorArrayName.obj);
	}
	
	
	private int returnRelOp(Relop relop) {
		if(relop instanceof RelopEq)
			return Code.eq;
		else if(relop instanceof RelopNeq)
			return Code.ne;
		else if(relop instanceof RelopLt)
			return Code.lt;
		else if(relop instanceof RelopLte)
			return Code.le;
		else if(relop instanceof RelopGt)
			return Code.gt;
		else if(relop instanceof RelopGte)
			return Code.ge;
		else
			return 0;
	}
	
	
	private Stack<Integer> skipCondFact = new Stack<>();
	private Stack<Integer> skipCondition = new Stack<>();
	private Stack<Integer> skipThen = new Stack<>();
	private Stack<Integer> skipElse = new Stack<>();
	
	@Override
	public void visit(CondFactExpr condFactExpr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); //netacna
		skipCondFact.push(Code.pc - 2);
		//tacna
	}
	
	@Override
	public void visit(CondFactRel condFactRel) {
		Code.putFalseJump(returnRelOp(condFactRel.getRelop()), 0); //netacna
		skipCondFact.push(Code.pc - 2);
		//tacna
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		Code.putJump(0);
		skipCondition.push(Code.pc - 2);
		while(!skipCondFact.empty())
			Code.fixup(skipCondFact.pop());
	}
	
	@Override
	public void visit(Condition condition) {
		Code.putJump(0);
		skipThen.push(Code.pc - 2);
		while(!skipCondition.empty())
			Code.fixup(skipCondition.pop());
		
	}
	
//	private Stack<Integer> skipFalseTernary = new Stack<>();
//	private Stack<Integer> skipEndTernary = new Stack<>();
//	
//	@Override
//	public void visit(CondExprTri condExprTri) {
//	    Code.putFalseJump(Code.eq, 0);
//	    skipFalseTernary.push(Code.pc - 2);
//
//	    // TRUE grana
//	    condExprTri.getExpr().accept(this); // generisemo exprTrue
//	    Code.putJump(0); // preskacemo false granu
//	    skipEndTernary.push(Code.pc - 2);
//
//	    // FALSE grana
//	    Code.fixup(skipFalseTernary.pop());
//	    condExprTri.getExpr1().accept(this); // generisemo exprFalse
//
//	    // kraj ternarnog operatora
//	    Code.fixup(skipEndTernary.pop());
//	    
//	}
	
	
	@Override
	public void visit(ElseOptionalN elseOptionalN) {
		Code.fixup(skipThen.pop());
	}
	
	@Override
	public void visit(Else else_) {
		//tacne
		Code.putJump(0); //tacne bacamo na kraj ELSE
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop());
		//netacne
	}
	
	@Override
	public void visit(ElseOptionalY elseOptionalY) {
		Code.fixup(skipElse.pop()); 
	}
	
	
//	private Stack<List<Integer>> breakJump = new Stack<>();
//	private Stack<List<Integer>> continueJumps = new Stack<>();
//	
//	@Override
//	public void visit(StatementBreak statementBreak) {
//		Code.putJump(0);
//		breakJump.peek().add(Code.pc - 2);
//	}
//	
//	@Override
//	public void visit(StatementContinue statementContinue) {
//		Code.putJump(0);
//		continueJumps.peek().add(Code.pc - 2);
//	}
	
//	@Override
//	public void visit(StatementFor statementFor) {
//		if (statementFor.getDesignatorStatementOptional() != null) {
//	        statementFor.getDesignatorStatementOptional().traverseTopDown(this);
//	    }
//
//	    // Start adresa uslova
//	    int forStart = Code.pc;
//
//	    // Ako postoji uslov
//	    if (statementFor.getConditionOptional() != null) {
//	        statementFor.getConditionOptional().traverseTopDown(this);
//	        // Skok iz petlje ako je uslov false
//	        int jumpIfFalse = Code.putFalseJump(statementFor.getConditionOptional().struct, 0); 
//	        statementFor.get = jumpIfFalse;
//	    }
//
//	    // Telo petlje
//	    statementFor.getStatement().traverseTopDown(this);
//
//	    // Ako postoji update
//	    if (statementFor.getDesignatorStatementOptional1() != null) {
//	        statementFor.getDesignatorStatementOptional1().traverseTopDown(this);
//	    }
//
//	    // Jump nazad na start uslova
//	    Code.putJump(forStart);
//
//	    // Fixup break
//	    if (statementFor.getConditionOptional() != null) {
//	        Code.fixup(statementFor.getStatement().struct);
//	    }
//	}
//	
	
}
