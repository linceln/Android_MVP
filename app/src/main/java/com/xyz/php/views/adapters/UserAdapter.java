package com.xyz.php.views.adapters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyz.php.R;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.views.activities.UserDetailActivity;

import java.util.List;

/**
 * 2017/10/23.
 */
public class UserAdapter extends RecyclerView.Adapter {

    private FragmentActivity activity;

    private List<UserEntity> users;

    public UserAdapter(FragmentActivity activity, List<UserEntity> users) {

        this.activity = activity;
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.include_recycler_user, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tvUsername = holder.itemView.findViewById(R.id.tvUsername);
        tvUsername.setText(users.get(position).username + "\n" + users.get(position).mobile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, UserDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
