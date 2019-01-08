package com.zst.ynh.widget.repayment.repaymentfragment;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.zst.ynh.widget.repayment.repaymentfragment.ListType.OTHER_ORDERS;
import static com.zst.ynh.widget.repayment.repaymentfragment.ListType.OTHER_REPAYMENT;
import static com.zst.ynh.widget.repayment.repaymentfragment.ListType.YNH_ORDERS;
import static com.zst.ynh.widget.repayment.repaymentfragment.ListType.YNH_REPAYMENT;


@IntDef({YNH_REPAYMENT,YNH_ORDERS,OTHER_REPAYMENT,OTHER_ORDERS})
@Retention(RetentionPolicy.SOURCE)
public @interface ListType {
    public static final int YNH_REPAYMENT=0;
    public static final int YNH_ORDERS=1;
    public static final int OTHER_REPAYMENT=2;
    public static final int OTHER_ORDERS=3;
}
