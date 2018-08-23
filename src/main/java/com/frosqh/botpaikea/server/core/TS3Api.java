package com.frosqh.botpaikea.server.core;

import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class TS3Api {
    private com.github.theholywaffle.teamspeak3.TS3Api ts3Api;
    private final String[] users = {"frosqh","Rheoklash","Izatof","Harinman"};
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
        ts3Api.login("BotPaikea","d9DmuZnq");
        ts3Api.selectVirtualServerById(1);
        System.out.println(Session.getSettings().get("bot_name"));
        ts3Api.setNickname(Session.getSettings().get("bot_name").replace(" ",""));
        if (ts3Api.isClientOnline(ts3Api.getClientByNameExact("Frosqh",true).getUniqueIdentifier())  //TODO Remplacer par un test pignouf != null
                &&
                ts3Api.whoAmI().getChannelId()!=ts3Api.getClientByNameExact("Frosqh",true).getChannelId()){
            ts3Api.moveQuery(ts3Api.getClientByNameExact("Frosqh",true).getChannelId());
        }
        final int clientId = ts3Api.whoAmI().getId();
        ts3Api.registerEvent(TS3EventType.TEXT_PRIVATE);
        ts3Api.addTS3Listeners(new TS3EventAdapter() {
            @Override
            public void onTextMessage(TextMessageEvent e) {
                if (e.getTargetMode()==TextMessageTargetMode.CLIENT && e.getInvokerId()!=clientId){
                    int id = e.getInvokerId();
                    if (e.getMessage().startsWith("!help ")){
                        String[] args = e.getMessage().split(" ");
                        String rep=null;
                        if (args.length<2){
                            rep = "Usage : !help [cmd]";
                        } else {
                            switch (args[1].toLowerCase()){
                                case "paikea":
                                    rep = "Usage : !paikea \n";
                                    rep += "Affiche une chanson traditionnelle maori";
                                    break;
                                case "next":
                                    rep = "Usage : !next \n";
                                    rep += "Stoppe la lecture courante, change la chanson courante vers la prochaine dans la queue et lance la lecture";
                                    break;
                                case "play":
                                    rep = "Usage : !play \n";
                                    rep += "Reprend une lecture en pause, ne lance pas une musique si aucune n'est en cours";
                                    break;
                                case "!pause":
                                    rep = "Usage : !pause \n";
                                    rep += "Interrompt une lecture en cours";
                                    break;
                                case "prev":
                                    rep = "Usage : !prev \n";
                                    rep += "Stoppe la lecture courante, change la chansonc ourante vers la dernière jouée, et lance la lecture";
                                    break;
                                case "setvolume":
                                    rep = "Usage : !setVolume vol \n";
                                    rep += "Change le volume du player vers la valeur vol";
                                    break;
                            }
                        }
                        ts3Api.sendPrivateMessage(id,rep);
                        return;
                    }
                    String rep = null;
                    switch (e.getMessage().toLowerCase()){
                        case "!help":
                            rep="Voici la liste des commandes disponibles : \n";
                            rep+="      • !paikea : Surprise ! \n";
                            rep+="      • !next : Lit la prochaine musique de la liste d'attente \n";
                            rep+="      • !play : Sort le player de l'état 'Pause' \n";
                            rep+="      • !pause : Met le player en état 'Pause' \n";
                            rep+="      • !prev : Revient à la dernière musique jouée \n";
                            rep+="      • !setVolume : Change le volume du player \n";


                            rep+="N'hésitez pas à taper !help [cmd] pour obtenir de l'aide spécifique à une commande.";
                            break;
                        case "!paikea":
                            rep=paikeaSong;
                            break;
                        case "!next":
                            Session.getPlayer().next();
                            rep = "♫ Now playing - "+Session.getPlayer().getPlaying().getTitle()+" ♫";
                            break;
                        case "!prev":
                            Session.getPlayer().prev();
                            rep = "♫ Now playing - "+Session.getPlayer().getPlaying().getTitle()+" ♫";
                            break;
                        case "!pause":
                            if (Session.getPlayer().isPlaying())
                                Session.getPlayer().pause();
                            else
                                rep="Rien n'est en train d'être joué, réfléchis un peu ><";
                            break;
                        case "!play":
                            if (!Session.getPlayer().isPlaying())
                                if (Session.getPlayer().play())
                                    rep="La lecture a bien été (re)prise !";
                                else
                                    rep="La lecture n'a pas pu être reprise, n'hésite pas à réessayer si cela te semble étrange.";
                            else
                                rep="Ça joue déjà, réfléchis un peu ><";
                            break;
                        case "toggleautoplay":
                            Session.getPlayer().toggleAutoPlay();

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
                            if (e.getMessage().toLowerCase().startsWith("!setvolume")){
                                System.out.println(Double.parseDouble(e.getMessage().split(" ")[1]));
                                Session.getPlayer().setVolume(Double.parseDouble(e.getMessage().split(" ")[1]));
                                break;
                            }
                            rep="Je ne connais pas cette commande, je te la renvoie donc ! (>'.')> ~= "+e.getMessage();
                            break;
                    }
                    if (rep!=null)
                        ts3Api.sendPrivateMessage(id,rep);
                }
            }

        });
        for (String user : users) {
            Client pignouf = ts3Api.getClientByNameExact(user, true);
            if (pignouf != null){
                ts3Api.sendPrivateMessage(pignouf.getId(), "Plop, je suis en ligne ! Tente !help pour avoir la liste des commandes disponibles.");
            }
        }
    }

    public static void main(String[] args){
        new TS3Api();
    }

    public void exit() {
        ts3Api.logout();
    }
}
