package per.wsj.commonlib.permission;

import androidx.annotation.NonNull;

/**
 * Created by shiju.wang on 2018/3/1.
 */

public interface PermissionListener {
    /**
     * 通过授权
     * @param permission
     */
    void permissionGranted(@NonNull String[] permission);

    /**
     * 拒绝授权
     * @param permission
     */
    void permissionDenied(@NonNull String[] permission);
}
