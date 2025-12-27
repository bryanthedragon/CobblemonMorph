package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

public final class DebugStackTraceElement {
   private final DebuggerSession session;
   final TruffleStackTraceElement traceElement;
   private final StackTraceElement hostTraceElement;
   private StackTraceElement stackTraceElement;

   DebugStackTraceElement(DebuggerSession session, TruffleStackTraceElement traceElement) {
      this.session = session;
      this.traceElement = traceElement;
      this.hostTraceElement = null;
   }

   DebugStackTraceElement(DebuggerSession session, StackTraceElement hostTraceElement) {
      this.session = session;
      this.traceElement = null;
      this.hostTraceElement = hostTraceElement;
   }

   public boolean isInternal() {
      if (this.isHost()) {
         return false;
      } else {
         RootNode root = this.findCurrentRoot();
         return root == null ? true : root.isInternal();
      }
   }

   public boolean isHost() {
      return this.hostTraceElement != null;
   }

   public StackTraceElement getHostTraceElement() {
      return this.hostTraceElement;
   }

   public String getName() {
      if (this.hostTraceElement != null) {
         return this.hostTraceElement.getClassName() + "." + this.hostTraceElement.getMethodName();
      } else {
         try {
            Object guestObject = this.traceElement.getGuestObject();
            if (InteropLibrary.getUncached().hasExecutableName(guestObject)) {
               try {
                  return InteropLibrary.getUncached().asString(InteropLibrary.getUncached().getExecutableName(guestObject));
               } catch (UnsupportedMessageException var4) {
                  throw CompilerDirectives.shouldNotReachHere(var4);
               }
            } else {
               return null;
            }
         } catch (ThreadDeath var5) {
            throw var5;
         } catch (Throwable var6) {
            RootNode root = this.findCurrentRoot();
            LanguageInfo languageInfo = root != null ? root.getLanguageInfo() : null;
            throw DebugException.create(this.session, var6, languageInfo);
         }
      }
   }

   public SourceSection getSourceSection() {
      if (this.isHost()) {
         return null;
      } else {
         Node node = this.traceElement.getLocation();
         return node != null ? this.session.resolveSection(node) : null;
      }
   }

   public DebugScope getScope() {
      if (this.isHost()) {
         return null;
      } else {
         Node node = this.traceElement.getLocation();
         if (node == null) {
            return null;
         } else {
            RootNode root = node.getRootNode();
            if (root.getLanguageInfo() == null) {
               return null;
            } else {
               Frame elementFrame = this.traceElement.getFrame();
               MaterializedFrame frame = elementFrame != null ? elementFrame.materialize() : null;
               if (!NodeLibrary.getUncached().hasScope(node, frame)) {
                  return null;
               } else {
                  try {
                     Object scope = NodeLibrary.getUncached().getScope(node, frame, true);
                     return new DebugScope(scope, this.session, null, node, frame, root);
                  } catch (UnsupportedMessageException var6) {
                     throw CompilerDirectives.shouldNotReachHere(var6);
                  }
               }
            }
         }
      }
   }

   private LanguageInfo getLanguage() {
      if (this.isHost()) {
         return null;
      } else {
         RootNode root = this.findCurrentRoot();
         LanguageInfo language;
         if (root != null) {
            language = root.getLanguageInfo();
         } else {
            language = null;
         }

         return language;
      }
   }

   private RootNode findCurrentRoot() {
      if (this.isHost()) {
         return null;
      } else {
         Node node = this.traceElement.getLocation();
         if (node != null) {
            return node.getRootNode();
         } else {
            RootCallTarget target = this.traceElement.getTarget();
            return target.getRootNode();
         }
      }
   }

   StackTraceElement toTraceElement() {
      if (this.stackTraceElement == null) {
         if (this.hostTraceElement != null) {
            this.stackTraceElement = this.hostTraceElement;
         } else {
            LanguageInfo language = this.getLanguage();
            String declaringClass = language != null ? "<" + language.getId() + ">" : "<unknown>";

            String methodName;
            try {
               Object guestObject = this.traceElement.getGuestObject();
               if (InteropLibrary.getUncached().hasExecutableName(guestObject)) {
                  try {
                     methodName = InteropLibrary.getUncached().asString(InteropLibrary.getUncached().getExecutableName(guestObject));
                  } catch (UnsupportedMessageException var7) {
                     throw CompilerDirectives.shouldNotReachHere(var7);
                  }
               } else {
                  methodName = "";
               }
            } catch (AssertionError | ThreadDeath var8) {
               throw var8;
            } catch (Throwable var9) {
               if (!InteropLibrary.getUncached().isException(var9)) {
                  throw var9;
               }

               methodName = "Error in generating method name: " + var9.getLocalizedMessage();
            }

            SourceSection sourceLocation = this.getSourceSection();
            String fileName = sourceLocation != null ? sourceLocation.getSource().getName() : "Unknown";
            int startLine = sourceLocation != null ? sourceLocation.getStartLine() : -1;
            this.stackTraceElement = new StackTraceElement(declaringClass, methodName, fileName, startLine);
         }
      }

      return this.stackTraceElement;
   }
}
