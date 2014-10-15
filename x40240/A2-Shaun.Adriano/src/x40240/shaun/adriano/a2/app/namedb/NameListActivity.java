package x40240.shaun.adriano.a2.app.namedb;

import java.util.List;

import x40240.shaun.adriano.a2.app.db.DBHelper;
import x40240.shaun.adriano.a2.app.model.PersonInfo;
import x40240.shaun.adriano.a2.app.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


public final class NameListActivity
    extends ListActivity
{
	
			
    private ListAdapter listAdapter;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intitialize();
        setListAdapter(listAdapter = new PersonInfoListAdpater(this));
    }

    //  Encapsulate non-ui related initialization here.
    private void intitialize() {
        Intent callingIntent = this.getIntent();  // Get the Intent that started us.
        
        //****************************************************************************************
        //  If we are being passed a Serialized POJO:
        //
        PersonInfo personInfo = (PersonInfo)callingIntent.getSerializableExtra("personInfo");
        
        //****************************************************************************************
        //  If we are being called with each PersonInfo field.
        //
//        String firstname = callingIntent.getStringExtra("firstname");
//        String lastname = callingIntent.getStringExtra("lastname");
//        int gender = callingIntent.getIntExtra("gender", -1);
//        
//        PersonInfo personInfo = new PersonInfo();
//        personInfo.setFirstname(firstname);
//        personInfo.setLastname(lastname);
//        personInfo.setGender(gender);
        
        new DBHelper(this).insert(personInfo);  //  Now do the insert.
    }
    
    private class PersonInfoListAdpater
        extends BaseAdapter
    {
        /**
         * Remember our context so we can use it when constructing views.
         */
        private Context mContext;
        private DBHelper dbHelper;
        private List<PersonInfo> list;
        private LayoutInflater   layoutInflater;

        public PersonInfoListAdpater (Context context) {
            mContext = context;
            dbHelper = new DBHelper(context);
            list = dbHelper.selectAll();
        }

        /**
         * The number of items in the list is determined by the number of
         * speeches in our array.
         * 
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount () {
            return list == null ? 0 : list.size();
        }

        /**
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem (int position) {
            return list.get(position);
        }

        /**
         * Use the array index as a unique id.
         * 
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId (int position) {
            return position;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        public View getView (int position, View convertView, ViewGroup parent)
        {
        	 ViewGroup listItem;
             if (convertView == null) {
             	listItem = (ViewGroup) getLayoutInflator().inflate(R.layout.list_item, null);
             }
             else {
             	listItem = (ViewGroup) convertView;
             }
             PersonInfo personInfo = list.get(position);
             TextView lastnameText = (TextView) listItem.findViewById(R.id.lastname_text);
             TextView firstnameText = (TextView) listItem.findViewById(R.id.firstname_text);
             TextView genderText = (TextView) listItem.findViewById(R.id.gender_text);
             TextView favmealText = (TextView) listItem.findViewById(R.id.favmeal_text);
             
             lastnameText.setText(personInfo.getLastname());
             firstnameText.setText(personInfo.getFirstname());
             

             String genderName;
             Resources resources = mContext.getResources();
             switch (personInfo.getGender()) {
             case PersonInfo.GENDER_MALE:
             	genderName = resources.getString(R.string.male);
                 break;
             case PersonInfo.GENDER_FEMALE:
             	genderName = resources.getString(R.string.female);
                 break;
             default:
             case PersonInfo.GENDER_UNKNOWN:
                 genderName = resources.getString(R.string.unknown);
                 break;
             }
             genderText.setText(genderName);
             
             favmealText.setText(personInfo.getFavmeal());
             return listItem;
        }
        
        private LayoutInflater getLayoutInflator() {
            if (layoutInflater == null) {
                layoutInflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            return layoutInflater;
        }
    }
}
