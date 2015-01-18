package com.blokura.sunshine.ui;

import com.blokura.sunshine.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This class is an adapter for the forecast data.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<String> dataSet;

    /**
     * Provide a reference to the views for each data item.
     * Complex data items may need more than one view per item, and you provide access to all the
     * views for a data item in a view holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Each data item is just a string in this case
        public TextView textView;

        public ViewHolder(TextView view) {
            super(view);
            textView = view;
        }
    }

    /**
     * Provide a suitable constructor. Depends on the kind of dataset
     *
     * @param data the data to show.
     */
    public ForecastAdapter(List<String> data) {
        this.dataSet = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Create a new view. Similar to item inflate in listview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_item_forecast, parent, false);

        // Here you can set the view's size, margins, paddings and layout parameters.

        return new ViewHolder((TextView)view);
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param holder the real view holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the element from the dataSet at this position
        // Replace the contents of the view with that element
        String item = dataSet.get(position);
        holder.textView.setText(item);
        holder.textView.setTag(item);
    }

    /**
     * @return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addOrUpdateItem(String item) {
        int pos = dataSet.indexOf(item);
        if (pos >= 0) {
            updateItem(item, pos);
        } else {
            addItem(item);
        }
    }

    private void updateItem(String item, int pos) {
        dataSet.remove(pos);
        notifyItemRemoved(pos);
        addItem(item);
    }

    private void addItem(String item) {
        dataSet.add(item);
        notifyItemInserted(dataSet.size() -1);
    }
}
