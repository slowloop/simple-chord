package slowloop;


import javafx.application.Application;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Collections;


public class UserInterface extends Application{

    private int count1;
    private double volume1, volume2;
    private boolean turnOn, secondVol;
    private MidiChannel channel[], channel1[];
    private Synthesizer synth, synth3;
    private Rectangle rect, rect1, rect2, rect3,rect4, rect5,rect6, rect7,rect8, rect9,
            rect10, rect11, rect12, rect13;
    private String note;
    private Circle circle, circle2;
    private Line line, line2;
    private ArrayList<Button> cNames, majButtons, miButtons;
    private HBox chordNames, majorButtons, minorButtons;
    private Group group, volume;

    public void start(Stage primaryStage) throws Exception {
        cNames = new ArrayList<Button>();
        majButtons = new ArrayList<Button>();
        miButtons = new ArrayList<Button>();

        chordNames = new HBox();
        majorButtons = new HBox();
        minorButtons = new HBox();

        group = new Group();
        volume = new Group();

        //Set the starting values for the volume control knobs.
        volume1 = 63.5;
        volume2 = 63.5;

        secondVol = false;

        try{
            //Synthesizer for the strumplate.
            synth3 = MidiSystem.getSynthesizer();
            synth3.open();
            channel1 = synth3.getChannels();
            channel1[5].programChange(0, 26);

            //Synthesizer for the chords.
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels();
            channel[4].programChange(0, 40);
        }
        catch(Exception e){
        }

        chordNames();
        getMajorButtons();
        getMinorButtons();
        textTitles();
        sonicStrings();
        volumeShapes();
        volumeControl();

        Scene scene = new Scene(group, 500,320);
        scene.setFill(Color.valueOf("#e6e6e6"));
        sounds(scene);
        moreSounds(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("SimpleChord");
        primaryStage.show();
    }

    public void chordNames(){

        Collections.addAll(cNames, new Button("C"), new Button("D"), new Button("E"), new Button("F"),
                new Button("G"), new Button("A"), new Button("B"));

        for(Button button : cNames){
            button.setStyle("-fx-background-color: #e6e6e6; -fx-pref-width: 30px;-fx-text-fill:#2132B0");
            chordNames.getChildren().add(button);
        }

        chordNames.setSpacing(7);
        chordNames.setTranslateX(80);
        chordNames.setTranslateY(95);
        chordNames.setStyle("-fx-font-weight: bold;");

        group.getChildren().add(chordNames);
    }

    //Create the major chord buttons.
    public void getMajorButtons(){

        Collections.addAll(majButtons, new Button("Q"), new Button("W"), new Button("E"),
                new Button("R"), new Button("T"), new Button("Y"), new Button("U"));

        for(Button button : majButtons){
            button.setStyle("-fx-border-color: #000000; -fx-background-color: #e6e6e6; -fx-border-width: 1px; -fx-pref-width: 30px; -fx-text-fill:#2132B0");
            majorButtons.getChildren().add(button);
        }

        majorButtons.setSpacing(7);
        majorButtons.setTranslateX(80);
        majorButtons.setTranslateY(125);

        group.getChildren().add(majorButtons);
    }

    //Create the minor chord buttons.
    public void getMinorButtons(){

        Collections.addAll(miButtons, new Button("A"), new Button("S"), new Button("D"), new Button("F"),
                new Button("G"), new Button("H"), new Button("J"));

        for(Button button : miButtons){
            button.setStyle("-fx-border-color: #000000; -fx-background-color: #e6e6e6; -fx-border-width: 1px; -fx-pref-width: 30px; -fx-text-fill:#2132B0");
            minorButtons.getChildren().add(button);
        }

        minorButtons.setSpacing(7);
        minorButtons.setTranslateX(80);
        minorButtons.setTranslateY(167);

        group.getChildren().add(minorButtons);
    }

    //Create the titles.
    public void textTitles(){
        Text title = new Text("SIMPLE CHORD");
        Text major = new Text("MAJOR");
        Text minor = new Text("MINOR");
        Text hVolume = new Text("HARP\nVOLUME");
        Text cVolume = new Text("CHORD\nVOLUME");

        hVolume.setTextAlignment(TextAlignment.CENTER);
        hVolume.setTranslateX(196);
        hVolume.setTranslateY(275);
        hVolume.setStyle("-fx-font-weight: bold;-fx-fill:#2132B0;");

        cVolume.setTextAlignment(TextAlignment.CENTER);
        cVolume.setTranslateX(117);
        cVolume.setTranslateY(275);
        cVolume.setStyle("-fx-font-weight: bold;-fx-fill:#2132B0;");

        title.setTranslateX(50);
        title.setTranslateY(55);

        major.setTranslateX(25);
        major.setTranslateY(143);
        major.setStyle("-fx-font-weight: bold; -fx-fill:#2132B0;");

        minor.setTranslateX(25);
        minor.setTranslateY(180);
        minor.setStyle("-fx-font-weight: bold;-fx-fill:#2132B0;");

        title.setFont(Font.loadFont(this.getClass().getResource("/fonts/ChangaOne-Italic.ttf").toString(), 40));
        title.setStyle("-fx-fill:#2132B0;");

        group.getChildren().addAll(title, major, minor, hVolume, cVolume);
    }

    //Create the volume control knobs.
    public void volumeShapes(){
        //Chord volume.
        circle = new Circle();

        circle.setTranslateX(140);
        circle.setTranslateY(240);
        circle.setRadius(20);
        circle.setStyle("-fx-stroke: #1a1a1a; -fx-fill: rgba(0,0,0,0);");

        line = new Line();

        line.setStartX(135);
        line.setEndX(145);

        line.setStartY(230);
        line.setEndY(230);

        line.setRotate(90);
        line.setStyle("-fx-stroke-width: 2.5;-fx-stroke:#2132B0;");

        //Harp volume.
        circle2 = new Circle();

        circle2.setTranslateX(220);
        circle2.setTranslateY(240);
        circle2.setRadius(20);
        circle2.setStyle("-fx-stroke: #1a1a1a; -fx-fill: rgba(0,0,0,0);");

        line2 = new Line();

        line2.setStartX(215);
        line2.setEndX(225);

        line2.setStartY(230);
        line2.setEndY(230);

        line2.setRotate(90);
        line2.setStyle("-fx-stroke-width: 2.5;-fx-stroke:#2132B0;");

        volume.getChildren().addAll(line,line2,circle,circle2);
        group.getChildren().add(volume);
    }

    //Create the chord and harp volume control functionality.
    public void volumeControl(){

        //Chord volume.
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {

            double angle = 90;
            boolean q1, q2, q3,q4;
            boolean allQ = true;

            public void handle(MouseEvent event) {

                //The volume is determined using the angle of the line inside the volume knob.
                if(angle <= 90){
                    volume1 = (63.5 - ((90 - angle)*0.47));
                }
                else if (angle >= 90 && angle < 270){
                    volume1 = (63.5 + ((angle - 90)*0.47));
                }
                else if (angle >= 270){
                    volume1 = (63.5 - ((90 + (360 - angle))*0.47));
                }

                if(turnOn != true){

                }else{
                    channel[4].controlChange(7,(int) volume1);
                }

                //The volume control knob is divided into 4 quadrants. The line inside of the control knob can only rotate
                //into the quadrant that comes after the one it is currently in and/or the one preceding it. So if the line is
                //located in the bottom left quadrant then it can only rotate into the top left quadrant (once in the top left quadrant
                //it can either rotate into the top right or back to the bottom left quadrant). The line cannot jump from the bottom left
                //quadrant to the top right or bottom right quadrant.

                //The angle of the line is determined using the X and Y coordinates of the cursor. If the location of the cursor reaches
                //or surpasses a certain point inside the control knob (or is outside the control knob) then the angle of the line stops rotating.
                if(event.getY() <= 12 && event.getY() > 0 && !(event.getX() >= -9) && q1 == true){

                    angle = 360 + Math.toDegrees(Math.atan(event.getY() / event.getX()));
                    q2 = true;
                    q3 = false;
                    q4 = false;
                    allQ = false;
                }
                else {
                }

                if (event.getX() < 0 && event.getY() < 0 && q2 == true || event.getX() < 0 && event.getY() < 0 && allQ == true){

                    angle = Math.toDegrees(Math.atan(event.getY()/event.getX()));
                    q1 = true;
                    q3 = true;
                    q4 = false;

                }
                else if (event.getX() > 0 && event.getY() < 0 && q3 == true || event.getX() > 0 && event.getY() < 0 && allQ == true) {

                    angle = 90 + (90 + (Math.toDegrees(Math.atan(event.getY() / event.getX()))));
                    q2 = true;
                    q4 = true;
                    q1 = false;
                }

                if(event.getY() >= 0 && event.getY() <= 12 && !(event.getX() <= 9) && q4 == true){

                    angle = 90 + (90 + (Math.toDegrees(Math.atan(event.getY() / event.getX()))));
                    q3 = true;
                    q2 = false;
                    q1 = false;
                    allQ = false;
                }
                else {
                }

                //Set the starting X and Y of the line and rotate it based on the given angle.
                line.setRotate(angle);

                line.setStartX(135 - (10 * Math.cos(Math.toRadians(line.getRotate()))));
                line.setEndX(145 - (10 * Math.cos(Math.toRadians(line.getRotate()))));

                line.setStartY(240 - (10 * Math.sin(Math.toRadians(line.getRotate()))));
                line.setEndY(240 - (10 * Math.sin(Math.toRadians(line.getRotate()))));

                line.setRotate(angle);
            }
        });

        //Harp Volume
        circle2.setOnMouseDragged(new EventHandler<MouseEvent>() {

            double angle = 90;
            boolean q1, q2, q3,q4;
            boolean allQ = true;

            public void handle(MouseEvent event) {

                //The volume is determined using the angle of the line inside the volume knob.
                if(angle <= 90){
                    volume2 = (63.5 - ((90 - angle)*0.47));
                }
                else if (angle >= 90 && angle < 270){
                    volume2 = (63.5 + ((angle - 90)*0.46));
                }
                else if (angle >= 270){
                    volume2 = (63.5 - ((90 + (360 - angle))*0.47));
                }

                if(secondVol != true){

                }else{
                    channel1[5].controlChange(7,(int) volume2);
                }

                //The volume control knob is divided into 4 quadrants. The line inside of the control knob can only rotate
                //into the quadrant that comes after the one it is currently in and/or the one preceding it. So if the line is
                //located in the bottom left quadrant then it can only rotate into the top left quadrant (once in the top left quadrant
                //it can either rotate into the top right or back to the bottom left quadrant). The line cannot jump from the bottom left
                //quadrant to the top right or bottom right quadrant.

                //The angle of the line is determined using the X and Y coordinates of the cursor. If the location of the cursor reaches
                //or surpasses a certain point inside the control knob (or is outside the control knob) then the angle of the line stops rotating.
                if(event.getY() <= 12 && event.getY() > 0 && !(event.getX() >= -9) && q1 == true){

                    angle = 360 + Math.toDegrees(Math.atan(event.getY() / event.getX()));
                    q2 = true;
                    q3 = false;
                    q4 = false;
                    allQ = false;
                }
                else {
                }

                if (event.getX() < 0 && event.getY() < 0 && q2 == true || event.getX() < 0 && event.getY() < 0 && allQ == true){

                    angle = Math.toDegrees(Math.atan(event.getY()/event.getX()));
                    q1 = true;
                    q3 = true;
                    q4 = false;
                }
                else if (event.getX() > 0 && event.getY() < 0 && q3 == true || event.getX() > 0 && event.getY() < 0 && allQ == true) {

                    angle = 90 + (90 + (Math.toDegrees(Math.atan(event.getY() / event.getX()))));
                    q2 = true;
                    q4 = true;
                    q1 = false;
                }
                if(event.getY() >= 0 && event.getY() <= 12 && !(event.getX() <= 9) && q4 == true){

                    angle = 90 + (90 + (Math.toDegrees(Math.atan(event.getY() / event.getX()))));
                    q3 = true;
                    q2 = false;
                    q1 = false;
                    allQ = false;
                }
                else {
                }

                //Set the starting X and Y of the line and rotate it based on the given angle.
                line2.setRotate(angle);

                line2.setStartX(215 - (10 * Math.cos(Math.toRadians(line2.getRotate()))));
                line2.setEndX(225 - (10 * Math.cos(Math.toRadians(line2.getRotate()))));

                line2.setStartY(240 - (10 * Math.sin(Math.toRadians(line2.getRotate()))));
                line2.setEndY(240 - (10 * Math.sin(Math.toRadians(line2.getRotate()))));

                line2.setRotate(angle);
            }
        });
    }

