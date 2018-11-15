package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.bean.Province;

import java.util.List;

/**
 * Created by Kitterick on 2017/9/4.
 */

public class ChooseCityDialog extends Dialog {
    private ListView list1, list2, list3;
    private ImageView close;
    private TextView title, address, use;
    List<Province> provinces;
    List<Province.City> citys;
    List<String> areas;
    private int choosePosition1, choosePosition2, choosePosition3;
    private MyAdapter1 myAdapter1;
    private MyAdapter2 myAdapter2;
    private MyAdapter3 myAdapter3;
    List<Province> data;
    private String p, c, a;
    private boolean inited = false;
    private ImageView iv;
    private String titleText;
    private LinearLayout ll_locate;
    private Context context;

    public ChooseCityDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ChooseCityDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.BottomDialog);
    }

    public void setData(List<Province> data) {
        this.data = data;
    }

    public void setTitle(String data) {
        this.titleText = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_chose_city_layout, null);
        setContentView(view);
        initView(view);
        setCancelable(false);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }


    private void initView(View contentView) {
        provinces = data;
        list1 = contentView.findViewById(R.id.list1);
        list2 = contentView.findViewById(R.id.list2);
        list3 = contentView.findViewById(R.id.list3);
        list1.setDivider(new ColorDrawable(Color.parseColor("#d0d0d0")));
        list1.setDividerHeight(1);
        list2.setDivider(new ColorDrawable(Color.parseColor("#d0d0d0")));
        list2.setDividerHeight(1);
        list3.setDivider(new ColorDrawable(Color.parseColor("#d0d0d0")));
        list3.setDividerHeight(1);
        ll_locate = contentView.findViewById(R.id.ll_locate);
        myAdapter1 = new MyAdapter1();
        myAdapter2 = new MyAdapter2();
        myAdapter3 = new MyAdapter3();
        list1.setAdapter(myAdapter1);
        list2.setAdapter(myAdapter2);
        list3.setAdapter(myAdapter3);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosePosition1 = position;
                myAdapter1.notifyDataSetChanged();
                list2.setVisibility(View.VISIBLE);
                list3.setVisibility(View.GONE);
                citys = ((Province) myAdapter1.getItem(position)).city;
                myAdapter2.notifyDataSetChanged();
                if (citys != null && citys.size() > 0) {
                    list2.smoothScrollToPosition(0);
                }
                p = ((Province) myAdapter1.getItem(position)).name;
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosePosition2 = position;
                myAdapter2.notifyDataSetChanged();
                list2.setVisibility(View.VISIBLE);
                list3.setVisibility(View.VISIBLE);
                areas = ((Province.City) myAdapter2.getItem(position)).area;
                myAdapter3.notifyDataSetChanged();
                if (areas != null && areas.size() > 0) {
                    list3.smoothScrollToPosition(0);
                }
                c = ((Province.City) myAdapter2.getItem(position)).name;
            }
        });
        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosePosition3 = position;
                myAdapter3.notifyDataSetChanged();
                a = ((String) myAdapter3.getItem(position));
                if (null != selectCallBack) {
                    selectCallBack.select(p + " ", c + " ", a, null);
                }
                ChooseCityDialog.this.dismiss();
            }
        });
        iv = contentView.findViewById(R.id.iv_close);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCityDialog.this.dismiss();
            }
        });
        title = contentView.findViewById(R.id.title);
        if (!TextUtils.isEmpty(titleText)) {
            title.setText(titleText);
        }
        address = contentView.findViewById(R.id.tv_address);
        use = contentView.findViewById(R.id.tv_use);
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != selectCallBack && null != JsmApplication.getInstance().aMapLocation) {
                    selectCallBack.select((JsmApplication.getInstance().aMapLocation.getProvince()) + " ",
                            (JsmApplication.getInstance().aMapLocation.getCity()) + " ",
                            JsmApplication.getInstance().aMapLocation.getDistrict(),
                            JsmApplication.getInstance().aMapLocation.getStreet() + JsmApplication.getInstance().aMapLocation.getStreetNum());
                }
                ChooseCityDialog.this.dismiss();
            }
        });
        getLocation();
    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            if (provinces != null) {
                return provinces.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (provinces != null) {
                return provinces.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Province item = (Province) getItem(position);
            ViewHolder1 holder;
            if (convertView == null) {
                holder = new ViewHolder1();
                convertView = View.inflate(context, R.layout.item_city_pick, null);
                holder.tv_provinces_item = convertView.findViewById(R.id.tv_fool_item);
                holder.v1 = convertView.findViewById(R.id.v1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder1) convertView.getTag();
            }
            if (!inited) {
                holder.v1.setVisibility(View.VISIBLE);
            } else {
                holder.v1.setVisibility(View.GONE);
            }
            holder.tv_provinces_item.setSelected(false);

            if (choosePosition1 == position) {
                holder.tv_provinces_item.setSelected(true);
                holder.tv_provinces_item.setTextColor(context.getResources().getColor(R.color.theme_color));
            } else {
                holder.tv_provinces_item.setTextColor(Color.parseColor("#666666"));
            }
            holder.tv_provinces_item.setText(item.name);
            return convertView;
        }
    }

    class MyAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            if (citys != null) {
                return citys.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (citys != null) {
                return citys.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Province.City item = (Province.City) getItem(position);
            ViewHolder2 holder;
            inited = true;
            if (convertView == null) {
                holder = new ViewHolder2();
                convertView = View.inflate(context, R.layout.item_city_pick, null);
                holder.tv_citys_item = convertView.findViewById(R.id.tv_fool_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder2) convertView.getTag();
            }
            holder.tv_citys_item.setSelected(false);
            if (choosePosition2 == position) {
                holder.tv_citys_item.setSelected(true);
                holder.tv_citys_item.setTextColor(context.getResources().getColor(R.color.theme_color));
            } else {
                holder.tv_citys_item.setTextColor(Color.parseColor("#666666"));
            }
            holder.tv_citys_item.setText(item.name);
            return convertView;
        }
    }

    class MyAdapter3 extends BaseAdapter {

        @Override
        public int getCount() {
            if (areas != null) {
                return areas.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (areas != null) {
                return areas.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = (String) getItem(position);
            ViewHolder3 holder;
            if (convertView == null) {
                holder = new ViewHolder3();
                convertView = View.inflate(context, R.layout.item_city_pick, null);
                holder.tv_areas_item = convertView.findViewById(R.id.tv_fool_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder3) convertView.getTag();
            }
            holder.tv_areas_item.setSelected(false);
            if (choosePosition3 == position) {
                holder.tv_areas_item.setSelected(true);
                holder.tv_areas_item.setTextColor(context.getResources().getColor(R.color.theme_color));
            } else {
                holder.tv_areas_item.setTextColor(Color.parseColor("#666666"));
            }
            holder.tv_areas_item.setText(item);
            return convertView;
        }
    }

    class ViewHolder1 {
        TextView tv_provinces_item;
        View v1;
    }

    class ViewHolder2 {
        TextView tv_citys_item;
    }

    class ViewHolder3 {
        TextView tv_areas_item;
    }

    private SelectCallBack selectCallBack;

    public void setCallBack(SelectCallBack selectCallBack) {
        this.selectCallBack = selectCallBack;
    }

    public interface SelectCallBack {
        void select(String province, String city, String area, String details);
    }

    private void getLocation() {
        if (null != JsmApplication.getInstance().aMapLocation) {
            String add = JsmApplication.getInstance().aMapLocation.getStreet() + JsmApplication.getInstance().aMapLocation.getStreetNum();
            address.setText(add);
            ll_locate.setVisibility(View.VISIBLE);
        } else {
            address.setText("定位失败");
            ll_locate.setVisibility(View.GONE);
        }
    }
}