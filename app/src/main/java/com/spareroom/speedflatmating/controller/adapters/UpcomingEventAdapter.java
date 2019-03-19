package com.spareroom.speedflatmating.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spareroom.speedflatmating.model.EventHeader;
import com.spareroom.speedflatmating.model.EventSection;
import com.spareroom.speedflatmating.utils.EventUtils;
import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.model.Event;
import com.spareroom.speedflatmating.ui.OnItemClickListener;

import java.util.List;

public class UpcomingEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<EventSection> mEventList;
    private EventUtils mEventUtils = new EventUtils();
    private OnItemClickListener mOnItemClickListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public UpcomingEventAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.list_item_header, parent, false);
            return new HeaderViewHolder(view);

        } else if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.list_item_event, parent, false);
            return new EventViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final EventSection section = mEventList.get(position);

        if (section instanceof Event) {
            bindEvent((EventViewHolder) holder, (Event) section);
        } else if (section instanceof EventHeader) {
            bindHeader((HeaderViewHolder) holder, (EventHeader) section);
        }
    }

    private void bindEvent(final EventViewHolder holder, final Event event) {
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

    private void bindHeader(HeaderViewHolder holder, EventHeader header) {
        holder.textViewHeader.setText(header.getHeader());
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewEventImage;
        private TextView textViewEventCost, textViewEventVenue, textViewEventLocation, textViewEventDate, textViewEventTime;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewEventImage = itemView.findViewById(R.id.image_view_event_image);
            textViewEventCost = itemView.findViewById(R.id.text_view_event_cost);
            textViewEventVenue = itemView.findViewById(R.id.text_view_event_venue);
            textViewEventLocation = itemView.findViewById(R.id.text_view_event_location);
            textViewEventDate = itemView.findViewById(R.id.text_view_event_date);
            textViewEventTime = itemView.findViewById(R.id.text_view_event_time);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewHeader;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHeader = itemView.findViewById(R.id.text_view_header);
        }
    }

    public void addEvents(List<EventSection> eventList) {
        this.mEventList = eventList;
    }

    private EventSection getItem(int position) {
        return mEventList.get(position);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return getItem(position) instanceof EventHeader;
    }
}