    //Create the rectangles for the strumplate.
    public void sonicStrings(){

        rect = new Rectangle(380, 38, 100, 10);
        rect1 = new Rectangle(380, 53, 100, 10);
        rect2 = new Rectangle(380, 68, 100, 10);
        rect3 = new Rectangle(380, 83, 100, 10);
        rect4 = new Rectangle(380, 98, 100, 10);
        rect5 = new Rectangle(380, 113, 100, 10);
        rect6 = new Rectangle(380, 128, 100, 10);
        rect7 = new Rectangle(380, 143, 100, 10);
        rect8 = new Rectangle(380, 158, 100, 10);
        rect9 = new Rectangle(380, 173, 100, 10);
        rect10 = new Rectangle(380, 188, 100, 10);
        rect11 = new Rectangle(380, 203, 100, 10);
        rect12 = new Rectangle(380, 218, 100, 10);
        rect13 = new Rectangle(380, 233, 100, 10);

        Rectangle rect0 = new Rectangle(15, 92, 330, 115);
        rect0.setArcHeight(11);
        rect0.setArcWidth(11);

        Rectangle rect14 = new Rectangle(370, 28, 120, 225);
        rect14.setArcHeight(11);
        rect14.setArcWidth(11);

        rect0.setStyle("-fx-fill:rgb(0,0,0,0);-fx-stroke:#2132B0;-fx-stroke-line-cap:round");
        rect14.setStyle("-fx-fill:rgb(0,0,0,0);-fx-stroke:#2132B0;-fx-stroke-line-cap:round");
        rect.setStyle("-fx-fill:#2132B0");
        rect1.setStyle("-fx-fill:#2132B0");
        rect2.setStyle("-fx-fill:#2132B0");
        rect3.setStyle("-fx-fill:#2132B0");
        rect4.setStyle("-fx-fill:#2132B0");
        rect5.setStyle("-fx-fill:#2132B0");
        rect6.setStyle("-fx-fill:#2132B0");
        rect7.setStyle("-fx-fill:#2132B0");
        rect8.setStyle("-fx-fill:#2132B0");
        rect9.setStyle("-fx-fill:#2132B0");
        rect10.setStyle("-fx-fill:#2132B0");
        rect11.setStyle("-fx-fill:#2132B0");
        rect12.setStyle("-fx-fill:#2132B0");
        rect13.setStyle("-fx-fill:#2132B0");

        group.getChildren().addAll(rect0, rect, rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11, rect12, rect13, rect14);
    }

