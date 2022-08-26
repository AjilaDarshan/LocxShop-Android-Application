package com.app.locxshop.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.locxshop.R;
import com.app.locxshop.SubActivity.WorkDetail;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    ArrayList<Blog> mLists;
    String myUid;

    public PostAdapter(Context context, ArrayList<Blog> mLists) {
        this.context = context;
        this.mLists = mLists;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Blog blog = mLists.get(position);
        holder.shop.setText(blog.getShop());
        holder.owner.setText(blog.getOwner());
        holder.state.setText(blog.getState());
        holder.city.setText(blog.getCity());
        holder.location.setText(blog.getLocation());
        holder.work_time.setText(blog.getWork_time());
        holder.contact.setText(blog.getContact());
        Picasso.get().load(mLists.get(position).getPostimage()).into(holder.imageView);

        // delete
        final String postimage = mLists.get(position).getPostimage();
        final String postid = mLists.get(position).getPostid();
        final  String publisher = mLists.get(position).getPublisher();


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNo = blog.getContact();
                String call = "tel:" +mobileNo.trim();
                Intent workdetail = new Intent(Intent.ACTION_DIAL);
                workdetail.setData(Uri.parse(call));
                context.startActivity(workdetail);


            }
        });
        // delete
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreOptions(holder.more, publisher, myUid, postimage, postid);
            }


        });

    }
    // delete
    private void showMoreOptions(ImageButton more, String publisher, String myUid, final String postimage, final String postid) {
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);

        if (publisher.equals(myUid)){
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Delete");
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == 0){
                        beginDelete(postimage, postid);

                    }
                    return false;
                }
            });
        }

        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Delete");

        popupMenu.show();
    }
    // delete
    private void beginDelete(String postimage, final String postid) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting......");
        StorageReference picref = FirebaseStorage.getInstance().getReferenceFromUrl(postimage);
        picref.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("postid").equalTo(postid);
                        fquery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds: snapshot.getChildren()){
                                    ds.getRef().removeValue();


                                }
                                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shop, owner, state, city, location, work_time, contact;
        ImageView imageView;
        ImageButton more;
        Button button;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            shop = itemView.findViewById(R.id.shop);
            owner = itemView.findViewById(R.id.owner);
            imageView = itemView.findViewById(R.id.postimage);
            button = itemView.findViewById(R.id.viewbut1);
            state = itemView.findViewById(R.id.location);
            city = itemView.findViewById(R.id.location1);
            location = itemView.findViewById(R.id.address);
            work_time = itemView.findViewById(R.id.type);
            contact = itemView.findViewById(R.id.contact);
            more = itemView.findViewById(R.id.more);
        }
    }

}