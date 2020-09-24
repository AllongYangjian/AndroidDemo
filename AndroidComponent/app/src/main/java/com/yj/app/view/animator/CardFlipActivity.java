package com.yj.app.view.animator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.yj.app.R;
import com.yj.app.databinding.ActivityCardFlipBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class CardFlipActivity extends AppCompatActivity {

    private ActivityCardFlipBinding binding;

    private boolean showingBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_flip);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, new CardFrontFragment())
                .commit();
    }

    private void flipCard() {
        if (showingBack) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        showingBack = true;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.content, new CardBackFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animator, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reverse:
                flipCard();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class CardFrontFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_positive, container, false);
        }
    }

    public static class CardBackFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_reverse, container, false);
        }
    }
}
