package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.CustomView.AttachItemLinear;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.FileDto;
import com.dazone.crewschedule.Dtos.ScheduleDetailDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Helper.AttachHelper;
import com.dazone.crewschedule.Interfaces.GetScheduleCallBack;
import com.dazone.crewschedule.Interfaces.IOrgDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.DaZoneTextUtils;
import com.dazone.crewschedule.Utils.Utils;
import com.google.gson.Gson;

import java.util.List;

public class ScheduleDetailFragment extends BaseFragment implements GetScheduleCallBack {
    String TAG = "ScheduleDetailFragment";
    int scheduleNo = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scheduleNo = getArguments().getInt(ParamKeys.KEY_SCHEDULE_NO, 0);
//        Log.e(TAG, "scheduleNo:" + scheduleNo);
        ScheduleDetailActivity.scheduleNo = scheduleNo;
        ((ScheduleDetailActivity) getActivity()).setToolBarTitle("");
    }

    public static ScheduleDetailFragment newInstance(int scheduleNo) {
        ScheduleDetailFragment fragment = new ScheduleDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ParamKeys.KEY_SCHEDULE_NO, scheduleNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_detail, container, false);
        initView(v);
        return v;
    }

    TextView start_date_title, end_date_title, start_hour_title, end_hour_title, txtcenter, tv_alldaylong,
            repeat_info, alarm_info, writer_info, content_tv, share_info, tv_center_hour;
    LinearLayout lnl_repeat, lnl_content, lnl_alarm, lnl_share, attach_lnl, row_attach_lnl;

    private void initView(View v) {
        tv_alldaylong = (TextView) v.findViewById(R.id.tv_alldaylong);
        tv_center_hour = (TextView) v.findViewById(R.id.tv_center_hour);
        start_date_title = (TextView) v.findViewById(R.id.start_date_title);
        txtcenter = (TextView) v.findViewById(R.id.txtcenter);
        end_date_title = (TextView) v.findViewById(R.id.end_date_title);
        start_hour_title = (TextView) v.findViewById(R.id.start_hour_title);
        end_hour_title = (TextView) v.findViewById(R.id.end_hour_title);
        repeat_info = (TextView) v.findViewById(R.id.repeat_info);
        content_tv = (TextView) v.findViewById(R.id.content_tv);
        alarm_info = (TextView) v.findViewById(R.id.alarm_info);
        writer_info = (TextView) v.findViewById(R.id.writer_info);
        share_info = (TextView) v.findViewById(R.id.share_info);
        lnl_repeat = (LinearLayout) v.findViewById(R.id.lnl_repeat);
        lnl_content = (LinearLayout) v.findViewById(R.id.lnl_content);
        lnl_alarm = (LinearLayout) v.findViewById(R.id.lnl_alarm);
        lnl_share = (LinearLayout) v.findViewById(R.id.lnl_share);
        attach_lnl = (LinearLayout) v.findViewById(R.id.attach_lnl);
        row_attach_lnl = (LinearLayout) v.findViewById(R.id.row_attach_lnl);
        HttpRequest.getInstance().getSchedule(scheduleNo, this);
//        HttpRequest.getInstance().getSchedule(2785,this);
    }

    private void bindData(ScheduleDetailDto dto) {
        ScheduleDetailActivity.DayContent = dto.getDayContent();
        ScheduleDetailActivity.RegUserNo = dto.getRegUserNo();
        ScheduleDetailActivity.UserName = dto.getUserName();
        ScheduleDetailActivity.PositionName = dto.getPositionName();
        ScheduleDetailActivity.RegDate = dto.getRegDate();
        ScheduleDetailActivity.Title = dto.getTitle();
        ScheduleDetailActivity.Content = dto.getContent();
        ScheduleDetailActivity.TimeContent = dto.getTimeContent();
        ScheduleDetailActivity.RepeatContent = dto.getRepeatContent();

        Utils.printLogs(dto.getDayContent());
        start_date_title.setText(dto.getDayContent().trim().split("From")[0]);
        if (!dto.getDayContent().contains("From")) {
            end_date_title.setText(dto.getDayContent().trim().split("From")[0]);
            txtcenter.setVisibility(View.GONE);
            end_date_title.setVisibility(View.GONE);
        } else if (dto.getDayContent().contains("From")) {
            if (dto.getDayContent().trim().split("From")[1].contains("To")) {
                end_date_title.setText(dto.getDayContent().trim().split("From")[1].trim().split("To")[0].trim());
            } else {
                end_date_title.setText(dto.getDayContent().trim().split("From")[0].trim());
                txtcenter.setVisibility(View.GONE);
                end_date_title.setVisibility(View.GONE);
            }
        }
        tv_center_hour.setText(getResources().getString(R.string.string_space));
        txtcenter.setText(getResources().getString(R.string.string_space));
        ScheduleDetailActivity.fromday = start_date_title.getText().toString();
        ScheduleDetailActivity.today = end_date_title.getText().toString();
        if (dto.getTimeContent().equals("All Day")) {
            start_hour_title.setText("00:00");
            end_hour_title.setText("00:00");
        } else {
            start_hour_title.setText(dto.getTimeContent().split("~")[0]);
            end_hour_title.setText(dto.getTimeContent().split("~")[1]);
        }
        ScheduleDetailActivity.startHour = start_hour_title.getText().toString().trim();
        ScheduleDetailActivity.endHour = end_hour_title.getText().toString().trim();
        if (start_hour_title.getText().toString().trim().equals("00:00") && end_hour_title.getText().toString().trim().equals("00:00")) {
            start_hour_title.setVisibility(View.GONE);
            end_hour_title.setVisibility(View.GONE);
            tv_center_hour.setVisibility(View.GONE);
            tv_alldaylong.setVisibility(View.VISIBLE);
            tv_alldaylong.setText(getResources().getString(R.string.string_all_day_long));
        }
        if (TextUtils.isEmpty(dto.getRepeatContent())) {
            lnl_repeat.setVisibility(View.GONE);
        } else {
            repeat_info.setText(dto.getRepeatContent());
        }
        content_tv.setText(dto.getContent());
        writer_info.setText(dto.getUserName());
        if (dto.getSharers() == null || dto.getSharers().size() == 0) {
            lnl_share.setVisibility(View.GONE);
            ScheduleDetailActivity.share_info = "";
            ScheduleDetailActivity.list_json = "";
        } else {
            share_info.setText(DaZoneTextUtils.getName(((List<IOrgDto>) (List<?>) dto.getSharers())));
            ScheduleDetailActivity.share_info = share_info.getText().toString();
            ScheduleDetailActivity.list_json = new Gson().toJson(dto.getSharers());
        }
        if (!TextUtils.isEmpty(dto.getTitle())) {

            ((ScheduleDetailActivity) getActivity()).setToolBarTitle(dto.getTitle());
        }
        if (dto.getFiles() == null || dto.getFiles().size() == 0) {
            attach_lnl.setVisibility(View.GONE);
        } else {
            for (FileDto fileDto : dto.getFiles()) {
                final AttachItemLinear item = new AttachItemLinear(getActivity(), fileDto);
                item.setTag(fileDto);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttachHelper.displayDownloadFileDialog(getActivity(), AttachHelper.getUrl((FileDto) v.getTag()), ((FileDto) v.getTag()).getFileName());
                    }
                });
                row_attach_lnl.addView(item);
            }
        }
    }

    @Override
    public void onGetScheduleSuccess(ScheduleDetailDto dto) {
        if (dto != null) {
            bindData(dto);
        }
    }

    @Override
    public void onGetScheduleFail(ErrorDto errorDto) {

    }
}
