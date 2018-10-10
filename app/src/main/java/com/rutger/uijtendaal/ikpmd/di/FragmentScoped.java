package com.rutger.uijtendaal.ikpmd.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * An unscoped component cannot depend on a scoped component. AppComponent is scoped so all
 * subcomponents must also be scoped.
 *
 * We create a custom scope for all activities and fragments.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FragmentScoped {}