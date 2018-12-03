package com.zst.ynh.widget.person.certification.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.zst.ynh.R;
import com.zst.ynh.bean.ContactRelationBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.utils.EncryptUtil;
import com.zst.ynh.view.BottomDialog;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.BaseDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.EMERGENCY_CONTACT)
@Layout(R.layout.activity_emergency_contact_layout)
public class EmergencyContactActivity extends BaseActivity implements IEmergencyContactView {
    @BindView(R.id.tv_emergency_relation)
    TextView tvEmergencyRelation;
    @BindView(R.id.rl_emergency_relation)
    RelativeLayout rlEmergencyRelation;
    @BindView(R.id.tv_emergency_name)
    TextView tvEmergencyName;
    @BindView(R.id.rl_emergency_contact)
    RelativeLayout rlEmergencyContact;
    @BindView(R.id.tv_usual_relation)
    TextView tvUsualRelation;
    @BindView(R.id.rl_usual_relation)
    RelativeLayout rlUsualRelation;
    @BindView(R.id.tv_usual_contact)
    TextView tvUsualContact;
    @BindView(R.id.rl_usual_contact)
    RelativeLayout rlUsualContact;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindColor(R.color.them_color)
    int themColor;

    private String[] arrayName;
    private int[] arrayType;
    private EmergencyContactPresent emergencyContactPresent;
    private ContactRelationBean contactRelationBean;
    private BottomDialog bottomDialog;
    private final int EMERGENCY_CONTACT = 1;
    private final int USUAL_CONTACT = 2;
    private int emergency_type;
    private int usual_type;
    private String emergencyName = "";
    private String emergencyPhone = "";
    private String usualName = "";
    private String usualPhone = "";
    //这是判断是否从认证页面来的 true:下方按钮显示下一步； false:下方按钮显示保存
    boolean isFromToCertification;

    @Override
    public void onRetry() {
        emergencyContactPresent.getContacts();
    }

    @Override
    public void initView() {
        mTitleBar.setTitle("联系方式");
        emergencyContactPresent = new EmergencyContactPresent();
        emergencyContactPresent.attach(this);
        emergencyContactPresent.getContacts();
        isFromToCertification = Constant.isIsStep();
        bottomDialog = new BottomDialog(this);
        if (isFromToCertification) {
            btnSave.setText("下一步");
        } else {
            btnSave.setText("保存");
        }
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }


