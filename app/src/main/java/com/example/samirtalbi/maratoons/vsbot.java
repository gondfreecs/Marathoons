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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Samir Talbi on 02/01/2018.
 */

public class vsbot extends Activity implements View.OnClickListener {

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

    TextView timerText;

    TextView nombre;

    static int distance = 10000;

    static int nb = 4;

    int value1 = 1;
    int value2 = 2;
    int value3 = 3;
    int value4 = 4;

    int deEnlever =0;

    int currentPlayer =0;

    static int a=nb;

    boolean etat1 = false;
    boolean etat2 = false;
    boolean etat3 = false;
    boolean etat4 = false;

    boolean lancer = true;

    boolean chronoT;

    String temps="";
    String date="";

    String level;
    String pseudo;

    ProgressBar chemin;

    MediaPlayer victoire;
    MediaPlayer defaite;

    Partie p ;

    Chrono chrono = new Chrono();
    ArrayList<Joueur> listjoueur;
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

        alpha.setVisibility(View.VISIBLE);
        beta.setVisibility(View.GONE);

        victoire = MediaPlayer.create(this,R.raw.win);
        defaite = MediaPlayer.create(this,R.raw.loose);

        rollButton.setOnClickListener(this);
        valider.setOnClickListener(this);
        passer.setOnClickListener(this);

