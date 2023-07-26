package com.javarush.ivovnyanko.project_1;

import java.util.ArrayList;

/**
 * The Map class is a helper class that provides a static map() method to create a list of characters
 * used in encrypting and decrypting text
 */
public class Map {
    private Map () {}
    public static ArrayList<Character> map () {
        ArrayList<Character> arrayList = new ArrayList(){{
            add(' ');
            add('.');
            add(',');
            add('"');
            add(':');
            add('!');
            add('?');
            add('-');
        }};

        for (int i = 65; i <= 90; i++ ) {
            arrayList.add((char) i);
        }
        for (int i = 97; i <= 122; i++ ) {
            arrayList.add((char) i);
        }

        return arrayList;

    }
}
