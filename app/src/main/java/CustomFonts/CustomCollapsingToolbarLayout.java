package CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;


public class CustomCollapsingToolbarLayout extends CollapsingToolbarLayout {
    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCollapsingToolbarLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Montserrat.ttf");
            setCollapsedTitleTypeface(tf);
        }
    }
}
