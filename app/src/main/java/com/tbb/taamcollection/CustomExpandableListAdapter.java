package com.tbb.taamcollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<Integer> listDataLotNumbers;
    private List<Integer> listDataImages; // List for image resources
    private List<Boolean> checkboxStates; // List for checkbox states
    private List<Integer> listIds;

    public CustomExpandableListAdapter(Context context, List<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData,
                                       List<Integer> listDataLotNumbers, List<Integer> listDataImages,
                                       List<Integer> listIds) {
        this.context = context;
        this.listDataHeader = listDataHeader != null ? listDataHeader : new ArrayList<>();
        this.listDataChild = listChildData != null ? listChildData : new HashMap<>();
        this.listDataLotNumbers = listDataLotNumbers != null ? listDataLotNumbers : new ArrayList<>();
        this.listDataImages = listDataImages != null ? listDataImages : new ArrayList<>();
        this.checkboxStates = new ArrayList<>(Collections.nCopies(this.listDataHeader.size(), false)); // Initialize checkbox states
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<String> childList = this.listDataChild.get(this.listDataHeader.get(groupPosition));
        return childList != null ? childList.get(childPosition) : null;
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
            convertView = inflater.inflate(R.layout.expandable_list_child_item, null);
        }

        TextView category = convertView.findViewById(R.id.category);
        TextView period = convertView.findViewById(R.id.period);
        TextView descriptionTitle = convertView.findViewById(R.id.description_title);
        TextView descriptionContent = convertView.findViewById(R.id.description_content);
        ImageView descriptionArrow = convertView.findViewById(R.id.description_arrow);

        // Set default text for missing child items
        String defaultText = "Unknown";

        // Set text or other properties of the views
        if (childPosition == 0) {
            category.setVisibility(View.VISIBLE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            category.setText("Category: " + (childText != null ? childText : defaultText));
        } else if (childPosition == 1) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.VISIBLE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            period.setText("Period: " + (childText != null ? childText : defaultText));
        } else if (childPosition == 2) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.VISIBLE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.VISIBLE);
            descriptionTitle.setText("Description: ");
            descriptionContent.setText("Detailed description: " + (childText != null ? childText : defaultText));

            descriptionTitle.setOnClickListener(v -> {
                if (descriptionContent.getVisibility() == View.GONE) {
                    descriptionContent.setVisibility(View.VISIBLE);
                    descriptionArrow.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    descriptionContent.setVisibility(View.GONE);
                    descriptionArrow.setImageResource(R.drawable.ic_arrow_down);
                }
            });
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<String> childList = this.listDataChild.get(this.listDataHeader.get(groupPosition));
        return childList != null ? childList.size() : 0;
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
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_parent_item, null);
        }

        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView lotNumber = convertView.findViewById(R.id.lot_number);
        CheckBox itemCheckbox = convertView.findViewById(R.id.item_checkbox);
        ImageView groupImage = convertView.findViewById(R.id.group_image);

        // Set default values for group items
        String defaultGroupName = "Unnamed Group";
        String defaultLotNumber = "Lot: 0";

        itemName.setText(headerTitle != null ? headerTitle : defaultGroupName);

        // Set the image resource for the group
        if (groupPosition < listDataImages.size()) {
            groupImage.setImageResource(listDataImages.get(groupPosition));
        } else {
            groupImage.setImageResource(R.drawable.default_image); // Default image
        }

        // Set the checkbox state based on the list
        try {
            itemCheckbox.setOnCheckedChangeListener(null);
            itemCheckbox.setChecked(checkboxStates.get(groupPosition));
            itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkboxStates.set(groupPosition, isChecked);
            });
        } catch (IndexOutOfBoundsException e) {
            Log.e("CustomExpandableListAdapter", "IndexOutOfBoundsException in getGroupView: " + e.getMessage());
            // Initialize checkboxStates correctly
            checkboxStates = new ArrayList<>(Collections.nCopies(this.listDataHeader.size(), false));
            itemCheckbox.setChecked(checkboxStates.get(groupPosition));
            itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkboxStates.set(groupPosition, isChecked);
            });
        }

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

    // Method to get the list of all checkbox states
    public List<Boolean> getCheckboxStates() {
        return checkboxStates;
    }
}