package com.example.ayushmandey.rentpay.Chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ayushmandey.rentpay.R;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.github.bassaer.chatmessageview.view.MessageAdapter;
import com.github.bassaer.chatmessageview.view.MessageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatWindow extends AppCompatActivity {

    private String mChatUser;
    private DatabaseReference mRootRef;
    private TextView chat_user;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;
    private Button send;
    private EditText message;
    private RecyclerView mMessageList;
    private List<com.example.ayushmandey.rentpay.Messages> messagesList;
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;
    String str;
    int myId, yourId;
    Bitmap myIcon, yourIcon;
    String myName, yourName;
    com.github.bassaer.chatmessageview.model.ChatUser you, me;
    Message message1,message2;
    ArrayList<Message> messages;
    ChatView chatView;
    MessageView messageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_chat);

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mChatUser = getIntent().getStringExtra("uid");
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        messagesList = new ArrayList<>();
        chatView = (ChatView) findViewById(R.id.chat_view);
        myId = 0;
        myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle);
        yourId = 1;
        yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle);
        myName = " ";
        yourName = " ";

         me = new com.github.bassaer.chatmessageview.model.ChatUser(myId, myName, myIcon);
         you = new com.github.bassaer.chatmessageview.model.ChatUser(yourId, yourName, yourIcon);


        loadMessages();

      mRootRef.child("chat").child(mCurrentUserId).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if(!dataSnapshot.hasChild(mChatUser)){
                Map chatAddMap = new HashMap();
                chatAddMap.put("seen",false);
                chatAddMap.put("TimeStamp", ServerValue.TIMESTAMP);

                Map chatUserMap =new HashMap();
                chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser,chatAddMap);
                chatUserMap.put("Chat/"+ mChatUser + "/" + mCurrentUserId,chatAddMap);

                mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });
              }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
        chatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void loadMessages() {
        mRootRef.child("messages").child(mCurrentUserId).child(mChatUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                com.example.ayushmandey.rentpay.Messages message = dataSnapshot.getValue(com.example.ayushmandey.rentpay.Messages.class);

                if(!message.getFrom().toString().equals(mCurrentUserId.toString())){
                    message1 = new Message.Builder()
                            .setUser(you) // Sender
                            .setRight(false)
                            .setText(message.getMessage().toString())
                            .build();
                    message1.hideIcon(true);
                    chatView.receive(message1);
                }
                else{
                    message1 = new Message.Builder()
                            .setUser(me) // Sender
                            .setRight(true)
                            .setText(message.getMessage().toString())
                            .build();
                    message1.hideIcon(true);
                    chatView.send(message1);
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {
        str = chatView.getInputText();


        if(!str.equals("")){
            String current_user_ref = "messages/"+ mCurrentUserId + "/" + mChatUser;
            String chat_user_ref = "messages/" + mChatUser+ "/" + mCurrentUserId;
            DatabaseReference user_message_push = mRootRef.child("messages")
                    .child(mCurrentUserId).child(mChatUser).push();

            String push_id = user_message_push.getKey();
                Map messageMap = new HashMap();
                messageMap.put("message", str);
                messageMap.put("seen", false);

                messageMap.put("type", "text");
                messageMap.put("time", ServerValue.TIMESTAMP);
                messageMap.put("from", mCurrentUserId);

                final Map messageUserMap = new HashMap();
                messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
                messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

                chatView.setInputText("");

                mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            message2 = new Message.Builder()
                                    .setUser(me) // Sender
                                    .setRight(true)
                                    .setText(str)
                                    .build();
                            message2.hideIcon(true);
                            chatView.send(message2);


                        }
                    }
                });


        }
    }
}
