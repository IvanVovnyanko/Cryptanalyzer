package com.javarush.ivovnyanko.project_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Map {
    // public static final Map INSTANCE = new Map ();
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
