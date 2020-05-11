package com.eminsaygi.flyhezarfen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;



public class UcHezarfen extends ApplicationAdapter {

	private SpriteBatch batch;  //Ara işlem birimi tanımladım
	private Texture arkaplan;   // arkaplan Texturesini tanımladım
	private Texture blurArkaplan;
	private Texture oyunBitti;
	private Texture ekranaBas;
	private Texture skorResim;
	private Texture girisYazi;
	private Texture hezarfen;   // hezarfen     "        	"
	private Texture dusman1;	// dusman1		"			"
	private Texture dusman2;	// dusman2		"			"
	private Texture dusman3;	// dusman3		"			"
	private float hezarfenX=0;	// Kahramanımızın x koordinatını tanımladım.
	private float hezarfenY=0;	// 		"		  y 	"			"
	private int oyunistatistik=0; // Oyunun başlama ve bitiş değişkeninini tanımladım.
	private float hiz =0;		// Hız değerini tanımladım.
	private Random random;     //Kuşlar için random sınıfı tanımladım.
	private int seviye =0;
	private int randomSayi=0;

	private int skor=0;        //Skor tanımladım.
	private int dusmanSkor=0;  //Oyunda 3 tane dusman olduğu için,skoru ona göre implement ettim.
	private BitmapFont font;   //Skor fontum için değişken tanımladım.
	private BitmapFont font7;

	private Circle hezarfenCircle; //Kontrol için daire tanımladım.

	private ShapeRenderer renderer; //Kontrol için render sınıfını kullandım.

	private int dusmansayisi =4; //Dusman sayisini tanımladım.

	private float [] dusmanX=new float[dusmansayisi]; //


	private float [] dusmanOfSet = new float[dusmansayisi]; // 1.Dusmanın Y ekseninde hareketi
	private float [] dusmanOfSet2= new float[dusmansayisi];	//	2.	"		"		""	"
	private float [] dusmanOfSet3= new float[dusmansayisi];// 3.	"		"		"		"


	private float mesafe =0;
	private float dusmanHiz=6.5f;// Dusmanın gelme hızı

	private Circle[] dusmanCircles1; //Dusman kontrol çemberi
	private Circle[] dusmanCircles2; //	"		"		"
	private Circle[] dusmanCircles3; // "		"		"

	private Music music;
	private Sound ucmak;
	private Sound carpma;
	private Sound puanSes;

