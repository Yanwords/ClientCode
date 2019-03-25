package com.nju.myapp.spotlists;

import java.util.List;

import com.nju.myapp.R;
import com.nju.myapp.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SpotAdapter extends ArrayAdapter<Spot> {
	private int resourceId;

	public SpotAdapter(Context context, int resourceId, List<Spot> objects) {

		super(context, resourceId, objects);
		this.resourceId = resourceId;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Spot spot = getItem(position);
		// View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		View view;
		ViewHolder holder;

		if (convertView == null) {

			view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
			holder = new ViewHolder();
			holder.order = (TextView) view.findViewById(R.id.order);
			holder.logo = (ImageView) view.findViewById(R.id.logo);
			holder.car_order = (TextView) view.findViewById(R.id.car_order);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.money = (TextView) view.findViewById(R.id.money);
			holder.jud = (CheckBox) view.findViewById(R.id.jud);
			holder.delta = (Button) view.findViewById(R.id.delta);

			view.setTag(holder);
		} else {

			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.order.setText(spot.getorder() + "");
		holder.logo.setImageResource(spot.getlogoID());
		holder.car_order.setText(spot.getcar_order());
		holder.name.setText(spot.getname() + "");
		holder.money.setText(spot.getmoney() + "");

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
		TextView car_order;
		TextView name;
		TextView money;
		CheckBox jud;
		Button delta;

	}
}
