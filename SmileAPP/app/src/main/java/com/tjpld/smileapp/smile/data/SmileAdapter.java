package com.tjpld.smileapp.smile.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.model.SmileContentModel;

import java.util.List;

/**
 * SmileContent的数据源
 */
public class SmileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SmileContentModel> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public SmileAdapter(Context mContext, List<SmileContentModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SmileItemViewHolder(inflater.inflate(R.layout.list_smile_card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SmileItemViewHolder) holder).tv_subtime.setText(mData.get(position).getReporttime());
        ((SmileItemViewHolder) holder).tv_smile_content.setText(mData.get(position).getContent());
        ((SmileItemViewHolder) holder).tv_subhead.setText(mData.get(position).getReportname());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class SmileItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_subhead;//用户头像
        private TextView tv_subhead, tv_subtime, tv_smile_content;//上传人，上传时间，上传内容

        public SmileItemViewHolder(View itemView) {
            super(itemView);
            img_subhead = (ImageView) itemView.findViewById(R.id.img_subhead);
            tv_subhead = (TextView) itemView.findViewById(R.id.tv_subhead);
            tv_smile_content = (TextView) itemView.findViewById(R.id.tv_smile_content);
            tv_subtime = (TextView) itemView.findViewById(R.id.tv_subtime);
        }
    }
}
