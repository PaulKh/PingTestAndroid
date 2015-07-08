package com.khvorostov.test.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.khvorostov.test.R;
import com.khvorostov.test.model.Ping;

/**
 * Created by Paul on 07/07/15.
 */
public class PingRowContent implements Row{
    private Ping ping;

    public PingRowContent(Ping ping) {
        this.ping = ping;
    }

    @Override
    public RowType getRowType() {
        return RowType.PING_ROW_CONTENT;
    }
    static class ViewHolder {
        TextView resultTextview;
    }
    public View getView(LayoutInflater inflater, View convertView) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.ping_row_content, null);
            viewHolder.resultTextview = ((TextView) convertView.findViewById(R.id.result_content));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.resultTextview.setText(ping.getPingResult());
        return convertView;
    }
}
