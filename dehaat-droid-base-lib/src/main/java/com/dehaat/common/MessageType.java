package com.dehaat.common;

import androidx.annotation.IntDef;

import kotlin.annotation.Retention;

@IntDef({MessageType.TOAST, MessageType.DIALOG})
@Retention()
public @interface MessageType {
    int TOAST = 0;
    int DIALOG = 1;
}
