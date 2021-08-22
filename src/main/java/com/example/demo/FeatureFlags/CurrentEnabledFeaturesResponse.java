package com.example.demo.FeatureFlags;

import com.example.demo.TodoFeatures;
import org.togglz.core.manager.FeatureManager;

public class CurrentEnabledFeaturesResponse {
    private Boolean createEnabled = false;
    private Boolean editEnabled = false;
    private Boolean deleteEnabled = false;


    public CurrentEnabledFeaturesResponse(FeatureManager featureManager, String user) {
        MyTogglzConfiguration.CurrentFeatureUser.Singleton(user);
        this.createEnabled = featureManager.isActive(TodoFeatures.CREATE_ENABLED);
        this.editEnabled = featureManager.isActive(TodoFeatures.UPDATE_BY_USER);
        this.deleteEnabled = featureManager.isActive(TodoFeatures.USER_BASED_PERCENTAGE_ROLLOUT_DELETE_FUNCTION);
    }


    public Boolean getCreateEnabled() {
        return createEnabled;
    }

    public void setCreateEnabled(Boolean createEnabled) {
        this.createEnabled = createEnabled;
    }

    public Boolean getEditEnabled() {
        return editEnabled;
    }

    public void setEditEnabled(Boolean editEnabled) {
        this.editEnabled = editEnabled;
    }

    public Boolean getDeleteEnabled() {
        return deleteEnabled;
    }

    public void setDeleteEnabled(Boolean deleteEnabled) {
        this.deleteEnabled = deleteEnabled;
    }

}
