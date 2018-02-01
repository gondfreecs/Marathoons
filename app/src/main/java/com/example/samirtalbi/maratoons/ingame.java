package com.example.samirtalbi.maratoons;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Samir Talbi on 02/01/2018.
 */

public class ingame extends Activity implements View.OnClickListener {

    MediaPlayer media;

    public static final Random RANDOM = new Random();

    Button rollButton;
    Button valider;
    Button passer;

    LinearLayout alpha;
    LinearLayout beta;


    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    TextView t1;
    TextView t2;

    TextView nombre;

    TextView timerText;

    ProgressBar chemin;

    boolean chronoT;

    int distance = 42195;

    int value1 = 1;
    int value2 = 2;
    int value3 = 3;
    int value4 = 4;

    int deEnlever =0;

    int currentPlayer =0;

    boolean etat1 = false;
    boolean etat2 = false;
    boolean etat3 = false;
    boolean etat4 = false;

    boolean lancer = true;

    String temps="";
    String date="";

    MediaPlayer victoire;
    MediaPlayer defaite;
    MediaPlayer debut;

    Partie p ;

    Chrono chrono = new Chrono();

    ArrayList<Integer> de = new ArrayList<Integer>();// les valeur des dé dans une arraylist
    ArrayList<String> value = new ArrayList<String>();// pour savoir quel dé

