package com.car.front.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.car.front.R;
import com.car.front.bean.FileInfo;
import com.car.front.glide.GlideUtils;
import com.car.front.ui.activity.PhotoActivity;
import com.car.front.util.MeasureWidthUtils;

import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/29
 * @name:PhotoNewAdapter
 */
public class PhotoNewAdapter extends AutoRVAdapter {
    private List<FileInfo> infos;
    private PhotoActivity activity;

    public PhotoNewAdapter(PhotoActivity activity, List<FileInfo> list) {
        super(activity, list);
        this.infos = list;
        this.activity = activity;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_pic;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        ImageView imageView = vh.getImageView(R.id.iv_photo);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        int width= MeasureWidthUtils.getScreenWidth(activity);
        layoutParams.width = (width-60)/2;
        layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        imageView.setLayoutParams(layoutParams);
        GlideUtils.setImageUrl(infos.get(position).getPhotoFile(), imageView);
        vh.getTextView(R.id.text_jcompany).setText(infos.get(position).getTitle());

    }
}
