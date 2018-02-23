package com.dazone.crewschedule.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.dazone.crewschedule.Adapters.SelectListAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.DialogFragment.DatePickerFragment;
import com.dazone.crewschedule.Dtos.MenuDto;
import com.dazone.crewschedule.Fragment.MonthPickerFragmentNew;
import com.dazone.crewschedule.Interfaces.DatePickerDialogListener;
import com.dazone.crewschedule.Interfaces.MenuDrawItem;
import com.dazone.crewschedule.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by david on 12/25/15.
 */
public class DialogUtils {
    public static void normalAlertDialog(final Context context, String title, String message, final OnAlertDialogViewClickEvent clickEvent) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (clickEvent != null) {
                    clickEvent.onOkClick(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    public static void normalAlertDialogWithCancel(final Context context, String title, String message, String okButton, String noButton, final OnAlertDialogViewClickEvent clickEvent) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(noButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (clickEvent != null) {
                    clickEvent.onCancelClick();
                }
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton(okButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (clickEvent != null) {
                    clickEvent.onOkClick(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    public interface OnAlertDialogViewClickEvent {
        void onOkClick(DialogInterface alertDialog);

        void onCancelClick();
    }


    public static void singleSelectDialogCustom(final Context context, List<MenuDrawItem> itemList, DialogInterface.OnClickListener listener, String title) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(context).setTitle(title);
        SelectListAdapter adapter = new SelectListAdapter(context, itemList);
        adb.setAdapter(adapter, listener);
        adb.show();
    }

    public static void displaySingleChoiceList(final Context context, List<String> itemList, DialogInterface.OnClickListener listener, String title) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(context).setTitle(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_item, itemList);
        adb.setAdapter(adapter, listener);
        adb.show();
    }

    public static void showGetImageDialog(final Activity activity) {
        if (null == activity)
            return;
        ArrayList<MenuDrawItem> list = new ArrayList<>();
        list.add(new MenuDto(R.string.string_take_photo, R.drawable.ic_photo_camera_black_24dp));
        list.add(new MenuDto(R.string.string_get_from_gallery, R.drawable.ic_photo_black_24dp));
        DialogInterface.OnClickListener onclick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(takePicture, Statics.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);//zero can be replaced with any action code
                        break;
                    case 1:
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(pickPhoto, Statics.IMAGE_PICKER_SELECT);
                        break;
                }
            }
        };
        singleSelectDialogCustom(activity, list, onclick, activity.getString(R.string.app_name));
    }

    public static void showGetImageDialogFragment(final Fragment activity, Context context) {
        if (null == activity)
            return;
        ArrayList<MenuDrawItem> list = new ArrayList<>();
        list.add(new MenuDto(R.string.string_take_photo, R.drawable.ic_photo_camera_black_24dp));
        list.add(new MenuDto(R.string.string_get_from_gallery, R.drawable.ic_photo_black_24dp));
        DialogInterface.OnClickListener onclick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(takePicture, Statics.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);//zero can be replaced with any action code
                        break;
                    case 1:
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(pickPhoto, Statics.IMAGE_PICKER_SELECT);
                        break;
                }
            }
        };
        singleSelectDialogCustom(context, list, onclick, activity.getString(R.string.app_name));
    }

    public static DialogFragment openCalendarFragmentDialog(FragmentManager fm, long time, int type) {
//        type 0 date, 1 month

        DialogFragment dateDialog;
        if (type == 0) {
            dateDialog = new DatePickerFragment().newInstance(time);
        } else {
            dateDialog = new MonthPickerFragmentNew().newInstance(time);
        }
        dateDialog.show(fm, Statics.DIALOG_DATE);
        return dateDialog;
    }

    public static void openCalendarDialog(Context context, String title, long time, final int type, final DatePickerDialogListener callback) {
        final View v = LayoutInflater.from(context).inflate(R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);

        final Calendar c = Calendar.getInstance();
        if (time != 0) {
            c.setTimeInMillis(time);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

            }
        });
        Dialog dialog = new AlertDialog.Builder(context)
                .setView(v)
                .setTitle(R.string.date_picker_title_date)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, datePicker.getYear());
                        cal.set(Calendar.MONTH, datePicker.getMonth());
                        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        callback.onFinishEditDialog(cal, type);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

}
