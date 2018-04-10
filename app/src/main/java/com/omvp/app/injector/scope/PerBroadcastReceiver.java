package com.omvp.app.injector.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the broadcastReceiver to be memorized in the
 * correct component.
 */
@Scope
@Retention(RUNTIME)
public @interface PerBroadcastReceiver {
}
