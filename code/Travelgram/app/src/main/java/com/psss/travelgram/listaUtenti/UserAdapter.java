package com.psss.travelgram.listaUtenti;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseUser;/*
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koddev.instagramtest.R;*/
import com.psss.travelgram.R;
import com.psss.travelgram.model.entity.Traveler;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<Traveler> travelers;
    private Context context;
    private boolean isFragment;

    private FirebaseUser firebaseUser;  /////////////

    // ---- classe innestata
    public static class MyViewHolder extends RecyclerView.ViewHolder {  // static l'ho aggiunto io
        public TextView username;
        public ToggleButton followBtn;

        public MyViewHolder(View v) {
            super(v);
            username = v.findViewById(R.id.username);
            followBtn = v.findViewById(R.id.followBtn);
        }
    }
    // ---- fine classe innestata


    // Costruttore
    public UserAdapter(Context context, ArrayList<Traveler> travelers, boolean isFragment){
        this.context = context;
        this.travelers = travelers;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Traveler traveler = travelers.get(position);

        // bottone
        //isFollowing(traveler.getId(), holder.followBtn);        /////non farla cosi

        // username
        holder.username.setText(traveler.getUsername());

        holder.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.followBtn.getText().toString().equals("follow")) {

                    // SPOSTARE NEL VIEW MODEL
                    traveler.follow();  // metto il follow al traveler
                    //addNotification(traveler.getId());
                } else {
                    traveler.unfollow();    // tolgo il follow al traveler
                }
            }

        });
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFragment) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", traveler.getId());
                    editor.apply();

                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ProfileFragment()).commit();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("publisherid", traveler.getId());
                    context.startActivity(intent);
                }
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return travelers.size();
    }


    /*
        private void addNotification(String userid){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userid", firebaseUser.getUid());
            hashMap.put("text", "started following you");
            hashMap.put("postid", "");
            hashMap.put("ispost", false);

            reference.push().setValue(hashMap);
        }*/

/*
    private void isFollowing(final String userid, final Button button){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userid).exists()){
                    button.setText("following");
                } else{
                    button.setText("follow");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}