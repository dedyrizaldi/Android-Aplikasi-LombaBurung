//package com.example.hobu;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Gallery;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//public class GalleryVIewExample extends Activity {
//
//    Gallery galleryView;
//    ImageView imgView;
//
//    //images from drawable
//    private int[] imageResource = {
//            R.drawable.add, R.drawable.add, R.drawable.add, R.drawable.add, R.drawable.add, R.drawable.add, R.drawable.add
//    };
//
//	@Override  
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.gallery_view_example);
//
//        imgView = (ImageView) findViewById(R.id.imageView);
//        galleryView = (Gallery) findViewById(R.id.gallery);
//
//        imgView.setImageResource(imageResource[0]);
//        galleryView.setAdapter(new myImageAdapter(this));
//
//        //gallery image onclick event
//        galleryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView parent, View v, int i, long id) {
//                imgView.setImageResource(imageResource[i]);
//                int imagePosition = i + 1;
//                //Toast.makeText(getApplicationContext(), "You have selected image = " + imagePosition, Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//    public class myImageAdapter extends BaseAdapter {
//        private Context mcontext;
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView mgalleryView = new ImageView(mcontext);
//            mgalleryView.setImageResource(imageResource[position]);
//            mgalleryView.setLayoutParams(new Gallery.LayoutParams(150, 150));
//            mgalleryView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            //imgView.setImageResource(R.drawable.image_border);
//            mgalleryView.setPadding(3, 3, 3, 3);
//
//            return mgalleryView;
//        }
//
//        public myImageAdapter(Context context) {
//            mcontext = context;
//        }
//
//        public int getCount() {
//            return imageResource.length;
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//    }
//
//}