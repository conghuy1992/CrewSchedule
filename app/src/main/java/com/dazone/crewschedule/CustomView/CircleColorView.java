package com.dazone.crewschedule.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.ImageUtils;

/**
 * Created by nguyentiendat on 1/28/16.
 */
public class CircleColorView extends View {
    Context context;
    public CircleColorView(Context context)
    {
        super(context);
        this.context = context;
    }

    public CircleColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(ImageUtils.getColor(CrewScheduleApplication.getInstance().getApplicationContext(), R.color.colorPrimary));
        canvas.drawCircle(x / 2, y / 2, x/2, paint);
    }
}
