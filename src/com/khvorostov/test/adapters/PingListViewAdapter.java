package com.khvorostov.test.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.khvorostov.test.model.Ping;
import com.khvorostov.test.rows.PingRowContent;
import com.khvorostov.test.rows.PingRowHeader;
import com.khvorostov.test.rows.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 07/07/15.
 */
public class PingListViewAdapter extends BaseExpandableListAdapter {

    private final List<Ping> pings;
    private Activity activity;

    public PingListViewAdapter(Activity activity) {
        this.pings = new ArrayList<>();
        this.activity = activity;
    }

    public void deleteRow(Ping ping) {
        pings.remove(ping);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        pings.clear();
    }

    public void addRow(Ping ping) {
        pings.add(ping);
    }

    @Override
    public int getGroupCount() {
        return pings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return "";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "";
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Ping ping = pings.get(groupPosition);
        PingRowHeader pingRow = new PingRowHeader(ping);
        return pingRow.getView(activity.getLayoutInflater(), convertView);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Ping ping = pings.get(groupPosition);
        PingRowContent pingRow = new PingRowContent(ping);
        return pingRow.getView(activity.getLayoutInflater(), convertView);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}