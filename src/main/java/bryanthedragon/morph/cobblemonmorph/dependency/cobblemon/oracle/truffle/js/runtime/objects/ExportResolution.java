package com.oracle.truffle.js.runtime.objects;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Objects;

public abstract class ExportResolution {
   private static final ExportResolution NULL = new ExportResolution.Null();
   private static final ExportResolution AMBIGUOUS = new ExportResolution.Ambiguous();

   private ExportResolution() {
   }

   public boolean isNull() {
      return false;
   }

   public boolean isAmbiguous() {
      return false;
   }

   public boolean isNamespace() {
      return false;
   }

   @CompilerDirectives.TruffleBoundary
   public JSModuleRecord getModule() {
      throw new UnsupportedOperationException();
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleString getBindingName() {
      throw new UnsupportedOperationException();
   }

   public static ExportResolution resolved(JSModuleRecord module, TruffleString bindingName) {
      return new ExportResolution.Resolved(module, bindingName);
   }

   public static ExportResolution notFound() {
      return NULL;
   }

   public static ExportResolution ambiguous() {
      return AMBIGUOUS;
   }

   private static final class Ambiguous extends ExportResolution {
      @Override
      public boolean isAmbiguous() {
         return true;
      }
   }

   private static final class Null extends ExportResolution {
      @Override
      public boolean isNull() {
         return true;
      }
   }

   public static final class Resolved extends ExportResolution {
      private final JSModuleRecord module;
      private final TruffleString bindingName;

      Resolved(JSModuleRecord module, TruffleString bindingName) {
         this.module = module;
         this.bindingName = bindingName;

         assert bindingName == Module.NAMESPACE_EXPORT_BINDING_NAME || !bindingName.equals(Module.NAMESPACE_EXPORT_BINDING_NAME);
      }

      @Override
      public JSModuleRecord getModule() {
         return this.module;
      }

      @Override
      public TruffleString getBindingName() {
         return this.bindingName;
      }

      @Override
      public boolean isNamespace() {
         return this.bindingName == Module.NAMESPACE_EXPORT_BINDING_NAME;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + (this.bindingName == null ? 0 : this.bindingName.hashCode());
         return 31 * result + (this.module == null ? 0 : this.module.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ExportResolution.Resolved other = (ExportResolution.Resolved)obj;
            return Objects.equals(this.module, other.module) && Objects.equals(this.bindingName, other.bindingName);
         }
      }
   }
}
