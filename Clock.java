import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import java.util.*;


public class Clock extends Application{
	private Canvas cv;
	final int WIDTH=600;
	final int HEIGHT=200;
	final int RADIUS=80;
	
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage stage)throws Exception{
		cv=new Canvas(WIDTH,HEIGHT);
		GraphicsContext gc=cv.getGraphicsContext2D();
		BorderPane bp=new BorderPane();
		bp.setCenter(cv);
		Scene sc=new Scene(bp,WIDTH,HEIGHT);
		stage.setScene(sc);
		stage.setTitle("現在時刻");
		stage.show();
		
		Thread th=new Thread(() -> {
			for(int t=0;t<60;t++,t%=60){
				Calendar cl=new GregorianCalendar();
				int hour=cl.get(Calendar.HOUR_OF_DAY);
				int minute=cl.get(Calendar.MINUTE);
				int second=cl.get(Calendar.SECOND);
				
				double minute2=minute+(second/60.0);
				double hour2=hour+(minute/60.0);
				
				gc.clearRect(0,0,WIDTH,HEIGHT);
				gc.setFont(new Font("Arial",32));
				gc.fillText(String.format("%02d:%02d:%02d",hour,minute,second),100,100);
				int rad_s=(int)(RADIUS*0.8);
				int rad_m=(int)(RADIUS*0.75);
				int rad_h=(int)(RADIUS*0.5);
				
				int cx=150+WIDTH/2;
				int cy=HEIGHT/2;
				
				double theta_sec=second*360/60-90;//
				double xs=rad_s*Math.cos(theta_sec*Math.PI/180);
				double ys=rad_s*Math.sin(theta_sec*Math.PI/180);
				
				double theta_min=minute2*360/60-90;//
				double xm=rad_m*Math.cos(theta_min*Math.PI/180);
				double ym=rad_m*Math.sin(theta_min*Math.PI/180);
				
				double theta_hour=hour2*360/12-90;//
				double xh=rad_h*Math.cos(theta_hour*Math.PI/180);
				double yh=rad_h*Math.sin(theta_hour*Math.PI/180);
				
				for(int time=0;time<60;time++){
					double theta=time*6*Math.PI/180;//ここではラジアン
					gc.setStroke(Color.BLACK);
					gc.setLineWidth(1.0f);//
					gc.strokeLine(cx+0.95*RADIUS*Math.cos(theta),cy+0.95*RADIUS*Math.sin(theta),cx+RADIUS*Math.cos(theta),cy+RADIUS*Math.sin(theta));
				}
				for(int time=0;time<12;time++){
					double theta=time*30*Math.PI/180;//ここではラジアン
					gc.setStroke(Color.BLACK);
					gc.setLineWidth(1.0f);//
					gc.strokeLine(cx+0.9*RADIUS*Math.cos(theta),cy+0.9*RADIUS*Math.sin(theta),cx+RADIUS*Math.cos(theta),cy+RADIUS*Math.sin(theta));
				}
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(1.0f);//
				gc.strokeOval(cx-RADIUS,cy-RADIUS,RADIUS*2,RADIUS*2);
				gc.setStroke(Color.RED);
				gc.setLineWidth(1.0f);//
				gc.strokeLine(cx,cy,(int)(cx+xs),(int)(cy+ys));
				gc.setStroke(Color.BLUE);
				gc.setLineWidth(1.0f);//
				gc.strokeLine(cx,cy,(int)(cx+xm),(int)(cy+ym));
				gc.setStroke(Color.BLUE);
				gc.setLineWidth(3.0f);//
				gc.strokeLine(cx,cy,(int)(cx+xh),(int)(cy+yh));
				try{
					Thread.sleep(1000);
				}catch(Exception e){
				}
			}
			
			
		});
		
		th.setDaemon(true);
		th.start();
		
	}
	
}