/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package brain;


import eu.hansolo.enzo.common.Section;
import eu.hansolo.enzo.gauge.SimpleGauge;
import eu.hansolo.enzo.gauge.SimpleGaugeBuilder;
import eu.hansolo.enzo.led.Led;
import eu.hansolo.enzo.led.LedBuilder;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
   @FXML public Label lblTimeOff;
   
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
   public int iTimeRemain = 0;
   private int iTimeCount = 0;
 
   
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
                        playsignal("/sound/push.mp3");
                        btnblock = true;
                        bredpush = true;
                        redLed.setOn(true);
                        timer.cancel();
                        if (!bgreenpush) btnCont.setDisable(false);
                    }
                    else{
                        playsignal("/sound/falsestart.mp3");
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
                            playsignal("/sound/push.mp3");
                            btnblock = true;
                            bgreenpush = true;
                            greenLed.setOn(true);
                            timer.cancel();
                            if (!bredpush) btnCont.setDisable(false);
                        }
                        else{ 
                            playsignal("/sound/falsestart.mp3");
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
       playsignal("/sound/start.mp3");
       timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
               /*if (controlTimer.getValue() == controlTimer.getMaxValue()){
                   lblTimeOff.setVisible(true);
                   //timer.cancel();
               } 
               else */controlTimer.setValue(controlTimer.getValue()+1.0);               
;            }
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
    controlTimer.setValue(0.0);    
    btnStart.setDisable(false);
    btnCont.setDisable(true);
    lblTimeOff.setVisible(false);
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
   
    @FXML protected void btnPropertyClick(ActionEvent event) {
      if (!bTimerStart){
        
          
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column2);
        gridPane.setPadding(new javafx.geometry.Insets (10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
                Scene secondScene = new Scene(gridPane, 400, 400); 
                Stage secondStage = new Stage(StageStyle.UTILITY);
                secondStage.setTitle("Настройки");
                secondStage.setScene(secondScene);
                secondStage.setResizable(false);   
                secondScene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
                
        Label lbRedTeamName = new Label("Название 1-й команды:");
        lbRedTeamName.setTextFill(Color.web("#CC0000"));
        GridPane.setHalignment(lbRedTeamName, HPos.LEFT);        
        
        TextField tfRedTeamName = new TextField(); 
        tfRedTeamName.setText(lblTeamRed.getText());
        GridPane.setHalignment(lbRedTeamName, HPos.LEFT);

        Label lbGreenTeamName = new Label("Название 2-й команды:");
        lbGreenTeamName.setTextFill(Color.web("#336600"));
        GridPane.setHalignment(lbGreenTeamName, HPos.LEFT);        
        
        TextField tfGreenTeamName = new TextField();
        tfGreenTeamName.setText(lblTeamGreen.getText());
        GridPane.setHalignment(tfGreenTeamName, HPos.LEFT);
        
        Label lbRedKey = new Label("Кнопка 1-й команды:");
        lbRedKey.setTextFill(Color.web("#CC0000"));
        GridPane.setHalignment(lbRedKey, HPos.LEFT);        
        
        TextField tfRedKey = new TextField();        
        tfRedKey.addEventFilter(KeyEvent.KEY_PRESSED, new javafx.event.EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
            keyTeamRed = ke.getCode();
            tfRedKey.clear();
            tfRedKey.setText(keyTeamRed.toString());
            }});
        tfRedKey.setPromptText(keyTeamRed.toString());
        GridPane.setHalignment(lbRedTeamName, HPos.LEFT);
        
        Label lbGreenKey = new Label("Кнопка 2-й команды:");
        lbGreenKey.setTextFill(Color.web("#336600"));
        GridPane.setHalignment(lbGreenKey, HPos.LEFT);        
        
        TextField tfGreenKey = new TextField(); 
        tfGreenKey.setText(keyTeamGreen.toString());
        GridPane.setHalignment(tfGreenKey, HPos.LEFT);
        
        ToggleGroup groupRB = new ToggleGroup();        
        Label groupLabel = new Label("После неправильного ответа команды");               
        RadioButton rbFull = new RadioButton("использовать все оставшееся время");
        rbFull.setToggleGroup(groupRB);
        rbFull.setSelected(true);        
        RadioButton rbTime = new RadioButton("давать на обсуждение сек.:");
        rbTime.setToggleGroup(groupRB);
        
        ChoiceBox cbTime = new ChoiceBox(FXCollections.observableArrayList(
            "5", "10", "15", "20", "25", "30")
            );
        cbTime.getSelectionModel().selectFirst();
        cbTime.setPadding(new javafx.geometry.Insets(0,0,0,5));
        HBox hbox = new HBox();       
        hbox.getChildren().addAll(rbTime, cbTime);
        Separator separatorTop = new Separator();
        Separator separatorBottom = new Separator();
        VBox vb = new VBox();
        vb.setSpacing(5.0);        
        vb.getChildren().addAll(separatorTop, groupLabel,rbFull,hbox,separatorBottom);
            
                Button okButton = new Button("Сохранить");
                okButton.setId("glass-grey");
                 okButton.setOnAction(new EventHandler<ActionEvent>(){ 
                  @Override
                  public void handle(ActionEvent arg0) {   
                   if (tfGreenTeamName.getText().length()>0 && tfGreenTeamName.getText().length() < 20)
                       lblTeamGreen.setText(tfGreenTeamName.getText());
                   if (tfGreenTeamName.getText().length() >= 20)
                      lblTeamGreen.setText(tfGreenTeamName.getText().substring(0, 19));
                   if (tfRedTeamName.getText().length()>0 && tfRedTeamName.getText().length() < 20)
                       lblTeamRed.setText(tfRedTeamName.getText());
                   if (tfRedTeamName.getText().length() >= 20)
                       lblTeamRed.setText(tfRedTeamName.getText().substring(0, 19));
                   if (groupRB.getSelectedToggle() == rbTime)
                       iTimeRemain =  java.lang.Integer.parseInt(cbTime.getValue().toString());
                   secondStage.close();
                  }                 
                });
                 
                 Button noButton = new Button("Отмена");
                 noButton.setId("glass-grey");
                 noButton.setOnAction(new EventHandler<ActionEvent>(){ 
                  @Override
                  public void handle(ActionEvent arg0) {   
                  secondStage.close();
                  }                 
                });
                 
                
                gridPane.add(lbRedTeamName, 0, 0);
                gridPane.add(tfRedTeamName, 1, 0); 
                gridPane.add(lbGreenTeamName, 0, 1);
                gridPane.add(tfGreenTeamName, 1, 1);
                gridPane.add(lbRedKey, 0, 2);
                gridPane.add(tfRedKey, 1, 2);
                gridPane.add(lbGreenKey, 0, 3);
                gridPane.add(tfGreenKey, 1, 3);
                gridPane.add(vb, 0, 4,2,1);
                gridPane.add(okButton, 0,5);  
                gridPane.add(noButton, 1,5);  
                gridPane.setHalignment(okButton, HPos.CENTER);
                gridPane.setHalignment(noButton, HPos.CENTER);
                
                
                secondStage.show();
        }       
    }
    private void playsignal (String path){
        /*String source =  "push.mp3";
        Media media = null;
        media = new Media(source);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();*/
       Media someSound = new Media(getClass().getResource(path).toString());
        MediaPlayer mp = new MediaPlayer(someSound);
        mp.play();
      }
}
