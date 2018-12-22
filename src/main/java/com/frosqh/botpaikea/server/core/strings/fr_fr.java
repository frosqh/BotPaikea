package com.frosqh.botpaikea.server.core.strings;

public class fr_fr extends Strings{
    public fr_fr(){};

    @Override
    public String WELCOME_MESSAGE() {
        return "Plop, je suis en ligne ! Tente !help pour avoir la liste des commandes disponibles.";
    }

    @Override
    public String USAGE_HELP() {
        return "Usage : !help [cmd]";
    }

    @Override
    public String USAGE_PAIKEA() {
        return "Usage : !paikea \n" +
                "Affiche une chanson traditionnelle maori";
    }

    @Override
    public String USAGE_NEXT() {
        return "Usage : !next \n" +
                "Stoppe la lecture courante, change la chanson courante vers la prochaine dans la queue et lance la lecture";
    }

    @Override
    public String USAGE_PLAY() {
        return "Usage : !play \n" +
                "Reprend une lecture en pause, ne lance pas une musique si aucune n'est en cours";
    }

    @Override
    public String USAGE_PAUSE() {
        return "Usage : !pause \n" +
                "Interrompt une lecture en cours";
    }

    @Override
    public String USAGE_PREV() {
        return "Usage : !prev \n" +
                "Stoppe la lecture courante, change la chanson courante vers la dernière jouée, et lance la lecture";
    }

    @Override
    public String USAGE_SETVOLUME() {
        return"Usage : !setVolume {volume}\n" +
                "Change le volume du lecteur vers la valeur {volume}";
    }

    @Override
    public String USAGE_TOGGLEAUTOPLAY() {
        return "Usage : !toggleAutoPlay\n" +
                "Bascule la lecture automatique entre on et off";
    }

    @Override
    public String USAGE_INFO() {
        return "Usage : !info\n" +
                "Affiche les informations du lecteur";
    }

    @Override
    public String DESC_HELP() {
        return "Affiche ce message d'aide";
    }

    @Override
    public String DESC_PAIKEA() {
        return "Suprise ! ";
    }

    @Override
    public String DESC_NEXT() {
        return "Lit la prochaine musique dans la liste d'attente";
    }

    @Override
    public String DESC_PLAY() {
        return "Sort le lecteur de l'état 'Pause'";
    }

    @Override
    public String DESC_PAUSE() {
        return "Met le lecteur dans l'état 'Pause'";
    }

    @Override
    public String DESC_PREV() {
        return "Revient à la dernière musique jouée";
    }

    @Override
    public String DESC_SETVOLUME() {
        return "Change le volume du lecteur";
    }

    @Override
    public String DESC_TOGGLEAUTOPLAY() {
        return "(Dés)Active la lecture automatique";
    }

    @Override
    public String DESC_INFO() {
        return "Affiche la musique en cours";
    }

    @Override
    public String NOW_PLAYING(String song, String artist) {
        return "♫ Now playing - "+song+" by " + artist + " ♫";
    }

    @Override
    public String NOTHING_PLAYING() {
        return "Aucune musique n'est en train d'être jouée.";
    }

    @Override
    public String EASTER_SHIT() {
        return "merde";
    }

    @Override
    public String NOT_FOUND(String cmd) {
        return "Commande "+cmd+" non trouvée";
    }

    @Override
    public String SUCC_PLAY() {
        return "La lecture a bien été (re)prise !";
    }

    @Override
    public String ERROR_ON_PLAY() {
        return "La lecture n'a pas pu être reprise, n'hésite pas à réessayer si cela te semble étrange.";
    }

    @Override
    public String ERROR_PLAY() {
        return "Ça joue déjà, réfléchis un peu ><";
    }

    @Override
    public String TOGGLE_AUTOPLAY_ON() {
        return "La lecture automatique est désormais active !";
    }

    @Override
    public String TOGGLE_AUTOPLAY_OFF() {
        return "La lecture automatique est désactivée";
    }

    @Override
    public String EASTER_SHIT_RESPONSE() {
        return "Diantre*";
    }

    @Override
    public String EASTER_GOOGLE_RESPONSE() {
        return "Nan mais oh, tu me prends pour qui là ? ><";
    }

    @Override
    public String EASTER_NO_RESPONSE() {
        return "Si.";
    }

    @Override
    public String UNDEFINED_BEHAVIOR() {
        return "Alors, crois le ou non, mais il semblerait qu'on est oublié de définir le fonctionnement de cette commande 0:)";
    }

    @Override
    public String EASTER_PLOP_RESPONSE() {
        return "Plop à toi, mon frère !";
    }

    @Override
    public String EASTER_UNDEFINED_BEHAVIOUR() {
        return "Ah, c'est un peu bête, je suis sûr qu'on avait pensé à un easter egg ici, mais on a du oublier :/";
    }

    @Override
    public String LIST(){
        return "Voici la liste de commandes disponibles : \n";
    }

    @Override
    public String SEE_MORE(){
        return "N'hésitez pas à taper !help [cmd] pour obtenir de l'aide spécifique à une commande";
    }

    @Override
    public String ERROR_PAUSE(){
        return "Rien n'est est en train d'être joué, réfléchis un peu ><";
    }

    @Override
    public String WIP(){return "Cette fonction est encore en cours de développement, elle peut ne pas être fonctionnelle";}

    @Override
    public String NO_PREV() {
        return "Désolé, mais aucune chanson n'a précédé celle-ci";
    }

    @Override
    public String DID_YOU_MEAN(String arg, String almost) {
        return "Désolé, mais la commande "+arg+" n'existe pas. Vouliez-vous dire "+almost+" ?";
    }
}
