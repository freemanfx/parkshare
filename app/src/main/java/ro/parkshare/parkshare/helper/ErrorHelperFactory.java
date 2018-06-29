package ro.parkshare.parkshare.helper;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import ro.parkshare.parkshare.ParkShareApp;

public class ErrorHelperFactory {
    private static final String TAG = ParkShareApp.class.getName();

    private Context context;

    private ErrorHelperFactory(Context context) {
        this.context = context;
    }

    public static ErrorHelperFactory errorHelper(Context context) {
        return new ErrorHelperFactory(context);
    }

    public void longToast(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
        Toast.makeText(context, message + throwable.toString(), Toast.LENGTH_LONG).show();
    }

    public void longToast(@StringRes int resId, Throwable throwable) {
        String message = context.getString(resId);
        longToast(message, throwable);
    }
}
