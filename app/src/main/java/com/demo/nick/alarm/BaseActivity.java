package com.demo.nick.alarm;

import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Base Activity
 * Created by nick on 18-12-5.
 */

public abstract class BaseActivity extends AppCompatActivity {

//    @Nullable
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;

    private Menu mMenu;

    @LayoutRes protected abstract int layoutResId();
    @MenuRes protected abstract int menuResId();

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Set theme after setContentView
        final String themeDark = getString(R.string.theme_dark);
        final String themeBlack = getString(R.string.theme_black);

        String theme = PreferenceManager.getDefaultSharedPreferences(this).getString(
                getString(R.string.key_theme), null);

        if (themeDark.equals(theme)) {
            setTheme(R.style.APPTheme_Dark);
        } else if (themeBlack.equals(theme)) {
            setTheme(R.style.AppTheme_Black);
        }

        setContentView(layoutResId());

        setVolumeControlStream(AudioManager.STREAM_ALARM);
        ButterKnife.bind(this);
//        if (mToolbar != null) {
//            setSupportActionBar(mToolbar);
//            ActionBar ab = getSupportActionBar();
//            if (ab != null) {
//                ab.setDisplayHomeAsUpEnabled(isDisplayHomeUpEnabled());
//                ab.setDisplayShowTitleEnabled(isDisplayShowTitleEnabled());
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId() != 0) {
            getMenuInflater().inflate(menuResId(), menu);
            mMenu = menu;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Nullable
    public final Menu getMenu() {
        return mMenu;
    }

    protected boolean isDisplayHomeUpEnabled() {
        return true;
    }

    protected boolean isDisplayShowTitleEnabled() {
        return false;
    }
}
