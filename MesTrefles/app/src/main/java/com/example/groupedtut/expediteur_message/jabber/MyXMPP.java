package com.example.groupedtut.expediteur_message.jabber;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;


public class MyXMPP {

    public static MyXMPP globalMyXmpp;

    private static final String DOMAIN = "mmtux.fr";

    private static final String HOST = "mmtux.fr";

    private static final int PORT = 5222;

    private String userName ="";

    private String passWord = "";

    AbstractXMPPConnection connection ;

    ChatManager chatmanager ;

    Chat newChat;

    XMPPConnectionListener connectionListener = new XMPPConnectionListener();

    private boolean connected;

    private boolean isToasted;

    private boolean chat_created;

    private boolean loggedin;

    private ChatMessageListener messageRecuListener;

    public boolean hasMessageRecuListener(){
        return messageRecuListener != null;
    }

    public void setMessageRecuListener(ChatMessageListener messageRecuListener){
        this.messageRecuListener = messageRecuListener;
    }

    //Initialize
    public void init(String userId,String pwd ) {

        Log.i("XMPP", "Initialisation !");

        this.userName = userId;

        this.passWord = pwd;

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();

        configBuilder.setUsernameAndPassword(userId, pwd);
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        try {
            configBuilder.setResource("Android");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        try {
            configBuilder.setXmppDomain("mmtux.fr");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        configBuilder.setHost(HOST);

        configBuilder.setPort(PORT);

        configBuilder.setDebuggerEnabled(true);

        connection = new XMPPTCPConnection(configBuilder.build());

        connection.addConnectionListener(connectionListener);
    }

    // Disconnect Function
    public void disconnectConnection(){

        new Thread(new Runnable() {

            @Override
            public void run() {

                connection.disconnect();

            }

        }).start();

    }

    public void connectConnection()

    {

        AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... arg0) {

// Create a connection
                try {

                    connection.connect();

                    login();

                    connected = true;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;

            }

        };

        connectionThread.execute();

    }

    public void sendMsg(String destinataire, String message) {

        if (connection.isConnected()== true) {
            Log.d("xmpp","co réalisée");

// Assume we’ve created an XMPPConnection name “connection”._
            chatmanager = ChatManager.getInstanceFor(connection);

            try {
                EntityBareJid jid = JidCreate.entityBareFrom(destinataire+"@"+HOST);
                newChat = chatmanager.createChat(jid);
            } catch (XmppStringprepException e) {
                e.printStackTrace();
            }

            try {

                newChat.sendMessage(message);
                Log.d("xmpp","Message envoyé !!!");

            } catch (SmackException.NotConnectedException e) {

                e.printStackTrace();
                Log.d("xmpp","Message non envoyé !!! RT");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else{
            Log.d("xmpp","message non envoyé co non réalisée");
        }

        if(this.hasMessageRecuListener()){
            newChat.addMessageListener(this.messageRecuListener);
        }

    }

    public void login() {

        try {

            connection.login(userName, passWord);

//Log.i(“LOGIN”, “Yey! We’re connected to the Xmpp server!”);

        } catch (XMPPException | SmackException | IOException e) {
            Log.d("Bug", "Bug ICI !");
            e.printStackTrace();

        } catch (Exception e) {

        }

    }

    //Connection Listener to check connection state
    public class XMPPConnectionListener implements ConnectionListener {

        @Override
        public void connected(final XMPPConnection connection) {

            Log.d("xmpp", "Connecté !");

            connected = true;

            if (!connection.isAuthenticated()) {

                login();

            }

        }

        @Override
        public void connectionClosed() {

            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

// TODO Auto-generated method stub

                    }

                });

            Log.d("xmpp", "Déconnecté !");

            connected = false;

            chat_created = false;

            loggedin = false;

        }

        @Override
        public void connectionClosedOnError(Exception arg0) {

            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                    }

                });

            Log.d("xmpp",  "Connexion terminée!");

            connected = false;

            chat_created = false;

            loggedin = false;

        }

        @Override
        public void reconnectingIn(int arg0) {

            Log.d("xmpp", "Reconnexion " + arg0);

            loggedin = false;

        }

        @Override
        public void reconnectionFailed(Exception arg0) {

            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                    }

                });

            Log.d("xmpp", "Reconnexion impossible");

            connected = false;

            chat_created = false;

            loggedin = false;

        }

        @Override
        public void reconnectionSuccessful() {

            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

// TODO Auto-generated method stub

                    }

                });

            Log.d("xmpp", "ReconnectionSuccessful");

            connected = true;

            chat_created = false;

            loggedin = false;

        }

        @Override
        public void authenticated(XMPPConnection arg0, boolean arg1) {

            Log.d("xmpp", "Authentifié !");

            loggedin = true;

            chat_created = false;

            new Thread(new Runnable() {

                @Override
                public void run() {

                    try {

                        Thread.sleep(500);

                    } catch (InterruptedException e) {

// TODO Auto-generated catch block
                        e.printStackTrace();

                    }

                }

            }).start();

            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

// TODO Auto-generated method stub

                    }

                });

        }

    }

}

