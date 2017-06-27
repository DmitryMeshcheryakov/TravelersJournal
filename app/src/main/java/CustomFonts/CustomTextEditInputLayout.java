package CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;


public class CustomTextEditInputLayout extends TextInputEditText {
    public CustomTextEditInputLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextEditInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextEditInputLayout(Context context) {
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
