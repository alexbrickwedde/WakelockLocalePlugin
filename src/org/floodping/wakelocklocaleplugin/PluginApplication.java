/*
 * Copyright 2012 two forty four a.m. LLC <http://www.twofortyfouram.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.floodping.wakelocklocaleplugin;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

/**
 * Implements an application object for the plug-in.
 * <p>
 * This application is non-essential for the plug-in's operation; it simply enables debugging options globally for the app.
 */
public final class PluginApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0)
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.v(Constants.LOG_TAG, "Application is debuggable.  Enabling additional debug logging"); //$NON-NLS-1$
            }

            if (Build.VERSION.SDK_INT >= 9)
            {
                enableApiLevel9Debugging();
            }

            if (Build.VERSION.SDK_INT >= 11)
            {
                enableApiLevel11Debugging();
            }

            /*
             * If using the Fragment compatibility library, enable debug logging here
             */
            // android.support.v4.app.FragmentManager.enableDebugLogging(true);
            // android.support.v4.app.LoaderManager.enableDebugLogging(true);
        }
    }

    @TargetApi(9)
    private static void enableApiLevel9Debugging()
    {
        android.os.StrictMode.enableDefaults();
    }

    @TargetApi(11)
    private static void enableApiLevel11Debugging()
    {
        android.app.LoaderManager.enableDebugLogging(true);
        android.app.FragmentManager.enableDebugLogging(true);
    }
}