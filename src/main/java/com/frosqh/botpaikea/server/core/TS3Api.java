package com.frosqh.botpaikea.server.core;

import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class TS3Api {
    private com.github.theholywaffle.teamspeak3.TS3Api ts3Api;
    private final String[] users = {"frosqh"};
    private final String paikeaSong = "\n" +
            "Uia mai koia, whakahuatia ake; \n" +
            "Ko wai te whare nei e?\n" +
            "Ko Te Kani / Ko Rangi / Whitireia! \n" +
            "Ko wai te tekoteko kei runga? \n" +
            "Ko Paikea! Ko Paikea! \n" +
            "Whakakau Paikea. Hei! \n" +
            "Whakakau he tipua. Hei! \n" +
            "Whakakau he taniwha. Hei! \n" +
            "Ka ū Paikea ki Ahuahu. Pakia! \n" +
            "Kei te whitia koe \n" +
            "ko Kahutia-te-rangi. Aue! \n" +
            "Me ai tō ure ki te tamahine \n" +
            "a Te Whironui - aue! -\n" +
            "nāna i noho te Roto-o-tahe. \n" +
            "Aue! Aue! \n" +
            "He koruru koe, koro e.";

    public TS3Api(){
        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost("harinman.ddns.net");
        TS3Query ts3Query = new TS3Query(ts3Config);
        ts3Query.connect();
        ts3Api = ts3Query.getApi();
        ts3Api.login("BotPaikea","QKtuQmzq");
        ts3Api.selectVirtualServerById(1);
        ts3Api.setNickname("Bot Paikea");
        final int clientId = ts3Api.whoAmI().getId();
        ts3Api.registerEvent(TS3EventType.TEXT_PRIVATE);
        ts3Api.addTS3Listeners(new TS3EventAdapter() {
            @Override
            public void onTextMessage(TextMessageEvent e) {
                if (e.getTargetMode()==TextMessageTargetMode.CLIENT && e.getInvokerId()!=clientId){
                    if (e.getMessage().startsWith("!help ")){
                        return;
                    }
                    String rep;
                    switch (e.getMessage().toLowerCase()){
                        case "!help":
                            rep="Voici la liste des commandes disponibles : \n";
                            rep+="  • !paikea : Surprise ! \n";
                            rep+="N'hésitez pas à taper !help [cmd] pour obtenir de l'aide spécifique à une commande.";
                            break;
                        case "!paikea":
                            rep=paikeaSong;
                            break;
                        case "merde":
                            rep="Diantre*";
                            break;
                        case "ok google":
                            rep="Nan mais oh, pour qui tu me prends ? ><";
                            break;
                        case "><":
                            rep=":D";
                            break;
                        case "nan":
                        case "nope":
                        case "no":
                        case "non":
                            rep="Si.";
                            break;
                        case "pong":
                            rep="Ping ?";
                            break;
                        case "ping":
                            rep="Pong !";
                            break;
                        case "plop":
                            rep="Plop à toi, mon frère !";
                            break;
                        default:
                            rep="Je ne connais pas cette commande, je te la renvoie donc ! (>'.')> ~= "+e.getMessage();
                            break;
                    }
                    int id = e.getInvokerId();
                    ts3Api.sendPrivateMessage(id,rep);
                }
            }

        });
        for (String user : users){
            ts3Api.sendPrivateMessage(ts3Api.getClientByNameExact(user,true).getId(),"Plop, je suis en ligne ! Tente !help pour avoir la liste des commandes disponibles.");
        }
    }

    public static void main(String[] args){
        new TS3Api();
    }
}
