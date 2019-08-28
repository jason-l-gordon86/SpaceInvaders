/**
 @author Jason Gordon
 @version Space Invaders v1.0
 */
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.Pane; 
import javafx.scene.layout.VBox; 
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MainClass extends Application
{
	/**Describes Game objects and its containers
	 * 
	 */
	Pane p;
	Circle ship;
	Line enemy;
	Line missile;
	Label score;
	Label lives;
	int score_num;
	int life_num = 3;
	/**
	 * Amination time for the enemy and missile respectively
	 */
	Timer t = new Timer();
	TimerMissile shoot = new TimerMissile();

	public static void main(String[] args) 
	{
		
		launch(args);

	}
	public void start(Stage stage) 
	{
		
		score = new Label("Score = 0");
		lives = new Label("Lives = 3");
		Image shipimg = new Image("2dship.png");
		ship = new Circle(190, 580, 20);
		ship.setFill(new ImagePattern(shipimg));
        ship.setStroke(Color.TRANSPARENT);
        /**Describes enemy and sets starting position
		 * 
		 */
		enemy = new Line();
		enemy.setStartX(190);
		enemy.setStartY(0);
		enemy.setEndX(190);
		enemy.setEndY(0);
		enemy.setStroke(Color.WHITE);
		
		/**Describes missile and its starting position
		 * 
		 */
		missile = new Line();
		missile.setStartX(ship.getCenterX());
		missile.setStartY(ship.getCenterY());
		missile.setEndX(ship.getCenterX());
		missile.setEndY(ship.getCenterY() - 5);
		missile.setStroke(Color.YELLOW);
		
		
        
        
        
        
        /** Describes buttons and the event handlers to start the animation timer for the enemy
         * @param Start and Stop
         */
        Button StartBtn = new Button("Start");
        StartBtn.setOnAction((ActionEvent e) -> {
        	t.start();
        });
        StartBtn.setFocusTraversable(false);
        
        
        Button StopBtn = new Button("Stop");
        StopBtn.setOnAction((ActionEvent e) -> {
        	t.stop();
        	
        });
        StopBtn.setFocusTraversable(false);
       
        /** Describes the pane and its background 
         * Create and set the background image for the pane
         * Include pane objects
         */
		Image img = new Image("const_2.jpg");
		BackgroundImage backgroundimage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, 
                BackgroundSize.DEFAULT);
                   
		
		Background background = new Background(backgroundimage);
		p = new Pane();
		p.setBackground(background);
		p.setMinSize(400, 600);
		p.getChildren().addAll(ship, enemy);
		
		/**Describes button container
		 * Container for the start and stop buttons
		 */
		HBox hb = new HBox();
		hb.setPadding(new Insets(10, 10, 10, 10));
		hb.setSpacing(7);
		hb.getChildren().addAll(StartBtn, StopBtn);
		
		/** Describes the container for the scores, lives, horizontal box and pane
		 * 
		 */
		VBox vb = new VBox();
		vb.setPadding(new Insets(10, 10, 10, 10));
		vb.setSpacing(7);
		vb.getChildren().addAll(p, hb, score, lives);
		
		
		
		
		
		
		
		
		
		
		
		
		
		Scene scene = new Scene(vb); 
		stage.setScene(scene);
		/**Describes event handlers that allow the user to move the ship and the missile
		 * 
		 */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			public void handle(KeyEvent event) 
			{
				switch(event.getCode()) 
				{
				
				case LEFT:
					ship.setCenterX(ship.getCenterX() - 10);
					
					missile.setStartX(ship.getCenterX() - 10);
					missile.setEndX(ship.getCenterX() - 10);
					missile.setStartY(ship.getCenterY());
					missile.setEndY(ship.getCenterY()-5);
					
					if(ship.getCenterX() <= 10 && missile.getStartX() <= 10 && missile.getEndX() <= 10) 
					{
						ship.setCenterX(390);
						missile.setStartX(390);
						missile.setEndX(390);
						missile.setStartY(ship.getCenterY());
						missile.setEndY(ship.getCenterY()-5);
						
						
					}
					break;
				case RIGHT:
					ship.setCenterX(ship.getCenterX() + 10);
					missile.setStartX(ship.getCenterX() + 10);
					missile.setEndX(ship.getCenterX() + 10);
					missile.setStartY(ship.getCenterY());
					missile.setEndY(ship.getCenterY()-5);
					
					
					if(ship.getCenterX() >= 390 && missile.getStartX() >= 390 && missile.getEndX() >= 390) 
					{
						ship.setCenterX(10);
						missile.setStartX(10);
						missile.setEndX(10);
						missile.setStartY(ship.getCenterY());
						missile.setEndY(ship.getCenterY()-5);
						
					}
					break;
				case ENTER:
					
					p.getChildren().add(missile);
					shoot.start();
					
				break;
				
					
					
					
				default: // no default end switch. 
				
				
				
				}
			}
		});
		stage.setTitle("Space Invaders");
		stage.setWidth(440.0); stage.setHeight(800.0);
		stage.setResizable(false);
		
		stage.show();
		
	}
	/** Describes method reloadMissile()
	 * @param no parameters
	 */
	public void reloadMissile() 
	{
		p.getChildren().remove(missile);
		shoot.stop();
		missile.setStartX(ship.getCenterX());
		missile.setStartY(ship.getCenterY());
		missile.setEndX(ship.getCenterX());
		missile.setEndY(ship.getCenterY() - 5);
		
		
		
	}
	
	/**Describes method removeEnemy()
	 * @param no parameters
	 */
	
	public void removeEnemy() 
	{
		p.getChildren().remove(enemy);
		int randomInt = ThreadLocalRandom.current().nextInt(10, 380);
		enemy.setStartY(0);
		enemy.setEndY(0);
		enemy.setStartX(randomInt);
		enemy.setEndX(randomInt);
		p.getChildren().add(enemy);
	}
	/**Describes method missileProxCheck()
	 * Verifies that the missile hits the enemy
	 * If the missile hits the enemy, the score increases by 10 points
	 * @param no parameters
	 */
	public void missileProxCheck() 
	{
		int killZoneX = (int)(missile.getEndX() - enemy.getEndX());
		int killZoneY = (int)(missile.getEndY() - enemy.getEndY());
		killZoneX = Math.abs(killZoneX);
		killZoneY = Math.abs(killZoneY);
		
		if (killZoneX <= 5 && killZoneY <= 5) 
		{
			removeEnemy();
			reloadMissile();
			score_num += 10;
			score.setText("score =" + score_num);
			
		}
	}
	/**Describes method enemyProxCheck()
	 * Checks the proximity of the enemy in relation to the end of the pane or ship
	 * If enemy reaches the end of the pane or hits the ship, player's lives are subtracted by one. 
	 * @param no parameters
	 */
	public void enemyProxCheck() 
	{
		if(enemy.getEndY() >= 580) 
		{
			life_num = life_num - 1;
			removeEnemy();
			lives.setText("lives = " + life_num);
			
			
			
		}
		
		int killZoneX = (int)(enemy.getEndX() - ship.getCenterX());
		int killZoneY = (int)(enemy.getEndY() - ship.getCenterY());
		killZoneX = Math.abs(killZoneX);
		killZoneY = Math.abs(killZoneY);
		if (killZoneX <= 3 && killZoneY <= 3) 
		{
			life_num = life_num - 1;
			removeEnemy();
			lives.setText("lives = " + life_num);
		}
		
	}
	/** Describes class Timer
	 * The animation timer that allows the enemy to move autonomously 
	 * 
	 *
	 */
	public class Timer extends AnimationTimer
	{
		
		long lastUpdate = 0;
		
		
		
		
		public Timer() 
		{
			
		}
		
		public void handle() 
		{
			
		}
		

		@Override
		public void handle(long now) 
		{
			lastUpdate++;
			if (lastUpdate >= 10) 
			{
				enemy.setEndY(enemy.getEndY() + 10);
				lastUpdate = 0;
				
				
			}
			enemyProxCheck();
			//stops the animation when lives are exhausted.
			if (life_num == 0) 
			{
				t.stop();
				life_num = 3;
				lives.setText("lives = " + life_num);
			}
			
			
		}

	}
	
	/** Describes class Timer
	 * The animation timer that allows the player to fire the missile 
	 * 
	 *
	 */
	
	public class TimerMissile extends AnimationTimer
	{
		long lastUpdate = 0;
		
		
		public TimerMissile() 
		{
			
		}
		
		public void handle() 
		{
			
		}
		

		@Override
		public void handle(long now) 
		{
			lastUpdate++;
			if (lastUpdate >= 7) 
			{
				missile.setStartY(missile.getStartY() - 10);
				missile.setEndY(missile.getEndY() - 10);
				
				
				
			}
			missileProxCheck();
			if (missile.getEndY() <= 1) 
			{
				reloadMissile();
				
			}
			
			
			
		}

	}
	
	


}
