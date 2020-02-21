package edu.stevens.cs522.chat.async;

import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * Created by dduggan.
 */

public interface IContinue<T> {
    public void kontinue(T value) throws ParseException, UnknownHostException;
}

