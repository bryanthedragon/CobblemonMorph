
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GlobalScopeLookupNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;

public abstract class GlobalScopeLookupNode
extends JavaScriptBaseNode {
    final TruffleString varName;
    final boolean write;

    GlobalScopeLookupNode(TruffleString varName, boolean write) {
        this.varName = varName;
        this.write = write;
    }

    public static GlobalScopeLookupNode create(TruffleString varName, boolean write) {
        return GlobalScopeLookupNodeGen.create(varName, write);
    }

    public abstract boolean execute(Object var1);

    @Specialization(assumptions={"assumption"})
    static boolean doAbsent(JSDynamicObject scope, @Cached(value="getAbsentPropertyAssumption(scope.getShape())") Assumption assumption) {
        return false;
    }

    @Specialization(guards={"scope.getShape() == cachedShape"}, assumptions={"cachedShape.getValidAssumption()"}, limit="cacheLimit", replaces={"doAbsent"})
    final boolean doCached(JSDynamicObject scope, @Cached(value="scope.getShape()") Shape cachedShape, @Cached(value="cachedShape.hasProperty(varName)") boolean exists, @Cached(value="isDead(cachedShape)") boolean dead, @Cached(value="isConstAssignment(cachedShape)") boolean constAssignment, @Cached(value="getPropertyCacheLimit()") int cacheLimit) {
        assert (!exists || dead == (JSDynamicObject.getOrNull(scope, this.varName) == Dead.instance()));
        if (dead) {
            throw Errors.createReferenceErrorNotDefined(this.getLanguage().getJSContext(), this.varName, this);
        }
        if (constAssignment) {
            throw Errors.createTypeErrorConstReassignment(this.varName, scope, this);
        }
        return exists;
    }

    protected int getPropertyCacheLimit() {
        return this.getLanguage().getJSContext().getPropertyCacheLimit();
    }

    @Specialization(replaces={"doCached"})
    final boolean doUncached(JSDynamicObject scope, @Cached(value="create()") BranchProfile errorBranch) {
        Property property = scope.getShape().getProperty(this.varName);
        if (property != null) {
            if (JSDynamicObject.getOrNull(scope, this.varName) == Dead.instance()) {
                errorBranch.enter();
                throw Errors.createReferenceErrorNotDefined(this.getLanguage().getJSContext(), this.varName, this);
            }
            if (this.write && JSProperty.isConst(property)) {
                errorBranch.enter();
                throw Errors.createTypeErrorConstReassignment(this.varName, scope, this);
            }
            return true;
        }
        return false;
    }

    final boolean isDead(Shape shape) {
        Property property = shape.getProperty(this.varName);
        assert (property == null || !property.getLocation().isConstant() || property.getLocation().getConstantValue() == Dead.instance());
        return property != null && property.getLocation().isConstant();
    }

    final boolean isConstAssignment(Shape shape) {
        if (this.write) {
            Property property = shape.getProperty(this.varName);
            return property != null && JSProperty.isConst(property);
        }
        return false;
    }

    final Assumption getAbsentPropertyAssumption(Shape shape) {
        Property property = shape.getProperty(this.varName);
        if (property == null) {
            return JSShape.getPropertyAssumption(shape, this.varName);
        }
        return Assumption.NEVER_VALID;
    }
}

