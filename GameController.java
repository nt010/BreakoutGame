void game_title(){
  fill(255);
  textSize(32);
  textAlign(CENTER);
  text("Breakout Game", width/2, height/2 - 50);
  mcnt++;
  if(mcnt % 60 < 40){
    textSize(16);
    text("Click to start!", width/2, height/2);
  }
}

void game_play(){
  fill(255);
  text("Score:" + score_count, width/2, height/2 - 50);
  text("Life:" + reset_count, width/2, height/2);
  
  
  // ブロックの動き
  arrange_blocks();
  
  // ボールの動き
  for (int i = 0; i < balls.length; i++) {
    if (balls[i] != null) {
      balls[i].move_ball(bar);
      balls[i].check_collision();
    }
  }
  ball_reset();
}

void game_clear(){
  fill(255,255,0);
  textSize(32);
  textAlign(CENTER);   
  text("Game Clear!", width/2, height/2 - 50);
  
  mcnt++;
  if(mcnt % 60 < 40){
    textSize(16);
    text("One more game?", width/2, height/2);
  }

  handle_music(); // 音楽を再生する関数を呼び出す
  game_reset();  
  
  if (keyPressed && key == ' ') {
    gseq = 1;
    reset_count = 5;
  }
}

void game_over(){
  reset_count = 1;
  gseq = 3;
  fill(255,0,0);
  textSize(32);
  textAlign(CENTER);
  text("Game Over!", width/2, height/2 - 50);
  
  mcnt++;
  if(mcnt % 60 < 40){
    textSize(16);
    text("Space to restart!", width/2, height/2);
    text("Enter to reset!", 250, 280);
  }

  handle_music(); // 音楽を再生する関数を呼び出す
  
  if (keyPressed && key == ' ') {
    gseq = 1;
    handle_music();
  }else if(keyCode == ENTER){
    gseq = 1;
    handle_music();
    game_reset();
  }
} 


void game_reset() {
  // ゲームの状態をリセット
  score_count = 0;
  reset_count = 5;
  balls[0] = new Ball(width/2, height/2, 3, -3, color(255)); // 最初のボール
  balls[1] = null; // 追加されるボールを初期化
  draw_blocks(); // ブロックを再描画
  draw_bar(); // バーを再描画
  combo_count = 0;
  combo_active = false;
  combo_timer = 0;
}



void keyPressed() {
  if (key == ' ') {
    bt.trigger();
    if (gseq == 0) {
      gseq = 1;
      mcnt = 0;
    } else if (gseq == 2 || gseq == 3) {
      gseq = 1;
      if(gseq == 3){
        game_reset();
      }
    }
    handle_music();
  }else if(keyCode == ENTER){
    ent.trigger();
  }
}


void handle_music() {
  // すべての音楽を一旦停止
  bgm.pause();
  title.pause();
  clear.pause();
  end.pause();

  // 状態に応じて音楽を再生
  switch(gseq){
    case 0:
      title.rewind();
      title.loop();
      break;
    case 1:
      bgm.rewind();
      bgm.loop();
      break;
    case 2:
      clear.play();
      break;
    default:
      end.play();
  }
}
