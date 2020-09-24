package org.example.yj.customview.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.yj.customview.R;
import org.example.yj.customview.activity.picturetag.PictureTagViewActivity;
import org.example.yj.customview.activity.tag.TagActivity;
import org.example.yj.customview.activity.tag.TagActivity2;
import org.example.yj.customview.activity.textwall.TextWallActivty;
import org.example.yj.customview.adapter.CommonStringAdapter;
import org.example.yj.customview.adapter.RecyclerItemCallback;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yj on  2018/5/16 17:36
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class UIFragment extends Fragment {


    //    @BindView(R.id.textwall)
//    TextWall textwall;
    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ui, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);

        CommonStringAdapter adapter = new CommonStringAdapter(getContext());
        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.customview_item_title));
        adapter.setRecItemClick(new RecyclerItemCallback<String, CommonStringAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, String model, int tag) {
                super.onItemClick(position, model, tag);
                if(model.equals("文字墙(TextWall)")){
                    Intent intent = new Intent(getContext(),TextWallActivty.class);
                    startActivity(intent);
                }else if(model.equals("TAG标签")){
                    Intent intent = new Intent(getContext(),TagActivity.class);
                    startActivity(intent);
                }else if(model.equals("TAG标签(×)")){
                    Intent intent = new Intent(getContext(),TagActivity2.class);
                    startActivity(intent);
                }else if(model.equals("PictureTagView")){
                    Intent intent = new Intent(getContext(),PictureTagViewActivity.class);
                    startActivity(intent);
                }
            }
        });

        adapter.setData(stringList);
        recyclerview.setAdapter(adapter);
    }


    public static UIFragment newInstance() {
        return new UIFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
