package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
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
    
    private Map<String, Integer>       labelAddrs   = new HashMap<>();
    private Map<String, List<Integer>> gotoPatches  = new HashMap<>();

    CodeGenerator() { initializePredeclaredMethods(); }

    // ===================== METHOD =====================

    @Override
    public void visit(TypeOptionY t) {
        t.obj.setAdr(Code.pc);
        if (t.getMethodName().equalsIgnoreCase("main")) mainPc = Code.pc;
        Code.put(Code.enter);
        Code.put(t.obj.getLevel());
        Code.put(t.obj.getLocalSymbols().size());
        labelAddrs.clear();
        gotoPatches.clear();
    }

    @Override
    public void visit(TypeOptionN t) {
        t.obj.setAdr(Code.pc);
        if (t.getMethodName().equalsIgnoreCase("main")) mainPc = Code.pc;
        Code.put(Code.enter);
        Code.put(t.obj.getLevel());
        Code.put(t.obj.getLocalSymbols().size());
        labelAddrs.clear();
        gotoPatches.clear();
    }

    @Override
    public void visit(MethodDec m) {
        Code.put(Code.exit);
        Code.put(Code.return_);
        labelAddrs.clear();
        gotoPatches.clear();
    }

    // ===================== DESIGNATOR STATEMENTS =====================

    @Override
    public void visit(DesignatorSignatureAssign d) { Code.store(d.getDesignator().obj); }

    @Override
    public void visit(DesignatorSignatureAct d) {
        Obj o = d.getDesignator().obj;
        Code.put(Code.call); Code.put2(o.getAdr() - Code.pc + 1);
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
        Code.put(Code.call); Code.put2(f.getDesignator().obj.getAdr() - Code.pc + 1);
    }

    // ===================== DESIGNATORS =====================

    @Override public void visit(DesignatorLen d)      { Code.load(d.obj); }
    @Override public void visit(DesignatorEnum d)      { Code.load(d.obj); }
    @Override public void visit(DesignatorArrayName d)  { Code.load(d.obj); }

    // ===================== CONDITIONS / IF-ELSE =====================

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
        for (int addr : forContinues.pop()) {
            int saved = Code.pc;
            Code.pc = addr;
            Code.put2(updateTarget - addr + 1);
            Code.pc = saved;
        }

        forCondStartAddr.pop();
        breakTarget.pop();
    }

    // ===================== SWITCH =====================

    private Stack<List<Integer>> switchBreaks    = new Stack<>();
    private Stack<Integer>       defaultEndJmp   = new Stack<>();
    // Per-switch stacks: each switch pushes its own working stack
    private Stack<Stack<Integer>> caseSkipAddrStack    = new Stack<>();
    private Stack<Stack<Integer>> caseFallThroughStack = new Stack<>();

    // Convenience accessors for the current (innermost) switch
    private Stack<Integer> caseSkipAddr()    { return caseSkipAddrStack.peek(); }
    private Stack<Integer> caseFallThrough() { return caseFallThroughStack.peek(); }

    @Override
    public void visit(SwitchStart s) {
        switchBreaks.push(new ArrayList<>());
        caseSkipAddrStack.push(new Stack<>());
        caseFallThroughStack.push(new Stack<>());
        breakTarget.push('S');
    }

    @Override
    public void visit(CaseLabel c) {
        Case caseNode = (Case) c.getParent();
        Code.put(Code.dup);
        Code.loadConst(caseNode.getCaseValue());
        Code.putFalseJump(Code.eq, 0);
        caseSkipAddr().push(Code.pc - 2);
        if (!caseFallThrough().empty()) {
            Code.fixup(caseFallThrough().pop());
        }
    }

    @Override
    public void visit(Case c) {
        Code.putJump(0);
        caseFallThrough().push(Code.pc - 2);
        if (!caseSkipAddr().empty()) Code.fixup(caseSkipAddr().pop());
    }

    @Override
    public void visit(DefaultLabel d) {
        if (!caseFallThrough().empty()) Code.fixup(caseFallThrough().pop());
        if (!caseSkipAddr().empty())    Code.fixup(caseSkipAddr().pop());
    }

    @Override
    public void visit(DefaultOptionalY d) {
        Code.putJump(0);
        defaultEndJmp.push(Code.pc - 2);
    }

    @Override
    public void visit(DefaultOptionalN d) {
        // Fixup ALL remaining fall-throughs and skips → afterSwitch (pop instruction)
        Stack<Integer> ft = caseFallThrough();
        Stack<Integer> sk = caseSkipAddr();
        while (!ft.empty()) Code.fixup(ft.pop());
        while (!sk.empty()) Code.fixup(sk.pop());
    }

    @Override
    public void visit(StatementSwitch s) {
        if (!defaultEndJmp.empty()) Code.fixup(defaultEndJmp.pop());
        // Fixup all break jumps → here (pop instruction)
        for (int addr : switchBreaks.pop()) Code.fixup(addr);
        // Pop the switchVal from the stack
        Code.put(Code.pop);
        // Discard per-switch working stacks (should be empty now, but clean up anyway)
        caseSkipAddrStack.pop();
        caseFallThroughStack.pop();
        breakTarget.pop();
    }
    
    private Stack<Integer> whileCond = new Stack<>();
    private Stack<Integer> doCond    = new Stack<>();
    private Stack<Integer> doCondCheck = new Stack<>(); // start of condition bytecode for continue

    private Stack<List<Integer>> whileBreaks    = new Stack<>();
    private Stack<List<Integer>> whileContinues = new Stack<>();
    private Stack<List<Integer>> doBreaks       = new Stack<>();
    private Stack<List<Integer>> doContinues    = new Stack<>();

    // ===================== WHILE =====================

    @Override
    public void visit(WhileStart w) {
        whileBreaks.push(new ArrayList<>());
        whileContinues.push(new ArrayList<>());
        breakTarget.push('W');
        whileCond.push(Code.pc);  // condStart
    }

    @Override
    public void visit(StatementWhile s) {
        int condStart = whileCond.pop();
        Code.putJump(condStart);          // jmp back to condition
        Code.fixup(skipThen.pop());       // false-jump → afterLoop (here)

        // continues → condStart
        for (int addr : whileContinues.pop()) {
            int saved = Code.pc; Code.pc = addr;
            Code.put2(condStart - addr + 1);
            Code.pc = saved;
        }
        // breaks → afterLoop (here)
        for (int addr : whileBreaks.pop()) Code.fixup(addr);

        breakTarget.pop();
    }

    // ===================== DO-WHILE =====================

    @Override
    public void visit(DoStart d) {
        doBreaks.push(new ArrayList<>());
        doContinues.push(new ArrayList<>());
        breakTarget.push('D');
        doCond.push(Code.pc);  // bodyStart
    }

    @Override
    public void visit(DoCondStart d) {
        // body is done; condition bytecode starts here — this is the continue target
        doCondCheck.push(Code.pc);
    }

    @Override
    public void visit(DoEnd d) {
        int bodyStart   = doCond.pop();
        int condStart   = doCondCheck.pop();

        Code.putJump(bodyStart);      // true: loop back to body start
        Code.fixup(skipThen.pop());   // false: exit loop (afterLoop = here)

        // continues → condStart (re-evaluate condition)
        for (int addr : doContinues.pop()) {
            int saved = Code.pc; Code.pc = addr;
            Code.put2(condStart - addr + 1);
            Code.pc = saved;
        }
        // breaks → afterLoop
        for (int addr : doBreaks.pop()) Code.fixup(addr);

        breakTarget.pop();
    }

    // ===================== FOREACH LOOP =====================
    private Stack<Obj>          foreachCounterObj  = new Stack<>();
    private Stack<Integer>      foreachLoopStart   = new Stack<>();
    private Stack<Integer>      foreachAfterPatch  = new Stack<>();
    private Stack<List<Integer>> foreachBreaks     = new Stack<>();
    private Stack<List<Integer>> foreachContinues  = new Stack<>();

    @Override
    public void visit(ForeachArrayStart f) {
        Obj counterObj = f.obj;
        foreachCounterObj.push(counterObj);

        Code.loadConst(0);
        Code.store(counterObj);

        foreachBreaks.push(new ArrayList<>());
        foreachContinues.push(new ArrayList<>());
        breakTarget.push('E'); 

        foreachLoopStart.push(Code.pc);
    }

    @Override
    public void visit(ForeachBodyStart f) {
        StatementForEach parent = (StatementForEach) f.getParent();
        Obj d1 = parent.getDesignator().obj;
        Obj d2 = parent.getDesignator1().obj;
        Obj counter = foreachCounterObj.peek();

        Code.load(counter);             
        Code.load(d2);                  
        Code.put(Code.arraylength);     
        Code.putFalseJump(Code.lt, 0);
        foreachAfterPatch.push(Code.pc - 2);

        Code.load(d2);                 
        Code.load(counter);            
        if (d2.getType().getElemType().getKind() == Struct.Char) {
            Code.put(Code.baload);
        } else {
            Code.put(Code.aload);
        }
        Code.store(d1);
    }

    @Override
    public void visit(StatementForEach s) {
        Obj counter = foreachCounterObj.pop();
        int loopStart = foreachLoopStart.pop();

        // continueTarget: increment counter and jump back
        int contTarget = Code.pc;
        Code.load(counter);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(counter);
        Code.putJump(loopStart);

        // afterLoop: fix condition false-jump
        Code.fixup(foreachAfterPatch.pop());

        // Fix continue jumps → continueTarget
        for (int addr : foreachContinues.pop()) {
            // patch to jump to contTarget
            int saved = Code.pc;
            Code.pc = addr;
            Code.put2(contTarget - addr + 1);
            Code.pc = saved;
        }

        // Fix break jumps → here (afterLoop)
        for (int addr : foreachBreaks.pop()) Code.fixup(addr);

        breakTarget.pop();
    }

    @Override
    public void visit(StatementBreak b) {
        Code.putJump(0);
        int patchAddr = Code.pc - 2;
        if (!breakTarget.empty()) {
            char top = breakTarget.peek();
            if (top == 'S') {
                switchBreaks.peek().add(patchAddr);
            } else if (top == 'F') {
                forBreaks.peek().add(patchAddr);
            } else if (top == 'E') {
                foreachBreaks.peek().add(patchAddr);
            } else if (top == 'W') {
                whileBreaks.peek().add(patchAddr);
            } else if (top == 'D') {
                doBreaks.peek().add(patchAddr);
            }
        }
    }

    @Override
    public void visit(StatementContinue c) {
        // For each switch layer we cross, we must pop its switchVal off the stack
        int switchLayers = 0;
        for (int i = breakTarget.size() - 1; i >= 0; i--) {
            char t = breakTarget.get(i);
            if (t == 'S') {
                switchLayers++;
            } else {
                // Found the innermost loop — emit pops for every switch layer crossed
                for (int s = 0; s < switchLayers; s++) Code.put(Code.pop);
                Code.putJump(0);
                int patchAddr = Code.pc - 2;
                if (t == 'F') {
                    forContinues.peek().add(patchAddr);
                } else if (t == 'E') {
                    foreachContinues.peek().add(patchAddr);
                } else if (t == 'W') {
                    whileContinues.peek().add(patchAddr);
                } else if (t == 'D') {
                    doContinues.peek().add(patchAddr);
                }
                return;
            }
        }
    }
    
    @Override
    public void visit(LabelDecl ld) {
        StatementLabel parent = (StatementLabel) ld.getParent();
        String name = parent.getLabelName();
        int targetPc = Code.pc;
        labelAddrs.put(name, targetPc);
        // Fix up any forward gotos that jumped to this label
        List<Integer> patches = gotoPatches.remove(name);
        if (patches != null) {
            for (int addr : patches) {
                int saved = Code.pc;
                Code.pc = addr;
                Code.put2(targetPc - addr + 1);
                Code.pc = saved;
            }
        }
    }

    @Override
    public void visit(StatementGoto sg) {
        String name = sg.getLabelName();
        if (labelAddrs.containsKey(name)) {
            // Backward goto: target already known
            Code.putJump(labelAddrs.get(name));
        } else {
            // Forward goto: emit placeholder jmp(0), save for patching
            Code.putJump(0);
            int patchAddr = Code.pc - 2;
            gotoPatches.computeIfAbsent(name, k -> new ArrayList<>()).add(patchAddr);
        }
    }
    
}