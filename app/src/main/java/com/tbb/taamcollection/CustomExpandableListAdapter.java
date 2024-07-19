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
        ImageView descriptionArrow = convertView.findViewById(R.id.description_arrow);
        TextView imageVideoTitle = convertView.findViewById(R.id.image_video_title);
        ImageView imageVideoContent = convertView.findViewById(R.id.image_video_content);
        ImageView imageVideoArrow = convertView.findViewById(R.id.image_video_arrow);

        // Set text or other properties of the views
        if (childPosition == 0) {
            category.setVisibility(View.VISIBLE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            imageVideoTitle.setVisibility(View.GONE);
            imageVideoContent.setVisibility(View.GONE);
            imageVideoArrow.setVisibility(View.GONE);
            category.setText("Category: " + childText);
        } else if (childPosition == 1) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.VISIBLE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            imageVideoTitle.setVisibility(View.GONE);
            imageVideoContent.setVisibility(View.GONE);
            imageVideoArrow.setVisibility(View.GONE);
            period.setText("Period: " + childText);
        } else if (childPosition == 2) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.VISIBLE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.VISIBLE);
            imageVideoTitle.setVisibility(View.GONE);
            imageVideoContent.setVisibility(View.GONE);
            imageVideoArrow.setVisibility(View.GONE);
            descriptionTitle.setText("Description");
            descriptionContent.setText("Detailed description: " + childText);

            descriptionTitle.setOnClickListener(v -> {
                if (descriptionContent.getVisibility() == View.GONE) {
                    descriptionContent.setVisibility(View.VISIBLE);
                    descriptionArrow.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    descriptionContent.setVisibility(View.GONE);
                    descriptionArrow.setImageResource(R.drawable.ic_arrow_down);
                }
            });
        } else if (childPosition == 3) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            imageVideoTitle.setVisibility(View.VISIBLE);
            imageVideoContent.setVisibility(View.GONE);
            imageVideoArrow.setVisibility(View.VISIBLE);
            imageVideoTitle.setText("Image/Video");

            imageVideoTitle.setOnClickListener(v -> {
                if (imageVideoContent.getVisibility() == View.GONE) {
                    imageVideoContent.setVisibility(View.VISIBLE);
                    imageVideoArrow.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    imageVideoContent.setVisibility(View.GONE);
                    imageVideoArrow.setImageResource(R.drawable.ic_arrow_down);
                }
            });
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 4; // We always have 4 child items: Category, Period, Description, and Image/Video
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

        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView lotNumber = convertView.findViewById(R.id.lot_number);
        CheckBox itemCheckbox = convertView.findViewById(R.id.item_checkbox);

        itemName.setText(headerTitle);
        lotNumber.setText("Lot: " + headerTitle);

        // Update the CheckBox state if needed
        itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
