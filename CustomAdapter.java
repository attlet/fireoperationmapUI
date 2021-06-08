/*작성자 : 신윤성
* 내용 : 리사이클러뷰 활용하기 위한 어댑터 파일 */

package com.example.fireoperationmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

//FireBase DB에 있는 점포 정보들을 연결해주는 클래스
public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public interface OnItemClickLister {
        void onItemClick(View view, OperationMap operationMap, int position);
    }

    private List<User> userList = new ArrayList<>();
    private List<User> userListFull = new ArrayList<>();
    private CustomAdapter.OnItemClickLister listener;
    private OperationMap operationMap;
    private enum SearchType {
        ST_NAME, ADDRESS, ID
    }
    private enum ViewType {
        SIMPLE, DETAILED
    }
    private SearchType searchState = SearchType.ST_NAME;
    private ViewType viewState = ViewType.SIMPLE;

    public void setOnSimpleItemClickListener(CustomAdapter.OnItemClickLister listener) {
        this.listener = listener;
    }

    //검색 필터
    private final Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();
            if (constraint != null && !constraint.toString().trim().equals("")) {
                String pattern = constraint.toString().toLowerCase().trim();
                for(User item : userListFull) {
                    if (searchState == SearchType.ST_NAME && item.getSt_name().toLowerCase().contains(pattern)) {
                        filteredList.add(item);
                    }
                    else if (searchState == SearchType.ADDRESS && item.getAddress().toLowerCase().contains(pattern)) {
                        filteredList.add(item);
                    }
                    else if (searchState == SearchType.ID && item.getId().toLowerCase().trim().equals(pattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userList.clear();
            userList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    //리스트에 대한 간단 정보
    class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView st_name, address;
        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            st_name = itemView.findViewById(R.id.st_name);
            address = itemView.findViewById(R.id.address);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    viewState = ViewType.DETAILED;
                    User tmp = new User(getItem(position));
                    userList.clear();
                    userList.add(tmp);
                    notifyDataSetChanged();
                    listener.onItemClick(view, operationMap, 0);
                }
            });
        }
    }

    //리스트에 대한 상세 정보
    class DetailedViewHolder extends RecyclerView.ViewHolder {
        TextView id, st_name, address, structure, floor, st_type, fire_plug;
        ImageView photo;
        DetailedViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            address = itemView.findViewById(R.id.address);
            st_name = itemView.findViewById(R.id.st_name);
            structure = itemView.findViewById(R.id.structure);
            floor = itemView.findViewById(R.id.floor);
            st_type = itemView.findViewById(R.id.st_type);
            fire_plug = itemView.findViewById(R.id.fire_flug);
            photo = itemView.findViewById(R.id.photo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (viewState == ViewType.SIMPLE) {
            return ViewType.SIMPLE.ordinal();
        }
        else
            return ViewType.DETAILED.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ViewType.SIMPLE.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
            return new SimpleViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_item, parent, false);
            return new DetailedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User currentItem = userList.get(position);
        if (holder.getItemViewType() == ViewType.SIMPLE.ordinal()) {
            SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
            simpleViewHolder.st_name.setText(currentItem.getTagSt_name());
            simpleViewHolder.address.setText(currentItem.getTagAddress());
        }
        else {
            DetailedViewHolder detailedViewHolder = (DetailedViewHolder) holder;
            String sectionNum = currentItem.getId().split("-")[0];
            String placeNum = currentItem.getId().split("-")[1];
            Glide.with(detailedViewHolder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/st-marketplace-operation-map.appspot.com/o/"+ sectionNum + "_Section%2F" + sectionNum + '_' + placeNum + ".png?alt=media")
                    .into(detailedViewHolder.photo);
            detailedViewHolder.id.setText(currentItem.getTagId());
            detailedViewHolder.address.setText(currentItem.getTagAddress());
            detailedViewHolder.floor.setText(currentItem.getTagFloor());
            detailedViewHolder.st_name.setText(currentItem.getTagSt_name());
            detailedViewHolder.st_type.setText(currentItem.getTagSt_type());
            detailedViewHolder.structure.setText(currentItem.getTagStructure());
            detailedViewHolder.fire_plug.setText(currentItem.getTagFire_plug());
        }
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void init(OperationMap map) {
        operationMap = map;
        userListFull = map.userList;
        userList = new ArrayList<>(userListFull);
    }

    public User getItem(int position) {
        return userList.get(position);
    }

    public User getItem(String id) {
        id = id.trim();
        for (User user : userListFull) {
            if (user.getId().trim().equals(id))
                return user;
        }
        return null;
    }

    public void clearRecyclerView() {
        this.userList.clear();
        viewState = ViewType.SIMPLE;
        notifyDataSetChanged();
    }

    public void setSearchState(String searchState) {
        if (searchState.equals("st_name")) this.searchState = SearchType.ST_NAME;
        else if (searchState.equals("address")) this.searchState = SearchType.ADDRESS;
        else if (searchState.equals("id")) this.searchState = SearchType.ID;
    }
}