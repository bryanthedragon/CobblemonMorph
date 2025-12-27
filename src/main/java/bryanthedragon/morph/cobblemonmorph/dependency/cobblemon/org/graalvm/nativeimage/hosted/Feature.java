package org.graalvm.nativeimage.hosted;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Platforms(Platform.HOSTED_ONLY.class)
public interface Feature {
   default String getURL() {
      return null;
   }

   default String getDescription() {
      return null;
   }

   default boolean isInConfiguration(Feature.IsInConfigurationAccess access) {
      return true;
   }

   default List<Class<? extends Feature>> getRequiredFeatures() {
      return Collections.emptyList();
   }

   default void afterRegistration(Feature.AfterRegistrationAccess access) {
   }

   default void duringSetup(Feature.DuringSetupAccess access) {
   }

   default void beforeAnalysis(Feature.BeforeAnalysisAccess access) {
   }

   default void duringAnalysis(Feature.DuringAnalysisAccess access) {
   }

   default void afterAnalysis(Feature.AfterAnalysisAccess access) {
   }

   default void onAnalysisExit(Feature.OnAnalysisExitAccess access) {
   }

   default void beforeUniverseBuilding(Feature.BeforeUniverseBuildingAccess access) {
   }

   default void beforeCompilation(Feature.BeforeCompilationAccess access) {
   }

   default void afterCompilation(Feature.AfterCompilationAccess access) {
   }

   default void afterHeapLayout(Feature.AfterHeapLayoutAccess access) {
   }

   default void beforeImageWrite(Feature.BeforeImageWriteAccess access) {
   }

   default void afterImageWrite(Feature.AfterImageWriteAccess access) {
   }

   default void cleanup() {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface AfterAnalysisAccess extends Feature.QueryReachabilityAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface AfterCompilationAccess extends Feature.CompilationAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface AfterHeapLayoutAccess extends Feature.FeatureAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface AfterImageWriteAccess extends Feature.FeatureAccess {
      Path getImagePath();
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface AfterRegistrationAccess extends Feature.FeatureAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface BeforeAnalysisAccess extends Feature.FeatureAccess {
      void registerAsUsed(Class<?> type);

      void registerAsInHeap(Class<?> type);

      void registerAsAccessed(Field field);

      void registerAsUnsafeAccessed(Field field);

      void registerReachabilityHandler(Consumer<Feature.DuringAnalysisAccess> callback, Object... elements);

      void registerMethodOverrideReachabilityHandler(BiConsumer<Feature.DuringAnalysisAccess, Executable> callback, Executable baseMethod);

      void registerSubtypeReachabilityHandler(BiConsumer<Feature.DuringAnalysisAccess, Class<?>> callback, Class<?> baseClass);

      void registerClassInitializerReachabilityHandler(Consumer<Feature.DuringAnalysisAccess> callback, Class<?> clazz);

      void registerFieldValueTransformer(Field field, FieldValueTransformer transformer);
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface BeforeCompilationAccess extends Feature.CompilationAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface BeforeImageWriteAccess extends Feature.FeatureAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface BeforeUniverseBuildingAccess extends Feature.FeatureAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface CompilationAccess extends Feature.FeatureAccess {
      long objectFieldOffset(Field field);

      void registerAsImmutable(Object object);

      void registerAsImmutable(Object root, Predicate<Object> includeObject);
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface DuringAnalysisAccess extends Feature.BeforeAnalysisAccess, Feature.QueryReachabilityAccess {
      void requireAnalysisIteration();
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface DuringSetupAccess extends Feature.FeatureAccess {
      void registerObjectReplacer(Function<Object, Object> replacer);
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface FeatureAccess {
      Class<?> findClassByName(String className);

      List<Path> getApplicationClassPath();

      List<Path> getApplicationModulePath();

      ClassLoader getApplicationClassLoader();
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface IsInConfigurationAccess extends Feature.FeatureAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface OnAnalysisExitAccess extends Feature.FeatureAccess {
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public interface QueryReachabilityAccess extends Feature.FeatureAccess {
      boolean isReachable(Class<?> clazz);

      boolean isReachable(Field field);

      boolean isReachable(Executable method);

      Set<Class<?>> reachableSubtypes(Class<?> baseClass);

      Set<Executable> reachableMethodOverrides(Executable baseMethod);
   }
}
