package com.frosqh.botpaikea.server.core.ts3api;

import com.frosqh.botpaikea.server.core.Session;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class TS3Api {
    private com.github.theholywaffle.teamspeak3.TS3Api ts3Api;
    private final String[] users = {"frosqh","Rheoklash"/*,"Izatof"*/,"Harinman"};

    public TS3Api(){
        // Connecting Query
        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost(Session.getSettings().get("sv_address"));
        TS3Query ts3Query = new TS3Query(ts3Config);
        ts3Query.connect();
        // Creating API and initializing query
        ts3Api = ts3Query.getApi();
        ts3Api.login(Session.getFromSettings("sv_login"),Session.getFromSettings("sv_password"));
        ts3Api.selectVirtualServerById(1);
        ts3Api.setNickname(Session.getFromSettings("bot_name").replace(" ",""));

        goToRightChannel();
        final int clientId = ts3Api.whoAmI().getId();

        ts3Api.registerEvent(TS3EventType.TEXT_PRIVATE);
        ts3Api.addTS3Listeners(new OnReceiveListener(clientId));

        welcomeAll();
    }

    private void goToRightChannel(){
        for (String user : users){
            Client tsClient = ts3Api.getClientByNameExact(user, true);
             if (tsClient != null && ts3Api.whoAmI().getChannelId() != tsClient.getChannelId()){
                 ts3Api.moveQuery(tsClient.getChannelId());
                 return;
             }
        }
    }

    private void welcomeAll(){
        for (String user : users) {
            Client pignouf = ts3Api.getClientByNameExact(user, true);
            if (pignouf != null && !pignouf.isOutputMuted() && !pignouf.isAway()){
                ts3Api.sendPrivateMessage(pignouf.getId(), Session.locale.WELCOME_MESSAGE());
            }
        }
    }

    public static void main(String[] args){
        new TS3Api();
    }

    public void exit() {
        ts3Api.logout();
    }

    private class OnReceiveListener extends TS3EventAdapter {
        public final int selfID;

        public OnReceiveListener(int self){
            selfID = self;
        }

        @Override
        public void onTextMessage(TextMessageEvent e) {
            if (e.getTargetMode()==TextMessageTargetMode.CLIENT && e.getInvokerId()!=selfID){
                int id = e.getInvokerId();
                String command = e.getMessage().toLowerCase();
                String[] args = command.split(" ");
                String rep = null;

                if ((args[0].equals("!help") && args.length<2) || Command.isBase(args[0])){
                    rep = Command.execBase(args[0]);
                }

                if (rep==null && Command.isEaster(args[0])){
                    rep = Command.execEaster(args[0]);
                }

                if (rep==null && Command.isComplex(args[0])) {
                    rep = Command.execComplex(args);
                }

                if (rep==null){
                    String almost = Command.isAlmostACommand(args[0]);
                    if (almost!=null){
                        rep = Session.locale.DID_YOU_MEAN(args[0],almost);
                    }
                }

                if (rep!=null)
                    ts3Api.sendPrivateMessage(id,rep);
                else
                    ts3Api.sendPrivateMessage(id,Session.locale.NOT_FOUND(args[0]));
            }
        }
    }
}
