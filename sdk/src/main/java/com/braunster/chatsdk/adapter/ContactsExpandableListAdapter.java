package com.braunster.chatsdk.adapter;

/**
 * Created by itzik on 6/17/2014.
 */


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.braunster.chatsdk.R;
import com.braunster.chatsdk.Utils.ImageLoading.ImageLoader;
import com.braunster.chatsdk.Utils.volley.VolleyUtills;
import com.braunster.chatsdk.dao.BUser;

import java.util.HashMap;
import java.util.List;

public class ContactsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<BUser>> _listDataChild;

    private TextView txtContactName;
    private TextView txtHeader;
    private ImageView imgPicture;
    private ImageLoader imageLoader;
    private LayoutInflater inflater;
    private BUser user;

    public ContactsExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<BUser>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(context);
    }

    @Override
    public BUser getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        user = getChild(groupPosition, childPosition);
        final String childText = user.getName();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_contact, null);
        }

        txtContactName = (TextView) convertView.findViewById(R.id.txt_name);
        imgPicture = (ImageView) convertView.findViewById(R.id.img_profile_picture);

        txtContactName.setText(childText);

        if (user.pictureExist)
        {
            imgPicture.setImageResource(0);
//            imageLoader.DisplayImage(user.pictureURL, imgPicture);
            VolleyUtills.getImageLoader().get(user.pictureURL,
                                VolleyUtills.getImageLoader().getImageListener(imgPicture,
                                        R.drawable.icn_user_x_2, android.R.drawable.stat_notify_error));
        }
        else
        {
            imgPicture.setImageResource(R.drawable.icn_user_x_2);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this._listDataChild.size() == 0)
            return 0;

        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        txtHeader = (TextView) convertView.findViewById(R.id.txt_head);
        txtHeader.setTypeface(null, Typeface.BOLD);
        txtHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}