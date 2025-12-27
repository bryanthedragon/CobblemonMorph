package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotExceptionFrame extends AbstractPolyglotImpl.AbstractStackFrameImpl {
   private final PolyglotLanguage language;
   private final SourceSection sourceLocation;
   private final String rootName;
   private final boolean host;
   private StackTraceElement stackTrace;
   private final String formattedSource;

   private PolyglotExceptionFrame(
      PolyglotExceptionImpl source, PolyglotLanguage language, SourceSection sourceLocation, String rootName, boolean isHost, StackTraceElement stackTrace
   ) {
      super(source.polyglot);
      this.language = language;
      this.sourceLocation = sourceLocation;
      this.rootName = rootName;
      this.host = isHost;
      this.stackTrace = stackTrace;
      if (!isHost) {
         this.formattedSource = formatSource(sourceLocation, language != null ? source.getFileSystemContext(language) : null);
      } else {
         this.formattedSource = null;
      }
   }

   @Override
   public SourceSection getSourceLocation() {
      return this.sourceLocation;
   }

   @Override
   public Language getLanguage() {
      return this.language.api;
   }

   @Override
   public String getRootName() {
      return this.rootName;
   }

   @Override
   public boolean isHostFrame() {
      return this.host;
   }

   @Override
   public StackTraceElement toHostFrame() {
      if (this.stackTrace == null) {
         String declaringClass;
         if (this.language != null) {
            declaringClass = "<" + this.language.getId() + ">";
         } else {
            declaringClass = "";
         }

         String methodName = this.rootName == null ? "" : this.rootName;
         String fileName = this.sourceLocation != null ? this.sourceLocation.getSource().getName() : "Unknown";
         int startLine = this.sourceLocation != null ? this.sourceLocation.getStartLine() : -1;
         this.stackTrace = new StackTraceElement(declaringClass, methodName, fileName, startLine);
      }

      return this.stackTrace;
   }

   @Override
   public String toStringImpl(int langColumn) {
      StringBuilder b = new StringBuilder();
      if (this.isHostFrame()) {
         String languageId = "";
      } else {
         String languageId = this.language.getId();
         b.append(spaces(Math.max(langColumn, languageId.length()) - languageId.length())).append("<").append(languageId).append("> ");
      }

      if (this.isHostFrame()) {
         b.append(this.stackTrace.toString());
      } else {
         b.append(this.rootName);
         b.append("(");

         assert this.formattedSource != null;

         b.append(this.formattedSource);
         b.append(")");
      }

      return b.toString();
   }

   static PolyglotExceptionFrame createGuest(PolyglotExceptionImpl exception, TruffleStackTraceElement frame, boolean first) {
      if (frame == null) {
         return null;
      } else {
         RootNode targetRoot = frame.getTarget().getRootNode();
         if (targetRoot.isInternal() && !exception.showInternalStackFrames) {
            return null;
         } else {
            LanguageInfo info = targetRoot.getLanguageInfo();
            if (info == null) {
               return null;
            } else {
               PolyglotEngineImpl engine = exception.engine;
               PolyglotLanguage language = null;
               SourceSection location = null;
               String rootName = targetRoot.getName();
               if (engine != null) {
                  language = engine.idToLanguage.get(info.getId());
                  Node callNode = frame.getLocation();
                  if (callNode != null) {
                     com.oracle.truffle.api.source.SourceSection section = callNode.getEncapsulatingSourceSection();
                     if (section != null) {
                        Source source = engine.getAPIAccess().newSource(exception.polyglot.getSourceDispatch(), section.getSource());
                        location = engine.getAPIAccess().newSourceSection(source, exception.polyglot.getSourceSectionDispatch(), section);
                     } else {
                        location = null;
                     }
                  } else {
                     location = first ? exception.getSourceLocation() : null;
                  }
               }

               return new PolyglotExceptionFrame(exception, language, location, rootName, false, null);
            }
         }
      }
   }

   static PolyglotExceptionFrame createHost(PolyglotExceptionImpl exception, StackTraceElement hostStack) {
      PolyglotLanguage language = exception.engine != null ? exception.engine.hostLanguage : null;
      SourceSection location = null;
      String rootname = hostStack.getClassName() + "." + hostStack.getMethodName();
      return new PolyglotExceptionFrame(exception, language, location, rootname, true, hostStack);
   }

   private static String spaces(int length) {
      StringBuilder b = new StringBuilder();

      for (int i = 0; i < length; i++) {
         b.append(' ');
      }

      return b.toString();
   }

   private static String formatSource(SourceSection sourceSection, Object fileSystemContext) {
      if (sourceSection == null) {
         return "Unknown";
      } else {
         Source source = sourceSection.getSource();
         if (source == null) {
            return "Unknown";
         } else {
            StringBuilder b = new StringBuilder();
            String path = source.getPath();
            if (path == null) {
               b.append(source.getName());
            } else if (fileSystemContext != null) {
               try {
                  TruffleFile pathAbsolute = EngineAccessor.LANGUAGE.getTruffleFile(path, fileSystemContext);
                  TruffleFile pathBase = EngineAccessor.LANGUAGE.getTruffleFile("", fileSystemContext).getAbsoluteFile();
                  TruffleFile pathRelative = pathBase.relativize(pathAbsolute);
                  b.append(pathRelative.getPath());
               } catch (UnsupportedOperationException | SecurityException | IllegalArgumentException var8) {
                  b.append(path);
               }
            } else {
               b.append(path);
            }

            b.append(":").append(formatIndices(sourceSection, true));
            return b.toString();
         }
      }
   }

   private static String formatIndices(SourceSection sourceSection, boolean needsColumnSpecifier) {
      StringBuilder b = new StringBuilder();
      boolean singleLine = sourceSection.getStartLine() == sourceSection.getEndLine();
      if (singleLine) {
         b.append(sourceSection.getStartLine());
      } else {
         b.append(sourceSection.getStartLine()).append("-").append(sourceSection.getEndLine());
      }

      if (needsColumnSpecifier) {
         b.append(":");
         if (sourceSection.getCharLength() <= 1) {
            b.append(sourceSection.getCharIndex());
         } else {
            b.append(sourceSection.getCharIndex()).append("-").append(sourceSection.getCharIndex() + sourceSection.getCharLength() - 1);
         }
      }

      return b.toString();
   }
}
