package com.example.android.diaryentry;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.StorageException;

class MyFailureListener implements OnFailureListener {
    @Override
    public void onFailure(@NonNull Exception exception) {
        int errorCode = ((StorageException) exception).getErrorCode();
        String errorMessage = exception.getMessage();

    }
}
