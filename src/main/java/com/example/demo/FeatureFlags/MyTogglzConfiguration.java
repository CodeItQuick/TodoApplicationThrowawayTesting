package com.example.demo.FeatureFlags;

import com.example.demo.TodoFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

import java.io.File;

@Component
public class MyTogglzConfiguration implements TogglzConfig {

    private String user;

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return TodoFeatures.class;
    }

    @Override
    public StateRepository getStateRepository() {
        return new FileBasedStateRepository(new File("/tmp/features.properties"));
    }

    public static class CurrentFeatureUser {
        private static CurrentFeatureUser single_instance = null;
        public FeatureUser simpleFeatureUser;
        private CurrentFeatureUser() {
            simpleFeatureUser = new SimpleFeatureUser("anonymous", false);
        }
        public static CurrentFeatureUser Singleton(String user) {
            if (single_instance == null) {
                single_instance = new CurrentFeatureUser();
            }
            single_instance.simpleFeatureUser = new SimpleFeatureUser(user, false);
            return single_instance;
        }

        public void setSimpleFeatureUser(String simpleFeatureUser) {
            this.simpleFeatureUser = new SimpleFeatureUser(simpleFeatureUser, false);
        }
    }


    @Bean
    public UserProvider getUserProvider() {
        return () -> CurrentFeatureUser.single_instance.simpleFeatureUser;
    }
}