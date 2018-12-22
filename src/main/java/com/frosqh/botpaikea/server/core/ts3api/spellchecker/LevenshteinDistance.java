package com.frosqh.botpaikea.server.core.ts3api.spellchecker;

public abstract class LevenshteinDistance {

    public static int getDistance(String word1, String word2){
        Integer[] d[] = new Integer[word1.length()][word2.length()];
        int i,j,subCost;
        for(i=0;i<word1.length();i++){
            d[i][0]=i;
        }
        for (j=0;j<word2.length();j++){
            d[0][j]=j;
        }
        for (i=1;i<word1.length();i++){
            for (j=1;j<word2.length();j++){
                if (word1.charAt(i-1) == word2.charAt(j-1))
                    subCost = 0;
                else
                    subCost = 1;
                d[i][j] = Math.min(Math.min(d[i-1][j]+1,d[i][j-1]+1),d[i-1][j-1]+subCost);
            }
        }
        return d[word1.length()-1][word2.length()-1];
    }
}
