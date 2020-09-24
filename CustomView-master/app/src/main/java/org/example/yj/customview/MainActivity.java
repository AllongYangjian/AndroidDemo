package org.example.yj.customview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.example.yj.customview.view.ComprehensiveFragment;
import org.example.yj.customview.view.MediaFragment;
import org.example.yj.customview.view.SystemFragment;
import org.example.yj.customview.view.UIFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.rb_ui)
    RadioButton rbUi;
    @BindView(R.id.rb_net_and_media)
    RadioButton rbNetAndMedia;
    @BindView(R.id.rb_system_and_hardware)
    RadioButton rbSystemAndHardware;
    @BindView(R.id.rb_comprehensive)
    RadioButton rbComprehensive;
    @BindView(R.id.rg_nav)
    RadioGroup rgNav;

    private Fragment oldFragment;
    private int currentFragmentIndex = -1;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(UIFragment.newInstance());
        fragmentList.add(MediaFragment.newInstance());
        fragmentList.add(SystemFragment.newInstance());
        fragmentList.add(ComprehensiveFragment.newInstance());
    }

    private void initView() {
        rgNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ui:
                        currentFragmentIndex = 0;
                        break;
                    case R.id.rb_net_and_media:
                        currentFragmentIndex = 1;
                        break;
                    case R.id.rb_system_and_hardware:
                        currentFragmentIndex = 2;
                        break;
                    case R.id.rb_comprehensive:
                        currentFragmentIndex = 3;
                        break;
                }
                Fragment to = fragmentList.get(currentFragmentIndex);
                switchFragment(oldFragment, to);
            }
        });

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        rbUi.setChecked(true);
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            oldFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.container, to).commit();
            }
        } else {
            //TODO refresh
        }
    }
}
