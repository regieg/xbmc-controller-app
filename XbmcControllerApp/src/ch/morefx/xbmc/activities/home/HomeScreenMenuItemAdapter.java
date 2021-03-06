package ch.morefx.xbmc.activities.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.ThumbnailHolder;
import ch.morefx.xbmc.util.ThumbnailLoader;

public class HomeScreenMenuItemAdapter extends ArrayAdapter<HomeScreenMenuItem> {

	private LayoutInflater inflater;
	
	public HomeScreenMenuItemAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.homescreenactivity_item_layout, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.homescreenactivity_item_title);
			holder.detail = (TextView) convertView.findViewById(R.id.homescreenactivity_item_description);
			holder.image = (ImageView) convertView.findViewById(R.id.homescreenactivity_item_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		HomeScreenMenuItem mnuItem = getItem(position);
		holder.title.setText(mnuItem.getTitle());
		holder.detail.setText(mnuItem.getDescription());
		
		if (mnuItem.getIconDrawable() != null){
			holder.image.setImageDrawable(mnuItem.getIconDrawable());	
		} else if (mnuItem.getThumbnailHolder() != null){
			loadThumbail(mnuItem.getThumbnailHolder(), holder.image);
		}
		
		return convertView;
	}
	
	private void loadThumbail(ThumbnailHolder holder, ImageView view){		
		ThumbnailLoader loader = new ThumbnailLoader(holder, getContext());
		loader.loadIntoView(view);
	}
	
  	private static class ViewHolder {
  		ImageView image;
 		TextView title;
 		TextView detail;
 	}
}
