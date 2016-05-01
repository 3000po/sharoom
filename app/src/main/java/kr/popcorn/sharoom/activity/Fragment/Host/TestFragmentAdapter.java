package kr.popcorn.sharoom.activity.Fragment.Host;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Fragment.User.*;
import kr.popcorn.sharoom.activity.Fragment.User.IconPagerAdapter;
import kr.popcorn.sharoom.activity.Fragment.User.TestFragment;

public class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "a", "b", "c", "d", "e" };
    protected static final int[] ICONS = new int[] {
            R.drawable.perm_group_roomview,
            R.drawable.perm_group_reservation,
            R.drawable.perm_group_register,
            R.drawable.perm_group_message,
            R.drawable.perm_group_myinformation
    };


    private int mCount = CONTENT.length;

    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return kr.popcorn.sharoom.activity.Fragment.Host.TestFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TestFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}