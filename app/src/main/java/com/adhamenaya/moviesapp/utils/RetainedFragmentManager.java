package com.adhamenaya.moviesapp.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by AENAYA on 7/11/2015.
 */
public class RetainedFragmentManager {

    /*
    * This class plays the role of organiser in memento pattern
    * */

    private WeakReference<FragmentManager> mFragmentManager;

    private String mRetainedFragmentTag;

    private RetainedFragment mRetainedFragment;

    public RetainedFragmentManager(FragmentManager fragmentManager, String retainedFragmentTag) {

        mFragmentManager = new WeakReference<FragmentManager>(fragmentManager);
        mRetainedFragmentTag = retainedFragmentTag;
    }

    public FragmentManager get() {
        return mFragmentManager.get();
    }

    public boolean firstTimeIn() {
        try {

            //Get fragment by tag, because this fragment doesn't have ID
            mRetainedFragment = (RetainedFragment) mFragmentManager.get().findFragmentByTag(mRetainedFragmentTag);

            if (mRetainedFragment == null) {
                mRetainedFragment = new RetainedFragment();

                //Display the fragment
                mFragmentManager.get().beginTransaction().add(mRetainedFragment, mRetainedFragmentTag).commit();

                return true;
            } else
                return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public void put(String key, Object value) {
        mRetainedFragment.put(key, value);
    }

    public void put(Object value) {
        put(value.getClass().getName(), value);
    }

    public FragmentActivity getActivity() {
        return mRetainedFragment.getActivity();
    }

    public <T> T get(String key) {
        return (T) mRetainedFragment.get(key);
    }

    public static class RetainedFragment extends Fragment {
        /*
        * This class plays the role of memento in the memento pattern
        * */

        // Hash map to store the objects
        private HashMap<String, Object> mData = new HashMap<String, Object>();

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setRetainInstance(true);
        }

        public void put(String key, Object value) {
            mData.put(key, value);
        }

        public void put(Object value) {
            mData.put(value.getClass().getName(), value);
        }

        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}
