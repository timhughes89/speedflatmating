package com.spareroom.speedflatmating.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventUtils {

    private SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());

    public String getFormattedStartDate(String startTime) {

        String formattedStartDate = null;

        try {
            Date startDate = utcFormatter.parse(startTime);
            formattedStartDate = dateFormatter.format(startDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedStartDate;
    }

    public String getFormattedEndDate(String endTime) {

        String formattedEndDate = null;

        try {
            Date endDate = utcFormatter.parse(endTime);
            formattedEndDate = dateFormatter.format(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedEndDate;
    }

    public String getFormattedTime(String startTime, String endTime) {

        String formattedTime = null;

        try {
            Date startDate = utcFormatter.parse(startTime);
            Date endDate = utcFormatter.parse(endTime);

            startTime = timeFormatter.format(startDate);
            endTime = timeFormatter.format(endDate);

            formattedTime = startTime + " - " + endTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).centerCrop().into(imageView);
    }
}