    @OnClick({R.id.rl_emergency_relation, R.id.rl_emergency_contact, R.id.rl_usual_relation, R.id.rl_usual_contact, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_emergency_relation:
                setBottomDialogLinealData(contactRelationBean);
                break;
            case R.id.rl_emergency_contact:
                requestAddressBookPermission(EMERGENCY_CONTACT);
                break;
            case R.id.rl_usual_relation:
                setBottomDialogOtherData(contactRelationBean);
                break;
            case R.id.rl_usual_contact:
                requestAddressBookPermission(USUAL_CONTACT);
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(tvEmergencyName.getText().toString())) {
                    ToastUtils.showShort("联系人不能为空");
                } else if (TextUtils.isEmpty(tvUsualContact.getText().toString())) {
                    ToastUtils.showShort("联系人不能为空");
                } else if ("请选择".equals(tvEmergencyRelation.getText().toString())) {
                    ToastUtils.showShort("请选择与本人的关系");
                } else if ("请选择".equals(tvUsualRelation.getText().toString())) {
                    ToastUtils.showShort("请选择与本人的关系");
                } else {
                   String emergencye=tvEmergencyName.getText().toString();
                    emergencyPhone=emergencye.split(" ")[1];
                    emergencyName=emergencye.split(" ")[0];
                    String usual=tvUsualContact.getText().toString();
                    usualPhone=usual.split(" ")[1];
                    usualName=usual.split(" ")[0];
                    emergencyContactPresent.saveContactData(emergencyPhone, emergencyName, emergency_type + "", usual_type + "", usualPhone, usualName);
                }
                break;
        }
    }

    @Override
    public void saveSuccess() {
        if (isFromToCertification) {
            //原来这里要在存管判断是否添加了银行卡 目前项目没有存管 先去掉  直接跳转到绑卡页面
            ARouter.getInstance().build(ArouterUtil.BIND_BANK_CARD).withBoolean(BundleKey.ISCHANGE,false).navigation();
            finish();
        }else {
            finish();
        }
    }

    @Override
    public void getContactRelation(ContactRelationBean contactRelationBean) {
        this.contactRelationBean = contactRelationBean;
        if (!TextUtils.isEmpty(contactRelationBean.item.lineal_name)) {
            for (int i = 0; i < contactRelationBean.item.lineal_list.size(); i++) {
                if (contactRelationBean.item.lineal_list.get(i).type == contactRelationBean.item.lineal_relation) {
                    tvEmergencyRelation.setText(contactRelationBean.item.lineal_list.get(i).name);
                    emergency_type=contactRelationBean.item.lineal_relation;
                }
            }
            for (int i = 0; i < contactRelationBean.item.other_list.size(); i++) {
                if (contactRelationBean.item.other_list.get(i).type == contactRelationBean.item.other_relation) {
                    tvUsualRelation.setText(contactRelationBean.item.other_list.get(i).name);
                    usual_type=contactRelationBean.item.other_relation;
                }
            }
            tvEmergencyName.setText(contactRelationBean.item.lineal_name +" "+ contactRelationBean.item.lineal_mobile);
            tvUsualContact.setText(contactRelationBean.item.other_name +" "+ contactRelationBean.item.other_mobile);
        }
    }


    /**
     * 设置底部弹窗数据直属
     *
     * @param contactRelationBean
     */
    private void setBottomDialogLinealData(ContactRelationBean contactRelationBean) {
        if (contactRelationBean != null) {
            arrayName = new String[contactRelationBean.item.lineal_list.size()];
            arrayType = new int[contactRelationBean.item.lineal_list.size()];
            for (int i = 0; i < contactRelationBean.item.lineal_list.size(); i++) {
                arrayName[i] = contactRelationBean.item.lineal_list.get(i).name;
                arrayType[i] = contactRelationBean.item.lineal_list.get(i).type;
            }
            bottomDialog.setData(arrayName);
            bottomDialog.setType(EMERGENCY_CONTACT);
            bottomDialog.notifyDataChanged();
            bottomDialog.show();
        }
    }

    /**
     * 设置底部弹窗数据普通
     *
     * @param contactRelationBean
     */
    private void setBottomDialogOtherData(ContactRelationBean contactRelationBean) {
        if (contactRelationBean != null) {
            arrayName = new String[contactRelationBean.item.other_list.size()];
            arrayType = new int[contactRelationBean.item.other_list.size()];
            for (int i = 0; i < contactRelationBean.item.other_list.size(); i++) {
                arrayName[i] = contactRelationBean.item.other_list.get(i).name;
                arrayType[i] = contactRelationBean.item.other_list.get(i).type;
            }
            bottomDialog.setData(arrayName);
            bottomDialog.setType(USUAL_CONTACT);
            bottomDialog.notifyDataChanged();
            bottomDialog.show();
        }
    }

    /**
     * 选择的赋值
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent messageEvent) {
        switch (bottomDialog.getType()) {
            case EMERGENCY_CONTACT:
                if (TextUtils.isEmpty(messageEvent.getMessage())) {
                    tvEmergencyRelation.setText("请选择");
                } else {
                    tvEmergencyRelation.setText(messageEvent.getMessage());
                    emergency_type = arrayType[messageEvent.getValue()];
                }
                break;
            case USUAL_CONTACT:
                if (TextUtils.isEmpty(messageEvent.getMessage())) {
                    tvUsualRelation.setText("请选择");
                } else {
                    tvUsualRelation.setText(messageEvent.getMessage());
                    usual_type = arrayType[messageEvent.getValue()];
                }
                break;
        }

    }

    @Override
    public void showLoadingProgressView() {
        loadLoadingView();
    }

    @Override
    public void hideLoadingProgressView() {
        loadContentView();
    }

    @Override
    public void showErrorView() {
        loadErrorView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emergencyContactPresent.detach();
        DialogUtil.hideDialog(bottomDialog);
    }

    /**
     * 请求通讯录权限,同意了就去跳转
     */
    private void requestAddressBookPermission(final int type) {
        if (XXPermissions.isHasPermission(this, Permission.Group.CONTACTS)) {
            startActivityForResult(new Intent(
                    Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), type);
        } else {
            XXPermissions.with(this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.Group.CONTACTS)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                startActivityForResult(new Intent(
                                        Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), type);
                            } else {
                                ToastUtils.showShort("为了您能正常使用，请授权");
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.showShort("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(EmergencyContactActivity.this);
                            } else {
                                ToastUtils.showShort("获取权限失败");
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            btnSave.setEnabled(true);
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                String userNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                userNumber = EncryptUtil.toNum(userNumber);//去除多余空格
                if (userNumber.startsWith("86") && userNumber.length() == 13) {
                    userNumber = userNumber.replace("86", "");//去除国际码
                }
                if (!RegexUtils.isMobileSimple(userNumber)) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
                switch (requestCode) {
                    case EMERGENCY_CONTACT:
                        emergencyName = username;
                        emergencyPhone = userNumber;
                        if (emergencyName.equals(usualName) || emergencyPhone.equals(usualPhone)) {
                            ToastUtils.showShort("不可选择2个一样的紧急联系人，请重新选择");
                            tvEmergencyName.setText("请选择");
                        } else {
                            tvEmergencyName.setText(username + " " + userNumber);
                        }

                        break;
                    case USUAL_CONTACT:
                        usualName = username;
                        usualPhone = userNumber;
                        tvUsualContact.setText(username + " " + userNumber);
                        if (usualName.equals(emergencyName) || usualPhone.equals(emergencyPhone)) {
                            ToastUtils.showShort("不可选择2个一样的紧急联系人，请重新选择");
                            tvUsualContact.setText("请选择");
                        } else {
                            tvUsualContact.setText(username + " " + userNumber);
                        }
                        break;
                }
            }

        }
    }

}
