package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagesUtil {
    public final static Image islandImage = new Image("images/table/islands/island2.png");
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

    public static Image assistantImage(int priority) {
        return assistantImages.get(priority - 1);
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

    public static ImageView profImageView(Color c, double scale) {
        ImageView res = new ImageView(profImage(c));
        res.setFitWidth(35.0 * scale);
        res.setFitHeight(30.31 * scale);

        return res;
    }
}
