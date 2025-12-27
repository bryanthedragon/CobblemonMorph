package com.oracle.truffle.polyglot;

import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotInstrumentDispatch extends AbstractPolyglotImpl.AbstractInstrumentDispatch {
   protected PolyglotInstrumentDispatch(PolyglotImpl impl) {
      super(impl);
   }

   @Override
   public String getId(Object receiver) {
      return ((PolyglotInstrument)receiver).getId();
   }

   @Override
   public String getName(Object receiver) {
      return ((PolyglotInstrument)receiver).getName();
   }

   @Override
   public OptionDescriptors getOptions(Object receiver) {
      return ((PolyglotInstrument)receiver).getOptions();
   }

   @Override
   public String getVersion(Object receiver) {
      return ((PolyglotInstrument)receiver).getVersion();
   }

   @Override
   public <T> T lookup(Object receiver, Class<T> type) {
      return ((PolyglotInstrument)receiver).lookup(type);
   }

   @Override
   public String getWebsite(Object receiver) {
      return ((PolyglotInstrument)receiver).getWebsite();
   }
}
