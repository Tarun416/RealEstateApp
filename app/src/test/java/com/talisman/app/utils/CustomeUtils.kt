package com.talisman.app.utils

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Created by Tarun on 11/16/17.
 */
/*
Annotation processor component class for custom scope for our components and module
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class CustomScope