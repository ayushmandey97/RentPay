package com.example.ayushmandey.rentpay.Home;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ayushmandey.rentpay.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ayushmandey.rentpay.Utils.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    List<Post> p;
    CustomAdapter adp;
//    final Context c = com.example.ayushmandey.rentpay.Home.HomeActivity.
    Context c = this;
    DatabaseReference db;

//    @Override
//    protected void onStart() {
//
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                p.clear();
//
//                for( DataSnapshot Postzz : dataSnapshot.getChildren() )
//                {
//                    p.add( Postzz.getValue(Post.class) );
//                }
//
//                adp = new CustomAdapter( c , p);
//                lv.setAdapter(adp);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        super.onStart();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        p = new ArrayList<>();
//
        lv = findViewById( R.id.listView11d);


//        ArrayAdapter<String> adp = new ArrayAdapter<String>( c , android.R.layout.simple_list_item_1 , arr );
//
//        db = FirebaseDatabase.getInstance().getReference("Items");

//        Post p1 = new Post("Senheiser Headphones" , "Tuesday" , "data:image/webp;base64,UklGRqoJAABXRUJQVlA4IJ4JAABwRQCdASr+ACwBPrFWo0skIq8jItJ6aegWCelu4XCBG/NP7TtTM/XcbiX4gjnqUP0B/tnOeaJPrD0d/SV9gX7meziPf6Po7/uKa2z+1Z5kQ4PWRHVD6n5bmRoNdIXkRwNprvoKb7Jicdu9f6BzkOWykMsbWzo0+oXEPzFiVjKjHbQdl//Aq71LXAzTAs7lA6nUnrO+0xSIXL6pt35z5UBqVNHVZDxylVpntGL/0o1SX8aWdW+8yfK4WkVKo7Dnv/l7utGIfLVqkhwXqAf8FQfsAzlL9EFKSm5LC0z9U0D4xHaegz1ozE6KsR3kd5qCxBJt3iRNsCDXn1expotBa/wrKNpYGdmo5LM3XSANEps/sB3WtVBM82EpC+Ri/6npg3lRlVMRq/QxyU72+OwefyTT/RqqCMuwDlTQc9EPYYLpZrcv2Do4sAUVAoAMBzoMrFTAdEzE50aGfn34CQ6JgjEKvXnrymwxqtoNd35rbm2161wOLhjeijkPCm4HCs7asOPfNeN2YW2rhjOTo05Ixx+FH8vmoDv1kDx3Y72+e1883fADAZnREBndzLF/PtDasg8aBzn9SZxij1Vddv/ih0NsbB1KTUoNq83SguoJ/TRu+jHdVUt5u+AF+hYSe5qhGJbI47kPFTmkVVk8+5DixvyGpa/d9yMkSfQbV3wwi70tAvizb8/bCNRyJZ32oUGENcalrvgBzoNq80XuUq0H0G1ebvf8uBE8tMrauGNXm734SK0bvIAA/v54AenNtTyH9HcsmcKY3E/5SsP/PVs9+TwzaQ68lif9iY8SS0Wvs2iePzF3HnsEH9wIg5nsv5y37gGf8BMUieenLb/V4WflsBOXpeomcm2wLyTQH5+2OfH6wH/a6Wf2SJYBW4IJ7PFt79uuQGZpTXEQg2B/QCSjOPGGFEsTNm4/vwIemrAWfRiWm1N4tuj5LnzmXwkMIvHZ5sICBKzI6T4+Hy1M0e9AGsfPgBrDWTrQVbm8BDRnkzDZBTtWYVylm6P/3MfiAlIARbCIUFMOFLO8khfAhCiY6/iJ5hVt4C/pn4SS2ky8LbV7a5tWNehJ+ZKcqigp8XGDNGXNS8kFg75tQ0Gi9hNff90rzLCqDvAEglwE9xwm8lMHreTs74Q4JJc9Ab38Qayc7/jmqEYtGKTwHxvSenMSeCi8kbQiHkcR+fA3bqU7XP44sjibRbFjo408MHICalPckYxTuyhKdquqLrInDXWeve0wJw/J9rUEGHTdgkMpqVHfBEjK96MsTi5LqrHPZTnKNrbk6Vodrzi4TXSLVXHXJ7EhF5lksqE6nsssg54rj2JRbTUSomOpxCscQH8hTTiNLaa5mBnrVTr0sm5wnNFrwuj1QHmsaw4d5ts7Hr9hBdtjcYlve/hTlPdfbMQfFR7rNFJSSjhULmCpfaH3oHT8pAaARYSJvk7i4Vn8Qs9CChoP4h7bvqk9nCXJL1sVaDzcsXyvATFUElmcDJJpWu24iO8tmS0gH6dckYp/Jg+6I0shxCOl0lvabnizlrCZBKdM0s1mWtGVDROjRSvw1c2cZ5JZ6W0zx0AexOlzo8WYobsQjUiaDCcx4q9AfsJ/oR48ZDBiaEC5JfkHF3l67XqD1uE4QO4/sPUN2gCwK1c/NwiiX57EKO9C96JgqoI0776fpm4bM0W5bp2WxhfsGXyV6kcIVMT1tQP1+EsKVWZR5O9qUWs+buwcCLvzIBGv519VwQpiCeX4NSd6a/8rah8Ue9+kfznAAlu3NulkGFlU0yvEVvsbBxXhWKyn+Mr7jfE/0naQfk6K325YXV+ULdaljUo8SVkjaERhTsD/zq4eURLQtKw66gaMzYEcGM5OIsUGzkaIan64qF1zPNfNlNJXvgD982RkyiA6uMH4ozg7q5z8wuuZWk/65W22eJCBot1/W/5YD+VFIdcsL+kMt091LkHBQOP52mMlI3QYhaNxm+h0LxJv9EfTDNpx+FwlNANLY54DN8s0ASD6O2XdpO+f8DY00J0Sqsa5UksGEASSOBXO/iHcpoIzSYcFkmZ5rOgE1Zd7GJjpRIZe4d9Q6hARssX2U9er4E22a2+zzLLFRhSHj4lSrE/h8VDL0K8aTiYPRYmgz3g4Kh4R7YV4Gk8CfXHb7V22Xi0Y0lxAmSgUgsiIzgtrmfgdBqnsiQbaxZVfbYz45LryTtNdGQKltnCU2B9s7SZtchBnRETCfinrm+w3cT6LtFSMVrFrTs5gTS3M0GnanQXUZPr6uBfz2e7A8g/c36wtIfsvA03ZMTDfqvKg5lzAIUlGsQe4BdUEhCK0IsODd2Kl9A2r2grSkXShDR0iCoESNFd/ZGKDL0xNDGwG2hGgl/XMPdvGDJyQyhxDj5X9vRcmxR8+aU33VBfMalman7vRpIXupC7hSnBIt7PnnnI1ac2Q7kj0C3wf89z/j+6C4QJJbFBxiP6StoxKiZ9QCSnUClbTJUzR1D2bsr683+um0AKFQc1mpfespyp0Hul4Xjr8MYCFPizhL4VfrsFtFXy3Oak+p6EcHjcZKjihSlSsv+0+e4PPK50WBlmH/wlhYREENSpUn+s4NT+3vEsCPB7XeEJMvo/jJWRAb7Qtx7eW8UOFbmAIEI/R3EolviIcYaxrRUwISjykLWC65hLNjt4q53AAuShTcyrzN5KvT3tu4knxRNe7wnGYoNb7BjdLRIrpjp1APWEWIAgt+2rN0DAnW79F8OJGDSBIaRkM6bCBx48CvQ0OBIHgfyCaOW95Xkp7B1fm0IMF7cqSEBq28WXhVxTWvQC1xgVvxLzrF4e+1XwXOn5TndYJvUP5oaNFn1zt3kp3zUXg9JzRC3xsjMLONRKBfA5OOwemAabP8mGj+ESiggJpzswaN44I8v/anAGtg2an/Ed0MMuEjhti4Nu4+wVYYfS0+5nMVMhQITBvHdjEY6SXHI5fUyQDkRT2FJd207vDZUQjcW1zDno9iYjLvZAkm65G4gihK0dh85QsKOKJ9shS9h3p6+AQYXCbw553h5HIg66bHDWP8xm+xC/dl2uMfNrlWnUCeMbRNf3C3yNbypc2tamAJZEkjx9GJ2xM956BoPuf0eykQD4rcedyX1J0I78Nt7OkXTICNhwnZ3IBHnV71uU/OsFBvBMrrdC2NRMfzgufPV3B39q/SS53LYxYzH9hTkx8ZJAzcV2jV/qKRMeNV7LMIJEWAxpyyod5I0X6hJgGCkZZLz7pZ29yNa9Tf0vDhyPrUyakQ6hAsepgloVmkLw4ATzv3GTVf4tpceR0OzhSnOAAAA==" , "ds" , "dsd" , "The ideal companion to all your audio devices is here. With a comfortable fit and sturdy design the Sennheiser CX 180 Street II In-Ear Headphone, fits perfectly with all your audio gadgets with its 3.5mm stereo mini plug and provide you acoustics to your music like never before." );
       Post p2 = new Post("Bedsheet" , "Tuesday" , "https://rukminim1.flixcart.com/image/704/704/jc0ttow0/bedsheet/w/4/y/florence-2315-flat-bella-casa-original-imaff5p6hctrke3q.jpeg?q=70" ,"dheeraj" ,"sdsd" , "dd" , "A necessary requirement in every household, purchasing a good quality bed sheet cannot be compromised upon. A good bed sheet allows you to have a good sleep, ensuring that you do not wake up every now and then due to even the mildest discomfort");
//         p.add(p1);
      p.add(p2);

        adp = new CustomAdapter( c , p);
        lv.setAdapter(adp);
    }


}
