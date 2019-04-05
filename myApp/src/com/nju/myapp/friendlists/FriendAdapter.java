package com.nju.myapp.friendlists;

import java.util.List;

import com.nju.myapp.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendAdapter extends ArrayAdapter<Friend> {
	private int resourceId;

	public FriendAdapter(Context context, int resourceId, List<Friend> objects) {

		super(context, resourceId, objects);
		this.resourceId = resourceId;
		Log.v("adapter:", Integer.toString(objects.size()));
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Friend friend = getItem(position);
		// View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		View view;
		ViewHolder holder;

		if (convertView == null) {

			view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
			holder = new ViewHolder();
			holder.order = (TextView) view.findViewById(R.id.order);
			holder.logo = (ImageView) view.findViewById(R.id.logo);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.gender = (TextView) view.findViewById(R.id.gender);
			holder.age = (TextView) view.findViewById(R.id.age);
			holder.jud = (CheckBox) view.findViewById(R.id.jud);
			holder.delta = (Button) view.findViewById(R.id.delta);

			view.setTag(holder);
		} else {

			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.order.setText(friend.getOrder() + "");
		holder.logo.setImageResource(friend.getLogo());
		holder.name.setText(friend.getName());
		holder.gender.setText(friend.getGender());
		holder.age.setText(Integer.toString(friend.getAge()));

		holder.jud.setTag(position);
		holder.delta.setTag(position);

		holder.delta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				itemSelect.seleId(position);
			}
		});

		return view;
	}

	public interface onItemSelect {// 回调接口

		void seleId(int i);
	}

	private onItemSelect itemSelect;

	public void setOnItemSelectListener(onItemSelect listener) {

		this.itemSelect = listener;
	}

	class ViewHolder {

		TextView order;
		ImageView logo;
		TextView name;
		TextView gender;
		TextView age;
		CheckBox jud;
		Button delta;

	}
}