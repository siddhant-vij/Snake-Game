package com.snakegame;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Room {
  private int width;
  private int height;
  private Snake snake;
  private Mouse mouse;
  static Room game;

  public Room(int width, int height, Snake snake) {
    this.width = width;
    this.height = height;
    this.snake = snake;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Snake getSnake() {
    return snake;
  }

  public void setSnake(Snake snake) {
    this.snake = snake;
  }

  public Mouse getMouse() {
    return mouse;
  }

  public void setMouse(Mouse mouse) {
    this.mouse = mouse;
  }

  public void createMouse() {
    mouse = new Mouse((int) (Math.random() * width), (int) (Math.random() * height));
  }

  public void eatMouse() {
    createMouse();
  }

  public void run() {
    KeyboardObserver keyboardObserver = new KeyboardObserver();
    keyboardObserver.start();
    while (snake.isAlive()) {
      if (keyboardObserver.hasKeyEvents()) {
        KeyEvent event = keyboardObserver.getEventFromTop();
        if (event.getKeyChar() == 'q')
          return;
        if (event.getKeyCode() == KeyEvent.VK_LEFT)
          snake.setDirection(SnakeDirection.LEFT);
        else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
          snake.setDirection(SnakeDirection.RIGHT);
        else if (event.getKeyCode() == KeyEvent.VK_UP)
          snake.setDirection(SnakeDirection.UP);
        else if (event.getKeyCode() == KeyEvent.VK_DOWN)
          snake.setDirection(SnakeDirection.DOWN);
      }
      snake.move();
      print();
      sleep();
    }
    System.out.println("Game Over!");
  }

  public void sleep() {
    int level = snake.getSections().size();
    int delay;

    if (level <= 10) {
      delay = 500 - (level - 1) * 20;
    } else if (level <= 14) {
      delay = 300 - (level - 11) * 20;
    } else {
      delay = 200;
    }

    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void print() {
    int[][] matrix = new int[height][width];
    ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
    for (SnakeSection snakeSection : sections) {
      matrix[snakeSection.getY()][snakeSection.getX()] = 1;
    }
    matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;
    matrix[mouse.getY()][mouse.getX()] = 3;
    String[] symbols = { ".", "x", "X", "^", "*" };
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        System.out.print(symbols[matrix[y][x]]);
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
    System.out.println();
  }

  public static void main(String[] args) {
    Snake snake = new Snake(0, 0);
    game = new Room(10, 10, snake);
    snake.setDirection(SnakeDirection.DOWN);
    game.createMouse();
    game.run();
  }
}
