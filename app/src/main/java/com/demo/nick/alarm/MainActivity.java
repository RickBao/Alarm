package com.demo.nick.alarm;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.demo.nick.alarm.list.RecyclerViewFragment;
import com.demo.nick.alarm.util.TimePickerUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "Nick.alarm.MainActivity";

    public static final int    PAGE_ALARMS             = 0;
    public static final int    PAGE_TIMERS             = 1;
    public static final int    PAGE_STOPWATCH          = 2;
    public static final int    REQUEST_THEME_CHANGE    = 5;
    public static final String EXTRA_SHOW_PAGE         = "com.nick.alarm.extra.SHOW_PAGE";


    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Drawable             mAddItemDrawable;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (mViewPager.getChildCount() == mSectionsPagerAdapter.getCount() - 1){
                    mFab.setTranslationX(mViewPager.getWidth() / -2f + getFabPixelOffsetForXTranslation());
                }
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, String.format("pos = %d, posOffset = %f, posOffsetPixels = %d",
                        position, positionOffset, positionOffsetPixels));

                int pageBeforeLast = mSectionsPagerAdapter.getCount() - 2;
                if (position <= pageBeforeLast) {
                    mFab.setTranslationX(0);
                } else {
                    float translationX = positionOffsetPixels / -2f;
                    translationX += positionOffset * getFabPixelOffsetForXTranslation();
                    mFab.setTranslationX(translationX);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected");
                if (position < mSectionsPagerAdapter.getCount() - 1) {
                    mFab.setImageDrawable(mAddItemDrawable);
                }

                Fragment f = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem());
                if (f instanceof BaseFragment) {
                    ((BaseFragment) f).onPageSelected();
                }
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ColorStateList tabIconColor = ContextCompat.getColorStateList(this, R.color.tab_icon_color);
        setTabIcon(PAGE_ALARMS, R.drawable.ic_alarm_24dp, tabIconColor);
        setTabIcon(PAGE_TIMERS, R.drawable.ic_timer_24dp, tabIconColor);
        setTabIcon(PAGE_STOPWATCH, R.drawable.ic_stopwatch_24dp, tabIconColor);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem());
//                if (f instanceof RecyclerViewFragment) {
//                    ((RecyclerViewFragment) f).onFabClick();
//                }
            }
        });

        mAddItemDrawable = ContextCompat.getDrawable(this, R.drawable.ic_add_24dp);
        handleActionScrollToStableId(getIntent(), false);
    }

    private void setTabIcon(int index, @DrawableRes int iconRes, @NonNull final ColorStateList color) {
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        Drawable icon = TimePickerUtils.getTintedDrawable(this, iconRes, color);
        DrawableCompat.setTintList(icon, color);
        tab.setIcon(icon);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleActionScrollToStableId(intent, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_THEME_CHANGE:
                break;
        }

    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected int menuResId() {
        return R.menu.menu_main;
    }

    @Override
    protected boolean isDisplayHomeUpEnabled() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //todo start setting activity use startActivityForResult;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private float getFabPixelOffsetForXTranslation() {
        final int margin;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            margin = ((ViewGroup.MarginLayoutParams) mFab.getLayoutParams()).rightMargin;
        } else {
            margin = 0;
        }
        return mFab.getWidth() / 2f + margin;
    }

    /**
     * Handles a PendingIntent, fired from e.g. clicking a notification, that tells us to
     * set the ViewPager`s current item and scroll to a specific RecyclerView item given
     * by its stable ID
     */
    private void handleActionScrollToStableId(@NonNull final Intent intent,
                                              final boolean performScroll) {
//        if ()
    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final SparseArray<Fragment> mFragments = new SparseArray<>(getCount());

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_ALARMS:
                    break;
                case PAGE_TIMERS:
                    break;
                case PAGE_STOPWATCH:
                    break;
                default:
                    throw new IllegalStateException("No fragment can be instantiated for position " + position);
            }
            // todo delete next line after add some fragments/
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            mFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getFragment(int position) {
            return mFragments.get(position);
        }
    }
}
