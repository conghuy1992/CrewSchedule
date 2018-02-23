package com.dazone.crewschedule.RCVViewHolders;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Interfaces.DatePickerFragmentDialogListener;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by david on 12/25/15.
 */
public class MonthPickerViewHolder extends ItemViewHolder<Long> {
    TextView tv_month_num,tv_month_char;
    public LinearLayout lnl_daily_main,schedule_lnl,lnl_daily_bound;

    public MonthPickerViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        tv_month_num = (TextView)v.findViewById(R.id.tv_month_num);
        tv_month_char = (TextView)v.findViewById(R.id.tv_month_char);
    }

    DatePickerFragmentDialogListener mListener;
    public void setListener(DatePickerFragmentDialogListener mListener)
    {
        this.mListener = mListener;
    }
    private long month_millis;
    public void setMonth_millis(long month_millis)
    {
        this.month_millis = month_millis;
    }
    @Override
    public void bindData(Long dto) {

        tv_month_num.setText(TimeUtils.showTime(dto, Statics.DATE_FORMAT_MONTH));
        tv_month_char.setText(TimeUtils.showTime(dto, Statics.DATE_FORMAT_MONTH_NAME));
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dto);
        final Calendar  cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(month_millis);

        if(cal1.get(Calendar.MONTH)==cal.get(Calendar.MONTH)
                &&cal1.get(Calendar.YEAR)==cal.get(Calendar.YEAR))
        {
            v.setBackgroundColor(ImageUtils.getColor(v.getContext(),R.color.colorPrimary));
            tv_month_char.setTextColor(Color.WHITE);
            tv_month_num.setTextColor(Color.WHITE);
        }
        else
        {
            v.setBackgroundColor(Color.WHITE);
            tv_month_num.setTextColor(Color.BLACK);
            tv_month_char.setTextColor(ImageUtils.getColor(v.getContext(),R.color.text_popup_month_c_color));
        }
        initClickAction(dto);
    }

    private void initClickAction(final long dto)
    {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(dto);
                    mListener.onFinishEditDialog(cal);
                }
            }
        });
    }
}
