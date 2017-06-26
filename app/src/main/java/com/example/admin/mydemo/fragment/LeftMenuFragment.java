package com.example.admin.mydemo.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.adapter.LeftMenuAdapter;
import com.example.admin.mydemo.base.BaseFragment;
import com.example.admin.mydemo.bean.LeftBean;

import java.util.ArrayList;
import java.util.List;

public class LeftMenuFragment extends BaseFragment {

	private List<LeftBean> list = null;
	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		list = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			LeftBean lb = new LeftBean();
			lb.setStr("我是第"+i+"个横条");
			list.add(lb);
		}

	}

	@Override
	protected int setLayout() {
		return R.layout.left_menu;
	}

	@Override
	protected void initViews() {
	   lv = bindView(R.id.left_menu_lv);
	}

	@Override
	protected void initDatas() {
		LeftMenuAdapter adapter = new LeftMenuAdapter(getActivity(),list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(context, "吐司表示一下", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
