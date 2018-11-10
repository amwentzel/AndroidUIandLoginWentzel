package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList data;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View listView;
        public MyViewHolder(View view) {
            super(view);
            listView = view;
        }
    }

    public UserAdapter(ArrayList data) {
        this.data = data;
    }

    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_display, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserProfile user = (UserProfile) data.get(position);
        TextView firstName = (TextView) holder.listView.findViewById(R.id.firstName);
        firstName.setText(user.getFirstName());
        TextView lastName = (TextView) holder.listView.findViewById(R.id.lastName);
        lastName.setText(user.getLastName());
        TextView username = (TextView) holder.listView.findViewById(R.id.username);
        username.setText(user.getUsername());
        TextView  birthday = (TextView) holder.listView.findViewById(R.id.birthday);
        birthday.setText(user.getBirthday());
        TextView phoneNumber = (TextView) holder.listView.findViewById(R.id.phoneNumber);
        phoneNumber.setText(user.getPhone());
        TextView email = (TextView) holder.listView.findViewById(R.id.email);
        email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}