package org.graalvm.nativeimage.c;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Platforms(Platform.HOSTED_ONLY.class)
public @interface CContext {
   Class<? extends CContext.Directives> value();

   public interface Directives {
      default boolean isInConfiguration() {
         return true;
      }

      default List<String> getHeaderFiles() {
         return Collections.emptyList();
      }

      default List<String> getMacroDefinitions() {
         return Collections.emptyList();
      }

      default List<String> getOptions() {
         return Collections.emptyList();
      }

      default List<String> getLibraries() {
         return Collections.emptyList();
      }

      default List<String> getLibraryPaths() {
         return Collections.emptyList();
      }
   }
}
