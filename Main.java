import ddf.minim.*;
Minim minim;
import processing.serial.*;
Serial serial;
float accelerationX, accelerationY, accelerationZ;
float smoothedX, smoothedY;

AudioPlayer bgm, title, clear, end;
AudioSample bt, at, ent;

int mcnt, gseq = 0;
int reset_count = 5;
Ball[] balls;
Bar bar;


void setup() {
  size(500, 500);
  serial = new Serial(this, "COM3", 9600);
  
  balls = new Ball[2];
  balls[0] = new Ball(width / 2, height / 2, 3, -3, color(255));
  balls[1] = null;

  bar = new Bar(width / 2, height - 20, 200, 10);

  draw_blocks();
  
  minim = new Minim(this);
  bgm = minim.loadFile("../data/sound/play.mp3");
  title = minim.loadFile("../data/sound/title.mp3");
  clear = minim.loadFile("../data/sound/clear.mp3");
  end = minim.loadFile("../data/sound/end.mp3");
  bt = minim.loadSample("../data/sound/click.mp3");
  ent = minim.loadSample("../data/sound/ent.mp3"); 
  at = minim.loadSample("../data/sound/SE09.wav");
  
  title.loop();
}

void draw() {
  background(0);
  switch(gseq){
    case 0:
      game_title();
      break;
    case 1:
      game_play();
      break;
    case 2:
      game_clear();
      break;
    default:
      game_over();
      break;
  }

  smoothedX = lerp(smoothedX, accelerationX, 0.1);
  bar.move(smoothedX);
  bar.display();
  println("here");
}



// Arduinoからの受信
char[] lineBuffer = new char[512];
int currentIndex;
final int DATA_SIZE = 2;  

void serialEvent(Serial serial) {
  String s = serial.readStringUntil('\n');
  if (s != null) {
    s = s.trim();  // 前後の空白を削除
    String[] data = s.split(" ");
    if (data.length == 2) {
      try {
        accelerationX = Float.parseFloat(data[0]);
        accelerationY = Float.parseFloat(data[1]);
      } catch (NumberFormatException e) {
        println("NumberFormatException: " + e.getMessage());
      }
    } else {
      println("データの長さが不正です");
    }
  }
}
