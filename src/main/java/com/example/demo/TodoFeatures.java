package com.example.demo;

import org.togglz.core.Feature;
import org.togglz.core.activation.GradualActivationStrategy;
import org.togglz.core.activation.UsernameActivationStrategy;
import org.togglz.core.annotation.ActivationParameter;
import org.togglz.core.annotation.DefaultActivationStrategy;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum TodoFeatures implements Feature {

    @EnabledByDefault
    @Label("Provide Create functionality to the TODO app")
    CREATE_ENABLED,

    @EnabledByDefault
    @DefaultActivationStrategy(id = GradualActivationStrategy.ID, parameters = {
            @ActivationParameter(name = GradualActivationStrategy.PARAM_PERCENTAGE, value = "50")
    })
    USER_BASED_PERCENTAGE_ROLLOUT_DELETE_FUNCTION,

    @EnabledByDefault
    @Label("Edit Todo Feature By Username")
    @DefaultActivationStrategy(id = UsernameActivationStrategy.ID, parameters =
            {@ActivationParameter(name = UsernameActivationStrategy.PARAM_USERS, value = "default")
            })
    UPDATE_BY_USER,
    @Label("If a todo is overdue it is displayed on the screen")
    OVERDUE_ENABLED
}