package per.wsj.commonlib.permission;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Request implements IRequest, PermissionListener {

    static final String TAG = Request.class.getSimpleName();

    private Context mContext;

    private String[] mPermissions;

    private Action mGranted;
    private Action mDenied;

    private Lazy<PermissionFragment> mPermissionFragment;

    public Request(@NonNull final FragmentActivity activity) {
        mContext = activity;
        mPermissionFragment = getLazySingleton(activity.getSupportFragmentManager());
    }

    public Request(@NonNull final Fragment fragment) {
        mContext = fragment.getContext();
        mPermissionFragment = getLazySingleton(fragment.getChildFragmentManager());
    }

    @NonNull
    private Lazy<PermissionFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<PermissionFragment>() {

            private PermissionFragment rxPermissionsFragment;

            @Override
            public synchronized PermissionFragment get() {
                if (rxPermissionsFragment == null) {
                    rxPermissionsFragment = getPermissionFragment(fragmentManager);
                }
                return rxPermissionsFragment;
            }
        };
    }

    @NonNull
    @Override
    public IRequest permission(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    @NonNull
    @Override
    public IRequest permission(String[]... groups) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : groups) {
            permissionList.addAll(Arrays.asList(group));
        }
        this.mPermissions = permissionList.toArray(new String[permissionList.size()]);
        return this;
    }

    @NonNull
    @Override
    public IRequest onGranted(Action granted) {
        this.mGranted = granted;
        return this;
    }

    @NonNull
    @Override
    public IRequest onDenied(Action denied) {
        this.mDenied = denied;
        return this;
    }

    @Override
    public void start() {
        if (PermissionUtil.hasPermission(mContext, mPermissions)) {
            callbackSucceed();
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                callbackFailed(mPermissions);
            } else {
                mPermissionFragment.get().request(mPermissions,this);
            }
        }
    }

    private PermissionFragment getPermissionFragment(@NonNull final FragmentManager fragmentManager) {
        PermissionFragment rxPermissionsFragment = findPermissionFragment(fragmentManager);
        boolean isNewInstance = rxPermissionsFragment == null;
        if (isNewInstance) {
            rxPermissionsFragment = new PermissionFragment();
            fragmentManager
                    .beginTransaction()
                    .add(rxPermissionsFragment, TAG)
                    .commitNow();
        }
        return rxPermissionsFragment;
    }

    private PermissionFragment findPermissionFragment(@NonNull final FragmentManager fragmentManager) {
        return (PermissionFragment) fragmentManager.findFragmentByTag(TAG);
    }

    /**
     * Callback acceptance status.
     */
    private void callbackSucceed() {
        if (mGranted != null) {
            mGranted.onAction(mPermissions);
        }
    }

    /**
     * Callback rejected state.
     */
    private void callbackFailed(@NonNull String[] deniedList) {
        if (mDenied != null) {
            mDenied.onAction(deniedList);
        }
    }

    @Override
    public void permissionGranted(@NonNull String[] permission) {
        callbackSucceed();
    }

    @Override
    public void permissionDenied(@NonNull String[] permission) {
        callbackFailed(permission);
    }

    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }
}
