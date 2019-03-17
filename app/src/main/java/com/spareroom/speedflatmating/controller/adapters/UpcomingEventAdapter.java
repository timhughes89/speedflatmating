package com.spareroom.speedflatmating.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spareroom.speedflatmating.utils.EventUtils;
import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.model.Event;
import com.spareroom.speedflatmating.ui.OnItemClickListener;

import java.util.List;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder> {

    private Context mContext;
    private List<Event> mEventList;
    private EventUtils mEventUtils = new EventUtils();
    private OnItemClickListener mOnItemClickListener;

    public UpcomingEventAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_event, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Event event = mEventList.get(position);

        if (event != null) {

            String formattedStartDate = mEventUtils.getFormattedStartDate(event.getStartTime());
            String formattedEventTime = mEventUtils.getFormattedTime(event.getStartTime(), event.getEndTime());

            holder.textViewEventCost.setText(event.getCost());
            holder.textViewEventVenue.setText(event.getVenue());
            holder.textViewEventLocation.setText(event.getLocation());
            holder.textViewEventDate.setText(formattedStartDate);
            holder.textViewEventTime.setText(formattedEventTime);

            mEventUtils.loadImage(mContext, event.getImageUrl(), holder.imageViewEventImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition(), event);
                }
            });
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
