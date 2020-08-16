package com.mygdx.game.novel1.utils;

import com.mygdx.game.novel1.constants.Separators;

public class StringBuilder {

    public static String generateFileName(String path, String fileName, String extension) {
        return path + fileName + Separators.FILETYPE + extension;
    }
}
