package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class SemanticPass extends VisitorAdaptor {

    boolean errorDetected = false;
    Obj currentMethod = null;
    Obj currentProgram = null;
    Struct currentType = null;
    boolean returnFound = false;
    Struct savedConstType = null;
    int savedConstValue;
    Obj enumSaved = null;
    Integer currentEnumValue = 0;
    Set <Integer> enumSet = new HashSet<>();
    boolean mainExists = false;
    boolean returnHappend = false;
    private Set<Integer> switchCaseValues = new HashSet<>();
    int loopCnt = 0;
    int switchCnt = 0;
    private List<Struct> actParsTypes = new ArrayList<>();
    int nVars;
    private Struct boolType = new Struct(Struct.Bool);

    Logger log = Logger.getLogger(getClass());

    public SemanticPass() {
        Tab.init();
        Tab.insert(Obj.Type, "bool", boolType);
    }

    public boolean passed() {
        return !errorDetected;
    }

    public void report_error(String message, SyntaxNode info) {
        errorDetected = true;
        int line = (info == null) ? 0 : info.getLine();
        log.error(message + (line != 0 ? " na liniji " + line : ""));
    }

    /* ================= PROGRAM ================= */

    @Override
    public void visit(ProgramName programName) {
        currentProgram = Tab.insert(Obj.Prog, programName.getProgramName(), Tab.noType);
        Tab.openScope();
    }

    @Override
    public void visit(Program program) {
    	nVars = Tab.currentScope().getnVars();
        Tab.chainLocalSymbols(currentProgram);
        Tab.closeScope();
        if(!mainExists) {
        	report_error("Main metoda ne postoji", null);
        }
    }

    /* ================= TYPE ================= */

    @Override
    public void visit(Type type) {

        Obj typeNode = Tab.find(type.getTypeName());

        if(typeNode == Tab.noObj) {
            report_error("Tip nije pronadjen: " + type.getTypeName(), type);
            type.struct = currentType = Tab.noType;
        }
        else if(typeNode.getKind() != Obj.Type) {
            report_error("Ime ne predstavlja tip: " + type.getTypeName(), type);
            type.struct = currentType = Tab.noType;
        }
        else {
        	type.struct = currentType = typeNode.getType();
        }
    }

    /* ================= VAR DECL ================= */

    @Override
    public void visit(VarList varList) {

        String varName = varList.getVarName();

        if(Tab.currentScope.findSymbol(varName) != null) {
            report_error("Promenljiva vec deklarisana: " + varName, varList);
            return;
        }

        Struct varType = currentType;

        if(varList.getBrackets() instanceof BracketsY) {
            varType = new Struct(Struct.Array, currentType);
        }
    	
        Tab.insert(Obj.Var, varName, varType);
    }
    
    @Override
    public void visit(VarListNextY varList) {

        String varName = varList.getVarName();

        if(Tab.currentScope.findSymbol(varName) != null) {
            report_error("Promenljiva vec deklarisana: " + varName, varList);
            return;
        }

        Struct varType = currentType;

        if(varList.getBrackets() instanceof BracketsY) {
            varType = new Struct(Struct.Array, currentType);
        }
    	
        Tab.insert(Obj.Var, varName, varType);
    }
    
    /* ================= CONST DECL ================= */
    
    @Override
    public void visit(ConstList constList) {

        String constName = constList.getConstName();

        if(Tab.currentScope.findSymbol(constName) != null) {
            report_error("Konstanta vec deklarisana: " + constName, constList);
            return;
        }
        
        if(!currentType.assignableTo(savedConstType)) {
        	report_error("Tip se ne poklapa: " + constName, constList);
            return;
        }

        Struct constType = currentType;

        Obj consta = Tab.insert(Obj.Con, constName, constType);
        consta.setAdr(savedConstValue);
    }
    
    @Override
    public void visit(ConstListNextY constList) {

        String constName = constList.getConstName();

        if(Tab.currentScope.findSymbol(constName) != null) {
            report_error("Konstanta vec deklarisana: " + constName, constList);
            return;
        }
        
        if(!currentType.assignableTo(savedConstType)) {
        	report_error("Tip se ne poklapa: " + constName, constList);
            return;
        }

        Struct constType = currentType;

        Obj consta = Tab.insert(Obj.Con, constName, constType);
        consta.setAdr(savedConstValue);
    }
    
    @Override
    public void visit(ConstN constN) {
    	savedConstType = Tab.intType;
    	savedConstValue = constN.getNumberValue();
    }
    
    @Override
    public void visit(ConstC constC) {
    	savedConstType = Tab.charType;
    	savedConstValue = constC.getCharValue();
    }
    
    @Override
    public void visit(ConstB constB) {
    	savedConstType = boolType;
    	savedConstValue = constB.getBoolValue();
    }
    
    /* ================= ENUM DECL ================= */
    
    @Override
    public void visit(EnumDecl enumDecl) {
    	Tab.chainLocalSymbols(enumSaved);
    	Tab.closeScope();
    	enumSet.clear();
   }
    
    @Override
    public void visit(EnumName enumName) {
    	
    	 String name = enumName.getEnumName();
    	
    	if(Tab.currentScope.findSymbol(name) != null) {
            report_error("Ime enuma vec deklarisana: " + name, enumName);
            return;
        }
    	
    	enumSaved = Tab.insert(Obj.Type, name, Tab.intType);
    	Tab.openScope();
    }

    @Override
    public void visit(EnumValueY enumList) {

    	String enumName = enumSet.isEmpty() 
    			? 
    			((EnumDecList)enumList.getParent()).getEnumElementName() 
    			: 
				((EnumListNextY)enumList.getParent()).getEnumElementName();
    	
        Integer enumValue = enumList.getEnumValueName();
        currentEnumValue = enumValue + 1;

        if(Tab.currentScope.findSymbol(enumName) != null) {
            report_error("Enum vec deklarisana: " + enumName, enumList);
            return;
        }
        
        if(enumSet.contains(enumValue)) {
            report_error("Enum vrednost vec koriscena: " + enumValue, enumList);
            return;
        }
        
        enumSet.add(enumValue);

        Obj enuma = Tab.insert(Obj.Con, enumName, Tab.intType);
        enuma.setAdr(enumValue);
    }
    
    @Override
    public void visit(EnumValueN enumList) {

    	String enumName = enumSet.isEmpty() 
    			? 
    			((EnumDecList)enumList.getParent()).getEnumElementName() 
    			: 
				((EnumListNextY)enumList.getParent()).getEnumElementName();
        
        Integer enumValue = currentEnumValue;
        currentEnumValue++;
        
        if(Tab.currentScope.findSymbol(enumName) != null) {
            report_error("Enum vec deklarisana: " + enumName, enumList);
            return;
        }
        
        if(enumSet.contains(enumValue)) {
            report_error("Enum vrednost vec koriscena: " + enumValue, enumList);
            return;
        }

        enumSet.add(enumValue);

        Obj enuma = Tab.insert(Obj.Con, enumName, Tab.intType);
        enuma.setAdr(enumValue);
    }
    

    /* ================= METHOD ================= */

    @Override
    public void visit(TypeOptionY methodName) {
//        Struct returnType;
    	
        String methodNameTemp = methodName.getMethodName();
        if(Tab.currentScope.findSymbol(methodNameTemp) != null) {
            report_error("Metoda vec deklarisana: " + methodNameTemp, methodName);
            return;
        }

        methodName.obj = currentMethod = Tab.insert(Obj.Meth, methodNameTemp, currentType);
        Tab.openScope();
//        returnFound = false;
    }
    
    @Override
    public void visit(TypeOptionN methodName) {
        String methodNameTemp = methodName.getMethodName();
        if(Tab.currentScope.findSymbol(methodNameTemp) != null) {
            report_error("Metoda vec deklarisana: " + methodNameTemp, methodName);
            return;
        }
        if(methodNameTemp.equals("main")) {
        	mainExists = true;
        }

        methodName.obj = currentMethod = Tab.insert(Obj.Meth, methodNameTemp, Tab.noType);
        Tab.openScope();
    }
    
    @Override
    public void visit(MethodDec methodName) {
    	Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
    }
    
    @Override
    public void visit(FormValueArray formValueArray) {
    	Obj varObj = null;
		if(currentMethod == null)
			report_error("Semanticka greska. [FormPar_var]", formValueArray);
		else
			varObj = Tab.currentScope().findSymbol(formValueArray.getI2());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, formValueArray.getI2(), new Struct(Struct.Array, currentType));
			varObj.setFpPos(1);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else{
			report_error("Dvostruka definicija formalnog parametra: " + formValueArray.getI2(), formValueArray);
		}

    }
    
    @Override
    public void visit(FormValueVar formValueVar) {
    	Obj varObj = null;
		if(currentMethod == null)
			report_error("Semanticka greska. [formValueVar]", formValueVar);
		else
			varObj = Tab.currentScope().findSymbol(formValueVar.getI2());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, formValueVar.getI2(), currentType);
			varObj.setFpPos(1);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else{
			report_error("Dvostruka definicija formalnog parametra: " + formValueVar.getI2(), formValueVar);
		}

    }
    
    /* ================= Designator ================= */
    
    @Override
    public void visit(DesignatorVar designator) {
    	Obj varObj = Tab.find(designator.getI1());
		if(varObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivi: " + designator.getI1(), designator);
			designator.obj = Tab.noObj;
		}
		else if(varObj.getKind() != Obj.Var && varObj.getKind() != Obj.Con && varObj.getKind() != Obj.Meth) {
			report_error("Neadekvatna promenljiva: " + designator.getI1(), designator);
			designator.obj = Tab.noObj;
		}
		else {
			designator.obj = varObj;
		}
    }

    @Override
    public void visit(DesignatorLen designator) {
    	Obj arrayObj = Tab.find(designator.getI1());

        if (arrayObj == Tab.noObj) {
            report_error("Nije deklarisano: " + designator.getI1(), designator);
            designator.obj = Tab.noObj;
            return;
        }

        if (arrayObj.getType().getKind() != Struct.Array) {
            report_error(".LENGTH moze samo za nizove.", designator);
            designator.obj = Tab.noObj;
            return;
        }

        designator.obj = new Obj(
                Obj.Var,
                "length",
                Tab.intType
        );
    }

    @Override
    public void visit(DesignatorEnum designator) {
    	String enumName = designator.getI1();
        String memberName = designator.getI2();

        Obj enumObj = Tab.find(enumName);
        if (enumObj == Tab.noObj) {
            report_error("Enum " + enumName + " nije deklarisan.", designator);
            designator.obj = Tab.noObj;
            return;
        }

        Obj member = Tab.noObj;
        for (Obj x : enumObj.getLocalSymbols()) {
            if (x.getName().equals(memberName)) {
                member = x;
                break;
            }
        }

        if (member == Tab.noObj) {
            report_error("Enum " + enumName + " nema clana " + memberName, designator);
            designator.obj = Tab.noObj;
            return;
        }

        designator.obj = member;
    }
    
    @Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Obj arrObj = Tab.find(designatorArrayName.getI1());
		if(arrObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivi niza: " + designatorArrayName.getI1(), designatorArrayName);
			designatorArrayName.obj = Tab.noObj;
		}
		else if(arrObj.getKind() != Obj.Var || arrObj.getType().getKind() != Struct.Array) {
			report_error("Neadekvatna promenljiva niza: " + designatorArrayName.getI1(), designatorArrayName);
			designatorArrayName.obj = Tab.noObj;
		}
		else {
			designatorArrayName.obj = arrObj;
		}
	}
    
    @Override
	public void visit(DesignatorArray designatorArray) {
		Obj arrObj = designatorArray.getDesignatorArrayName().obj;
		if(arrObj == Tab.noObj)
			designatorArray.obj = Tab.noObj;
		else if(!designatorArray.getExpr().struct.equals(Tab.intType)) {
			report_error("Indeksiranje sa ne int vrednosti. [Designator_elem]", designatorArray);
			designatorArray.obj = Tab.noObj;
		}
		else {
			designatorArray.obj = new Obj(Obj.Elem, arrObj.getName() + "[$]", arrObj.getType().getElemType());
		}
	}

    
    /* ================= Factor ================= */
    
    @Override
	public void visit(FactorOptionNumber factorOptionNumber) {
    	factorOptionNumber.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorOptionChar factorOptionChar) {
		factorOptionChar.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorOptionBoll factorOptionBoll) {
		factorOptionBoll.struct = boolType;
	}
	
	@Override
	public void visit(FactorOptionDesignator factorOptionDesignator) {
	    if(factorOptionDesignator.getDesignator() instanceof DesignatorLen) {
	        factorOptionDesignator.struct = Tab.intType;
	    } else {
	        factorOptionDesignator.struct = factorOptionDesignator.getDesignator().obj.getType();
	    }
	}
	
	@Override
	public void visit(FactorOptionFuncall factorOptionFuncall) {

	    Obj methodObj = factorOptionFuncall.getDesignator().obj;

	    if(methodObj.getKind() != Obj.Meth) {
	        report_error("Poziv neadekvatne metode: "
	                + methodObj.getName(), factorOptionFuncall);
	        factorOptionFuncall.struct = Tab.noType;
	        actParsTypes.clear();
	        return;
	    }

	    int formParsCnt = methodObj.getLevel();
	    int actParsCnt = actParsTypes.size();

	    if(formParsCnt != actParsCnt) {
	        report_error("Broj formalnih i stvarnih parametara nije isti.", factorOptionFuncall);
	    }
	    else {
	        int i = 0;
	        for(Obj formPar : methodObj.getLocalSymbols()) {

	            if(formPar.getFpPos() > 0) {

	                Struct actType = actParsTypes.get(i);

	                if(!actType.assignableTo(formPar.getType())) {
	                    report_error("Tip stvarnog parametra na poziciji "
	                            + (i+1) + " nije kompatibilan.", factorOptionFuncall);
	                }

	                i++;
	            }
	        }
	    }

	    factorOptionFuncall.struct = methodObj.getType();
	    actParsTypes.clear();
	}
	
	@Override
	public void visit(FactorOptionNewY factorOptionNew) {
		if(!factorOptionNew.getCondExpr().struct.equals(Tab.intType)) {
			report_error("Velicina niza nije int tipa.", factorOptionNew);
			factorOptionNew.struct = Tab.noType;
		}
		else
			factorOptionNew.struct = new Struct(Struct.Array, currentType);
			
	}
	
	@Override
	public void visit(FactorOptionCond factorOptionCond) {
		factorOptionCond.struct = factorOptionCond.getCondExpr().struct;
	}
	
	
	@Override
	public void visit(Factor factor) {
		if(factor.getUnaryMinus() instanceof UnaryMinusY) {
			if(factor.getFactorOption().struct.equals(Tab.intType))
				factor.struct = Tab.intType;
			else {
				report_error("Negacija ne int vrednosti", factor);
				factor.struct = Tab.noType;
			}
		}
		else
			factor.struct = factor.getFactorOption().struct;
	}
    
	@Override
	public void visit(Expr expr) {
		expr.struct = expr.getAddopTermList().struct;
	}
	
	@Override
	public void visit(MulopFactorListFactor mulopFactorListFactor) {
		mulopFactorListFactor.struct = mulopFactorListFactor.getFactor().struct;
	}
	
	@Override
	public void visit(MulopFactorListMul mulopFactorListMul) {
		Struct left = mulopFactorListMul.getMulopFactorList().struct;
		Struct right = mulopFactorListMul.getFactor().struct;
		if(left == null || right == null) {
		    mulopFactorListMul.struct = Tab.noType;
		    return;
		}
		if(left.equals(Tab.intType) && right.equals(Tab.intType))
			mulopFactorListMul.struct = Tab.intType;
		else {
			report_error("Mulop operacija ne int vrednosti.", mulopFactorListMul);
			mulopFactorListMul.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Term term) {
		term.struct = term.getMulopFactorList().struct;
	}
	
	@Override
	public void visit(AddopTermListTerm addopTermListTerm) {
		addopTermListTerm.struct = addopTermListTerm.getTerm().struct;
	}
	
	@Override
	public void visit(AddopTermListAdd addopTermListAdd) {
		Struct left = addopTermListAdd.getAddopTermList().struct;
		Struct right = addopTermListAdd.getTerm().struct;
		
		if(left == null || right == null) {
	        addopTermListAdd.struct = Tab.noType;
	        return;
	    }
		
		if(left.assignableTo(Tab.intType) && right.assignableTo(Tab.intType))
			addopTermListAdd.struct = Tab.intType;
		else {
			report_error("Addop operacija ne int vrednosti.", addopTermListAdd);
			addopTermListAdd.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(DesignatorSignatureAssign designatorSignatureAssign) {
		int kind = designatorSignatureAssign.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem && kind != Obj.Fld) 
			report_error("Dodela u neadekvatnu promenljivu: " + designatorSignatureAssign.getDesignator().obj.getName(), designatorSignatureAssign);
		else if(!designatorSignatureAssign.getCondExpr().struct.assignableTo(designatorSignatureAssign.getDesignator().obj.getType())) {
			report_error("Neadekvatna dodela vrednosti u promenljivu: " + designatorSignatureAssign.getDesignator().obj.getName(), designatorSignatureAssign);
		}
			
	}
	
	@Override
	public void visit(DesignatorSignatureInc designatorSignatureInc) {
		int kind = designatorSignatureInc.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem && kind != Obj.Fld) 
			report_error("Inkrement neadekvatne promenljive: " + designatorSignatureInc.getDesignator().obj.getName(), designatorSignatureInc);
		else if(!designatorSignatureInc.getDesignator().obj.getType().equals(Tab.intType))
			report_error("Inkrement ne int promenljive: " + designatorSignatureInc.getDesignator().obj.getName(), designatorSignatureInc);
	}
	
	@Override
	public void visit(DesignatorSignatureDec designatorSignatureDec) {
		int kind = designatorSignatureDec.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem && kind != Obj.Fld) 
			report_error("Dekrement neadekvatne promenljive: " + designatorSignatureDec.getDesignator().obj.getName(), designatorSignatureDec);
		else if(!designatorSignatureDec.getDesignator().obj.getType().equals(Tab.intType))
			report_error("Dekrement ne int promenljive: " + designatorSignatureDec.getDesignator().obj.getName(), designatorSignatureDec);
	}
	
	@Override
	public void visit(DesignatorSignatureAct designatorSignatureAct) {
		Obj methodObj = designatorSignatureAct.getDesignator().obj;

	    if(methodObj.getKind() != Obj.Meth) {
	        report_error("Poziv neadekvatne metode: " + methodObj.getName(), designatorSignatureAct);
	        actParsTypes.clear();
	        return;
	    }

	    int formParsCnt = methodObj.getLevel(); 
	    int actParsCnt = actParsTypes.size();

	    if(formParsCnt != actParsCnt) {
	        report_error("Broj formalnih i stvarnih parametara nije isti.", designatorSignatureAct);
	    }
	    else {
	        int i = 0;
	        for(Obj formPar : methodObj.getLocalSymbols()) {

	            if(formPar.getFpPos() > 0) { 

	                Struct actType = actParsTypes.get(i);

	                if(!actType.assignableTo(formPar.getType())) {
	                    report_error("Tip stvarnog parametra na poziciji "
	                            + (i+1) + " nije kompatibilan.", designatorSignatureAct);
	                }

	                i++;
	            }
	        }
	    }

	    actParsTypes.clear();
	}
	
	@Override
	public void visit(CondExprSimple condExprSimple) {
		condExprSimple.struct = condExprSimple.getCondFact().struct;
	}
	
	@Override
	public void visit(CondExprTri condExprTri) {

	    Struct condType = condExprTri.getCondFact().struct;
	    Struct trueType = condExprTri.getExpr().struct;
	    Struct falseType = condExprTri.getExpr1().struct;

	    if(!condType.equals(boolType)) {
	        report_error("Uslov ternarnog operatora mora biti bool.", condExprTri);
	        condExprTri.struct = Tab.noType;
	        return;
	    }

	    if(trueType.equals(falseType)) {
	    	condExprTri.struct = trueType;
	    }
	    else {
	        report_error("Tipovi u ternarnom operatoru se ne poklapaju.", condExprTri);
	        condExprTri.struct = Tab.noType;
	    }
	}
	
	@Override
	public void visit(CondFactExpr condFactExpr) {
		if(!condFactExpr.getExpr().struct.equals(boolType)) {
			report_error("Logicki operand nije tipa bool.", condFactExpr);
			condFactExpr.struct = Tab.noType;
		}
		else {
			condFactExpr.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondFactRel condFactRel) {
		Struct left = condFactRel.getExpr().struct;
		Struct right = condFactRel.getExpr1().struct;
		if(left == null || right == null) {
			condFactRel.struct = Tab.noType;
		    return;
		}
		if(left.compatibleWith(right)) {
			if(left.isRefType() || right.isRefType()) {
				if(condFactRel.getRelop() instanceof RelopEq || condFactRel.getRelop() instanceof RelopNeq)
					condFactRel.struct = boolType;
				else {
					report_error("Poredjenje ref tipova sa ne adekvatnim relacionim operatorom.", condFactRel);
					condFactRel.struct = Tab.noType;
				}
			}
			else
				condFactRel.struct = boolType;
		}
		else {
			report_error("Logicki operandi nisu kompatibilni.", condFactRel);
			condFactRel.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondFactListOptionalN condFactListOptionalN) {
		condFactListOptionalN.struct = condFactListOptionalN.getCondFact().struct;
	}
	
	@Override
	public void visit(CondFactListOptionalY condFactListOptionalY) {
		Struct left = condFactListOptionalY.getCondFactListOptional().struct;
		Struct right = condFactListOptionalY.getCondFact().struct;
		if(left == null || right == null) {
			condFactListOptionalY.struct = Tab.noType;
		    return;
		}
		if(left.equals(boolType) && right.equals(boolType))
			condFactListOptionalY.struct = boolType;
		else {
			report_error("And operacija ne bool vrednosti.", condFactListOptionalY);
			condFactListOptionalY.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		condTerm.struct = condTerm.getCondFactListOptional().struct;
	}
	
	@Override
	public void visit(CondTermListOptionalY condTermListOptionalY) {
		Struct left = condTermListOptionalY.getCondTermListOptional().struct;
		Struct right = condTermListOptionalY.getCondTerm().struct;
		if(left == null || right == null) {
			condTermListOptionalY.struct = Tab.noType;
		    return;
		}
		if(left.equals(boolType) && right.equals(boolType))
			condTermListOptionalY.struct = boolType;
		else {
			report_error("Or operacija ne bool vrednosti.", condTermListOptionalY);
			condTermListOptionalY.struct = Tab.noType;
		}
	}
	
	public void visit(CondTermListOptionalN condTermListOptionalN) {
		condTermListOptionalN.struct = condTermListOptionalN.getCondTerm().struct;
	}
	
	@Override
	public void visit(Condition condition) {
		condition.struct = condition.getCondTermListOptional().struct;
		if(condition.struct == null) {
	        condition.struct = Tab.noType;
	        return;
	    }
		if(!condition.struct.equals(boolType))
			report_error("Uslov nije tipa bool.", condition);
	}
	
	
	/* ================= Statements ================= */
	
	@Override
	public void visit(ExprOptionalY exprOptionalY) {
		if(exprOptionalY.getCondExpr().struct != currentMethod.getType()) {
			report_error("Vrednost koja se vraca se ne poklapa sa povratnom vrednoscu metode", exprOptionalY);
		}
		returnHappend = true;
	}
	
	@Override
	public void visit(ExprOptionalN exprOptionalN) {
		if(Tab.noType != currentMethod.getType()) {
			report_error("Vrednost koja se vraca se ne poklapa sa povratnom vrednoscu metode", exprOptionalN);
		}
		returnHappend = true;
	}
	
	@Override
	public void visit(StatementBreak statementBreak) {
		if(loopCnt == 0 && switchCnt == 0)
			report_error("Break naredba se ne nalazi unutar tela petlje.", statementBreak);
	}
	
	@Override
	public void visit(StatementContinue statementContinue) {
		if(loopCnt == 0)
			report_error("Continue naredba se ne nalazi unutar tela petlje.", statementContinue);
	}
	
	@Override
	public void visit(StatementRead statementRead) {
		int kind = statementRead.getDesignator().obj.getKind();
		Struct type = statementRead.getDesignator().obj.getType();
		if(kind != Obj.Var && kind != Obj.Elem && kind != Obj.Fld)
			report_error("Read operacija neadekvatne promenljive: " + statementRead.getDesignator().obj.getName(), statementRead);
		else if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType))
			report_error("Read operacija ne int/char/bool promenljive: " + statementRead.getDesignator().obj.getName(), statementRead);
	}
	
	@Override
	public void visit(StatementPrint statementPrint) {
		Struct type = statementPrint.getCondExpr().struct;
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType))
			report_error("Print operacija ne int/char/bool izraza", statementPrint);
	}
	
	@Override
	public void visit(Case caseNode) {

	    int value = caseNode.getN1();

	    if (switchCaseValues.contains(value)) {
	        report_error("Duplirana case vrednost: " + value, caseNode);
	    } else {
	        switchCaseValues.add(value);
	    }
	}
	
	@Override
	public void visit(StatementSwitch statementSwitch) {
	    switchCaseValues.clear();
	    switchCnt--;
	}
	
	@Override
	public void visit(ActPars actPars) {
	    actParsTypes.add(actPars.getCondExpr().struct);
	}
	
	@Override
	public void visit(ExprNextY exprNextY) {
	    actParsTypes.add(exprNextY.getCondExpr().struct);
	}
	
	@Override
	public void visit(StatementFor statementFor) {
	    loopCnt++;
	}

	@Override
	public void visit(Statement statement) {
	    if(statement.getParent() instanceof StatementFor) {
	        loopCnt--;
	    }
	}
	
	@Override
	public void visit(CaseListSingle caseListSingle) {
	    switchCnt++;
	}

}
	