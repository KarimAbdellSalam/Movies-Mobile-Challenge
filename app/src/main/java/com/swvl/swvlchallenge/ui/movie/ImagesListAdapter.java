package com.swvl.swvlchallenge.ui.movie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.databinding.FlickrListItemBinding;
import com.swvl.swvlchallenge.databinding.LayoutProgressBinding;
import com.swvl.swvlchallenge.ui.base.BaseViewHolder;
import com.swvl.swvlchallenge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class ImagesListAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_PROGRESS = 2;

    private List<FlickrResponse.Photo> listItems= new ArrayList<>();

    private OnInteractionListener mListener;

    public ImagesListAdapter(List<FlickrResponse.Photo> list) {
        this.listItems = list;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (listItems.get(position) != null)
            return VIEW_TYPE_NORMAL;
        else
            return VIEW_TYPE_PROGRESS;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_NORMAL) {
            FlickrListItemBinding itemBinding = FlickrListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(itemBinding);
        } else {
            LayoutProgressBinding layoutProgressBinding = LayoutProgressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false); // in case pagination
            return new ProgressViewHolder(layoutProgressBinding);
        }


    }

    public void addItems(@NonNull List<FlickrResponse.Photo> list) {
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

    public void updateItems(@NonNull List<FlickrResponse.Photo> list) {
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

    public interface OnInteractionListener {
        void onItemClicked(String item);
    }

    private class ViewHolder extends BaseViewHolder  {

        private final FlickrListItemBinding binding;

        public ViewHolder(FlickrListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            String item = Utils.Const.HTTP.getFlickrImagePath(listItems.get(position));
            binding.setImagePath(item);
            binding.executePendingBindings();
        }


    }

    private class ProgressViewHolder extends BaseViewHolder {

        private final LayoutProgressBinding binding;

        public ProgressViewHolder(LayoutProgressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            binding.executePendingBindings();
        }
    }
}
