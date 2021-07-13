package com.example.demo;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum TodoFeatures implements Feature {

    @EnabledByDefault
    @Label("Provide A Read functionality to the TODO app")
    READ_ENABLED,

    @Label("If a todo is overdue it is displayed on the screen")
    OVERDUE_ENABLED
}