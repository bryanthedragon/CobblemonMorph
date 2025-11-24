
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.commonjs.CommonJSDirnameGetterBuiltinNodeGen;
import com.oracle.truffle.js.builtins.commonjs.CommonJSFilenameGetterBuiltinNodeGen;
import com.oracle.truffle.js.builtins.commonjs.CommonJSGlobalExportsGetterBuiltinNodeGen;
import com.oracle.truffle.js.builtins.commonjs.CommonJSGlobalModuleGetterBuiltinNodeGen;
import com.oracle.truffle.js.builtins.commonjs.CommonJSRequireBuiltinNodeGen;
import com.oracle.truffle.js.builtins.commonjs.CommonJSResolveBuiltinNodeGen;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;

public class GlobalCommonJSRequireBuiltins
extends JSBuiltinsContainer.SwitchEnum<GlobalRequire> {
    public GlobalCommonJSRequireBuiltins() {
        super(GlobalRequire.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GlobalRequire builtinEnum) {
        switch (builtinEnum) {
            case require: {
                return CommonJSRequireBuiltinNodeGen.create(context, builtin, GlobalCommonJSRequireBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
            }
            case dirnameGetter: {
                return CommonJSDirnameGetterBuiltinNodeGen.create(context, builtin, GlobalCommonJSRequireBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case filenameGetter: {
                return CommonJSFilenameGetterBuiltinNodeGen.create(context, builtin, GlobalCommonJSRequireBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case globalExportsGetter: {
                return CommonJSGlobalExportsGetterBuiltinNodeGen.create(context, builtin, GlobalCommonJSRequireBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case globalModuleGetter: {
                return CommonJSGlobalModuleGetterBuiltinNodeGen.create(context, builtin, GlobalCommonJSRequireBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case resolve: {
                return CommonJSResolveBuiltinNodeGen.create(context, builtin, GlobalCommonJSRequireBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum GlobalRequire implements BuiltinEnum<GlobalRequire>
    {
        require(1),
        dirnameGetter(0),
        filenameGetter(0),
        globalExportsGetter(0),
        globalModuleGetter(0),
        resolve(1);

        private final int length;

        private GlobalRequire(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

