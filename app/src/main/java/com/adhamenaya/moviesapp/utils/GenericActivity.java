package com.adhamenaya.moviesapp.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class GenericActivity<OpsType extends ConfigurableOps> extends AppCompatActivity {

    private final RetainedFragmentManager mRetainedFragmentManager
            = new RetainedFragmentManager(this.getSupportFragmentManager(), getClass().getSimpleName());

    private OpsType mOpsType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void handleConfiguration(Class<OpsType> opsType) {
        try {
            if (mRetainedFragmentManager.firstTimeIn()) {
                // Initialize the GenericActivity fields.
                initialize(opsType);
            } else {

                mOpsType = mRetainedFragmentManager.get(opsType.getSimpleName());

                if (mOpsType == null) {
                    // Initialize the activity again rather than crashing the app.
                    initialize(opsType);
                } else {
                    // Inform it that the runtime configuration change has
                    // completed.

                    mOpsType.onConfiguration(this, false);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            // Propagate this as a runtime exception.
            throw new RuntimeException(e);
        }
        // If this method returns true it's the first time the
        // Activity has been created.
    }

    private void initialize(Class<OpsType> opsType)
            throws InstantiationException, IllegalAccessException {

        mOpsType = opsType.newInstance();

        // Retain the main activity ops
        mRetainedFragmentManager.put(opsType.getSimpleName(), mOpsType);

        // Call the configuration method with time first in
        mOpsType.onConfiguration(this, true);
    }

    public OpsType getOps() {
        return mOpsType;
    }

    public RetainedFragmentManager getRetainedFragmentManager() {
        return mRetainedFragmentManager;
    }
}
