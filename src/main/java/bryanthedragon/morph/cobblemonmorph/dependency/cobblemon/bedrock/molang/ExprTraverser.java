/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.NonNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ExprTraverser {
    private boolean stopTraversal = false;
    private final List<ExprVisitor> visitors = new LinkedList<ExprVisitor>();

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
        ArrayList<Expression> list = new ArrayList<Expression>(expressions);
        for (int i = 0; i < list.size(); ++i) {
            Expression expression = (Expression)list.get(i);
            boolean removeCurrent = false;
            boolean traverseChildren = true;
            boolean traverseCurrent = true;
            for (ExprVisitor visitor : this.visitors) {
                Object result = visitor.onVisit(expression);
                if (result instanceof ActionType) {
                    switch ((ActionType)((Object)result)) {
                        case REMOVE_CURRENT: {
                            removeCurrent = true;
                            break;
                        }
                        case STOP_TRAVERSAL: {
                            this.stopTraversal = true;
                            break;
                        }
                        case DONT_TRAVERSE_CURRENT_AND_CHILDREN: {
                            traverseCurrent = false;
                        }
                        case DONT_TRAVERSE_CHILDREN: {
                            traverseChildren = false;
                        }
                    }
                    continue;
                }
                if (!(result instanceof Expression)) continue;
                expression = (Expression)result;
            }
            if (!traverseCurrent) break;
            if (traverseChildren && !removeCurrent) {
                this.traverseExpr(expression);
            }
            for (ExprVisitor visitor : this.visitors) {
                visitor.onLeave(expression);
            }
            if (removeCurrent) {
                expressions.remove(i);
            } else {
                expressions.set(i, expression);
            }
            if (this.stopTraversal) break;
        }
    }

    private void traverseExpr(@NonNull Expression expression) {
        if (expression == null) {
            throw new NullPointerException("expression is marked non-null but is null");
        }
        for (Field field : ExprTraverser.getAllFields(expression.getClass())) {
            field.setAccessible(true);
            Object fieldValue = this.getFieldValue(field, expression);
            if (fieldValue instanceof Expression) {
                Expression subExpr = (Expression)fieldValue;
                boolean removeCurrent = false;
                boolean traverseChildren = true;
                boolean traverseCurrent = true;
                for (ExprVisitor visitor : this.visitors) {
                    Object result = visitor.onVisit(subExpr);
                    if (result instanceof ActionType) {
                        switch ((ActionType)((Object)result)) {
                            case REMOVE_CURRENT: {
                                removeCurrent = true;
                                break;
                            }
                            case STOP_TRAVERSAL: {
                                this.stopTraversal = true;
                                break;
                            }
                            case DONT_TRAVERSE_CURRENT_AND_CHILDREN: {
                                traverseCurrent = false;
                            }
                            case DONT_TRAVERSE_CHILDREN: {
                                traverseChildren = false;
                            }
                        }
                        continue;
                    }
                    if (!(result instanceof Expression)) continue;
                    subExpr = (Expression)result;
                }
                if (!traverseCurrent) break;
                if (traverseChildren && !removeCurrent) {
                    this.traverseExpr(subExpr);
                }
                for (ExprVisitor visitor : this.visitors) {
                    visitor.onLeave(subExpr);
                }
                if (removeCurrent) {
                    this.setFieldValue(field, expression, null);
                } else {
                    this.setFieldValue(field, expression, subExpr);
                }
                if (!this.stopTraversal) continue;
                break;
            }
            if (fieldValue == null || !fieldValue.getClass().isArray()) continue;
            Object[] array = (Object[])fieldValue;
            ArrayList<Expression> exprs = new ArrayList<Expression>();
            for (Object i : array) {
                if (!(i instanceof Expression)) continue;
                exprs.add((Expression)i);
            }
            this.traverseArray(exprs);
            this.setFieldValue(field, expression, exprs.toArray(new Expression[0]));
        }
    }

    public static List<Field> getAllFields(Class<?> type) {
        ArrayList<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    private Object getFieldValue(Field field, Object obj) {
        try {
            return field.get(obj);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private void setFieldValue(Field field, Object obj, Object value2) {
        try {
            field.set(obj, value2);
        }
        catch (Throwable throwable) {
            // empty catch block
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

