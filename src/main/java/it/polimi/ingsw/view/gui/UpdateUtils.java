package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.reduced.ReducedCharacterCard;
import it.polimi.ingsw.model.reduced.ReducedIsland;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class UpdateUtils {
    public final static Image islandImage = new Image("images/table/islands/island2.png");
    public final static Image noEntryTileImage = new Image("images/table/noEntry.png");
    public final static Image schoolDashboardImage = new Image("images/table/schoolDashboard.png");
    public final static Image redStudentImage = new Image("images/table/students/redStudent3D.png");
    public final static Image greenStudentImage = new Image("images/table/students/greenStudent3D.png");
    public final static Image yellowStudentImage = new Image("images/table/students/yellowStudent3D.png");
    public final static Image pinkStudentImage = new Image("images/table/students/pinkStudent3D.png");
    public final static Image blueStudentImage = new Image("images/table/students/blueStudent3D.png");
    public final static Image redProfImage = new Image("images/table/professors/red.png");
    public final static Image greenProfImage = new Image("images/table/professors/green.png");
    public final static Image yellowProfImage = new Image("images/table/professors/yellow.png");
    public final static Image pinkProfImage = new Image("images/table/professors/pink.png");
    public final static Image blueProfImage = new Image("images/table/professors/blue.png");
    public final static Image cloudCard3StudentsImage = new Image("/images/table/cloudCards/cloud3.png");
    public final static Image cloudCard4StudentsImage = new Image("/images/table/cloudCards/cloud4.png");
    public final static Image blackTowerImage = new Image("/images/table/towers/black.png");
    public final static Image greyTowerImage = new Image("/images/table/towers/grey.png");
    public final static Image whiteTowerImage = new Image("/images/table/towers/white.png");

    public final static Image allRemoveColorImage = new Image("/images/table/characterCards/AllRemoveColor.jpg");
    public final static Image blockColorOnceImage = new Image("/images/table/characterCards/BlockColorOnce.jpg");
    public final static Image blockTowerImage = new Image("/images/table/characterCards/BlockTower.jpg");
    public final static Image choose1DiningRoomImage = new Image("/images/table/characterCards/Choose1DiningRoom.jpg");
    public final static Image choose1ToIslandImage = new Image("/images/table/characterCards/Choose1ToIsland.jpg");
    public final static Image choose3toEntranceImage = new Image("/images/table/characterCards/Choose3toEntrance.jpg");
    public final static Image chooseIslandImage = new Image("/images/table/characterCards/ChooseIsland.jpg");
    public final static Image exchange2StudentsImage = new Image("/images/table/characterCards/Exchange2Students.jpg");
    public final static Image noEntryIslandImage = new Image("/images/table/characterCards/NoEntryIsland.jpg");
    public final static Image plus2InfluenceImage = new Image("/images/table/characterCards/Plus2Influence.jpg");
    public final static Image tempControlProfImage = new Image("/images/table/characterCards/TempControlProf.jpg");
    public final static Image twoAdditionalMovesImage = new Image("/images/table/characterCards/TwoAdditionalMoves.jpg");

    public final static List<Image> assistantImages = Arrays.asList(
        new Image("/images/table/assistantCards/1.png"),
        new Image("/images/table/assistantCards/2.png"),
        new Image("/images/table/assistantCards/3.png"),
        new Image("/images/table/assistantCards/4.png"),
        new Image("/images/table/assistantCards/5.png"),
        new Image("/images/table/assistantCards/6.png"),
        new Image("/images/table/assistantCards/7.png"),
        new Image("/images/table/assistantCards/8.png"),
        new Image("/images/table/assistantCards/9.png"),
        new Image("/images/table/assistantCards/10.png")
    );

    public static ImageView islandImageView(int islandIndex, EventHandler<MouseEvent> eventEventHandler) {
        ImageView imageView = new ImageView(islandImage);
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(150.0);
        imageView.setUserData(islandIndex);
        imageView.setOnMouseClicked(eventEventHandler);

        return imageView;
    }

    public static ImageView noEntryImageView(double size) {
        ImageView imageView = new ImageView(noEntryTileImage);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        return imageView;
    }

    public static Image assistantImage(int priority) {
        return assistantImages.get(priority - 1);
    }

    public static Image studentImage(Color c) {
        switch (c) {
            case GREEN -> {return greenStudentImage;}
            case RED -> {return redStudentImage;}
            case YELLOW -> {return yellowStudentImage;}
            case PINK -> {return pinkStudentImage;}
            case BLUE -> {return blueStudentImage;}
            default -> throw new RuntimeException("Unexpected color");
        }
    }

    public static ImageView studentImageView(Color c, double size) {
        ImageView imageView = new ImageView(studentImage(c));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        return imageView;
    }

    public static Image profImage(Color c) {
        switch (c) {
            case GREEN -> {return greenProfImage;}
            case RED -> {return redProfImage;}
            case YELLOW -> {return yellowProfImage;}
            case PINK -> {return pinkProfImage;}
            case BLUE -> {return blueProfImage;}
            default -> throw new RuntimeException("Unexpected color");
        }
    }

    public static ImageView profImageView(Color c, double scale) {
        ImageView res = new ImageView(profImage(c));
        res.setFitWidth(35.0 * scale);
        res.setFitHeight(30.31 * scale);

        return res;
    }

    public static ImageView cloudCardImageView(int playersCount) {
        Image image = null;
        if (playersCount == 2)
            image = cloudCard3StudentsImage;
        else if (playersCount == 3)
            image = cloudCard4StudentsImage;

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100.0);
        imageView.setFitHeight(100.0);
        return imageView;
    }

    public static Image towerImage(TowerColor towerColor) {
        switch (towerColor) {
            case BLACK -> { return blackTowerImage;}
            case GREY -> { return greyTowerImage;}
            case WHITE -> { return whiteTowerImage;}
            default -> throw new RuntimeException("Unexpected color");
        }
    }

    public static ImageView towerImageView(TowerColor towerColor, double size) {
        ImageView towerImageView = new ImageView(towerImage(towerColor));
        towerImageView.setLayoutX(90.0);
        towerImageView.setLayoutY(80.0);
        towerImageView.setFitHeight(size);
        towerImageView.setFitWidth(size);

        return towerImageView;
    }

    public static Text counterText(int number, Paint paint) {
        Text text = new Text(number + "");
        text.setFill(paint);
        return text;
    }

    public static Text islandText(int number) {
        return counterText(number, Paint.valueOf("WHITE"));
    }

    public static ImageView characterCardImageView(ReducedCharacterCard card) {
        String imagePath = "images/table/characterCards/" + card.getName() + ".jpg";
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(114.0);
        imageView.setFitHeight(174.0);

        return imageView;
    }

    /**
     * Attaches an ImageView and a Text to describe the quantity of students to the given AnchorPane.
     * @param anchorPane AnchorPane to attach to.
     * @param color color of the student to attach.
     * @param value number of students.
     * @param x layoutX to use to attach.
     * @param y layoutY to use to attach.
     * @param paint color to use for the Text.
     */
    public static void attachStudentWithCounter(AnchorPane anchorPane, Color color, int value, double x, double y, Paint paint, String id) {
        ImageView imageView = studentImageView(color, 20.0);
        imageView.setId(id);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        anchorPane.getChildren().add(imageView);

        Text numberText = counterText(value, paint);
        numberText.setLayoutX(x + 20);
        numberText.setLayoutY(y + 20);
        anchorPane.getChildren().add(numberText);
    }




}
