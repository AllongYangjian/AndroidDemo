package org.example.yj.customview.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.yj.customview.R;

/**
 * @author yj on  2018/5/16 17:41
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class ComprehensiveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comprehensive,container,false);
        return view;
    }

    public static ComprehensiveFragment newInstance(){
        return new ComprehensiveFragment();
    }
}
