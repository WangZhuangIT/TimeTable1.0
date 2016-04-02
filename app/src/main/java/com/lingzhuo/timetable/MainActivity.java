package com.lingzhuo.timetable;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lingzhuo.Utils.GetTimeTable;
import com.lingzhuo.bean.CourseBean;
import com.lingzhuo.bean.LoginData;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wang on 2016/3/29.
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Fragment> pager_list;
    private LoginData loginData;
    private List<CourseBean> courseList, courseList1, courseList2, courseList3, courseList4, courseList5;
    private ListView listView_pager1, listView_pager2, listView_pager3, listView_pager4, listView_pager5;
    private MyFragment myFragment1, myFragment2, myFragment3, myFragment4, myFragment5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent intent = getIntent();
        loginData = intent.getParcelableExtra("loginData");
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), pager_list);
        viewPager.setAdapter(adapter);
        Toast.makeText(MainActivity.this, "欢迎登陆，" + loginData.getName() + "同学", Toast.LENGTH_SHORT).show();
        new Thread(new GetTimeTable(loginData, courseList, myHanler)).start();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        listView_pager1 = myFragment1.getListView();
                        MyAdapter adapter1 = new MyAdapter(MainActivity.this, R.layout.layout_item_listview, courseList1);
                        listView_pager1.setAdapter(adapter1);
                        break;
                    case 1:
                        listView_pager2 = myFragment2.getListView();
                        MyAdapter adapter2 = new MyAdapter(MainActivity.this, R.layout.layout_item_listview, courseList2);
                        listView_pager2.setAdapter(adapter2);
                        break;
                    case 2:
                        listView_pager3 = myFragment3.getListView();
                        MyAdapter adapter3 = new MyAdapter(MainActivity.this, R.layout.layout_item_listview, courseList3);
                        listView_pager3.setAdapter(adapter3);
                        break;
                    case 3:
                        listView_pager4 = myFragment4.getListView();
                        MyAdapter adapter4 = new MyAdapter(MainActivity.this, R.layout.layout_item_listview, courseList4);
                        listView_pager4.setAdapter(adapter4);
                        break;
                    case 4:
                        listView_pager5 = myFragment5.getListView();
                        MyAdapter adapter5 = new MyAdapter(MainActivity.this, R.layout.layout_item_listview, courseList5);
                        listView_pager5.setAdapter(adapter5);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private Handler myHanler = new Handler() {
        public void handleMessage(Message msg) {
            courseList = (List) msg.obj;
            for (int i = 0; i < courseList.size(); i++) {
                CourseBean courseBean = courseList.get(i);
                if (courseBean.getCourse_week().contains("周一")) {
                    courseList1.add(courseBean);
                } else if (courseBean.getCourse_week().contains("周二")) {
                    courseList2.add(courseBean);
                } else if (courseBean.getCourse_week().contains("周三")) {
                    courseList3.add(courseBean);
                } else if (courseBean.getCourse_week().contains("周四")) {
                    courseList4.add(courseBean);
                } else if (courseBean.getCourse_week().contains("周五")) {
                    courseList5.add(courseBean);
                }
            }
            listView_pager1 = myFragment1.getListView();
            MyAdapter adapter1 = new MyAdapter(MainActivity.this, R.layout.layout_item_listview, courseList1);
            listView_pager1.setAdapter(adapter1);
        }
    };


    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pager_list = new ArrayList<>();
        myFragment1 = new MyFragment();
        myFragment2 = new MyFragment();
        myFragment3 = new MyFragment();
        myFragment4 = new MyFragment();
        myFragment5 = new MyFragment();
        pager_list.add(myFragment1);
        pager_list.add(myFragment2);
        pager_list.add(myFragment3);
        pager_list.add(myFragment4);
        pager_list.add(myFragment5);
        courseList = new ArrayList<>();
        courseList1 = new ArrayList<>();
        courseList2 = new ArrayList<>();
        courseList3 = new ArrayList<>();
        courseList4 = new ArrayList<>();
        courseList5 = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_out:
                this.finish();
                Toast.makeText(MainActivity.this, "用户已注销，请重新登录！！！", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
        }
        return true;
    }
}