        chemin = (ProgressBar)findViewById(R.id.chemin);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);



        level = intent.getStringExtra("level");
        pseudo = intent.getStringExtra("pseudo");
        chronoT = intent.getBooleanExtra("chrono",false);
        distance = intent.getIntExtra("distance",42195);
        p = generateurPartie(pseudo);

        System.out.println(chronoT);

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
                cancel();

                //todo retirer distance
                p.getListe().get(currentPlayer).setDistance(p.getListe().get(currentPlayer).getDistance()+Integer.parseInt(sb.toString()));

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
                    chrono.stop();
                    temps=chrono.getDureeTxt();
                    popup(null);//Si tous le monde a perdu
                }else {
                    botplay();
                }
            }
        }//valider

        if(view == passer){

            cancel();


            botplay();

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

        a=nb-deEnlever;




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
            defaite.start();

            customPopup.setGagnant(" \uD83D\uDE31 Vous avez perdu \uD83D\uDE31");
            customPopup.setStats("en "+temps);
            customPopup.setCancelable(false);
            customPopup.getAgain().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    generateurPartie(pseudo);
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
                    if(p.getListe().get(currentPlayer).getId()==58){
                        defaite.start();
                        customPopup.setGagnant("L'Ordinateur à encore GAGNER Retente ta chance \uD83D\uDE0B");
                        customPopup.setStats("en "+ j.getScore() +" coups et en "+temps);
                        customPopup.setCancelable(false);
                    }else{
                        victoire.start();
                        customPopup.setGagnant("Félicitation "+ j.getPseudo() +" tu as gagné \uD83C\uDFC6 ");
                        customPopup.setStats("en "+ j.getScore() +" coups et en "+temps);
                        customPopup.setCancelable(false);
                        enregistrement(j,perdant);
                    }
                    customPopup.getAgain().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            generateurPartie(pseudo);
                            customPopup.dismiss();
                            finish();
                            startActivity(intent);
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
    public Partie generateurPartie(String a){
        listjoueur = new ArrayList<>();

        Joueur j1 = new Joueur(0,a,0);
        Joueur ia = new Joueur(58,"Ordinateur",0);

        listjoueur.add(j1);
        listjoueur.add(ia);

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

    public void botplay(){
        initialisation();
        int diff=0;
        if(level.equalsIgnoreCase("faible")){
            diff = intel_tab_facile(combinaisons(a),p.getListe().get(currentPlayer));
        }
        if(level.equalsIgnoreCase("fort")){
            System.out.println("NOMBRE DE DES: " +a);
            diff = intel_tab_fort(permute(genere(a)),p.getListe().get(currentPlayer));
        }
        //distance = p.getListe().get(0).getDistance();
        p.getListe().get(currentPlayer).setDistance(p.getListe().get(currentPlayer).getDistance()+diff);
        p.getListe().get(currentPlayer).setScore(p.getListe().get(currentPlayer).getScore()+1);
        System.out.println("\n \t\n\tDISSSTANNCEEEE : "+distance+" et distance reste a parcourrir "+(distance-p.getListe().get(currentPlayer).getDistance()));
        System.out.println("\n \t\n\tnb dé : "+a);
        System.out.println("\n \t\n\t"+p.getListe().get(currentPlayer).getPseudo()+" a parcourru : "+diff);
        Toast.makeText(getApplicationContext(),"Il reste plus que "+ (distance-p.getListe().get(currentPlayer).getDistance()) +"m à l'ordinateur. \uD83C\uDFC3",Toast.LENGTH_SHORT).show();
        if(p.getListe().get(currentPlayer).getDistance()==distance) {
            chrono.stop();
            temps=chrono.getDureeTxt();

            p.setGagnan(p.getListe().get(currentPlayer));//set le gagnant de la partie.
            //todo popup et quitter le jeu
            popup(p.getListe().get(currentPlayer));

        }
        if(p.getListe().get(currentPlayer).getDistance()>distance){
            System.out.println("ELIMINATION: "+p.getListe().get(currentPlayer).getPseudo());
            Toast.makeText(getApplicationContext(),p.getListe().get(currentPlayer).getPseudo()+" est éliminer du Marathon !",Toast.LENGTH_SHORT).show();
            p.getListe().remove(currentPlayer);

            for (Joueur s: p.getListe())
            {

                System.out.println(s.getPseudo());

            }
            currentPlayer--;
        }
        initialisation();
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

    public void enregistrement(Joueur j,Boolean perdu){
        if(distance==42195) {
            DBManager bd = new DBManager(this.getBaseContext());

            bd.open();
            Joueur xy = new Joueur(0, pseudo, j.getScore());
            bd.createJoueur(xy);
            bd.close();
        }
    }

/*DEBUT IA*/

    // Retourne un tableau contenant 'a' valeur aléatoire compris entre 1 à 6
    public static int[] genere(int a) {
        int valeur_dés_lancer[] = new int[a];
        System.out.println("VOICI VOS DES:");
        for (int i = 0; i < a; i++) {
            int randomNum = 1 + (int) (Math.random() * 6);
            valeur_dés_lancer[i] = randomNum;
            System.out.println(valeur_dés_lancer[i]);
        }
        return valeur_dés_lancer;
    }

    // Calcule le factorielle de a
    public static int factorielle(int a) {
        int res = 1;
        for (int i = 1; i <= a; i++) {
            res = res * i;
        }
        return res;
    }

    // Concatene toutes les valeurs du tableau ---> 1,2,3,4 ===> 1234
    public static int concatene_nombre(List<Integer> genere) {

        String valeur = "";
        for (int i = 0; i < genere.size(); i++) {
            valeur = valeur + genere.get(i);
        }

        int res = Integer.parseInt(valeur);

        return res;
    }

    public static int concatene_nombre2(int genere[]) {

        String valeur = "";
        for (int i = 0; i < genere.length; i++) {
            valeur = valeur + genere[i];
        }

        int res = Integer.parseInt(valeur);

        return res;
    }

    // Affiche un tableau proprement.
    public static void affichetab(int tab[]) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + ((i == tab.length - 1) ? "" : ","));
        }
    }

    // Permute les valeurs d'un tableau
    public static int[] permut(int[] tab_tmp) {

        int[] tab_aux = new int[tab_tmp.length];

        for (int i = 0; i < tab_aux.length; i++) {
            tab_aux[i] = tab_tmp[i];
        }

        for (int i = 0; i < tab_tmp.length; i++) {
            if (i == tab_tmp.length - 1) {
                tab_tmp[i] = tab_aux[0];
            } else {
                tab_tmp[i] = tab_aux[i + 1];
            }

        }
        return tab_tmp;

    }

    // rempli un tableau de toute les combinaison possible avec a dés
    public static int[] combinaisons(int a) {

        int[] tab_res = new int[factorielle(a) + a];
        int[] tab_tmp = new int[a];// tab tempo
        int[] tab = new int[a];
        tab = genere(a); // REMPLI par NB alea

        for (int i = 0; i < tab.length; i++) {
            tab_tmp[i] = tab[i];
        }
        System.out.print("Combinaison obtenue : ");
        affichetab(tab_tmp);

        tab_res[0] = concatene_nombre2(tab_tmp);
        int j = 1;
        while (j != factorielle(a)) {

            permut(tab_tmp);
            tab_res[j] = concatene_nombre2(tab_tmp);
            j++;

        }
        return tab_res;
    }

    public List<List<Integer>> permute(int[] aleatoir) {

        List<List<Integer>> permutations = new ArrayList<List<Integer>>();
        permutations.add(new ArrayList<Integer>());

        for ( int i = 0; i < aleatoir.length; i++ ) {
            List<List<Integer>> current = new ArrayList<List<Integer>>();
            for ( List<Integer> permutation : permutations ) {
                for ( int j = 0, n = permutation.size() + 1; j < n; j++ ) {
                    List<Integer> temp = new ArrayList<Integer>(permutation);
                    temp.add(j, aleatoir[i]);
                    current.add(temp);
                }
            }
            permutations = new ArrayList<List<Integer>>(current);
        }

        return permutations;
    }

    public static int intel_tab_fort(List<List<Integer>> tab,Joueur r) {


        int test;
        int reste = distance-r.getDistance();
        int res = 0;

        for (int i = 0; i < tab.size(); i++) {
            test = concatene_nombre(tab.get(i));
            System.out.println(reste);
            if(reste-test>=0){
                if (!(reste<1111 && reste >=1000)){
                    if (!(reste<111 && reste >=100)){
                        if(reste!=10){
                            if(res<test){
                                res = test;
                                System.out.println("SA PASSE :" + test);
                            }
                        }
                    }
                }
            } else {
            }

        }
        //a partir de 3000 je dois passé en dessous de 1000.

        if (res > getDistance()) {
            System.out.println("\nLa plus grande valeur possible :" + res);
            System.out.println("Valeur optimal : 0");
            return 0;
        } else {
            System.out.println("\nDistance optimal retourné : "+res);
            return res;
        }

    }

    // Coeur de L'IA Faible total random
    public static int intel_tab_facile(int[] tab,Joueur p) {

        List<Integer> val_inf_egal_dist = new ArrayList<Integer>();

        for (int i = 0; i < tab.length; i++) {
            if (tab[i] <= getDistance()-p.getDistance() &&!(getDistance()-p.getDistance()-tab[i]>=1000 && getDistance()-p.getDistance()-tab[i]<=1110) || (getDistance()-p.getDistance()-tab[i]>=100 && getDistance()-p.getDistance()-tab[i]<=110) || getDistance()-p.getDistance()-tab[i]==10) {
                val_inf_egal_dist.add(tab[i]);
            }
        }

        for (int i = 0; i < val_inf_egal_dist.size(); i++) {
            if (val_inf_egal_dist.get(i) == 0) {
                val_inf_egal_dist.remove(i);
            }
        }

        for(int i = 0;i<val_inf_egal_dist.size();i++) {
            System.out.println(val_inf_egal_dist.get(i));
        }

        int randomNum = 0 + (int) (Math.random() * val_inf_egal_dist.size() - 1);
        if(val_inf_egal_dist.size()==0) {
            return 0;
        }
        else {
            return val_inf_egal_dist.get(randomNum);
        }
    }

    // retourne la distance restante
    public static int getDistance() {
        return distance;
    }

    /*FIN IA*/

    public void cancel(){
        if(chronoT){
        timerText.setText("");
        timer.cancel();
        }
    }

}




