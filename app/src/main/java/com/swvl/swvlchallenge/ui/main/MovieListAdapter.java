package com.swvl.swvlchallenge.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.swvl.swvlchallenge.callbacks.OnInteractionListener;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.databinding.MovieListItemBinding;
import com.swvl.swvlchallenge.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class MovieListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Movie> listItems;

    private OnInteractionListener mListener;

    public MovieListAdapter(List<Movie> list) {
        this.listItems = list;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Nullable
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieListItemBinding itemBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    public void addItems(@NonNull List<Movie> list) {
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

    public void updateItems(@NonNull List<Movie> list) {
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

    private class ViewHolder extends BaseViewHolder implements OnInteractionListener {

        private final MovieListItemBinding binding;

        public ViewHolder(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            Movie item = listItems.get(position);
            MovieItemViewModel itemViewModel = new MovieItemViewModel(item, this);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onItemClicked(Movie item) {
            if (mListener != null)
                mListener.onItemClicked(item);
        }
    }
}
