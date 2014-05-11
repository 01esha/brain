/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package brain;

import eu.hansolo.enzo.clock.Clock;
import eu.hansolo.enzo.clock.ClockBuilder;
import eu.hansolo.enzo.common.Section;
import eu.hansolo.enzo.gauge.Gauge;
import eu.hansolo.enzo.gauge.GaugeBuilder;
import eu.hansolo.enzo.led.LedBuilder;
import eu.hansolo.steelseries.extras.StopWatch;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author ОиО
 */

public class FXMLDocumentController implements Initializable {
   @FXML public GridPane gridpaneMain;    
   //private Clock      clock1;
   public Gauge control;  
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    gridpaneMain.add(LedBuilder.create()
                                   .ledColor(Color.GREENYELLOW)
                                   .frameVisible(true)
                                   //.interval(500_000_000l)
                                   //.blinking(true)
                                   .on(true)
                                   .build(), 3, 2);
    
    gridpaneMain.add(LedBuilder.create()
                                   .ledColor(Color.RED)
                                   .frameVisible(true)
                                   //.interval(500_000_000l)
                                   //.blinking(true)
                                   .build(), 1, 2);
    /*   
    gridpaneMain.add(clock1 = ClockBuilder.create()
                             //.prefSize(400, 400)
                             .design(Clock.Design.DB)                             
                             .running(true)
                             //.text("San Francisco")
                             .offset(Duration.of(-9, ChronoUnit.HOURS))
                             //.autoNightMode(false)
                             //.running(false)
                        .build(),2,2);
    */
    control = GaugeBuilder.create()
           
                              //.prefSize(400, 400)
                              .startAngle(180)
                              .angleRange(360)
                              .minValue(0)
                              .maxValue(60)
                              .sections(new Section(0, 50),
                                        new Section(50, 55),
                                        new Section(55, 60))
            .sectionFill0(Color.GREEN)
            .sectionFill1(Color.YELLOW)
            .sectionFill2(Color.RED)
                              .majorTickSpace(5)
                              .plainValue(false)
                              .tickLabelOrientation(Gauge.TickLabelOrientation.HORIZONTAL)
                              //.threshold(35)
                              //.thresholdVisible(true)
                              //.minMeasuredValueVisible(true)
                              //.maxMeasuredValueVisible(true)
                              .title("Title")            
                              //.unit("Unit")
                              .build();
        //control.setStyle("-tick-label-fill: red;");        
        //control.setHistogramEnabled(true);        
        //marker0 = new Marker(5);    
        //control.addMarker(marker0);              
gridpaneMain.add(control,2,2);
     

    } 
    
}
