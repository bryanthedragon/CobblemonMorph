package com.oracle.truffle.js.lang;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@GeneratedBy(JavaScriptLanguage.class)
@TruffleLanguage.Registration(
   characterMimeTypes = {"application/javascript", "text/javascript", "application/javascript+module"},
   contextPolicy = TruffleLanguage.ContextPolicy.SHARED,
   defaultMimeType = "application/javascript",
   dependentLanguages = "regex",
   id = "js",
   implementationName = "GraalVM JavaScript",
   name = "JavaScript",
   website = "https://www.graalvm.org/javascript/"
)
@ProvidedTags(
   {
         StandardTags.StatementTag.class,
         StandardTags.RootTag.class,
         StandardTags.RootBodyTag.class,
         StandardTags.ExpressionTag.class,
         StandardTags.CallTag.class,
         StandardTags.ReadVariableTag.class,
         StandardTags.WriteVariableTag.class,
         StandardTags.TryBlockTag.class,
         DebuggerTags.AlwaysHalt.class,
         JSTags.ObjectAllocationTag.class,
         JSTags.BinaryOperationTag.class,
         JSTags.UnaryOperationTag.class,
         JSTags.WriteVariableTag.class,
         JSTags.ReadElementTag.class,
         JSTags.WriteElementTag.class,
         JSTags.ReadPropertyTag.class,
         JSTags.WritePropertyTag.class,
         JSTags.ReadVariableTag.class,
         JSTags.LiteralTag.class,
         JSTags.FunctionCallTag.class,
         JSTags.BuiltinRootTag.class,
         JSTags.EvalCallTag.class,
         JSTags.ControlFlowRootTag.class,
         JSTags.ControlFlowBlockTag.class,
         JSTags.ControlFlowBranchTag.class,
         JSTags.DeclareTag.class,
         JSTags.InputNodeTag.class
   }
)
public final class JavaScriptLanguageProvider implements TruffleLanguage.Provider {
   @Override
   public String getLanguageClassName() {
      return "com.oracle.truffle.js.lang.JavaScriptLanguage";
   }

   @Override
   public TruffleLanguage<?> create() {
      return new JavaScriptLanguage();
   }

   @Override
   public List<TruffleFile.FileTypeDetector> createFileTypeDetectors() {
      return Arrays.asList(new JSFileTypeDetector());
   }

   @Override
   public Collection<String> getServicesClassNames() {
      return Collections.emptySet();
   }
}
