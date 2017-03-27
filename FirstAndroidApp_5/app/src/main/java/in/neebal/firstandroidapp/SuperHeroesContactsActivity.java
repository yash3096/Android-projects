package in.neebal.firstandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.neebal.firstandroidapp.core.SuperHero;

public class SuperHeroesContactsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<SuperHero> superHeroes;
    private ListView superHeroesList;
    private SharedPreferences sharedPreferences;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_heroes_contacts);
        superHeroes=new ArrayList<SuperHero>();
        superHeroes.add(new SuperHero("Spiderman", "9834567823", "makdijaal@gmail.com"));
        superHeroes.add(new SuperHero("Iron Man", "9999988823", "feadman@gmail.com"));
        superHeroes.add(new SuperHero("Superman","9920010252","super.man@gmail.com"));
        superHeroes.add(new SuperHero("Spiderman", "9834567823", "makdijaal@gmail.com"));
        superHeroes.add(new SuperHero("Iron Man", "9999988823", "feadman@gmail.com"));
        superHeroes.add(new SuperHero("Superman","9920010252","super.man@gmail.com"));
        superHeroes.add(new SuperHero("Spiderman", "9834567823", "makdijaal@gmail.com"));
        superHeroes.add(new SuperHero("Iron Man", "9999988823", "feadman@gmail.com"));
        superHeroes.add(new SuperHero("Superman","9920010252","super.man@gmail.com"));

        superHeroesList=(ListView)findViewById(R.id.lv_super_heroes);
        SuperHeroesAdapter adapter=new SuperHeroesAdapter();
        superHeroesList.setAdapter(adapter);
        superHeroesList.setOnItemClickListener(this);
        sharedPreferences=getSharedPreferences("MyAppPrefs",MODE_PRIVATE);
        relativeLayout=(RelativeLayout)findViewById(R.id.rl_superHeroesContacts);
        int colorToApply = sharedPreferences.getInt("Color", Color.WHITE);
        relativeLayout.setBackgroundColor(colorToApply);

    }

    @Override
    protected void onStart() {
        super.onStart();
       // System.out.println("On Start of SuperHeroesContactsActivity");

         }

    @Override
    protected void onResume() {
        super.onResume();
       // System.out.println("On Resume of SuperHeroesContactsActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   System.out.println("On Pause of SuperHeroesContactsActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   System.out.println("On Stop of SuperHeroesContactsActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   System.out.println("On Destroy of SuperHeroesContactsActivity");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SuperHero superHero=superHeroes.get(position);
        String mobilenumber=superHero.getPhone();
        //tel is the scheme for dialers
        String telString="tel:"+mobilenumber;
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse(telString));
    }

    class SuperHeroHolder
    {
        TextView name,phone,email;
    }
    class SuperHeroesAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return superHeroes.size();
        }

        @Override
        public Object getItem(int position) {
            return superHeroes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SuperHeroHolder holder=null;
            if(convertView==null) {
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.list_item_super_heroes, null);
                holder=new SuperHeroHolder();
                holder.name=(TextView)convertView.findViewById(R.id.tv_super_hero_name);
                 holder.email=(TextView)convertView.findViewById(R.id.tv_super_hero_email);
                 holder.phone=(TextView)convertView.findViewById(R.id.tv_super_hero_mobile);
                convertView.setTag(holder);
            }
            else
            {
                holder=(SuperHeroHolder)convertView.getTag();
            }
            SuperHero hero=(SuperHero)getItem(position);

            holder.name.setText(hero.getName());
            holder.phone.setText(hero.getPhone());
            holder.email.setText(hero.getEmail());
            return convertView;
        }
    }
}
