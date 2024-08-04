package com.tbb.taamcollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<Integer> listDataLotNumbers;
    private List<String> listDataImageUrls; // Updated to store image URLs
    private List<String> listDataVideoUrls; // List for video URLs
    private HashMap<Integer, List<Boolean>> checkBoxState;
    private List<Integer> listIds;

    public CustomExpandableListAdapter(Context context, List<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData,
                                       List<Integer> listDataLotNumbers, List<String> listDataImageUrls,
                                       List<String> listDataVideoUrls, List<Integer> listIds,
                                       HashMap<Integer, List<Boolean>> checkBoxState) {
        this.context = context;
        this.listDataHeader = listDataHeader != null ? listDataHeader : new ArrayList<>();
        this.listDataChild = listChildData != null ? listChildData : new HashMap<>();
        this.listDataLotNumbers = listDataLotNumbers != null ? listDataLotNumbers : new ArrayList<>();
        this.listDataImageUrls = listDataImageUrls != null ? listDataImageUrls : new ArrayList<>();
        this.listDataVideoUrls = listDataVideoUrls != null ? listDataVideoUrls : new ArrayList<>();
        this.listIds = listIds != null ? listIds : new ArrayList<>();
        this.checkBoxState = checkBoxState != null ? checkBoxState : new HashMap<>();
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
        TextView videoTitle = convertView.findViewById(R.id.video_title);
        ImageView videoArrow = convertView.findViewById(R.id.video_arrow);
        VideoView videoContent = convertView.findViewById(R.id.video_content); // Assuming video_content is the ID for the VideoView

        String defaultText = "Unknown";

        if (childPosition == 0) {
            category.setVisibility(View.VISIBLE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            videoTitle.setVisibility(View.GONE);
            videoArrow.setVisibility(View.GONE);
            videoContent.setVisibility(View.GONE);
            category.setText("Category: " + (childText != null ? childText : defaultText));
        } else if (childPosition == 1) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.VISIBLE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);
            videoTitle.setVisibility(View.GONE);
            videoArrow.setVisibility(View.GONE);
            videoContent.setVisibility(View.GONE);
            period.setText("Period: " + (childText != null ? childText : defaultText));
        } else if (childPosition == 2) {
            category.setVisibility(View.GONE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.VISIBLE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.VISIBLE);
            videoTitle.setVisibility(View.GONE);
            videoArrow.setVisibility(View.GONE);
            videoContent.setVisibility(View.GONE);
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
        } else if (childPosition == 3) {
            String videoUrl = listDataVideoUrls.get(groupPosition);

            category.setVisibility(View.GONE);
            period.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);
            descriptionContent.setVisibility(View.GONE);
            descriptionArrow.setVisibility(View.GONE);

            if (videoUrl != null && !videoUrl.isEmpty()) {
                videoTitle.setVisibility(View.VISIBLE);
                videoArrow.setVisibility(View.VISIBLE);
                videoContent.setVisibility(View.GONE);
                videoTitle.setText("Video: ");
                videoTitle.setOnClickListener(v -> {
                    if (videoContent.getVisibility() == View.GONE) {
                        videoContent.setVisibility(View.VISIBLE);
                        videoArrow.setImageResource(R.drawable.ic_arrow_up);

                        // Set the video URL
                        videoContent.setVideoPath(videoUrl);
                        videoContent.start();
                    } else {
                        videoContent.setVisibility(View.GONE);
                        videoArrow.setImageResource(R.drawable.ic_arrow_down);
                        videoContent.stopPlayback();
                    }
                });
            } else {
                videoTitle.setVisibility(View.GONE);
                videoArrow.setVisibility(View.GONE);
                videoContent.setVisibility(View.GONE);
            }
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

        String defaultGroupName = "Unnamed Group";
        String defaultLotNumber = "Lot: 0";

        itemName.setText(headerTitle != null ? headerTitle : defaultGroupName);
        lotNumber.setText("Lot: " + (groupPosition < listDataLotNumbers.size() ? listDataLotNumbers.get(groupPosition) : defaultLotNumber));

        // Set the image resource for the group
        String imageUrl = listDataImageUrls.get(groupPosition);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            groupImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(imageUrl).placeholder(R.drawable.default_image).into(groupImage);
        } else {
            groupImage.setVisibility(View.VISIBLE);
            groupImage.setImageResource(R.drawable.default_image); // Default image
        }
        int itemId = listIds.get(groupPosition);
        List<Boolean> groupCheckBoxState = checkBoxState.get(itemId);
        if (groupCheckBoxState == null) {
            groupCheckBoxState = new ArrayList<>(Collections.nCopies(getChildrenCount(groupPosition), false));
            checkBoxState.put(itemId, groupCheckBoxState);
        }

        itemCheckbox.setOnCheckedChangeListener(null);
        itemCheckbox.setChecked(groupCheckBoxState.get(0)); // Assuming first checkbox state represents the group

        List<Boolean> finalGroupCheckBoxState = groupCheckBoxState;
        itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (int i = 0; i < finalGroupCheckBoxState.size(); i++) {
                finalGroupCheckBoxState.set(i, isChecked);
            }
            notifyDataSetChanged();
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

    public List<Boolean> getCheckboxStates() {
        List<Boolean> allStates = new ArrayList<>();
        for (Integer key : checkBoxState.keySet()) {
            allStates.addAll(checkBoxState.get(key));
        }
        return allStates;
    }
}
