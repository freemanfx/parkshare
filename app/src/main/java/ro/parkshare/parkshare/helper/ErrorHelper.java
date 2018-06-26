package ro.parkshare.parkshare.helper;

import android.content.Context;
import android.widget.Toast;

public class ErrorHelper {

    private Context context;

    private ErrorHelper(Context context) {
        this.context = context;
    }

    public static ErrorHelper with(Context context) {
        return new ErrorHelper(context);
    }

    public void longToast(String message, Throwable throwable) {
        Toast.makeText(context, message + throwable.toString(), Toast.LENGTH_LONG).show();
    }
}
