package com.xyz.php.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyz.php.R;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.views.activities.UserActivity;

import java.util.List;

/**
 * 2017/10/23.
 */
public class UserAdapter extends RecyclerView.Adapter {

    private UserActivity userActivity;
    private List<UserEntity> users;

    public UserAdapter(UserActivity userActivity, List<UserEntity> users) {

        this.userActivity = userActivity;
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(userActivity).inflate(R.layout.include_recycler_user, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tvUsername = holder.itemView.findViewById(R.id.tvUsername);
        tvUsername.setText(users.get(position).username + "\n" + users.get(position).mobile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userActivity.onItemClick();
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
