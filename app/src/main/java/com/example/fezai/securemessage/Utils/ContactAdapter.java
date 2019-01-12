package com.example.fezai.securemessage.Utils;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fezai.securemessage.R;


import com.example.fezai.securemessage.models.Contact;


import java.util.List;



public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private List<Contact> usersLists;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder  {

        // define the View objects
        public ImageView image;
        public TextView contact;
        public TextView phone;
        //public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.contactimage);
            contact = (TextView) itemView.findViewById(R.id.contact);
            phone = (TextView) itemView.findViewById(R.id.phone);
            //linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

    }

    public ContactAdapter(Context context, List<Contact> developersLists) {

        this.usersLists = developersLists;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // this method will bind the data to the ViewHolder from whence it'll be shown to other Views

        final Contact contact = usersLists.get(position);
        holder.contact.setText(contact.getUsername());
        holder.phone.setText(contact.getPhone());

        /*holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact user1 = usersLists.get(position);
                holder.contact.setText(user1.getUsername());
                holder.phone.setText(user1.getPhone());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return usersLists.size();
    }

}