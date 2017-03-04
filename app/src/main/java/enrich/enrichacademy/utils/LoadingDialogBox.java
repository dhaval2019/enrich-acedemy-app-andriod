package enrich.enrichacademy.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import enrich.enrichacademy.R;

/**
 * Created by Admin on 03-Mar-17.
 */

public class LoadingDialogBox extends Dialog {

    static int i = 1;
    public Activity c;
    public Dialog d;
    String message;

    public LoadingDialogBox(Activity a) {
        super(a);
        this.c = a;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.item_loader);
        if (message != null) {
            ((TextView) findViewById(R.id.loader_text_view)).setText(message);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
        }
    }
}
