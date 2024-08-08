import processing.serial.*; // シリアル通信ライブラリをインポート


class Ball {
  float x, y, r;
  float speedX, speedY;
  int c;
  boolean extraBall;

  Ball(float tempX, float tempY, float tempSpeedX, float tempSpeedY, int tempC) {
    x = tempX;
    y = tempY;
    r = 20;
    speedX = tempSpeedX;
    speedY = tempSpeedY;
    c = tempC;
    extraBall = (c == color(255, 255, 0));
  }

  void move_ball(Bar bar) {
    fill(c);
    circle(x, y, r);
    x += speedX;
    y += speedY;

    if (x + r / 2 > width || x - r / 2 < 0) speedX *= -1;
    if (y - r / 2 < 0) speedY *= -1;

    if (y + r / 2 > bar.y && y - r / 2 < bar.y + bar.h &&
        x + r / 2 > bar.x && x - r / 2 < bar.x + bar.w) {
      speedY *= -1;
      y = bar.y - r / 2;
    }
  }

  void check_collision() {
    for (int j = 0; j < 5; j++) {
      for (int i = 0; i < 5; i++) {
        if (is_alive[i][j]) {
          if ((x + r / 2 > i * block_w && x - r / 2 < (i + 1) * block_w) &&
              (j * block_h < y - r / 2 && y - r / 2 < (j + 1) * block_h)) {
            speedY *= -1;
            y = r / 2 + (j + 1) * block_h + 1;
            at.trigger();
            is_alive[i][j] = false;
            score_count += 1;
            
            // ファンを回す信号を送信
            serial.write('F');
            println("test");

            if (special_block[i][j] == 4) {
              balls[1] = new Ball(width / 2, height / 2, 3, -3, color(255, 255, 0));
            } else {
              activate_special_effect(special_block[i][j]);
            }

            if (score_count == 25) {
              gseq = 2;
              mcnt = 0;
            }
          }
        }
      }
    }
  }
}

void ball_reset() {
  for (int i = 0; i < balls.length; i++) {
    if (balls[i] != null && balls[i].y > height) {
      if (bar.javaalls[i].extraBall) {
        balls[i] = null;
        bar.w *= 1.50;
      } else {
        balls[i] = new Ball(width / 2, height / 2, 3, -3, color(255));
        reset_count -= 1;
        if (reset_count == 0) {
          game_over();
        }
      }
    }
  }
}

void activate_special_effect(int effectType) {
  switch(effectType) {
    case 0:
      for (Ball ball : balls) {
        if (ball != null) {
          ball.speedX *= 1.5;
          ball.speedY *= 1.5;
        }
      }
      break;
    case 1:
      for (Ball ball : balls) {
        if (ball != null) {
          ball.speedX *= 0.8;
          ball.speedY *= 0.8;
        }
      }
      break;
    case 2:
      bar.w *= 0.80;
      break;
    case 3:
      bar.w *= 0.75;
      break;
    case 4:
      break;
  }
}
