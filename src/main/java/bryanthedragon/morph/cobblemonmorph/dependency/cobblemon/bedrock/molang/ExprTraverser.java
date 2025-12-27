package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ExprTraverser {
   private boolean stopTraversal = false;
   private final List<ExprVisitor> visitors = new LinkedList<>();

   public void traverse(List<Expression> expressions) {
      for (ExprVisitor visitor : this.visitors) {
         visitor.beforeTraverse(expressions);
      }

      this.stopTraversal = false;
      this.traverseArray(expressions);

      for (ExprVisitor visitor : this.visitors) {
         visitor.afterTraverse(expressions);
      }
   }

   private void traverseArray(List<Expression> expressions) {
      List<Expression> list = new ArrayList<>(expressions);

      for (int i = 0; i < list.size(); i++) {
         Expression expression = list.get(i);
         boolean removeCurrent = false;
         boolean traverseChildren = true;
         boolean traverseCurrent = true;

         for (ExprVisitor visitor : this.visitors) {
            Object result = visitor.onVisit(expression);
            if (result instanceof ExprTraverser.ActionType) {
               switch ((ExprTraverser.ActionType)result) {
                  case REMOVE_CURRENT:
                     removeCurrent = true;
                     break;
                  case STOP_TRAVERSAL:
                     this.stopTraversal = true;
                     break;
                  case DONT_TRAVERSE_CURRENT_AND_CHILDREN:
                     traverseCurrent = false;
                  case DONT_TRAVERSE_CHILDREN:
                     traverseChildren = false;
               }
            } 
            else if (result instanceof Expression) {
               expression = (Expression)result;
            }
         }

         if (!traverseCurrent) {
            break;
         }

         if (traverseChildren && !removeCurrent) {
            this.traverseExpr(expression);
         }

         for (ExprVisitor visitorx : this.visitors) {
            visitorx.onLeave(expression);
         }

         if (removeCurrent) {
            expressions.remove(i);
         } 
         else {
            expressions.set(i, expression);
         }

         if (this.stopTraversal) {
            break;
         }
      }
   }

   private void traverseExpr(@NonNull Expression expression) {
      if (expression == null) {
         throw new NullPointerException("expression is marked non-null but is null");
      } else {
         for (Field field : getAllFields(expression.getClass())) {
            field.setAccessible(true);
            Object fieldValue = this.getFieldValue(field, expression);
            if (fieldValue instanceof Expression subExprx) {
               boolean removeCurrent = false;
               boolean traverseChildren = true;
               boolean traverseCurrent = true;

               for (ExprVisitor visitor : this.visitors) {
                  Object result = visitor.onVisit(subExprx);
                  if (result instanceof ExprTraverser.ActionType) {
                     switch ((ExprTraverser.ActionType)result) {
                        case REMOVE_CURRENT:
                           removeCurrent = true;
                           break;
                        case STOP_TRAVERSAL:
                           this.stopTraversal = true;
                           break;
                        case DONT_TRAVERSE_CURRENT_AND_CHILDREN:
                           traverseCurrent = false;
                        case DONT_TRAVERSE_CHILDREN:
                           traverseChildren = false;
                     }
                  } 
                  else if (result instanceof Expression subExprx) {
                     
                  }
               }

               if (!traverseCurrent) {
                  break;
               }

               if (traverseChildren && !removeCurrent) {
                  this.traverseExpr(subExprx);
               }

               for (ExprVisitor visitorx : this.visitors) {
                  visitorx.onLeave(subExprx);
               }

               if (removeCurrent) {
                  this.setFieldValue(field, expression, null);
               } else {
                  this.setFieldValue(field, expression, subExprx);
               }

               if (this.stopTraversal) {
                  break;
               }
            } else if (fieldValue != null && fieldValue.getClass().isArray()) {
               Object[] array = (Object[])fieldValue;
               List<Expression> exprs = new ArrayList<>();

               for (Object i : array) {
                  if (i instanceof Expression) {
                     exprs.add((Expression)i);
                  }
               }

               this.traverseArray(exprs);
               this.setFieldValue(field, expression, exprs.toArray(new Expression[0]));
            }
         }
      }
   }

   public static List<Field> getAllFields(Class<?> type) {
      List<Field> fields = new ArrayList<>();

      for (Class<?> c = type; c != null; c = c.getSuperclass()) {
         fields.addAll(Arrays.asList(c.getDeclaredFields()));
      }

      return fields;
   }

   private Object getFieldValue(Field field, Object obj) {
      try {
         return field.get(obj);
      } catch (Throwable var4) {
         return null;
      }
   }

   private void setFieldValue(Field field, Object obj, Object value) {
      try {
         field.set(obj, value);
      } catch (Throwable var5) {
      }
   }

   public boolean isStopTraversal() {
      return this.stopTraversal;
   }

   public List<ExprVisitor> getVisitors() {
      return this.visitors;
   }

   public static enum ActionType {
      REMOVE_CURRENT,
      STOP_TRAVERSAL,
      DONT_TRAVERSE_CURRENT_AND_CHILDREN,
      DONT_TRAVERSE_CHILDREN;
   }
}
