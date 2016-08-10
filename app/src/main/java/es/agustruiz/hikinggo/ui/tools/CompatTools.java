package es.agustruiz.hikinggo.ui.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class CompatTools {

    //region [Public static methods]

    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context context, int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(drawableId);
        } else {
            return context.getResources().getDrawable(drawableId);
        }
    }

    //endregion

}
