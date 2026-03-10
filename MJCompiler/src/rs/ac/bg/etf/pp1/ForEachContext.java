package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared state for foreach loop counter variables.
 * SemanticPass fills foreachCounters in order; CodeGenerator reads them in order.
 */
public class ForEachContext {
    private static final List<Obj> foreachCounters = new ArrayList<>();

    public static void reset() {
        foreachCounters.clear();
    }

    public static void add(Obj counterObj) {
        foreachCounters.add(counterObj);
    }

    public static Obj get(int index) {
        return foreachCounters.get(index);
    }
}