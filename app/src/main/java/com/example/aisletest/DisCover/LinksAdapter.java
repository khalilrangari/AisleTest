package com.example.aisletest.DisCover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aisletest.DisCover.ResponseModel.Profile__1;
import com.example.aisletest.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.MyViewHolder> {
  private List<Profile__1> profiles;
  private Context mContext;

  public LinksAdapter(List<Profile__1> profiles) {
    this.profiles = profiles;
  }

  @NonNull
  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    this.mContext = parent.getContext();
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_list_item, parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
    holder.tvName.setText(profiles.get(position).getFirstName());
    Glide.with(mContext)
        .load(profiles.get(position).getAvatar())
        .into(holder.ivProfile);
  }

  @Override
  public int getItemCount() {
    return profiles != null && profiles.size() > 0 ? profiles.size() : 0;
  }


  public class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivProfile;
    private TextView tvName;

    public MyViewHolder(@NonNull @NotNull View itemView) {
      super(itemView);
      ivProfile = itemView.findViewById(R.id.ivProfile);
      tvName = itemView.findViewById(R.id.tvName);
    }
  }
}
