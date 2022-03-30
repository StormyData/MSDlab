package agh.ics.msd.lab4;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = Config.POINT_SIZE;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
                if(y<2 || points[x].length - y < 3)
                    points[x][y].type = 5;
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                for(int i = 0; i<=Point.MAX_SPEED; i++)
                {
                    points[x][y].forwardNeighborhoodCurrentLane[i] = points[(x + 1 + i) % points.length][y];
                    if(y > 0)
                    {
                        points[x][y].forwardNeighborhoodUpperLane[i] = points[(x + 1 + i) % points.length][y - 1];
                        points[x][y].backwardNeighborhoodUpperLane[i] = points[(x - 1 - i + points.length) % points.length][y - 1];
                        points[x][y].upperNeighborhood = points[x][y - 1];
                    }
                    if(y + 1 < points[x].length)
                    {
                        points[x][y].forwardNeighborhoodLowerLane[i] = points[(x + 1 + i) % points.length][y + 1];
                        points[x][y].backwardNeighborhoodLowerLane[i] = points[(x - 1 - i + points.length) % points.length][y + 1];
                        points[x][y].lowerNeighborhood = points[x][y + 1];
                    }
                    points[x][y].backwardNeighborhoodCurrentLane[i] = points[(x - 1 - i + points.length) % points.length][y];

                }

            }
        }

    }

    public void iteration() {
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved = false;
                points[x][y].tryEndOvertake();
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].move();
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved = false;
                points[x][y].tryBeginOvertake();
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].move();
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved = false;
                points[x][y].doNormalMove();
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].move();
            }
        }
        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
                if(y<2 || points[x].length - y < 3)
                    points[x][y].type = 5;
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                Color color = switch (points[x][y].type)
                {
                    case 0 -> Color.WHITE;
                    case 1 -> Color.YELLOW;
                    case 2 -> Color.BLUE;
                    case 3 -> Color.RED;
                    case 5 -> Color.GREEN;
                    default -> Color.BLACK;
                };
                g.setColor(color);
                //g.setColor(new Color(R, G, B, 0.7f));

                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].type= editType;
                points[x][y].speed = points[x][y].getMaxSpeed();
            }
            this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            } else {
                points[x][y].type = editType;
                points[x][y].speed = points[x][y].getMaxSpeed();
            }
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