    Intent intent;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);
        intent = getIntent();

        media = MediaPlayer.create(this, R.raw.roll);

        rollButton = (Button) findViewById(R.id.rollButton);
        valider = (Button) findViewById(R.id.valider);
        passer = (Button) findViewById(R.id.passer);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        t1 = (TextView) findViewById(R.id.aQui);
        t2 = (TextView) findViewById(R.id.distancerest);

        timerText = (TextView) findViewById(R.id.timer);

        alpha = (LinearLayout) findViewById(R.id.alpha);
        beta = (LinearLayout) findViewById(R.id.beta);

        nombre = (TextView) findViewById(R.id.nombre);

        chemin = (ProgressBar)findViewById(R.id.chemin);

        alpha.setVisibility(View.VISIBLE);
        beta.setVisibility(View.GONE);

        rollButton.setOnClickListener(this);
        valider.setOnClickListener(this);
        passer.setOnClickListener(this);


        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        victoire = MediaPlayer.create(this,R.raw.win);
        defaite = MediaPlayer.create(this,R.raw.loose);
        debut = MediaPlayer.create(this,R.raw.start);

        debut.start();

        distance = 42195;

        ArrayList<String> test = getIntent().getStringArrayListExtra("liste");
        chronoT = intent.getBooleanExtra("chrono",false);
        distance = intent.getIntExtra("distance",42195);

        p= generateurPartie(test);

        t1.setText("Au tour de "+p.getListe().get(currentPlayer).getPseudo()+" de jouer !");
        t2.setText("Il vous reste "+(distance-p.getListe().get(currentPlayer).getDistance())+ " metres");


        retirerDe();

        changerImageGris(imageView1,value1);
        changerImageGris(imageView2,value2);
        changerImageGris(imageView3,value3);
        changerImageGris(imageView4,value4);

        chemin.setMax(distance);
        chemin.setScaleY(5f);
        chrono.start();

        Date d=new Date();
        date= d.toString();


        timer = new CountDownTimer(5*1000,1000) {
            @Override
            public void onTick(long l) {
                timerText.setText("Il vous reste : "+String.valueOf(((l+1000)/1000))+" Secondes");
            }

            @Override
            public void onFinish() {
                onClick(passer);
                timerText.setText("");
            }


        };

    }


    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {


        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(400);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);

        //quand on clique sur ROLL
        if (view == rollButton) {
            media.start();
            if (lancer) {

                p.getListe().get(currentPlayer).setScore(p.getListe().get(currentPlayer).getScore()+1);

                imageView1.startAnimation(rotate);
                imageView2.startAnimation(rotate);
                imageView3.startAnimation(rotate);
                imageView4.startAnimation(rotate);

                value1 = randomDiceValue();
                value2 = randomDiceValue();
                value3 = randomDiceValue();
                value4 = randomDiceValue();

               changerImage(imageView1,value1);
               changerImage(imageView2,value2);
               changerImage(imageView3,value3);
               changerImage(imageView4,value4);

                lancer = false;

                alpha.setVisibility(View.GONE);
                beta.setVisibility(View.VISIBLE);

                if(chronoT)
                timer.start();

            }

        }//fin roll

        if (!lancer) {
            //Quand on clique sur les dé et que on a ROLL
            if (view == imageView1) {
                if (etat1 == false) {
                    changerImageGris(imageView1,value1);

                    de.add(value1);
                    value.add("dé1");

                    etat1 = true;
                } else {
                    changerImage(imageView1,value1);

                    de.remove(value.indexOf("dé1"));
                    value.remove(value.indexOf("dé1"));

                    etat1 = false;
                }
            }//img1


            if (view == imageView2) {
                if (etat2 == false) {
                    changerImageGris(imageView2,value2);

                    de.add(value2);
                    value.add("dé2");

                    etat2 = true;
                } else {
                    changerImage(imageView2,value2);
                    de.remove(value.indexOf("dé2"));
                    value.remove(value.indexOf("dé2"));
                    etat2 = false;
                }

            }//img2


            if (view == imageView3) {
                if (etat3 == false) {
                    changerImageGris(imageView3,value3);

                    de.add(value3);
                    value.add("dé3");

                    etat3 = true;

                } else {
                    changerImage(imageView3,value3);

                    de.remove(value.indexOf("dé3"));
                    value.remove(value.indexOf("dé3"));

                    etat3 = false;

                }

            }//img3


            if (view == imageView4) {
                if (etat4 == false) {
                    changerImageGris(imageView4,value4);

                    de.add(value4);
                    value.add("dé4");

                    etat4 = true;

                } else {
                    changerImage(imageView4,value4);

                    de.remove(value.indexOf("dé4"));
                    value.remove(value.indexOf("dé4"));

                    etat4 = false;

                }
            }//img4
        }

        StringBuilder sb = new StringBuilder();
        for (int i : de) {
            sb.append(i);
            chemin.setSecondaryProgress(p.getListe().get(currentPlayer).getDistance()+Integer.parseInt(sb.toString()));
        }

        sb.toString();


        // quand on clique sur validé
        if (view == valider) {
            if ((compteDeSelectionner()+deEnlever)==4) {
                lancer = true;
            }

            if (!lancer) {
                //todo message pour dire qu'il faut lancer les dé.
                Toast.makeText(getApplicationContext(),"⛔ Vous devez selectionner tous les dés ou passer votre tour ⛔",Toast.LENGTH_SHORT).show();
            } else {
                //todo retirer distance
                p.getListe().get(currentPlayer).setDistance(p.getListe().get(currentPlayer).getDistance()+Integer.parseInt(sb.toString()));
                cancel();
                //todo Si il GAGNE
                if(p.getListe().get(currentPlayer).getDistance()==distance) {
                    chrono.stop();
                    temps=chrono.getDureeTxt();

                    p.setGagnan(p.getListe().get(currentPlayer));//set le gagnant de la partie.
                    //todo popup et quitter le jeu
                    popup(p.getListe().get(currentPlayer));



                }

                //todo Si il PERD
                if(p.getListe().get(currentPlayer).getDistance()>distance){
                    System.out.println("ELIMINATION: "+p.getListe().get(currentPlayer).getPseudo());
                    Toast.makeText(getApplicationContext(),p.getListe().get(currentPlayer).getPseudo()+" est éliminer du Marathon ! \uD83D\uDE2D",Toast.LENGTH_SHORT).show();
                    p.getListe().remove(currentPlayer);

                    for (Joueur s: p.getListe())
                    {

                        System.out.println(s.getPseudo());

                    }

                    currentPlayer--;



                }

                if(p.getListe().isEmpty()){
                    chrono.stop();
                    temps=chrono.getDureeTxt();
                    popup(null);//Si tous le monde a perdu
                }else {
                    initialisation();//initialisation des dé passage au joueur suivant
                }
            }
        }//valider

        if(view == passer){
            cancel();
            initialisation();
        } //passer son tours

        if (!de.isEmpty()) {
            nombre.setText(sb.toString() + " metres");
        } else {
            nombre.setText("");
        }

    }

    //todo Passage de tours et incrémentation du score
    @TargetApi(Build.VERSION_CODES.N)
    public void initialisation(){

        currentPlayer++;
        if(currentPlayer>=p.getListe().size()){
            currentPlayer=0;
        }
        t1.setText("Au tour de "+p.getListe().get(currentPlayer).getPseudo()+" de jouer !");
        t2.setText("Il vous reste "+(distance-p.getListe().get(currentPlayer).getDistance())+" metres");

        value.clear();
        de.clear();
        nombre.setText("");

        etat1 = false;
        etat2 = false;
        etat3 = false;
        etat4 = false;

        deEnlever=0;
        imageView4.setVisibility(View.VISIBLE);
        imageView3.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);

        changerImageGris(imageView1,value1);
        changerImageGris(imageView2,value2);
        changerImageGris(imageView3,value3);
        changerImageGris(imageView4,value4);


         retirerDe();


        alpha.setVisibility(View.VISIBLE);
        beta.setVisibility(View.GONE);


        chemin.setSecondaryProgress(0);
        chemin.setProgress(p.getListe().get(currentPlayer).getDistance(),true);
        lancer = true;
    }

    //todo Generation du popup
    public void popup(Joueur j){

        boolean perdant = true;
        p.setDate(date);
        p.setTemps(temps);

        final CustomPopup customPopup = new CustomPopup(this);

        if(j==null){
            //todo pas de gagnant
            perdant=false;

            enregistrement(null,perdant);

            defaite.start();
            customPopup.setGagnant("\uD83D\uDE2D Vous avez tous perdu \uD83D\uDE2D");
            customPopup.setStats("en "+temps);
            customPopup.setCancelable(false);
            customPopup.getAgain().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> test = getIntent().getStringArrayListExtra("liste");
                    generateurPartie(test);
                    customPopup.dismiss();
                    finish();
                    startActivity(intent);

                }
            });
            customPopup.getHome().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customPopup.dismiss();
                    finish();
                }
            });
        }else{
            //todo un gagnant
            enregistrement(p.getListe().get(currentPlayer),perdant);
            victoire.start();
            customPopup.setGagnant("\uD83C\uDFC6 Félicitation à "+ j.getPseudo() +" qui gagne le marathon !");
            customPopup.setStats("en "+ j.getScore() +" coups et en "+temps);
            customPopup.setCancelable(false);
            customPopup.getAgain().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> test = getIntent().getStringArrayListExtra("liste");
                    generateurPartie(test);
                    onRestart();
                }
            });
            customPopup.getHome().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    customPopup.dismiss();
                }
            });
        }
        customPopup.build();
    }

    //todo Création de la partie
    public Partie generateurPartie(ArrayList<String> a){
        ArrayList<Joueur> listjoueur = new ArrayList<>();
        for (String pseudo : a) {
            Joueur j = new Joueur(0,pseudo,0);
            listjoueur.add(j);
        }
        Partie p = new Partie(0,"","",null,listjoueur);
        return p;
    }


    public int compteDeSelectionner(){
        int i=0;
        if (etat1==true) i++;
        if (etat2==true) i++;
        if (etat3==true) i++;
        if (etat4==true) i++;

        return i;


    }


    public void changerImage(ImageView i,int j){

            int res1 = getResources().getIdentifier("dice_" + j, "drawable", "com.example.samirtalbi.maratoons");
            i.setImageResource(res1);


    }

    public void changerImageGris(ImageView i,int j){

        int res1 = getResources().getIdentifier("dice_" + j+"gris", "drawable", "com.example.samirtalbi.maratoons");
        i.setImageResource(res1);


    }


    public void retirerDe(){
        if((distance-p.getListe().get(currentPlayer).getDistance())<1000){
            imageView4.setVisibility(View.GONE);
            deEnlever=1;

        }

        if((distance-p.getListe().get(currentPlayer).getDistance())<100){
            imageView3.setVisibility(View.GONE);
            deEnlever=2;

        }

        if((distance-p.getListe().get(currentPlayer).getDistance())<10){
            imageView2.setVisibility(View.GONE);
            deEnlever=3;


        }



    }

    public void enregistrement(Joueur gagnant,boolean perdu){
        if(distance==42195) {
            ArrayList<String> test = getIntent().getStringArrayListExtra("liste");
            DBManager bd = new DBManager(this.getBaseContext());
            String listejoueur = "Liste des joueur: ";
            for (String a : test) {
                listejoueur = listejoueur + " ," + a.toString();
            }
            bd.open();


            if (perdu == true) {
                bd.createPartie(p.getTemps(), p.getDate(), gagnant, listejoueur);
                bd.createJoueur(gagnant);
            } else {
                Joueur j = new Joueur(0, "", 0);

                bd.createPartie(p.getTemps(), p.getDate(), j, listejoueur);
            }

            bd.close();
        }
    }


    public void cancel(){
        if(chronoT){
        timerText.setText("");
        timer.cancel();
        }
    }




}




