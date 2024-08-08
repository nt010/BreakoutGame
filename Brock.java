float block_w = 100, block_h = 30;
boolean is_alive[][] = new boolean[5][5];
int special_block[][] = new int[5][5];
int score_count = 0;
int combo_count = 0;
boolean combo_active = false;
int combo_timer = 0;
AC
// 初期設定で呼ばれるブロック配置
void draw_blocks() {
  // 全ての初期値をtrueに
  for (int i = 0; i < 5; i++) {
    for (int j = 0; j < 5; j++) {
      is_alive[i][j] = true;
      special_block[i][j] = (int)random(0, 5); // ランダムに特殊効果を設定
    }
  }
}

// ゲームプレイ中に呼ばれるブロック配置
void arrange_blocks() {
  // ブロックの描画と当たり判定
  for (int j = 0; j < 5; j++) {
    for (int i = 0; i < 5; i++) {
      if (is_alive[i][j]) {
        if (special_block[i][j] == 4) {
          fill(0, 255, 0); // 特定のブロックを緑色で表示
        } else {
          fill(255);
        }
        rect(i * block_w, j * block_h, block_w, block_h);
      }
    }
  }
}



// コンボシステムを処理する関数
void handle_combo() {
  if (combo_active) {
    combo_timer++;
    if (combo_timer > 60) {
      combo_active = false;
      combo_count = 0;
    }
  }
}
