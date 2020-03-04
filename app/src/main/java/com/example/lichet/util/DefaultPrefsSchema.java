package com.example.lichet.util;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table(name = "com.example.lichet.user_preferences")
public abstract class DefaultPrefsSchema {
    @Key(name = "accessToken")
    String accessToken;
}
