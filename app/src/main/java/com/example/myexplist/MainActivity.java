package com.example.myexplist;

import com.example.myexplist.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myexplist.anime_resources.AnimeFragment;
import com.example.myexplist.games_resources.GamesFragment;
import com.example.myexplist.manga_resources.MangaFragment;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    private ActivityMainBinding binding;
    public String filter = "All";
    public Menu appBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appBarMenu = binding.toolbar.getMenu();

        TextView toolbarTextView = findViewById(R.id.toolbarTextView);
        toolbarTextView.setText("Anime");
        replaceFragment(new AnimeFragment());

        appBarMenu.findItem(R.id.menuMangaFilterRead).setVisible(false);
        appBarMenu.findItem(R.id.menuGamesFilterFinished).setVisible(false);
        appBarMenu.findItem(R.id.menuGamesFilterTried).setVisible(false);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.action_search:
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menuAnimeFilterAll:
                case R.id.menuAnimeFilterViewed:
                case R.id.menuAnimeFilterInProcess:
                case R.id.menuAnimeFilterDropped:
                case R.id.menuMangaFilterRead:
                case R.id.menuGamesFilterFinished:
                case R.id.menuGamesFilterTried:
                    filter = item.toString();

                    Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                    if (currentFragment instanceof AnimeFragment) {
                        replaceFragment(new AnimeFragment());
                    } else if (currentFragment instanceof MangaFragment) {
                        replaceFragment(new MangaFragment());
                    } else if (currentFragment instanceof GamesFragment)
                        replaceFragment(new GamesFragment());

                    break;
            }
            return true;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.anime_activity:

                    appBarMenu.findItem(R.id.menuAnimeFilterViewed).setVisible(true);
                    appBarMenu.findItem(R.id.menuMangaFilterRead).setVisible(false);
                    appBarMenu.findItem(R.id.menuGamesFilterFinished).setVisible(false);
                    appBarMenu.findItem(R.id.menuGamesFilterTried).setVisible(false);

                    filter = "All";

                    toolbarTextView.setText("Anime");
                    replaceFragment(new AnimeFragment());
                    break;
                case R.id.manga_activity:

                    appBarMenu.findItem(R.id.menuAnimeFilterViewed).setVisible(false);
                    appBarMenu.findItem(R.id.menuMangaFilterRead).setVisible(true);
                    appBarMenu.findItem(R.id.menuGamesFilterFinished).setVisible(false);
                    appBarMenu.findItem(R.id.menuGamesFilterTried).setVisible(false);

                    filter = "All";

                    toolbarTextView.setText("Manga");
                    replaceFragment(new MangaFragment());
                    break;
                case R.id.games_activity:

                    appBarMenu.findItem(R.id.menuAnimeFilterViewed).setVisible(false);
                    appBarMenu.findItem(R.id.menuMangaFilterRead).setVisible(false);
                    appBarMenu.findItem(R.id.menuGamesFilterFinished).setVisible(true);
                    appBarMenu.findItem(R.id.menuGamesFilterTried).setVisible(true);

                    filter = "All";

                    toolbarTextView.setText("Games");
                    replaceFragment(new GamesFragment());
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public String getFilter() {
        return filter;
    }
}