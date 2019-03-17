package com.spareroom.speedflatmating.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spareroom.speedflatmating.EventUtils;
import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.model.Event;

import java.util.List;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder> {

    private Context mContext;
    private List<Event> mEventList;
    private EventUtils mEventUtils = new EventUtils();

    public UpcomingEventAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_event, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = mEventList.get(position);

        if (event != null) {

            String formattedStartDate = mEventUtils.getFormattedStartDate(event.getStartTime());
            String formattedEventTime = mEventUtils.getFormattedTime(event.getStartTime(), event.getEndTime());

            holder.textViewEventCost.setText(event.getCost());
            holder.textViewEventVenue.setText(event.getVenue());
            holder.textViewEventLocation.setText(event.getLocation());
            holder.textViewEventDate.setText(formattedStartDate);
            holder.textViewEventTime.setText(formattedEventTime);

            mEventUtils.loadImage(mContext, event.getImageUrl(), holder.imageViewEventImage);
        }
    }

    public void addEvents(List<Event> eventList) {
        this.mEventList = eventList;
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewEventImage;
        private TextView textViewEventCost, textViewEventVenue, textViewEventLocation, textViewEventDate, textViewEventTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewEventImage = itemView.findViewById(R.id.image_view_event_image);
            textViewEventCost = itemView.findViewById(R.id.text_view_event_cost);
            textViewEventVenue = itemView.findViewById(R.id.text_view_event_venue);
            textViewEventLocation = itemView.findViewById(R.id.text_view_event_location);
            textViewEventDate = itemView.findViewById(R.id.text_view_event_date);
            textViewEventTime = itemView.findViewById(R.id.text_view_event_time);
        }
    }
}
