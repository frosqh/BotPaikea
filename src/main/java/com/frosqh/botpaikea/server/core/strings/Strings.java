package com.frosqh.botpaikea.server.core.strings;

public abstract class Strings {
    public final String PAIKEA_SONG = "\"\\n\" +\n" +
            "            \"Uia mai koia, whakahuatia ake; \\n\" +\n" +
            "            \"Ko wai te whare nei e?\\n\" +\n" +
            "            \"Ko Te Kani / Ko Rangi / Whitireia! \\n\" +\n" +
            "            \"Ko wai te tekoteko kei runga? \\n\" +\n" +
            "            \"Ko Paikea! Ko Paikea! \\n\" +\n" +
            "            \"Whakakau Paikea. Hei! \\n\" +\n" +
            "            \"Whakakau he tipua. Hei! \\n\" +\n" +
            "            \"Whakakau he taniwha. Hei! \\n\" +\n" +
            "            \"Ka ū Paikea ki Ahuahu. Pakia! \\n\" +\n" +
            "            \"Kei te whitia koe \\n\" +\n" +
            "            \"ko Kahutia-te-rangi. Aue! \\n\" +\n" +
            "            \"Me ai tō ure ki te tamahine \\n\" +\n" +
            "            \"a Te Whironui - aue! -\\n\" +\n" +
            "            \"nāna i noho te Roto-o-tahe. \\n\" +\n" +
            "            \"Aue! Aue! \\n\" +\n" +
            "            \"He koruru koe, koro e.\"";
    public abstract String WELCOME_MESSAGE();

    public abstract String LIST();
    public abstract String SEE_MORE();

    public abstract String USAGE_HELP();
    public abstract String USAGE_PAIKEA();
    public abstract String USAGE_NEXT();
    public abstract String USAGE_PLAY();
    public abstract String USAGE_PAUSE();
    public abstract String USAGE_PREV();
    public abstract String USAGE_SETVOLUME();
    public abstract String USAGE_TOGGLEAUTOPLAY();
    public abstract String USAGE_INFO();

    public abstract String DESC_HELP();
    public abstract String DESC_PAIKEA();
    public abstract String DESC_NEXT();
    public abstract String DESC_PLAY();
    public abstract String DESC_PAUSE();
    public abstract String DESC_PREV();
    public abstract String DESC_SETVOLUME();
    public abstract String DESC_TOGGLEAUTOPLAY();
    public abstract String DESC_INFO();

    public abstract String NOW_PLAYING(String song, String artist);

    public abstract String NOTHING_PLAYING();

    public abstract String ERROR_PAUSE();

    public abstract String EASTER_SHIT();
    public abstract String NOT_FOUND(String cmd);

    public abstract String SUCC_PLAY();

    public abstract String ERROR_ON_PLAY();

    public abstract String ERROR_PLAY();

    public abstract String TOGGLE_AUTOPLAY_ON();

    public abstract String TOGGLE_AUTOPLAY_OFF();

    public abstract String EASTER_SHIT_RESPONSE();

    public abstract String EASTER_GOOGLE_RESPONSE();

    public abstract String EASTER_NO_RESPONSE();

    public abstract String UNDEFINED_BEHAVIOR();

    public abstract String EASTER_PLOP_RESPONSE();

    public abstract String EASTER_UNDEFINED_BEHAVIOUR();

    public abstract String WIP();

    public abstract String NO_PREV();

    public abstract String DID_YOU_MEAN(String arg, String almost);
}
