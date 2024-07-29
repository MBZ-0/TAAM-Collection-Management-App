package com.tbb.taamcollection;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<Integer> listDataLotNumbers;
    private List<String> listDataUrls; // List for URLs

    public CustomExpandableListAdapter(Context context, List<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData,
                                       List<Integer> listDataLotNumbers, List<String> listDataUrls) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.listDataLotNumbers = listDataLotNumbers;
        this.listDataUrls = listDataUrls; // Initialize the list of URLs
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
        return 3; // We now have 3 child items: Category, Period, Description
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
            convertView = inflater.inflate(R.layout.expandable_list_parent_item, null);
        }

        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView lotNumber = convertView.findViewById(R.id.lot_number);
        CheckBox itemCheckbox = convertView.findViewById(R.id.item_checkbox);
        ImageView groupImage = convertView.findViewById(R.id.group_image);
        VideoView groupVideo = convertView.findViewById(R.id.group_video);

        // Set default values for group items
        String defaultGroupName = "Unnamed Group";
        String defaultLotNumber = "Lot: 0";

        itemName.setText(headerTitle != null ? headerTitle : defaultGroupName);
        lotNumber.setText("Lot: " + (listDataLotNumbers.get(groupPosition) != null ? listDataLotNumbers.get(groupPosition) : 0));

        // Set the image or video resource for the group
        String url = listDataUrls.get(groupPosition);
        if (url != null && !url.isEmpty()) {
            int urlStatus = getUrlStatus(url);
            if (urlStatus == 0) {
                groupImage.setVisibility(View.VISIBLE);
                groupVideo.setVisibility(View.GONE);
                Glide.with(context).load(url).placeholder(R.drawable.default_image).into(groupImage);
            } else if (urlStatus == 1) {
                groupImage.setVisibility(View.GONE);
                groupVideo.setVisibility(View.VISIBLE);
                Uri videoUri = Uri.parse(url);
                groupVideo.setVideoURI(videoUri);
                groupVideo.setOnPreparedListener(mp -> mp.start());
            } else {
                groupImage.setVisibility(View.VISIBLE);
                groupVideo.setVisibility(View.GONE);
                groupImage.setImageResource(R.drawable.default_image); // Default image
            }
        } else {
            groupImage.setVisibility(View.VISIBLE);
            groupVideo.setVisibility(View.GONE);
            groupImage.setImageResource(R.drawable.default_image); // Default image
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

    // Helper method to determine URL status
    private int getUrlStatus(String url) {
        if (url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".bmp") || url.endsWith(".svg")) {
            return 0;
        } else if (url.endsWith(".mp4") || url.endsWith(".avi") || url.endsWith(".mov") || url.endsWith(".mkv") || url.endsWith(".flv") || url.endsWith(".wmv")) {
            return 1;
        } else {
            return -1;
        }
    }
}
