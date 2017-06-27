package CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Montserrat.ttf");
            setTypeface(tf);
        }
    }
}
