package com.zst.ynh_base.net.download;

/**
 * Created by kevin on 17/10/16.
 */

public abstract class DownLoadCallBack {

    public void onStart(String key) {
    }

    public void onCancel() {
    }

    public void onCompleted() {
    }


    /**
     * Note : the Fun run not MainThred
     *
     * @param e
     */
    abstract public void onError(Throwable e);

    public void onProgress(String key, int progress, long fileSizeDownloaded, long totalSize) {
    }

    /**
     * Note : the Fun run UIThred
     *
     * @param path
     * @param name
     * @param fileSize
     */
    abstract public void onSuccess(String key, String path, String name, long fileSize);
}

