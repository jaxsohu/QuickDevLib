package per.wsj.commonlib.permission;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PermissionFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 64;
    private String[] mPermission;
    private PermissionListener mPermissionListener;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPermissionListener = null;
    }

    public void request(String[] permissions, PermissionListener permissionListener) {
        this.mPermission = permissions;
        this.mPermissionListener = permissionListener;
        if (PermissionUtil.hasPermission(getActivity(), permissions)) {
            permissionsGranted();
        } else {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSIONS_REQUEST_CODE) return;

        if (PermissionUtil.isGranted(grantResults)
                && PermissionUtil.hasPermission(getActivity(), permissions)) {
            permissionsGranted();
        } else { //不需要提示用户
            permissionsDenied();
        }
    }

    private void permissionsDenied() {
        if(mPermissionListener!=null){
            mPermissionListener.permissionDenied(mPermission);
        }
    }

    // 全部权限均已获取
    private void permissionsGranted() {
        if(mPermissionListener!=null){
            mPermissionListener.permissionGranted(mPermission);
        }
    }
}
