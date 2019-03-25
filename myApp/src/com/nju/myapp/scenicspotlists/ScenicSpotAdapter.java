package com.nju.myapp.scenicspotlists;

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

public class ScenicSpotAdapter extends ArrayAdapter<ScenicSpot> {
	private int resourceId;

	public ScenicSpotAdapter(Context context, int resourceId, List<ScenicSpot> objects) {

		super(context, resourceId, objects);
		this.resourceId = resourceId;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ScenicSpot scenicspot = getItem(position);
		// View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		View view;
		ViewHolder holder;

		if (convertView == null) {

			view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
			holder = new ViewHolder();
			holder.order = (TextView) view.findViewById(R.id.order);
			holder.logo = (ImageView) view.findViewById(R.id.logo);
			holder.spotname = (TextView) view.findViewById(R.id.spotname);
			holder.score = (TextView) view.findViewById(R.id.score);
			holder.information = (TextView) view.findViewById(R.id.information);
			holder.jud = (CheckBox) view.findViewById(R.id.jud);
			holder.delta = (Button) view.findViewById(R.id.delta);

			view.setTag(holder);
		} else {

			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.order.setText(scenicspot.getorder() + "");
		holder.logo.setImageResource(scenicspot.getlogoID());
		holder.spotname.setText(scenicspot.getSpotname());
		holder.score.setText(Float.toString(scenicspot.getScore()));
		holder.information.setText(scenicspot.getInformation());

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
		TextView spotname;
		TextView score;
		TextView information;
		CheckBox jud;
		Button delta;

	}
}
