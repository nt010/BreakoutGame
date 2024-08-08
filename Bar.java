float barX = 500.0f;
float barY = 600.0f;
float barVX = 5.0f;
float barVY = 5.0f;
float barWidth = 800.0f;
float barHeight = 50.0f;


void draw_bar(){
  accelerationX = width / 2 - barWidth / 2;
  accelerationY = height - 40;
  barVX = 5.0f;
  barWidth = 100;
  barHeight = 10;
}

class Bar {
  float x, y, w, h;
  Bar(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  void move(float accX) { 
    x = map(accX, -256, 256, 0, width); // Adjust the map range based on sensor output
    x = constrain(x, 0, width - w);
  }
  // Barの動き
  void display() {
    rect(x, y, w, h);
  }
}
