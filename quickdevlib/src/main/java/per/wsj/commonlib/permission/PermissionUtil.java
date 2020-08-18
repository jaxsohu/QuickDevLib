package per.wsj.commonlib.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by shiju.wang on 2018/3/1.
 */

public class PermissionUtil {

    private PermissionUtil() {

    }

    /**
     * 判断权限是否授权
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        if (permissions.length == 0) {
            return false;
        }
        for (String per : permissions) {
            int result = PermissionChecker.checkSelfPermission(context, per);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一组授权结果是否为授权通过
     *
     * @param grantResult
     * @return
     */
    public static boolean isGranted(@NonNull int... grantResult) {
        if (grantResult.length == 0) {
            return false;
        }
        for (int result : grantResult) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 跳转到当前应用对应的设置页面
     * @param context
     */
    public static void gotoSetting(@NonNull Context context) {
        new PermissionSetting(context).execute();
    }

    public static IRequest with(@NonNull final FragmentActivity activity) {
        return new Request(activity);
    }

    public static IRequest with(@NonNull final Fragment fragment) {
        return new Request(fragment);
    }
}
