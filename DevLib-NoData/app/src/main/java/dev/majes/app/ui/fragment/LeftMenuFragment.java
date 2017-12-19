package dev.majes.app.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import dev.majes.app.R;
import dev.majes.app.adapter.LeftMenuAdapter;
import dev.majes.app.bean.LeftBean;
import dev.majes.base.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class LeftMenuFragment extends BaseFragment {

	private List<LeftBean> list = null;
	private ListView lv;


	@Override
	public void bindUI(View rootView) {
		lv = rootView.findViewById(R.id.left_menu_lv);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		list = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			LeftBean lb = new LeftBean();
			lb.setStr("我是第"+i+"个横条");
			list.add(lb);
		}
		LeftMenuAdapter adapter = new LeftMenuAdapter(getActivity(),list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(), "吐司表示一下", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public int getLayoutId() {
		return R.layout.left_menu;
	}

}
