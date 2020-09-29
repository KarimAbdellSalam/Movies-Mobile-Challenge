package com.swvl.swvlchallenge.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swvl.swvlchallenge.callbacks.OnInteractionListener;
import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.databinding.HeaderItemBinding;
import com.swvl.swvlchallenge.databinding.MovieListItemBinding;

import java.util.List;


public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = -1;
    public static final int ITEM_VIEW_TYPE_HEADER = -2;

    private List<DataItem> listItems;

    private OnInteractionListener mListener;

    public SearchResultAdapter(List<DataItem> list) {
        this.listItems = list;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            MovieListItemBinding itemBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(itemBinding);
        } else {
            HeaderItemBinding layoutProgressBinding = HeaderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HeaderViewHolder(layoutProgressBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBind(((DataItem.Header)listItems.get(position)).header);
        } else {
            ((ViewHolder) holder).onBind(((DataItem.MovieItem)listItems.get(position)).movie);
        }
    }


    @Override
    public int getItemViewType(int position) {
        DataItem dataItem = listItems.get(position);
        if (dataItem instanceof DataItem.Header) {
            return ITEM_VIEW_TYPE_HEADER;
        } else return VIEW_TYPE_NORMAL;

    }

    public void addItems(@NonNull List<DataItem> list) {
        int insertIndex = listItems.size();
        listItems.addAll(list);
        if (insertIndex != 0)
            notifyItemRangeInserted(insertIndex, list.size());
        else
            notifyDataSetChanged();
    }

    public void clearItems() {
        listItems.clear();
    }

    public void updateItems(@NonNull List<DataItem> list) {
        clearItems();
        listItems.addAll(list);
        notifyDataSetChanged();
    }

    public void removeLastItems() {
        if (listItems.get(listItems.size() - 1) == null) {
            listItems.remove(listItems.size() - 1);
            notifyItemRemoved(listItems.size());
        }
    }


    public void setOnInteractionListener(OnInteractionListener listener) {
        this.mListener = listener;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements OnInteractionListener {

        private final MovieListItemBinding binding;

        public ViewHolder(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Movie movie) {
            MovieItemViewModel itemViewModel = new MovieItemViewModel(movie, this);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onItemClicked(Movie item) {
            if (mListener != null)
                mListener.onItemClicked(item);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final HeaderItemBinding binding;

        public HeaderViewHolder(HeaderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(String header) {
            binding.setHeader(header);
            binding.executePendingBindings();
        }

    }

}