package com.ppp_admin.ppp_order_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10/5/2015.
 */
public class LoginAdapter extends ArrayAdapter<LoginUser_Data>
{

    ArrayList<LoginUser_Data> list;
    private static Context context;

    public LoginAdapter(Context context, int resource) {
        super(context, resource);
        list = new ArrayList<LoginUser_Data>();
    }

    public ArrayList<LoginUser_Data> getList()
    {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LoginUser_Data getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_sign__up__page, viewGroup, false);

        TextView tv = (TextView) row.findViewById(R.id.text_view_final_wish);
        tv.setText(list.get(i).getUserName());

        return row;
    }
}
