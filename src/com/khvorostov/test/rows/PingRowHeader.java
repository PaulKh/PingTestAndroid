package com.khvorostov.test.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.khvorostov.test.R;
import com.khvorostov.test.model.Ping;

/**
 * Created by Paul on 07/07/15.
 */
public class PingRowHeader implements Row {
    private Ping ping;

    public PingRowHeader(Ping ping) {
        this.ping = ping;
    }

    @Override
    public RowType getRowType() {
        return RowType.PING_ROW_HEADER;
    }

    static class ViewHolder {
        TextView sourceAddress;
        TextView destinationAddress;
    }

    public View getView(LayoutInflater inflater, View convertView) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_ping_header, null);
            viewHolder.destinationAddress = ((TextView) convertView.findViewById(R.id.destinationAddress));
            viewHolder.sourceAddress = ((TextView) convertView.findViewById(R.id.sourceAddress));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sourceAddress.setText("Source : " + ping.getSourceAddress());
        viewHolder.destinationAddress.setText("Destination : " + ping.getDestinationAddress());
        return convertView;
    }
}
