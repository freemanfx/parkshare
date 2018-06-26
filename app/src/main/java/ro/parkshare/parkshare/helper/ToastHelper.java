package ro.parkshare.parkshare.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    private Context context;

    public ToastHelper(Context context) {
        this.context = context;
    }

    public static ToastHelper of(Context context) {
        ToastHelper toastHelper = new ToastHelper(context);
        return toastHelper;
    }

    public void show(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG);
    }
}
