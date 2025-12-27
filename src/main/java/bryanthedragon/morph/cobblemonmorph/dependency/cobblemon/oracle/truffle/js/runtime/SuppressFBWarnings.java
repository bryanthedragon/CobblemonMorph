package com.oracle.truffle.js.runtime;

public @interface SuppressFBWarnings {
   String[] value();

   String justification();
}
