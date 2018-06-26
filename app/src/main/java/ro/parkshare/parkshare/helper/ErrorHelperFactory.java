package ro.parkshare.parkshare.helper;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ErrorHelperFactory {

    private Context context;

    private ErrorHelperFactory(Context context) {
        this.context = context;
    }

    public static ErrorHelperFactory errorHelper(Context context) {
        return new ErrorHelperFactory(context);
    }

    public void longToast(String message, Throwable throwable) {
        Toast.makeText(context, message + throwable.toString(), Toast.LENGTH_LONG).show();
    }

    public void longToast(@StringRes int resId, Throwable throwable) {
        String message = context.getString(resId);
        Toast.makeText(context, message + ": " + throwable.toString(), Toast.LENGTH_LONG).show();
    }
}
