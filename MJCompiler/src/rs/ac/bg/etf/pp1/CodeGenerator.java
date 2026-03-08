package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPc;
    public int getMainPc() { return mainPc; }

    // breakTarget tracks which construct is innermost for break:
    // 'F' = for loop, 'S' = switch
    private Stack<Character> breakTarget = new Stack<>();

    private void initializePredeclaredMethods() {
        Obj ordMethod = Tab.find("ord");
        Obj chrMethod = Tab.find("chr");
        ordMethod.setAdr(Code.pc);
        chrMethod.setAdr(Code.pc);
        Code.put(Code.enter); Code.put(1); Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit); Code.put(Code.return_);

        Obj lenMethod = Tab.find("len");
        lenMethod.setAdr(Code.pc);
        Code.put(Code.enter); Code.put(1); Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit); Code.put(Code.return_);
    }

    CodeGenerator() { initializePredeclaredMethods(); }

    // ===================== METHOD =====================

    @Override
    public void visit(TypeOptionY t) {
        t.obj.setAdr(Code.pc);
        if (t.getMethodName().equalsIgnoreCase("main")) mainPc = Code.pc;
        Code.put(Code.enter);
        Code.put(t.obj.getLevel());
        Code.put(t.obj.getLocalSymbols().size());
    }

    @Override
    public void visit(TypeOptionN t) {
        t.obj.setAdr(Code.pc);
        if (t.getMethodName().equalsIgnoreCase("main")) mainPc = Code.pc;
        Code.put(Code.enter);
        Code.put(t.obj.getLevel());
        Code.put(t.obj.getLocalSymbols().size());
    }

    @Override
    public void visit(MethodDec m) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    // ===================== DESIGNATOR STATEMENTS =====================

    @Override
    public void visit(DesignatorSignatureAssign d) { Code.store(d.getDesignator().obj); }

    @Override
    public void visit(DesignatorSignatureAct d) {
        Obj o = d.getDesignator().obj;
        Code.put(Code.call); Code.put2(o.getAdr() - Code.pc);
        if (o.getType() != Tab.noType) Code.put(Code.pop);
    }

    @Override
    public void visit(DesignatorSignatureInc d) {
        if (d.getDesignator().obj.getKind() == Obj.Elem) Code.put(Code.dup2);
        Code.load(d.getDesignator().obj);
        Code.loadConst(1); Code.put(Code.add);
        Code.store(d.getDesignator().obj);
    }

    @Override
    public void visit(DesignatorSignatureDec d) {
        if (d.getDesignator().obj.getKind() == Obj.Elem) Code.put(Code.dup2);
        Code.load(d.getDesignator().obj);
        Code.loadConst(1); Code.put(Code.sub);
        Code.store(d.getDesignator().obj);
    }

    // ===================== PRINT / READ / RETURN =====================

    @Override
    public void visit(StatementPrint s) {
        int w = (s.getPrintOptional() instanceof PrintOptionalY) ? ((PrintOptionalY) s.getPrintOptional()).getN1() : 0;
        Code.loadConst(w);
        if (s.getCondExpr().struct.equals(Tab.charType)) Code.put(Code.bprint);
        else Code.put(Code.print);
    }

    @Override public void visit(ExprOptionalY e) { Code.put(Code.exit); Code.put(Code.return_); }
    @Override public void visit(ExprOptionalN e) { Code.put(Code.exit); Code.put(Code.return_); }

    @Override
    public void visit(StatementRead s) {
        if (s.getDesignator().obj.getType().equals(Tab.charType)) Code.put(Code.bread);
        else Code.put(Code.read);
        Code.store(s.getDesignator().obj);
    }

    // ===================== ARITHMETIC =====================

    @Override
    public void visit(AddopTermListAdd a) {
        Code.put(a.getAddop() instanceof AddopPlus ? Code.add : Code.sub);
    }

    @Override
    public void visit(MulopFactorListMul m) {
        if (m.getMulop() instanceof MulopMul) Code.put(Code.mul);
        else if (m.getMulop() instanceof MulopDiv) Code.put(Code.div);
        else Code.put(Code.rem);
    }

    @Override
    public void visit(Factor f) {
        if (f.getUnaryMinus() instanceof UnaryMinusY) Code.put(Code.neg);
    }

    // ===================== FACTORS =====================

    @Override public void visit(FactorOptionNumber f) { Code.loadConst(f.getN1()); }
    @Override public void visit(FactorOptionChar f)   { Code.loadConst(f.getC1()); }
    @Override public void visit(FactorOptionBoll f)   { Code.loadConst(f.getB1()); }

    @Override
    public void visit(FactorOptionDesignator f) {
        if (f.getDesignator() instanceof DesignatorLen) {
            Code.put(Code.arraylength);
        } else {
            Obj obj = f.getDesignator().obj;
            if (obj.getKind() == Obj.Con) Code.loadConst(obj.getAdr());
            else Code.load(obj);
        }
    }

    @Override
    public void visit(FactorOptionNewY f) {
        Code.put(Code.newarray);
        Code.put(f.getType().struct.equals(Tab.charType) ? 0 : 1);
    }

    @Override
    public void visit(FactorOptionFuncall f) {
        Code.put(Code.call); Code.put2(f.getDesignator().obj.getAdr() - Code.pc);
    }

    // ===================== DESIGNATORS =====================

    @Override public void visit(DesignatorLen d)      { Code.load(Tab.find(d.getI1())); }
    @Override public void visit(DesignatorEnum d)      { Code.load(d.obj); }
    @Override public void visit(DesignatorArrayName d)  { Code.load(d.obj); }

    // ===================== CONDITIONS / IF-ELSE =====================
    // AND-chain: each false CondFact jumps to the next OR-term (skipCondFact)
    // OR-chain:  each true CondTerm jumps to the then-branch (skipCondition)
    // Condition: overall false path goes to skipThen

    private Stack<Integer> skipCondFact  = new Stack<>();
    private Stack<Integer> skipCondition = new Stack<>();
    private Stack<Integer> skipThen      = new Stack<>();
    private Stack<Integer> skipElse      = new Stack<>();

    private int relOp(Relop r) {
        if (r instanceof RelopEq)  return Code.eq;
        if (r instanceof RelopNeq) return Code.ne;
        if (r instanceof RelopLt)  return Code.lt;
        if (r instanceof RelopLte) return Code.le;
        if (r instanceof RelopGt)  return Code.gt;
        if (r instanceof RelopGte) return Code.ge;
        return 0;
    }

    // CondFact is in "condition context" (if/for) when parent is CondFactListOptional.
    // In CondExprSimple/CondExprTri the parent is CondExpr directly.
    private boolean isConditionContext(SyntaxNode n) {
        SyntaxNode p = n.getParent();
        return (p instanceof CondFactListOptionalY || p instanceof CondFactListOptionalN);
    }

    @Override
    public void visit(CondFactExpr c) {
        if (!isConditionContext(c)) return; // expression ctx: value already on stack
        // bool value on stack; jump to false if == 0
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        skipCondFact.push(Code.pc - 2);
    }

    @Override
    public void visit(CondFactRel c) {
        if (!isConditionContext(c)) {
            // Expression context: materialise comparison as 0/1
            Code.putFalseJump(relOp(c.getRelop()), 0);
            int fa = Code.pc - 2;
            Code.loadConst(1);
            Code.putJump(0);
            int ea = Code.pc - 2;
            Code.fixup(fa);
            Code.loadConst(0);
            Code.fixup(ea);
            return;
        }
        Code.putFalseJump(relOp(c.getRelop()), 0);
        skipCondFact.push(Code.pc - 2);
    }

    @Override
    public void visit(CondTerm c) {
        // True path: jump over the false-fixups to the then-branch
        Code.putJump(0);
        skipCondition.push(Code.pc - 2);
        // False path: fix all false-jumps within this AND-chain to here (next OR-term)
        while (!skipCondFact.empty()) Code.fixup(skipCondFact.pop());
    }

    @Override
    public void visit(Condition c) {
        // All OR terms exhausted and none was true → false path
        Code.putJump(0);
        skipThen.push(Code.pc - 2);
        // Fix all true-jumps from OR-terms to here (start of then-branch)
        while (!skipCondition.empty()) Code.fixup(skipCondition.pop());
    }

    @Override
    public void visit(ElseOptionalN e) {
        Code.fixup(skipThen.pop());
    }

    @Override
    public void visit(Else e) {
        Code.putJump(0);
        skipElse.push(Code.pc - 2);
        Code.fixup(skipThen.pop());
    }

    @Override
    public void visit(ElseOptionalY e) {
        Code.fixup(skipElse.pop());
    }

    // ===================== TERNARY =====================
    // Grammar: CondFact TernaryCondEnd ? Expr TernaryTrueEnd : Expr
    // Bottom-up: CondFact → TernaryCondEnd → Expr(true) → TernaryTrueEnd → Expr(false) → CondExprTri
    //
    // CondFact parent = CondExprTri → isConditionContext=false
    //   CondFactExpr: value already on stack (no jump emitted)
    //   CondFactRel: materialised as 0/1 on stack

    private Stack<Integer> ternaryFalse = new Stack<>();
    private Stack<Integer> ternaryEnd   = new Stack<>();

    @Override
    public void visit(TernaryCondEnd t) {
        // 0/1 on stack; if 0 (false) jump to false branch
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        ternaryFalse.push(Code.pc - 2);
    }

    @Override
    public void visit(TernaryTrueEnd t) {
        // True branch value on stack; jump over false branch
        Code.putJump(0);
        ternaryEnd.push(Code.pc - 2);
        // False branch starts here
        Code.fixup(ternaryFalse.pop());
    }

    @Override
    public void visit(CondExprTri t) {
        // False branch value on stack; fix end-jump
        Code.fixup(ternaryEnd.pop());
    }

    // ===================== FOR LOOP =====================
    // Grammar:
    //   FOR ( ForInit DSOpt ; ForCondStart CondOpt ; ForUpdateStart DSOpt ForBodyStart ) Statement
    //
    // Bottom-up:
    //   ForInit → init → ForCondStart → cond → ForUpdateStart → update → ForBodyStart → body → StatementFor
    //
    // Bytecode layout:
    //   [init]
    //   condStart: [cond jfalse→afterLoop]
    //   jmpSkip → bodyStart
    //   updateTarget: [update]
    //   jmpBack → condStart
    //   bodyStart: [body]
    //   jmp → updateTarget
    //   afterLoop:

    private Stack<Integer> forCondStartAddr   = new Stack<>();
    private Stack<Integer> forCondFalseAddr   = new Stack<>();
    private Stack<Integer> forSkipUpdatePatch = new Stack<>();
    private Stack<Integer> forUpdateTargetAddr = new Stack<>();
    private Stack<List<Integer>> forBreaks    = new Stack<>();
    private Stack<List<Integer>> forContinues = new Stack<>();

    @Override
    public void visit(ForInit f) {
        forBreaks.push(new ArrayList<>());
        forContinues.push(new ArrayList<>());
        breakTarget.push('F');
    }

    @Override
    public void visit(ForCondStart f) {
        forCondStartAddr.push(Code.pc);
    }

    @Override
    public void visit(ForUpdateStart f) {
        // Capture condition false-jump (if condition existed)
        int condFalse = -1;
        if (!skipThen.empty()) condFalse = skipThen.pop();
        forCondFalseAddr.push(condFalse);

        // Emit jmp(0) to skip update on first iteration; patched in ForBodyStart
        Code.putJump(0);
        forSkipUpdatePatch.push(Code.pc - 2);

        // Update bytecode starts here
        forUpdateTargetAddr.push(Code.pc);
    }

    @Override
    public void visit(ForBodyStart f) {
        // Update just ended; emit jmp back to condStart
        Code.putJump(forCondStartAddr.peek());
        // Patch skipUpdate to jump here (body start)
        Code.fixup(forSkipUpdatePatch.pop());
    }

    @Override
    public void visit(StatementFor s) {
        int updateTarget = forUpdateTargetAddr.pop();
        // Body just ended; jump to update
        Code.putJump(updateTarget);

        // afterLoop: fixup condFalse
        int condFalse = forCondFalseAddr.pop();
        if (condFalse != -1) Code.fixup(condFalse);

        // fixup breaks → here (afterLoop)
        for (int addr : forBreaks.pop()) Code.fixup(addr);

        // fixup continues → updateTarget
        for (int addr : forContinues.pop()) Code.fixup(addr);

        forCondStartAddr.pop();
        breakTarget.pop();
    }

    // ===================== BREAK / CONTINUE =====================

    @Override
    public void visit(StatementBreak b) {
        Code.putJump(0);
        int patchAddr = Code.pc - 2;
        // Find innermost break target
        if (!breakTarget.empty() && breakTarget.peek() == 'S') {
            switchBreaks.peek().add(patchAddr);
        } else if (!breakTarget.empty() && breakTarget.peek() == 'F') {
            forBreaks.peek().add(patchAddr);
        }
    }

    @Override
    public void visit(StatementContinue c) {
        // Continue always targets innermost for loop
        if (!forContinues.empty()) {
            // If inside a switch, pop the switch value first
            if (!breakTarget.empty() && breakTarget.peek() == 'S') {
                Code.put(Code.pop);
            }
            Code.putJump(0);
            forContinues.peek().add(Code.pc - 2);
        }
    }

    // ===================== SWITCH =====================
    // Grammar: SWITCH ( SwitchStart CondExpr ) { CaseList }
    //          Case: CASE NUMBER CaseLabel : StatementList
    //
    // Bottom-up per case: CaseLabel → StatementList → Case
    //
    // switchVal stays on the stack the entire switch. Each case:
    //   CaseLabel: dup; loadConst(val); putFalseJump(eq, skipAddr)
    //              if previous case fell through, fixup its fallThrough jmp to HERE
    //   Case:      emit fallThrough jmp(0); fixup skipAddr
    //
    // putFalseJump(eq, skipAddr) consumes the dup'd copy and the const.
    // Original switchVal remains on stack.
    // At end of switch: pop switchVal.

    private Stack<List<Integer>> switchBreaks = new Stack<>();
    private Stack<Integer> caseSkipAddr    = new Stack<>();
    private Stack<Integer> caseFallThrough = new Stack<>();

    @Override
    public void visit(SwitchStart s) {
        switchBreaks.push(new ArrayList<>());
        breakTarget.push('S');
    }

    @Override
    public void visit(CaseLabel c) {
        Case caseNode = (Case) c.getParent();
        // Emit comparison: dup switchVal, load case value, compare
        Code.put(Code.dup);
        Code.loadConst(caseNode.getCaseValue());
        // Jump if NOT equal (skip this case body)
        Code.putFalseJump(Code.eq, 0);
        caseSkipAddr.push(Code.pc - 2);
        // If previous case fell through, fixup that jmp to HERE (past this comparison, into body)
        if (!caseFallThrough.empty()) {
            Code.fixup(caseFallThrough.pop());
        }
    }

    @Override
    public void visit(Case c) {
        // Emit fall-through jmp: if no break, this jumps past next CaseLabel's comparison
        Code.putJump(0);
        caseFallThrough.push(Code.pc - 2);
        // Fixup this case's skip to HERE (next case's CaseLabel or end of switch)
        if (!caseSkipAddr.empty()) Code.fixup(caseSkipAddr.pop());
    }

    @Override
    public void visit(StatementSwitch s) {
        // Fixup last case's fall-through (lands here)
        if (!caseFallThrough.empty()) Code.fixup(caseFallThrough.pop());
        // Fixup all break jumps → here (they land on the pop instruction)
        for (int addr : switchBreaks.pop()) Code.fixup(addr);
        // Pop the switchVal from the stack
        Code.put(Code.pop);
        breakTarget.pop();
    }
}