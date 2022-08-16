package uz.alijonovz.startdroid21onlineshopping.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class LocaleManager {

    public static Context setLocale(Context mContext) {
        Log.d("prefs", getLanguagePref(mContext));
        return updateResources(mContext, getLanguagePref(mContext));
    }

    public Context setNewLocale(Context mContext, String language) {
        setLanguagePref(mContext, language);
        return updateResources(mContext, language);
    }

    public static String getLanguagePref(Context mContext) {
        return Hawk.get("pref_lang", "en");
    }

    public static void setLanguagePref(Context mContext, String localeKey) {
        Hawk.put("pref_lang", localeKey);
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();

        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
            res.updateConfiguration(config, res.getDisplayMetrics());
        } else {
            config.setLocale(locale);
        }
        return context;
    }
}
