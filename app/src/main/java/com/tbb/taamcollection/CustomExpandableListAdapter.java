package com.tbb.taamcollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    public CustomExpandableListAdapter(Context context, List<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView category = convertView.findViewById(R.id.category);
        TextView period = convertView.findViewById(R.id.period);
        TextView descriptionTitle = convertView.findViewById(R.id.description_title);
        TextView descriptionContent = convertView.findViewById(R.id.description_content);
        TextView imageVideoTitle = convertView.findViewById(R.id.image_video_title);
        ImageView imageVideoContent = convertView.findViewById(R.id.image_video_content);

        // Set text or other properties of the views
        category.setText("Category: " + childText);
        period.setText("Period: " + childText);
        descriptionTitle.setText("Description");
        descriptionContent.setText("Detailed description: " + childText);
        imageVideoTitle.setText("Image/Video");

        // Handle visibility toggling for Description
        if (childText.equals("Description")) {
            descriptionTitle.setVisibility(View.VISIBLE);
            descriptionContent.setVisibility(View.GONE);

            descriptionTitle.setOnClickListener(v -> {
                if (descriptionContent.getVisibility() == View.GONE) {
                    descriptionContent.setVisibility(View.VISIBLE);
                } else {
                    descriptionContent.setVisibility(View.GONE);
                }
            });
        } else {
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
        }

        // Handle visibility toggling for Image/Video
        if (childText.equals("Image/Video")) {
            imageVideoTitle.setVisibility(View.VISIBLE);
            imageVideoContent.setVisibility(View.GONE);

            imageVideoTitle.setOnClickListener(v -> {
                if (imageVideoContent.getVisibility() == View.GONE) {
                    imageVideoContent.setVisibility(View.VISIBLE);
                } else {
                    imageVideoContent.setVisibility(View.GONE);
                }
            });
        } else {
            imageVideoTitle.setVisibility(View.GONE);
            imageVideoContent.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }

        TextView groupText = convertView.findViewById(R.id.group_text);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView lotNumber = convertView.findViewById(R.id.lot_number);
        CheckBox groupCheckbox = convertView.findViewById(R.id.group_checkbox);

        groupText.setText(headerTitle);
        itemName.setText("Item: " + headerTitle);
        lotNumber.setText("Lot: " + headerTitle);

        // Handle CheckBox state changes
        groupCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle CheckBox state changes
        });

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
