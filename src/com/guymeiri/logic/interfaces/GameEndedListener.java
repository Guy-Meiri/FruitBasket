package com.guymeiri.logic.interfaces;

import com.guymeiri.logic.GameOverInfo;

public interface GameEndedListener {
    public void onGameEnded(GameOverInfo gameOverInfo);
}