    //Create the functionality for the major and minor chord buttons.
    public void sounds(Scene scene1){

        final Scene scene = scene1;
        final int velocity = 100;
        count1 = 0;

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {

                //A musical chord is played when the right key is pressed. An int variable (called count1) is used to
                //prevent a chord from repeatedly turning on and off when holding down a key. So a chord can only be
                //turned on or off if count1 is less than 1.
                if(event.getCode() == KeyCode.Q && !(count1 >= 1) || event.getCode() == KeyCode.W && !(count1 >= 1)
                        || event.getCode() == KeyCode.E && !(count1 >= 1) || event.getCode() == KeyCode.R && !(count1 >= 1)
                        || event.getCode() == KeyCode.T && !(count1 >= 1) || event.getCode() == KeyCode.A && !(count1 >= 1) || event.getCode() == KeyCode.S && !(count1 >= 1)
                        || event.getCode() == KeyCode.D && !(count1 >= 1) || event.getCode() == KeyCode.F && !(count1 >= 1)
                        || event.getCode() == KeyCode.G && !(count1 >= 1) || event.getCode() == KeyCode.Y && !(count1 >= 1)
                        || event.getCode() == KeyCode.U && !(count1 >= 1) || event.getCode() == KeyCode.H && !(count1 >= 1)
                        || event.getCode() == KeyCode.J && !(count1 >= 1)){

                    if(turnOn == true){
                        //Pressing the same key will stop the chord from playing.
                        if(event.getCode().getName().equals(note)){
                            channel[4].allNotesOff();

                            for(Button button : majButtons){
                                button.setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #e6e6e6; -fx-border-width: 1px; -fx-pref-width: 30px; -fx-text-fill:#2132B0");
                            }
                            for(Button button : miButtons){
                                button.setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #e6e6e6; -fx-border-width: 1px; -fx-pref-width: 30px; -fx-text-fill:#2132B0");
                            }
                            turnOn = false;
                        }
                        else{
                            //Pressing another key will turn off the chord that is currently playing.
                            channel[4].allNotesOff();
                            note = event.getCode().getName();

                            for(Button button : majButtons){
                                button.setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #e6e6e6; -fx-border-width: 1px; -fx-pref-width: 30px; -fx-text-fill:#2132B0");
                            }
                            for(Button button : miButtons){
                                button.setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #e6e6e6; -fx-border-width: 1px; -fx-pref-width: 30px; -fx-text-fill:#2132B0");
                            }
                            count1++;
                        }
                    }else{
                        note = event.getCode().getName();
                        turnOn = true;
                        count1++;
                    }
                }
                if(turnOn == true && !(count1 >= 2)){
                    count1++;

                    //Create the major and minor chords (a chord consists of 3 notes).
                    try{
                        channel[4].controlChange(7,(int) volume1);

                        //------------------Major Chords-------------------------

                        if(note.equalsIgnoreCase("q")){
                            channel[4].noteOn(60, velocity);
                            channel[4].noteOn(64, velocity);
                            channel[4].noteOn(67, velocity);

                            majButtons.get(0).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("w")){
                            channel[4].noteOn(62, velocity);
                            channel[4].noteOn(66, velocity);
                            channel[4].noteOn(69, velocity);

                            majButtons.get(1).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("e")){
                            channel[4].noteOn(64, velocity);
                            channel[4].noteOn(68, velocity);
                            channel[4].noteOn(71, velocity);

                            majButtons.get(2).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("r")){
                            channel[4].noteOn(65, velocity);
                            channel[4].noteOn(69, velocity);
                            channel[4].noteOn(60, velocity);

                            majButtons.get(3).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("t")){
                            channel[4].noteOn(67, velocity);
                            channel[4].noteOn(71, velocity);
                            channel[4].noteOn(62, velocity);

                            majButtons.get(4).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("y")){
                            channel[4].noteOn(69, velocity);
                            channel[4].noteOn(61, velocity);
                            channel[4].noteOn(64, velocity);

                            majButtons.get(5).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("u")){
                            channel[4].noteOn(71, velocity);
                            channel[4].noteOn(63, velocity);
                            channel[4].noteOn(66, velocity);

                            majButtons.get(6).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }

                        //------------------Minor Chords-------------------------

                        else if(note.equalsIgnoreCase("a")){
                            channel[4].noteOn(60, velocity);
                            channel[4].noteOn(63, velocity);
                            channel[4].noteOn(67, velocity);

                            miButtons.get(0).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("s")){
                            channel[4].noteOn(62, velocity);
                            channel[4].noteOn(65, velocity);
                            channel[4].noteOn(69, velocity);

                            miButtons.get(1).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("d")){
                            channel[4].noteOn(64, velocity);
                            channel[4].noteOn(67, velocity);
                            channel[4].noteOn(71, velocity);

                            miButtons.get(2).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("f")){
                            channel[4].noteOn(65, velocity);
                            channel[4].noteOn(68, velocity);
                            channel[4].noteOn(60, velocity);

                            miButtons.get(3).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("g")){
                            channel[4].noteOn(67, velocity);
                            channel[4].noteOn(66, velocity);
                            channel[4].noteOn(62, velocity);

                            miButtons.get(4).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("h")){
                            channel[4].noteOn(69, velocity);
                            channel[4].noteOn(60, velocity);
                            channel[4].noteOn(64, velocity);

                            miButtons.get(5).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                        else if(note.equalsIgnoreCase("j")){
                            channel[4].noteOn(71, velocity);
                            channel[4].noteOn(62, velocity);
                            channel[4].noteOn(66, velocity);

                            miButtons.get(6).setStyle("-fx-border-color: #1a1a1a; -fx-background-color: #cccccc; -fx-border-width: 1px; -fx-pref-width: 30px;");
                        }
                    }
                    catch(Exception e){
                    }
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                //Count is set back to 0 when the key is released.
                if(event.getCode() == KeyCode.Q || event.getCode() == KeyCode.W ||
                        event.getCode() == KeyCode.E || event.getCode() == KeyCode.R || event.getCode() == KeyCode.T ||
                        event.getCode() == KeyCode.A || event.getCode() == KeyCode.S ||
                        event.getCode() == KeyCode.D || event.getCode() == KeyCode.F || event.getCode() == KeyCode.G
                        || event.getCode() == KeyCode.Y || event.getCode() == KeyCode.U || event.getCode() == KeyCode.H || event.getCode() == KeyCode.J){
                    count1 = 0;
                }
            }
        });
    }

