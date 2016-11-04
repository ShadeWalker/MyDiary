package com.kiminonawa.mydiary.entries;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kiminonawa.mydiary.R;
import com.kiminonawa.mydiary.entries.calendar.CalendarFragment;
import com.kiminonawa.mydiary.entries.diary.DiaryFragment;
import com.kiminonawa.mydiary.entries.entries.EntriesFragment;

import info.hoang8f.android.segmented.SegmentedGroup;

public class DiaryActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {


    private long topicId;

    /**
     * UI
     */
    private LinearLayout LL_diary_topbar_content;
    private ViewPager ViewPager_diary_content;
    private TextView TV_diary_topbar_title;
    private SegmentedGroup SG_diary_topbar;
    private RadioButton But_diary_topbar_entries, But_diary_topbar_calendar, But_diary_topbar_diary;


    /**
     * View pager
     */
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        topicId = getIntent().getLongExtra("topicId", -1);
        if (topicId == -1) {
            //TODO close this activity and show toast
        }
        initViewPager();
        /**
         * init UI
         */
        LL_diary_topbar_content = (LinearLayout) findViewById(R.id.LL_diary_topbar_content);
        SG_diary_topbar = (SegmentedGroup) findViewById(R.id.SG_diary_topbar);
        SG_diary_topbar.setOnCheckedChangeListener(this);
        But_diary_topbar_entries = (RadioButton) findViewById(R.id.But_diary_topbar_entries);
        But_diary_topbar_calendar = (RadioButton) findViewById(R.id.But_diary_topbar_calendar);
        But_diary_topbar_diary = (RadioButton) findViewById(R.id.But_diary_topbar_diary);
        But_diary_topbar_entries.setChecked(true);

        TV_diary_topbar_title = (TextView) findViewById(R.id.TV_diary_topbar_title);

        String diaryTitle = getIntent().getStringExtra("diaryTitle");
        if (diaryTitle == null) {
            diaryTitle = "Diary";
        }
        TV_diary_topbar_title.setText(diaryTitle);
    }


    private void initViewPager() {
        /**
         * Init Viewpager
         */
        ViewPager_diary_content = (ViewPager) findViewById(R.id.ViewPager_diary_content);
        //Make viewpager load one fragment every time.
        ViewPager_diary_content.setOffscreenPageLimit(2);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        ViewPager_diary_content.setAdapter(mPagerAdapter);
        ViewPager_diary_content.addOnPageChangeListener(onPageChangeListener);
    }

    public long getTopicId() {
        return topicId;
    }

    public void gotoPage(int postion) {
        ViewPager_diary_content.setCurrentItem(postion);
    }

    public void popTopBar() {
        //TODO make topbar always on Top
//        LL_diary_topbar_content.bringToFront();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.But_diary_topbar_entries:
                ViewPager_diary_content.setCurrentItem(0);
                break;
            case R.id.But_diary_topbar_calendar:
                ViewPager_diary_content.setCurrentItem(1);
                break;
            case R.id.But_diary_topbar_diary:
                ViewPager_diary_content.setCurrentItem(2);
                break;
        }
    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                default:
                    But_diary_topbar_entries.setChecked(true);
                    break;
                case 1:
                    But_diary_topbar_calendar.setChecked(true);
                    break;
                case 2:
                    But_diary_topbar_diary.setChecked(true);
                    break;
            }
        }
    };

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseDiaryFragment getItem(int position) {
            BaseDiaryFragment fragment;
            switch (position) {
                default:
                    fragment = new EntriesFragment();
                    break;
                case 1:
                    fragment = new CalendarFragment();
                    break;
                case 2:
                    fragment = new DiaryFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
