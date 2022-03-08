package agh.ics.msd.lab1;

import java.awt.*;
import java.util.EnumMap;

public interface IRule {
    int getNextState(int current_state, EnumMap<Direction, Integer> neighborStates);

    Color getStateColor(int state);

    int getNofStates();
}

/*
  	switch (points[x][y].getState()) {
  					case 1:
  						g.setColor(new Color(0x0000ff));
  						break;
  					case 2:
  						g.setColor(new Color(0x00ff00));
  						break;
  					case 3:
  						g.setColor(new Color(0xff0000));
  						break;
  					case 4:
  						g.setColor(new Color(0x000000));
  						break;
  					case 5:
  						g.setColor(new Color(0x444444));
  						break;
  					case 6:
  						g.setColor(new Color(0xffffff));
  						break;
                                         }
*/