	public void create () {

		batch= new SpriteBatch(); //Çizdirmek için gerekli değişken

      arkaplan = new Texture("arkaplan.jpg");//Arkaplanımızı tanıttık
	  blurArkaplan = new Texture("BlurArkaplan.jpg");
	  oyunBitti = new Texture("oyunBitti.png");
	  ekranaBas = new Texture("ekranaBas.png");
	  girisYazi = new Texture("girisYazi.png");
	  skorResim= new Texture("skor.png");


      hezarfen = new Texture("hezarfen.png");//Kahramaınımızı 	"
      dusman1 = new Texture("enemy.png");//Dusman 1 			"
      dusman2 = new Texture("enemy.png");//Dusman 2				"
      dusman3 = new Texture("enemy.png");//Dusman 3 			"


		music = Gdx.audio.newMusic(Gdx.files.internal("arkaplanMuzik.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();

		ucmak = Gdx.audio.newSound(Gdx.files.internal("ucma.ogg"));
		ucmak.setVolume(0,0.1f);



		carpma = Gdx.audio.newSound(Gdx.files.internal("oyunBitti.ogg"));
		carpma.setVolume(0,0.1f);


		puanSes = Gdx.audio.newSound(Gdx.files.internal("puanSes.mp3"));
		puanSes.setVolume(0,0.1f);




      mesafe = (float)Gdx.graphics.getWidth()/2;
      random = new Random();



      hezarfenX = (float)Gdx.graphics.getWidth()/6;
      hezarfenY = (float)Gdx.graphics.getHeight()/2;

      renderer = new ShapeRenderer();

      hezarfenCircle = new Circle();
		//Circle engelCember = new Circle();
      dusmanCircles1 = new Circle[dusmansayisi];//1. Düşman, Carpma ve kontrol değişkeni
      dusmanCircles2 = new Circle[dusmansayisi];//2. Düşman, Carpma ve kontrol değişkeni
      dusmanCircles3 = new Circle[dusmansayisi];//3. Düşman, Carpma ve kontrol değişkeni

		font = new BitmapFont(Gdx.files.internal("arial.fnt"));//Skor Yazı fontunu tanımladım
		font.getData().setScale(1.5f);//Büyüklüğünü tanımladım

		font7 = new BitmapFont(Gdx.files.internal("arial.fnt"));
		font7.setColor(com.badlogic.gdx.graphics.Color.RED);
		font7.getData().setScale(4);

		for (int i =0;i<dusmansayisi;i++){

         randomSayi = random.nextInt(450);

					if (randomSayi<Gdx.graphics.getHeight()/4 && randomSayi>Gdx.graphics.getHeight()/100){

						dusmanOfSet[i]= (randomSayi);//1.Dusmanın random yerini belirleyen kısım
						dusmanOfSet2[i]= (randomSayi+150);//2. "			"		"		"
						dusmanOfSet3[i]= (randomSayi+650);//3.	"		"		"		"		"	"		"		"

						}

					else {

						dusmanOfSet[i]= (Gdx.graphics.getHeight()-200);//1.Dusmanın random yerini belirleyen kısım
						dusmanOfSet2[i]= (Gdx.graphics.getHeight()-350);//2. "			"		"		"
						dusmanOfSet3[i]= (Gdx.graphics.getHeight()-900);//3.	"		"		"		"		"	"		"		"
					}

		   dusmanX[i]=Gdx.graphics.getWidth()-(float)dusman1.getWidth()/2+i*mesafe; //Oyun başında karmaşayı engelliyor.


			dusmanCircles1[i]=new Circle();
		   dusmanCircles2[i]=new Circle();
		   dusmanCircles3[i]=new Circle();

		}
	}
	@Override
	public void render () {

		batch.begin();
		batch.draw(blurArkaplan,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(girisYazi,(float)Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()-250);
		batch.draw(ekranaBas,(float)Gdx.graphics.getWidth()/10,(float)Gdx.graphics.getHeight()/9,Gdx.graphics.getWidth()/1.5f,(float)Gdx.graphics.getHeight()/3);
		if (oyunistatistik==1)

		{   //Oyun başladı

			batch.draw(arkaplan,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			music.play();
			font.draw(batch,String.valueOf(skor),(float)Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-100); //Skoru çizdirme kısmı

           if (dusmanX[dusmanSkor]< (float)Gdx.graphics.getWidth()/2- (float)dusman1.getHeight() / 2-200){

				skor++;
				puanSes.play();
				seviye++;
				if (dusmanSkor<3){

					dusmanSkor++;

				}else {
					dusmanSkor=0;
				}
		   }

           if (seviye==3){

           	dusmanHiz+=3;
           	seviye=0;

		   }


			if (Gdx.input.justTouched()){
				hiz=-13;  // Ekrana tıklayınca kahramanın zıplama mesafesi



				ucmak.play();

			}

			for (int i=0;i<dusmansayisi;i++){

               if (dusmanX[i]<-(float)Gdx.graphics.getWidth()/15){

                dusmanX[i]=dusmanX[i]+dusmansayisi*mesafe;

				   randomSayi = random.nextInt(450);

				   if (randomSayi<Gdx.graphics.getHeight()/4 && randomSayi>Gdx.graphics.getHeight()/100){

					   dusmanOfSet[i]= (randomSayi);//1.Dusmanın random yerini belirleyen kısım
					   dusmanOfSet2[i]= (randomSayi+150);//2. "			"		"		"
					   dusmanOfSet3[i]= (randomSayi+650);//3.	"		"		"		"		"	"		"		"

				   }else {

					   dusmanOfSet[i]= (Gdx.graphics.getHeight()-200);//1.Dusmanın random yerini belirleyen kısım
					   dusmanOfSet2[i]= (Gdx.graphics.getHeight()-350);//2. "			"		"		"
					   dusmanOfSet3[i]= (Gdx.graphics.getHeight()-900);//3.	"		"		"		"		"	"		"		"
				   }

				   System.out.println("Kontrol4 : "+dusmanOfSet[i]);
				   System.out.println("Kontrol5 : "+dusmanOfSet2[i]);
				   System.out.println("Kontrol6 : "+dusmanOfSet3[i]);
			   }
               else {
				   dusmanX[i]=dusmanX[i]-dusmanHiz;//Dusman hızı
			   }


				batch.draw(dusman1,dusmanX[i],dusmanOfSet[i],(float)Gdx.graphics.getWidth()/10,(float)Gdx.graphics.getHeight()/10);//1.Dusmanın ekrana gelme yeri
				batch.draw(dusman2,dusmanX[i],dusmanOfSet2[i],(float)Gdx.graphics.getWidth()/10,(float)Gdx.graphics.getHeight()/10);//2. "			"	"		"
				batch.draw(dusman3,dusmanX[i],dusmanOfSet3[i],(float)Gdx.graphics.getWidth()/10,(float)Gdx.graphics.getHeight()/10);//3.  "			"		"	"

				dusmanCircles1[i]= new Circle(dusmanX[i]+70,dusmanOfSet[i]+50,(float)Gdx.graphics.getWidth()/30);
				dusmanCircles2[i]= new Circle(dusmanX[i]+70,dusmanOfSet2[i]+50,(float)Gdx.graphics.getWidth()/30);
				dusmanCircles3[i]= new Circle(dusmanX[i]+70,dusmanOfSet3[i]+50,(float)Gdx.graphics.getWidth()/30);


             }

         if (hezarfenY>0 && hezarfenY<Gdx.graphics.getHeight()-100 ){ // Oyunun üst sınırı ve alt sınırı belirlenmesi
			 // Yercekimi için değişken tanımladım.
			 float yercekimi = 0.7f;
			 hiz+= yercekimi; // Basınca zıplaması
			 hezarfenY -=hiz; // Basınca zıplaması

         }

         else

         	{

			carpma.play();



         	oyunistatistik=2;

		 }

		}


		else if (oyunistatistik==0)
		{

			if (Gdx.input.justTouched()){
				oyunistatistik=1;



			}

		}


		else if (oyunistatistik==2)


		{

			hezarfenY=-500;


			music.stop();


			batch.draw(blurArkaplan,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(oyunBitti,(float)Gdx.graphics.getWidth()/5-50,(float)Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2.5f);
			batch.draw(ekranaBas,(float)Gdx.graphics.getWidth()/10,(float)Gdx.graphics.getHeight()/9,Gdx.graphics.getWidth()/1.5f,(float)Gdx.graphics.getHeight()/3);
			batch.draw(skorResim,(float)Gdx.graphics.getWidth()/2+450,(float)Gdx.graphics.getHeight()-330,(float)Gdx.graphics.getWidth()/6,(float)Gdx.graphics.getHeight()/6);

			font7.draw(batch,""+skor,Gdx.graphics.getWidth()-400,Gdx.graphics.getHeight()-400);



			if (Gdx.input.justTouched()){

				oyunistatistik=1;
				hezarfenY = (float)Gdx.graphics.getWidth()/4;


				for (int i =0;i<dusmansayisi;i++){

					randomSayi = random.nextInt(450);

					if (randomSayi<Gdx.graphics.getHeight()/4 && randomSayi>Gdx.graphics.getHeight()/100){

						dusmanOfSet[i]= (randomSayi);//1.Dusmanın random yerini belirleyen kısım
						dusmanOfSet2[i]= (randomSayi+150);//2. "			"		"		"
						dusmanOfSet3[i]= (randomSayi+650);//3.	"		"		"		"		"	"		"		"

					}else {

						dusmanOfSet[i]= (Gdx.graphics.getHeight()-200);//1.Dusmanın random yerini belirleyen kısım
						dusmanOfSet2[i]= (Gdx.graphics.getHeight()-350);//2. "			"		"		"
						dusmanOfSet3[i]= (Gdx.graphics.getHeight()-900);//3.	"		"		"		"		"	"		"		"
					}



					dusmanX[i]=Gdx.graphics.getWidth()-(float)dusman1.getWidth()/2+i*mesafe;

					dusmanCircles1[i]=new Circle();
					dusmanCircles2[i]=new Circle();
					dusmanCircles3[i]=new Circle();

				}
               	hiz =0;
				dusmanSkor=0;
				skor=0;
				dusmanHiz=5;
				seviye=0;


			}


		}



     batch.draw(hezarfen,hezarfenX,hezarfenY,(float)Gdx.graphics.getWidth()/11,(float)Gdx.graphics.getHeight()/11); //Kahramanımızın boyutu



     batch.end();

     hezarfenCircle.set(hezarfenX+(float)Gdx.graphics.getWidth()/22,hezarfenY+(float)Gdx.graphics.getHeight()/22,(float)Gdx.graphics.getWidth()/35);



     renderer.begin(ShapeRenderer.ShapeType.Filled);

     //renderer.circle(hezarfenCircle.x,hezarfenCircle.y,hezarfenCircle.radius);



     for (int i =0;i<dusmansayisi;i++){

		//renderer.circle(dusmanX[i]+70,dusmanOfSet[i]+50,Gdx.graphics.getWidth()/30); // Dusman1 Kontrol carpısma
		//renderer.circle(dusmanX[i]+70,dusmanOfSet2[i]+50,Gdx.graphics.getWidth()/30);// Dusman2 	"			"
		//renderer.circle(dusmanX[i]+70,dusmanOfSet3[i]+50,Gdx.graphics.getWidth()/30);// Dusman3 	"			"



		 if (Intersector.overlaps(hezarfenCircle,dusmanCircles1[i])||Intersector.overlaps(hezarfenCircle,dusmanCircles2[i])||Intersector.overlaps(hezarfenCircle,dusmanCircles3[i])){

		 	if (oyunistatistik==1){

		 		carpma.play();

				oyunistatistik=2;
			}
        }


	 }
		renderer.end();
	}



	@Override
	public void dispose () {



	}
}
