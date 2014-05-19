/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package brain;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import eu.hansolo.enzo.clock.Clock;
import eu.hansolo.enzo.clock.ClockBuilder;
import eu.hansolo.enzo.common.Section;
import eu.hansolo.enzo.gauge.Gauge;
import eu.hansolo.enzo.gauge.GaugeBuilder;
import eu.hansolo.enzo.gauge.SimpleGauge;
import eu.hansolo.enzo.gauge.SimpleGaugeBuilder;
import eu.hansolo.enzo.led.Led;
import eu.hansolo.enzo.led.LedBuilder;

import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author ОиО
 */

public class FXMLDocumentController implements Initializable {
   @FXML public GridPane gridpaneMain;    
   @FXML public Label lblTeamRed;
   @FXML public Label lblTeamGreen;
   @FXML public Label lblFalseGreen;
   @FXML public Label lblFalseRed;
   @FXML public Button btnCont;
   @FXML public Button btnStart;
   
   public SimpleGauge controlTimer;  
   private long                lastTimerCall;
   //private AnimationTimer      timer;
   public Led greenLed;
   public Led redLed;
   public Timer timer ;
   public boolean bTimerStart = false;
   public KeyCode keyTeamRed =  KeyCode.CONTROL;
   public KeyCode keyTeamGreen =  KeyCode.ALT;
   public boolean bredpush = false;
   public boolean bgreenpush = false;
   public boolean btnblock = false;
   
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    gridpaneMain.requestFocus();
        greenLed =          LedBuilder.create()
                                   .ledColor(Color.GREENYELLOW)
                                   .frameVisible(true)
                                   //.interval(500_000_000l)
                                   //.blinking(true)
                                   .on(false)
                                   .build();
        gridpaneMain.add(greenLed,3,2);
        redLed =            LedBuilder.create()
                                   .ledColor(Color.RED)
                                   .frameVisible(true)
                                   //.interval(500_000_000l)
                                   //.blinking(true)
                                    .on(false)
                                   .build();
        gridpaneMain.add(redLed,1,2);   
    
    controlTimer = SimpleGaugeBuilder.create()                                        
                                        .sections(new Section(0, 50, "0"),
                                            new Section(50, 55, "1"),
                                            new Section(55, 60, "2"))
                                        .sectionFill0(Color.GREEN)
                                        .sectionFill1(Color.YELLOW)
                                        .sectionFill2(Color.RED)
                                        .title("Время")
                                        .unit("сек")
                                        .value(0.0)                     
                                        .maxValue(60)
                                        .animationDuration(500)
                                        .build();
    gridpaneMain.add(controlTimer,2,2);
    /*
    lastTimerCall = System.nanoTime(); //  + 500_000_000l
        timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 100000000) {
                    control.setValue(control.getValue()+1.0);                    
                    lastTimerCall = now;
                }
            }
        };
    timer.start(); */ 
    gridpaneMain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {                
                if (event.getCode() == keyTeamRed && !bredpush &&!btnblock) {
                    if (bTimerStart) {
                        btnblock = true;
                        bredpush = true;
                        redLed.setOn(true);
                        timer.cancel();
                        btnCont.setDisable(false);
                    }
                    else{
                        btnblock = true;
                        lblFalseRed.setVisible(true);                        
                        redLed.setBlinking(true);
                        btnCont.setDisable(false);
                        bredpush = true;
                        btnStart.setDisable(true);
                    }
                } else 
                    if (event.getCode() == keyTeamGreen && !bgreenpush &&!btnblock){
                        if (bTimerStart){
                            btnblock = true;
                            bgreenpush = true;
                            greenLed.setOn(true);
                            timer.cancel();
                            btnCont.setDisable(false);
                        }
                        else{ 
                            btnblock = true;
                            lblFalseGreen.setVisible(true);
                            greenLed.setBlinking(true);                        
                            btnCont.setDisable(false);
                            bgreenpush = true;
                            btnStart.setDisable(true);
                        }
            }
            }});
    
    
    } 
    
    @FXML protected void btnStartClick() {
    btnStart.setDisable(true);
        lastTimerCall = System.nanoTime();
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                controlTimer.setValue(controlTimer.getValue()+1.0); 
            }
        }; 
    timer.schedule(timerTask, 0, 1000);
    bTimerStart = true; 
    }

    @FXML protected void btnStopClick(ActionEvent event) {
       /* 
        timer.cancel();
        bTimerStart = false;
        //controlTimer.setValue(0);
        double  d = (System.nanoTime()-lastTimerCall)/1000000000.0;
        DecimalFormat format = new DecimalFormat("##.####"); 
        controlTimer.setTitle(String.valueOf(format.format(d)));
               */
    if (bTimerStart) timer.cancel();    
    bTimerStart = false;   
    bredpush = false;
    bgreenpush = false;
    btnblock = false;
    lblFalseGreen.setVisible(false);
    lblFalseRed.setVisible(false); 
    redLed.setOn(false);
    redLed.setBlinking(false);
    greenLed.setOn(false);
    greenLed.setBlinking(false);
    controlTimer.setValue(0);
    btnStart.setDisable(false);
    btnCont.setDisable(true);       
    }
    
    @FXML protected void btnContClick(ActionEvent event) {
     if (!bTimerStart){
        btnblock = false;
        btnStart.setDisable(false);
        btnCont.setDisable(true);                
        if (bredpush)
             redLed.setBlinking(false);
        if (bgreenpush)
            greenLed.setBlinking(false);
     }
     else {        
        btnblock = false;
        if (bredpush)
             redLed.setOn(false);
        if (bgreenpush)
            greenLed.setOn(false);
        btnStartClick();        
        }
     btnCont.setDisable(true); 
     }
    
}