    //Play a specific note based on where the cursor is dragged or clicked on the strumplate.
    public void rectSound (Rectangle rectangle, MouseEvent event, int channelNumber, int noteNumber, Scene scene){

        if(event.getX() >= rectangle.getX() && event.getX() <= (rectangle.getX() + 100) &&
                event.getY() >= (rectangle.getY() - 5) && event.getY() <= (rectangle.getY() + 9)){
            try {
                scene.setCursor(Cursor.HAND);
                channel1[channelNumber].noteOn(noteNumber, 100);
                channel1[channelNumber].controlChange(7,(int) volume2);
                secondVol = true;
            } catch (Exception e) {
            }
        }
    }

    //Strumplate functionality.
    public void moreSounds(Scene scene1){

        final int mouseChannel = 5;
        final Scene scene = scene1;

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                if(event.getX() >= rect.getX() && event.getX() <= (rect.getX() + 100) &&
                        event.getY() >= rect.getY() && event.getY() <= (rect13.getY() + 10) ||
                        event.getX() > 120 && event.getX() < 160 && event.getY() > 220 && event.getY() < 260 ||
                        event.getX() > 200 && event.getX() < 240 && event.getY() > 220 && event.getY() < 260){

                    scene.setCursor(Cursor.HAND);
                }
                else{
                    scene.setCursor(Cursor.CLOSED_HAND);
                }
            }
        });

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

                    int count = 0;
                    int count2 = 0;
                    int count3 = 0;
                    int count4 = 0;
                    int count5 = 0;
                    int count6 = 0;
                    int count7 = 0;
                    int count8 = 0;
                    int count9 = 0;
                    int count10 = 0;
                    int count11 = 0;
                    int count12 = 0;
                    int count13 = 0;
                    int count14 = 0;

                    public void handle(MouseEvent event) {

                        //A musical note is played when the cursor is dragged over any one of the rectangles in the strumplate. An
                        //int variable is used to prevent a note from repeatedly being played when the cursor is dragged around on
                        //the rectangle. So a note can only be played when the value of the count variable is less than 1. The value
                        //of the count variable goes back to 0 when the mouse click is released or when the cursor is dragged outside
                        //of specific coordinates around the rectangle.
                        if(!(count >= 1)){
                            rectSound(rect, event, mouseChannel, 60, scene);
                            count++;
                        }
                        if(event.getX() < rect.getX() || event.getX() > (rect.getX() + 100) ||
                                event.getY() < (rect.getY() - 5) || event.getY() > (rect.getY() + 9)){
                            count = 0;
                        }

                        if(!(count2 >= 1)){
                            rectSound(rect1, event, mouseChannel, 62, scene);
                            count2++;
                        }
                        if(event.getX() < rect1.getX() || event.getX() > (rect1.getX() + 100) ||
                                event.getY() < (rect1.getY() - 5) || event.getY() > (rect1.getY() + 9)){
                            count2 = 0;
                        }

                        if(!(count3 >= 1)){
                            rectSound(rect2, event, mouseChannel,64, scene);
                            count3++;
                        }
                        if(event.getX() < rect2.getX() || event.getX() > (rect2.getX() + 100) ||
                                event.getY() < (rect2.getY() - 5) || event.getY() > (rect2.getY() + 9)){
                            count3 = 0;
                        }

                        if(!(count4 >= 1)){
                            rectSound(rect3, event, mouseChannel,65, scene);
                            count4++;
                        }
                        if(event.getX() < rect3.getX() || event.getX() > (rect3.getX() + 100) ||
                                event.getY() < (rect3.getY() - 5) || event.getY() > (rect3.getY() + 9)){
                            count4 = 0;
                        }

                        if(!(count5 >= 1)){
                            rectSound(rect4, event, mouseChannel,67, scene);
                            count5++;
                        }
                        if(event.getX() < rect4.getX() || event.getX() > (rect4.getX() + 100) ||
                                event.getY() < (rect4.getY() - 5) || event.getY() > (rect4.getY() + 9)){
                            count5 = 0;
                        }

                        if(!(count6 >= 1)){
                            rectSound(rect5, event, mouseChannel,69, scene);
                            count6++;
                        }
                        if(event.getX() < rect5.getX() || event.getX() > (rect5.getX() + 100) ||
                                event.getY() < (rect5.getY() - 5) || event.getY() > (rect5.getY() + 9)){
                            count6 = 0;
                        }

                        if(!(count7 >= 1)){
                            rectSound(rect6, event, mouseChannel,71, scene);
                            count7++;
                        }
                        if(event.getX() < rect6.getX() || event.getX() > (rect6.getX() + 100) ||
                                event.getY() < (rect6.getY() - 5) || event.getY() > (rect6.getY() + 9)){
                            count7 = 0;
                        }

                        if(!(count8 >= 1)){
                            rectSound(rect7, event, mouseChannel,72, scene);
                            count8++;
                        }
                        if(event.getX() < rect7.getX() || event.getX() > (rect7.getX() + 100) ||
                                event.getY() < (rect7.getY() - 5) || event.getY() > (rect7.getY() + 9)){
                            count8 = 0;
                        }

                        if(!(count9 >= 1)){
                            rectSound(rect8, event, mouseChannel, 74, scene);
                            count9++;
                        }
                        if(event.getX() < rect8.getX() || event.getX() > (rect8.getX() + 100) ||
                                event.getY() < (rect8.getY() - 5) || event.getY() > (rect8.getY() + 9)){
                            count9 = 0;
                        }

                        if(!(count10 >= 1)){
                            rectSound(rect9, event, mouseChannel, 76, scene);
                            count10++;
                        }
                        if(event.getX() < rect9.getX() || event.getX() > (rect9.getX() + 100) ||
                                event.getY() < (rect9.getY() - 5) || event.getY() > (rect9.getY() + 9)){
                            count10 = 0;
                        }

                        if(!(count11 >= 1)){
                            rectSound(rect10, event, mouseChannel, 77, scene);
                            count11++;
                        }
                        if(event.getX() < rect10.getX() || event.getX() > (rect10.getX() + 100) ||
                                event.getY() < (rect10.getY() - 5) || event.getY() > (rect10.getY() + 9)){
                            count11 = 0;
                        }

                        if(!(count12 >= 1)){
                            rectSound(rect11, event, mouseChannel, 77, scene);
                            count12++;
                        }
                        if(event.getX() < rect11.getX() || event.getX() > (rect11.getX() + 100) ||
                                event.getY() < (rect11.getY() - 5) || event.getY() > (rect11.getY() + 9)){
                            count12 = 0;
                        }

                        if(!(count13 >= 1)){
                            rectSound(rect12, event, mouseChannel, 77, scene);
                            count13++;
                        }
                        if(event.getX() < rect12.getX() || event.getX() > (rect12.getX() + 100) ||
                                event.getY() < (rect12.getY() - 5) || event.getY() > (rect12.getY() + 9)){
                            count13 = 0;
                        }

                        if(!(count14 >= 1)){
                            rectSound(rect13, event, mouseChannel, 77, scene);
                            count14++;
                        }
                        if(event.getX() < rect13.getX() || event.getX() > (rect13.getX() + 100) ||
                                event.getY() < (rect13.getY() - 5)|| event.getY() > (rect13.getY() + 9)){
                            count14 = 0;
                        }
                    }
                });

                scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if(event.isPrimaryButtonDown() == true){

                            //A musical note is played when the strumplate is clicked on.
                            rectSound (rect, event, mouseChannel, 60, scene);
                            rectSound (rect1, event, mouseChannel,62, scene);
                            rectSound (rect2, event, mouseChannel,64, scene);
                            rectSound (rect3, event, mouseChannel,65, scene);
                            rectSound (rect4, event, mouseChannel,67, scene);
                            rectSound (rect5, event, mouseChannel,69, scene);
                            rectSound (rect6, event, mouseChannel,71, scene);
                            rectSound (rect7, event, mouseChannel,72, scene);
                            rectSound (rect8, event, mouseChannel, 74, scene);
                            rectSound (rect9, event, mouseChannel, 76, scene);
                            rectSound (rect10, event, mouseChannel, 77, scene);
                            rectSound (rect11, event, mouseChannel, 79, scene);
                            rectSound (rect12, event, mouseChannel, 81, scene);
                            rectSound (rect13, event, mouseChannel, 83, scene);
                        }
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
    }
}
