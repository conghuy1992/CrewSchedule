package com.dazone.crewschedule.CustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dazone.crewschedule.Dtos.FileDto;
import com.dazone.crewschedule.R;

/**
 * Created by THANHTUNG on 18/12/2015.
 */
public class AttachItemLinear extends LinearLayout {
    public ImageButton imgMailCreateAttachDownload, imgMailCreateAttachDelete;
    public TextView txtMailCreateAttachFileName, txtMailCreateAttachFileSize;

    public void setImageButtonDelete(ImageButton imgMailCreateAttachDelete) {
        this.imgMailCreateAttachDelete = imgMailCreateAttachDelete;
    }

    public void setImageButtonDownLoad(ImageButton imgMailCreateAttachDownload) {
        this.imgMailCreateAttachDownload = imgMailCreateAttachDownload;
    }

    public ImageButton getImageButtonDelete() {
        return imgMailCreateAttachDelete;
    }

    public ImageButton getImageButtonDownLoad() {
        return imgMailCreateAttachDownload;
    }

    public AttachItemLinear(Context context, FileDto file) {
        super(context);
        init(context, file);
    }

    private void init(Context context, FileDto attachData) {
        LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.row_attach_item, this, true);
        this.imgMailCreateAttachDownload = (ImageButton) findViewById(R.id.imgMailCreateItemAttachDownload);
        this.imgMailCreateAttachDelete = (ImageButton) findViewById(R.id.imgMailCreateItemAttachDelete);
        this.txtMailCreateAttachFileName = (TextView) findViewById(R.id.txtMailCreateItemAttachFileName);
        this.txtMailCreateAttachFileSize = (TextView) findViewById(R.id.txtMailCreateItemAttachFileSize);
        setImageButtonDelete(imgMailCreateAttachDelete);
        setImageButtonDownLoad(imgMailCreateAttachDownload);
        this.txtMailCreateAttachFileName.setText( attachData.getFileName());
        this.txtMailCreateAttachFileSize.setText(" ("+ attachData.getFileLengthToString()+")");
    }
}
