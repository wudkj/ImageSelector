/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sumile.sumileimagechooser;

import android.app.Application;


import net.sumile.sumileimagechooser.utils.Config;
import net.sumile.sumileimagechooser.utils.CrashHandler;

import java.io.File;


public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Config config = new Config(this); //初始化配置
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this, config.getCrashLogDir());
    }

    public String getSDPath() {
        String mCacheRoot = "";
        File dir = this.getExternalFilesDir("cache");
        if (dir == null) {
            mCacheRoot = this.getFilesDir().toString() + "/cache";
        } else {
            mCacheRoot = dir.toString();
        }
        return mCacheRoot;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
