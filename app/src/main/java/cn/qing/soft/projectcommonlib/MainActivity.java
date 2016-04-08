package cn.qing.soft.projectcommonlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.qing.soft.projectcommonlib.fgnavigator.FragmentNavigatorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fragmentNavigator(View view) {

        startActivity(new Intent(this, FragmentNavigatorActivity.class));

    }
}
