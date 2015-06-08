package hamburg.haw.polyshift.Menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hamburg.haw.polyshift.Adapter.LoginAdapter;
import hamburg.haw.polyshift.R;
import hamburg.haw.polyshift.Tools.AlertDialogs;
import hamburg.haw.polyshift.Tools.PasswordHash;

/**
 * Created by Andi on 12.03.2015.
 */
public class MainMenuActivity extends Activity {

    Button newGameButton;
    Button myGamesButton;
    Button scoresButton;
    Button logoutButton;
    Button quitGameButton;
    private static boolean crashed = false;
    private static Context context;
    private LoginAdapter loginAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        loginAdapter = new LoginAdapter(context,MainMenuActivity.this);
        loginAdapter.handleSessionExpiration();


        if(crashed) {
            Toast.makeText(this, "Bei der Übertragung ist ein Fehler aufgetreten.", Toast.LENGTH_SHORT);
        }

        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_NoActionBar);
        setContentView(R.layout.activity_main_menu);

        newGameButton = (Button)findViewById(R.id.new_game_button);
        myGamesButton = (Button)findViewById(R.id.my_games_button);
        scoresButton = (Button)findViewById(R.id.scores_button);
        logoutButton = (Button)findViewById(R.id.logout_button);
        quitGameButton = (Button)findViewById(R.id.quit_game_button);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChooseOpponentActivity.class);
                startActivity(intent);
                MainMenuActivity.this.finish();
            }
        });

        myGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyGamesActivity.class);
                startActivity(intent);
                MainMenuActivity.this.finish();
            }
        });

        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScoresActivity.class);
                startActivity(intent);
                MainMenuActivity.this.finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleSharedPreferences.setUserCredentials(context,"","");
                Intent intent = new Intent(v.getContext(), WelcomeActivity.class);
                startActivity(intent);
                MainMenuActivity.this.finish();
                WelcomeActivity.userLogout();
            }
        });

        quitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
    public static void setCrashed(){
        crashed = true;
    }
}