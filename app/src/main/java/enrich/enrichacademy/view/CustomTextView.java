package enrich.enrichacademy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.utils.Constants;

/**
 * Created by Admin on 21-Feb-17.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public static Typeface REGULAR_TTF;
    public static Typeface BOLD_TTF;

    public CustomTextView(Context context) {
        super(context);
        setCustomTypeFace(context, 2);
    }

    public CustomTextView(Context context, AttributeSet attr) {
        super(context, attr);
        TypedArray a = context.obtainStyledAttributes(attr, R.styleable.CustomTextView);
        if (a.hasValue(R.styleable.CustomTextView_font_type)) {
            int value = a.getInt(R.styleable.CustomTextView_font_type, 0);
            setCustomTypeFace(context, value);
        }
    }

    private int setCustomTypeFace(Context context, int fontType) {
        try {
            if (!isInEditMode()) {
                if (fontType == 1)
                    setTypeface(getRegularTtf(context), Typeface.NORMAL);
                else if (fontType == 2)
                    setTypeface(getBoldTtf(context), Typeface.NORMAL);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fontType;
    }

    public static Typeface getRegularTtf(Context context) {
        if (REGULAR_TTF == null)
            REGULAR_TTF = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + Constants.REGULAR_FONT);
        return REGULAR_TTF;
    }

    public static Typeface getBoldTtf(Context context) {
        if (BOLD_TTF == null)
            BOLD_TTF = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + Constants.BOLD_FONT);
        return BOLD_TTF;
    }
}
