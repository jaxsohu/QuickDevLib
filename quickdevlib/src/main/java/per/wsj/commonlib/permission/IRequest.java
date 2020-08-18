/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.wsj.commonlib.permission;


import androidx.annotation.NonNull;

/**
 * <p>Permission request.</p>
 */
public interface IRequest {

    /**
     * One or more permissions.
     */
    @NonNull
    IRequest permission(String... permissions);

    /**
     * One or more permission groups.
     */
    @NonNull
    IRequest permission(String[]... groups);

    /**
     * Action to be taken when all permissions are granted.
     */
    @NonNull
    IRequest onGranted(Action granted);

    /**
     * Action to be taken when all permissions are denied.
     */
    @NonNull
    IRequest onDenied(Action denied);

    /**
     * IRequest permission.
     */
    void start();

}