
package com.oracle.truffle.api.impl.asm.tree.analysis;

import com.oracle.truffle.api.impl.asm.tree.JumpInsnNode;
import com.oracle.truffle.api.impl.asm.tree.LabelNode;
import java.util.ArrayList;
import java.util.List;

final class Subroutine {
    final LabelNode start;
    final boolean[] localsUsed;
    final List<JumpInsnNode> callers;

    Subroutine(LabelNode start2, int maxLocals, JumpInsnNode caller) {
        this.start = start2;
        this.localsUsed = new boolean[maxLocals];
        this.callers = new ArrayList<JumpInsnNode>();
        this.callers.add(caller);
    }

    Subroutine(Subroutine subroutine) {
        this.start = subroutine.start;
        this.localsUsed = (boolean[])subroutine.localsUsed.clone();
        this.callers = new ArrayList<JumpInsnNode>(subroutine.callers);
    }

    public boolean merge(Subroutine subroutine) {
        int i;
        boolean changed = false;
        for (i = 0; i < this.localsUsed.length; ++i) {
            if (!subroutine.localsUsed[i] || this.localsUsed[i]) continue;
            this.localsUsed[i] = true;
            changed = true;
        }
        if (subroutine.start == this.start) {
            for (i = 0; i < subroutine.callers.size(); ++i) {
                JumpInsnNode caller = subroutine.callers.get(i);
                if (this.callers.contains(caller)) continue;
                this.callers.add(caller);
                changed = true;
            }
        }
        return changed;
    }
}

