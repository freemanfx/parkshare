package ro.parkshare.parkshare.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.ToastHelper;

import static ro.parkshare.parkshare.BeanProvider.activityNavigator;
import static ro.parkshare.parkshare.BeanProvider.userService;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText usernameField;

    @BindView(R.id.password)
    EditText passwordField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void onLoginClicked() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        userService()
                .login(username, password)
                .observeOn(mainThread())
                .subscribe((o) -> activityNavigator().toMain(), this::onLoginError);
    }

    private void onLoginError(Throwable throwable) {
        String message = getString(R.string.login_error_message) + throwable.getMessage();
        ToastHelper.of(this).show(message);
    }
}
