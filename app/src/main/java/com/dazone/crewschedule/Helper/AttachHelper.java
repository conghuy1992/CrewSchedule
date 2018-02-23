package com.dazone.crewschedule.Helper;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.FileDto;
import com.dazone.crewschedule.Interfaces.Urls;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.DialogUtils;
import com.dazone.crewschedule.Utils.FileUtils;
import com.dazone.crewschedule.Utils.Prefs;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.Locale;

/**
 * Created by David on 2/3/16.
 */
public class AttachHelper {
    public static String getUrl(FileDto attachData) {
        String url = CrewScheduleApplication.getInstance().getmPrefs().getServerSite() + Urls.URL_DOWNLOAD_ATTACH + "sessionId=" + new Prefs().getaccesstoken() + "&languageCode=" + Locale.getDefault().getLanguage().toUpperCase() +
                "&timeZoneOffset=" + TimeUtils.getTimezoneOffsetInMinutes() + "&fileNo=" + attachData.getAttachNo();
        Utils.printLogs("Link tải nè: " + url);
        return url;
    }
    public static void displayDownloadFileDialog(final Context context, final String url, final String name) {

        DialogUtils.normalAlertDialogWithCancel(context, context.getString(R.string.app_name), context.getString(R.string.string_alert_download_attach), context.getString(R.string.string_ok), context.getString(R.string.string_cancel), new DialogUtils.OnAlertDialogViewClickEvent() {
            @Override
            public void onOkClick(DialogInterface alertDialog) {
                String mimeType;
                String serviceString = Context.DOWNLOAD_SERVICE;
                String fileType = name.substring(name.lastIndexOf(".")).toLowerCase();
                final DownloadManager downloadmanager;
                downloadmanager = (DownloadManager) context.getSystemService(serviceString);
                Uri uri = Uri
                        .parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(name);
                int type = FileUtils.getTypeFile(fileType);
                switch (type) {
                    case 1:
                        request.setMimeType(Statics.MIME_TYPE_IMAGE);
                        break;
                    case 2:
                        request.setMimeType(Statics.MIME_TYPE_VIDEO);
                        break;
                    case 3:
                        request.setMimeType(Statics.MIME_TYPE_AUDIO);
                        break;
                    default:
                        try {
                            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
                        } catch (Exception e) {
                            e.printStackTrace();
                            mimeType = Statics.MIME_TYPE_ALL;
                        }
                        if (TextUtils.isEmpty(mimeType)) {
                            request.setMimeType(Statics.MIME_TYPE_ALL);
                        } else {
                            request.setMimeType(mimeType);
                        }
                        break;
                }
                final Long reference = downloadmanager.enqueue(request);

                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                            long downloadId = intent.getLongExtra(
                                    DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                            DownloadManager.Query query = new DownloadManager.Query();
                            query.setFilterById(reference);
                            Cursor c = downloadmanager.query(query);
                            if (c.moveToFirst()) {
                                int columnIndex = c
                                        .getColumnIndex(DownloadManager.COLUMN_STATUS);
                                if (DownloadManager.STATUS_SUCCESSFUL == c
                                        .getInt(columnIndex)) {
                                }
                            }
                        }
                    }
                };
                context.registerReceiver(receiver, new IntentFilter(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }

            @Override
            public void onCancelClick() {

            }
        });
    }
}
