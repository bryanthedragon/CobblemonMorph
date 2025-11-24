
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalNowBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendarObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalNowBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalNow> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalNowBuiltins();

    protected TemporalNowBuiltins() {
        super(TemporalConstants.NOW, TemporalNow.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalNow builtinEnum) {
        switch (builtinEnum) {
            case timeZone: {
                return TemporalNowBuiltinsFactory.TemporalNowTimeZoneNodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case instant: {
                return TemporalNowBuiltinsFactory.TemporalNowInstantNodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case plainDateTime: {
                return TemporalNowBuiltinsFactory.TemporalNowPlainDateTimeNodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case plainDateTimeISO: {
                return TemporalNowBuiltinsFactory.TemporalNowPlainDateTimeISONodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case zonedDateTime: {
                return TemporalNowBuiltinsFactory.TemporalNowZonedDateTimeNodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case zonedDateTimeISO: {
                return TemporalNowBuiltinsFactory.TemporalNowZonedDateTimeISONodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case plainDate: {
                return TemporalNowBuiltinsFactory.TemporalNowPlainDateNodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case plainDateISO: {
                return TemporalNowBuiltinsFactory.TemporalNowPlainDateISONodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case plainTimeISO: {
                return TemporalNowBuiltinsFactory.TemporalNowPlainTimeISONodeGen.create(context, builtin, TemporalNowBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class TemporalNowPlainTimeISONode
    extends JSBuiltinNode {
        protected TemporalNowPlainTimeISONode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject plainTimeISO(Object temporalTimeZoneLike, @Cached BranchProfile errorBranch) {
            JSTemporalCalendarObject calendar = TemporalUtil.getISO8601Calendar(this.getContext(), this.getRealm(), errorBranch);
            JSTemporalPlainDateTimeObject dateTime = TemporalUtil.systemDateTime(temporalTimeZoneLike, calendar, this.getContext());
            return JSTemporalPlainTime.create(this.getContext(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond(), errorBranch);
        }
    }

    public static abstract class TemporalNowPlainDateISONode
    extends JSBuiltinNode {
        protected TemporalNowPlainDateISONode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject plainDateISO(Object temporalTimeZoneLike, @Cached BranchProfile errorBranch) {
            JSTemporalCalendarObject calendar = TemporalUtil.getISO8601Calendar(this.getContext(), this.getRealm(), errorBranch);
            JSTemporalPlainDateTimeObject dateTime = TemporalUtil.systemDateTime(temporalTimeZoneLike, calendar, this.getContext());
            return JSTemporalPlainDate.create(this.getContext(), dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getCalendar(), errorBranch);
        }
    }

    public static abstract class TemporalNowPlainDateNode
    extends JSBuiltinNode {
        protected TemporalNowPlainDateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject plainDate(Object calendar, Object temporalTimeZoneLike, @Cached BranchProfile errorBranch) {
            JSTemporalPlainDateTimeObject dateTime = TemporalUtil.systemDateTime(temporalTimeZoneLike, calendar, this.getContext());
            return JSTemporalPlainDate.create(this.getContext(), dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getCalendar(), errorBranch);
        }
    }

    public static abstract class TemporalNowZonedDateTimeISONode
    extends JSBuiltinNode {
        protected TemporalNowZonedDateTimeISONode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject zonedDateTimeISO(Object temporalTimeZoneLike, @Cached BranchProfile errorBranch) {
            return TemporalUtil.systemZonedDateTime(temporalTimeZoneLike, TemporalUtil.getISO8601Calendar(this.getContext(), this.getRealm(), errorBranch), this.getContext());
        }
    }

    public static abstract class TemporalNowZonedDateTimeNode
    extends JSBuiltinNode {
        protected TemporalNowZonedDateTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject zonedDateTime(Object calendar, Object temporalTimeZoneLike) {
            return TemporalUtil.systemZonedDateTime(temporalTimeZoneLike, calendar, this.getContext());
        }
    }

    public static abstract class TemporalNowPlainDateTimeISONode
    extends JSBuiltinNode {
        protected TemporalNowPlainDateTimeISONode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject plainDateTimeISO(Object temporalTimeZoneLike, @Cached BranchProfile errorBranch) {
            return TemporalUtil.systemDateTime(temporalTimeZoneLike, TemporalUtil.getISO8601Calendar(this.getContext(), this.getRealm(), errorBranch), this.getContext());
        }
    }

    public static abstract class TemporalNowPlainDateTimeNode
    extends JSBuiltinNode {
        protected TemporalNowPlainDateTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject plainDateTime(Object calendar, Object temporalTimeZoneLike) {
            return TemporalUtil.systemDateTime(temporalTimeZoneLike, calendar, this.getContext());
        }
    }

    public static abstract class TemporalNowInstantNode
    extends JSBuiltinNode {
        protected TemporalNowInstantNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject intstant() {
            return TemporalUtil.systemInstant(this.getContext());
        }
    }

    public static abstract class TemporalNowTimeZoneNode
    extends JSBuiltinNode {
        protected TemporalNowTimeZoneNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject timeZone() {
            return TemporalUtil.systemTimeZone(this.getContext());
        }
    }

    public static enum TemporalNow implements BuiltinEnum<TemporalNow>
    {
        timeZone(0),
        instant(0),
        plainDateTime(1),
        plainDateTimeISO(0),
        zonedDateTime(1),
        zonedDateTimeISO(0),
        plainDate(1),
        plainDateISO(0),
        plainTimeISO(0);

        private final int length;

        private TemporalNow(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

