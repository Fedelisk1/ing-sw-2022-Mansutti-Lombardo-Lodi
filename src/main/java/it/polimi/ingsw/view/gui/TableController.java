package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedIsland;
import it.polimi.ingsw.model.reduced.ReducedSchoolDashboard;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class TableController extends ViewObservable implements Initializable {
    public GridPane handGridPane;
    public ImageView currentPlayerAssistant;
    public VBox cloudCardsVBox;
    public HBox unudedProfHBox;
    private ReducedGame game;
    private Phase phase;
    private Color actionPhaseSelectedColor;
    public Text promptText;
    public Button playButton;
    public HBox charactersHBox;
    public HBox islandsHBox1;
    public HBox islandsHBox2;
    public AnchorPane currentPlayerSchoolDashboardAP;
    public AnchorPane schoolDashboardAP2;
    public AnchorPane schoolDashboardAP3;
    public HBox topHBox;
    private ObservableMap<Integer, Integer> hand;
    private ObservableList<AssistantCard> handOL;
    private List<Integer> alreadyPlayedCards;
    private GuiManager guiManager;
    private int maxMNSteps;
    private Integer chosenAssisant;
    private final List<Button> handButtons = new ArrayList<>();
    private List<Integer> allowedClodCards = new ArrayList<>();

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public void handlePlayButton(ActionEvent actionEvent) {
        if(chosenAssisant == null)
            return;

        notifyObservers(o -> o.onAssistantCardChosen(chosenAssisant));

        handButtons.stream().filter(hb -> hb.getUserData() == chosenAssisant).findFirst().ifPresent(hb -> {
            hb.getStyleClass().add("chosen");
        });

        // show the chosen assistant
        currentPlayerAssistant.setImage(ImagesUtil.assistantImages.get(chosenAssisant - 1));

        playButton.setDisable(true);
        chosenAssisant = null;
    }

    public void askActionPhase1(int count) {
        phase = Phase.ACTION_1;
        showMessage("Action phase 1 - move " + count + ". Please, select a student from the entrance");
    }

    public void askActionPhase2(int maxMNStpes) {
        phase = Phase.ACTION_2;
        this.maxMNSteps = maxMNStpes;
        showMessage("Action phase 2: please select the island to move MN to (max " + maxMNStpes + " step(s) allowed).");
    }

    public void askActionPhase3(List<Integer> alloweValues) {
        phase = Phase.ACTION_3;
        allowedClodCards = alloweValues;
        showMessage("Action phase 3: please select a cloud Card to refill your School Dashboard's entrance.");
    }

    public void showPlayedAssistant(String player, int card) {
        ImageView assistantIV = (ImageView) Gui.getStage().getScene().lookup("#assistantIV" + player);
        assistantIV.setImage(ImagesUtil.assistantImages.get(card - 1));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handGridPane.getChildren().clear();

        //handListView.setItems(handOL);
        //handListView.setCellFactory((a) -> new AssistantListCell());
    }

    public void update(ReducedGame game) {
        this.game = game;
        /*if (game.isExpert()) {
            int i = 0;
            for (ReducedCharacterCard rc : game.getCharacterCards()) {
                Button characterButton = new Button();
                characterButton.setPrefHeight(208.0);
                characterButton.setPrefWidth(120.0);
                int cardIndex = i;
                characterButton.setOnAction((actionEvent) -> notifyObservers(o -> o.onCCChosen(cardIndex)));
                characterButton.setId("character" + cardIndex + "Button");
                characterButton.setDisable(true);

                Image characterImage = new Image("/images/table/characterCards/" + rc.getName() + ".jpg");
                ImageView characterIV = new ImageView(characterImage);
                characterIV.setFitWidth(114.0);
                characterIV.setFitHeight(174.0);

                Pane charcaterPane = new Pane();
                charcaterPane.setPrefHeight(208.0);
                charcaterPane.setPrefWidth(120.0);
                charcaterPane.getChildren().add(characterIV);

                if (rc.getStudents().size() > 0) {
                    GridPane characterGP = new GridPane();
                    characterGP.getColumnConstraints().add(new ColumnConstraints(60));
                    characterGP.getColumnConstraints().add(new ColumnConstraints(60));
                    characterGP.getRowConstraints().add(new RowConstraints(33));
                    characterGP.getRowConstraints().add(new RowConstraints(33));
                    characterGP.getRowConstraints().add(new RowConstraints(33));

                    characterGP.setLayoutX(0.0);
                    characterGP.setLayoutY(105.0);
                    characterGP.setId("character" + rc.getName() + "gp");

                    int j = 0;
                    for (Color c : rc.getStudents().keySet()) {
                        for (int k = 0; k < rc.getStudents().get(c); k++) {
                            characterGP.add(studentImage(c), j % 3, j % 2);
                            j++;
                        }
                    }

                    charcaterPane.getChildren().add(characterGP);
                }


                characterButton.setGraphic(charcaterPane);
                charactersHBox.getChildren().add(characterButton);

                i++;
            }
        }*/

        // update islands
        Platform.runLater(() -> {
            islandsHBox1.getChildren().clear();
            islandsHBox2.getChildren().clear();
            int i = 0;
            for (ReducedIsland island : game.getIslands()) {
                int finalI = i;
                Platform.runLater(() -> {
                    if (finalI < game.getIslands().size() / 2)
                        addIsland(island, finalI, islandsHBox1);
                    else
                        addIsland(island, finalI, islandsHBox2);
                });
                i++;
            }

            // reverse the order of the islands in the second line
            long startMillis = System.currentTimeMillis();
            ObservableList<Node> islandsHBox2Children = FXCollections.observableArrayList(islandsHBox2.getChildren());
            FXCollections.reverse(islandsHBox2Children);
            islandsHBox2.getChildren().setAll(islandsHBox2Children);
            long endMillis = System.currentTimeMillis();
            System.out.println("reverse order took " + (endMillis-startMillis));
        });

        // update cloud cards
        Platform.runLater(() -> {
            long startMillis = System.currentTimeMillis();
            cloudCardsVBox.getChildren().clear();

            Image ccImage;
            List<Double> layoutXs;
            List<Double> layoutYs;

            if (game.getCloudCards().size() == 2) {
                long startMillis1 = System.currentTimeMillis();
                ccImage = ImagesUtil.cloudCard3StudentsImage;
                long endMillis1 = System.currentTimeMillis();
                System.out.println("ccImage init took " + (endMillis1-startMillis1));
                layoutXs = Arrays.asList(11.5, 62.0, 50.0);
                layoutYs = Arrays.asList(33.5, 19.0, 72.0);
            } else {
                ccImage = ImagesUtil.cloudCard4StudentsImage;
                layoutXs = Arrays.asList(9.0, 54.0, 71.0, 33.0);
                layoutYs = Arrays.asList(33.0, 16.0, 54.0, 73.0);
            }

            int ccIndex = 1;
            for (CloudCard cc : game.getCloudCards()) {
                long startMillis1 = System.currentTimeMillis();
                AnchorPane ap = new AnchorPane();

                ImageView ccImageView = new ImageView(ccImage);
                ccImageView.setFitWidth(100.0);
                ccImageView.setFitHeight(100.0);
                ccImageView.setUserData(ccIndex);
                ccImageView.setOnMouseClicked(this::onCloudCardClicked);

                // cc image
                ap.getChildren().add(ccImageView);

                // students

                int i = 0;
                for (Color c : Color.values()) {
                    if (cc.getStudents().getOrDefault(c, 0) > 0) {
                        for (int j = 0; j < cc.getStudents().get(c); j++) {
                            ImageView iv = studentImage(c);
                            iv.setLayoutX(layoutXs.get(i));
                            iv.setLayoutY(layoutYs.get(i));
                            ap.getChildren().add(iv);

                            i++;
                        }
                    }
                }
                long endMillis1 = System.currentTimeMillis();
                System.out.println("add students to cc took " + (endMillis1-startMillis1));

                cloudCardsVBox.getChildren().add(ap);

                ccIndex++;
            }
            long endMillis = System.currentTimeMillis();
            System.out.println("update cc took " + (endMillis-startMillis));
        });

        // update schoolDashboards
        Platform.runLater(() -> {
            long startMillis = System.currentTimeMillis();

            VBox mockup = (VBox) topHBox.lookup("#mockupPlayerVBox");
            if (mockup != null)
                topHBox.getChildren().clear();

            AnchorPane destAP;
            List<Double> entranceLayoutsX = Arrays.asList(56.0, 21.5, 56.0, 21.5, 56.0, 21.5, 56.0, 21.5, 56.0);
            List<Double> entranceLayoutsY = Arrays.asList(34.5, 76.0, 76.0, 117.0, 117.0, 158.0, 158.0, 198.0, 198.0);;
            double drLayoutXStart = 110.0;
            double drLayoutXDelta = 27.5;
            List<Double> drLayoutsY = Arrays.asList(34.5, 76.0, 117.0, 158.0, 198.0);
            double scale;

            double profBaseLayoutY = 29.0;
            double profLayoutYDelta = 41.0;
            double profLayoutX = 403.0;


            for (String nick : game.getSchoolDashboards().keySet()) {
                if (nick.equals(guiManager.getNickname())) {
                    destAP = currentPlayerSchoolDashboardAP;
                    destAP.getChildren().clear();
                    ImageView sdIV = new ImageView(ImagesUtil.schoolDashboardImage);
                    sdIV.setFitWidth(577.0);
                    sdIV.setFitHeight(250.0);
                    sdIV.setLayoutX(0.0);
                    sdIV.setLayoutY(0.0);
                    destAP.setMaxWidth(577.0);
                    destAP.setMaxHeight(250.0);
                    destAP.getChildren().add(sdIV);

                    entranceLayoutsX = Arrays.asList(56.0, 21.5, 56.0, 21.5, 56.0, 21.5, 56.0, 21.5, 56.0);
                    entranceLayoutsY = Arrays.asList(34.5, 76.0, 76.0, 117.0, 117.0, 158.0, 158.0, 198.0, 198.0);
                    drLayoutXStart = 110.0;
                    drLayoutXDelta = 27.5;
                    drLayoutsY = Arrays.asList(34.5, 76.0, 117.0, 158.0, 198.0);
                    scale = 1.0;

                    profBaseLayoutY = 29.0;
                    profLayoutYDelta = 41.0;
                    profLayoutX = 403.0;
                } else
                {
                    VBox vBox = (VBox) topHBox.lookup("#" + nick + "VBox");
                    AnchorPane ap; // school dashboard AnchorPane

                    if (vBox == null) { // if the player's VBox han never been created, create it
                        vBox = new VBox();
                        vBox.setId(nick + "VBox"); // and assign it an id for future reference
                        vBox.setAlignment(Pos.CENTER);
                        vBox.setSpacing(10.0);
                        topHBox.getChildren().add(vBox);

                        Text nickText = new Text(nick);
                        vBox.getChildren().add(nickText);

                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER);
                        hBox.setSpacing(10.0);
                        vBox.getChildren().add(hBox);

                        ImageView playedAssistantIV = new ImageView();
                        playedAssistantIV.setId("assistantIV" + nick);
                        playedAssistantIV.setFitHeight(144.0);
                        playedAssistantIV.setFitWidth(98.0);
                        hBox.getChildren().add(playedAssistantIV);
                        ap = new AnchorPane();
                        hBox.getChildren().add(ap);

                        ap.setId("schoolDashboardAP" + nick);
                    } else {
                        ap = (AnchorPane) topHBox.lookup("#schoolDashboardAP" + nick);
                    }

                    ImageView sdIV = new ImageView(ImagesUtil.schoolDashboardImage);
                    sdIV.setLayoutX(0.0);
                    sdIV.setLayoutY(0.0);
                    sdIV.setFitWidth(404.0);
                    sdIV.setFitHeight(175.0);
                    ap.setMaxWidth(404.0);
                    ap.setMaxHeight(175.0);
                    ap.getChildren().add(sdIV);

                    destAP = ap;
                    entranceLayoutsX = Arrays.asList(56.0 * .7, 21.5 * .7, 56.0 * .7, 21.5 * .7, 56.0 * .7, 21.5 * .7, 56.0 * .7, 21.5 * .7, 56.0 * .7);
                    entranceLayoutsY = Arrays.asList(34.5 * .7, 76.0 * .7, 76.0 * .7, 117.0 * .7, 117.0 * .7, 158.0 * .7, 158.0 * .7, 198.0 * .7, 198.0 * .7);
                    drLayoutXStart = 110.0 * .7;
                    drLayoutXDelta = 27.5 * .7;
                    drLayoutsY = Arrays.asList(34.5 * .7, 76.0 * .7, 117.0 * .7, 158.0 * .7, 198.0 * .7);
                    scale = 0.7;
                }

                ReducedSchoolDashboard sd = game.getSchoolDashboards().get(nick);
                long startMillis1 = System.currentTimeMillis();

                // fill entrance
                int i = 0;
                for (Color c : Color.values()) {
                    for (int j = 0; j < sd.getEntrance().getOrDefault(c, 0); j++) {
                        ImageView iv = studentImage(c, scale);
                        iv.setLayoutX(entranceLayoutsX.get(i));
                        iv.setLayoutY(entranceLayoutsY.get(i));
                        if (nick.equals(guiManager.getNickname())) {
                            iv.setUserData(c);
                            iv.setOnMouseClicked(this::onEntranceStudentClicked);
                        }
                        destAP.getChildren().add(iv);

                        i++;
                    }
                }

                long endMillis1 = System.currentTimeMillis();
                System.out.println("fill entrance sd took " + (endMillis1-startMillis1));

                // fill dr
                for (Color c : Color.values()) {
                    i = 0;
                    for (int j = 0; j < sd.getDiningRoom().getOrDefault(c, 0); j++) {
                        ImageView iv = studentImage(c, scale);
                        iv.setLayoutX(drLayoutXStart + i * drLayoutXDelta);
                        iv.setLayoutY(drLayoutsY.get(c.ordinal()));
                        destAP.getChildren().add(iv);

                        i++;
                    }
                }

                // fill professors

                for (Color c : sd.getProfessors()) {
                    ImageView profIV = ImagesUtil.profImageView(c, scale);
                    profIV.setLayoutX(profLayoutX * scale);
                    profIV.setLayoutY(profBaseLayoutY * scale + c.ordinal() * profLayoutYDelta * scale);

                    destAP.getChildren().add(profIV);
                }
            }

            long endMillis = System.currentTimeMillis();
            System.out.println("update sd took " + (endMillis-startMillis));
        });

        // update unused professors
        Platform.runLater(() -> {
            unudedProfHBox.getChildren().clear();

            for (Color c : game.getUnusedProfessors()) {
                ImageView profIV = ImagesUtil.profImageView(c, 1.0);
                unudedProfHBox.getChildren().add(profIV);
            }
        });
    }

    /**
     * To be performed when a student in the entrance of the current player's dashboard gets clicked.
     * @param event
     */
    private void onEntranceStudentClicked(MouseEvent event) {
        if (phase.equals(Phase.ACTION_1) && actionPhaseSelectedColor == null) {
            ImageView source = (ImageView) event.getSource();
            Color c = (Color) source.getUserData();
            actionPhaseSelectedColor = c;
            showMessage("Select destination for the " + c.toString().toLowerCase() + " student selected");
        }
    }

    /**
     * To be performed when the current player's dashboard gets clicked.
     * @param mouseEvent
     */
    public void onSchoolDashboardClick(MouseEvent mouseEvent) {
        if (phase.equals(Phase.ACTION_1) && actionPhaseSelectedColor != null) {
            if (mouseEvent.getX() > 110.0) {
                notifyObservers(o -> o.onStudentMovedToDiningRoom(actionPhaseSelectedColor));
                actionPhaseSelectedColor = null;
            }
        }
    }

    /**
     * To be performed when an island gets clicked.
     * @param mouseEvent
     */
    private void onIslandClicked(MouseEvent mouseEvent) {
        System.out.println("island clicked");
        ImageView source = (ImageView) mouseEvent.getSource();
        int islandIndex = (int) source.getUserData();

        if (phase == Phase.ACTION_1  && actionPhaseSelectedColor != null) {
            notifyObservers(o -> o.onStudentMovedToIsland(islandIndex + 1, actionPhaseSelectedColor));
            actionPhaseSelectedColor = null;
        } else if (phase == Phase.ACTION_2) {
            int requestedSteps = (islandIndex - game.getMNPosition()) % game.getIslands().size();

            if (islandIndex < game.getMNPosition())
                requestedSteps = islandIndex + game.getIslands().size() - game.getMNPosition();

            if (requestedSteps <= maxMNSteps && requestedSteps >= 1) {
                int finalRequestedSteps = requestedSteps;
                notifyObservers(o -> o.onMotherNatureMoved(finalRequestedSteps));
            }
        }
    }

    /**
     * To be performed when the user clicks on a cloud Card.
     * @param event
     */
    private void onCloudCardClicked(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        int ccIndex = (int) source.getUserData();
        if (phase == Phase.ACTION_3) {
            if (allowedClodCards.contains(ccIndex))
                notifyObservers(o -> o.onCloudCardChosen(ccIndex));
        }
    }

    private void addIsland(ReducedIsland island, int islandIndex, HBox hBox) {
        long startMillis = System.currentTimeMillis();
        AnchorPane ap = new AnchorPane();
        hBox.getChildren().add(ap);

        // island img
        ImageView islandIV = new ImageView(ImagesUtil.islandImage);
        islandIV.setFitHeight(150.0);
        islandIV.setFitWidth(150.0);
        islandIV.setUserData(islandIndex);
        islandIV.setOnMouseClicked(this::onIslandClicked);
        ap.getChildren().add(islandIV);

        // island index
//        Text indexText = new Text(islandIndex + "");
//        indexText.setLayoutX(0.0);
//        indexText.setLayoutY(0.0);
//        ap.getChildren().add(indexText);

        // students images
        List<Double> layoutXs = Arrays.asList(39.0, 85.0, 24.0, 64.0, 107.0);
        List<Double> layoutYs = Arrays.asList(24.0, 24.0, 57.0, 57.0, 57.0);
        int i = 0;
        for (Color c : Color.values()) {
            if (island.getStudents().getOrDefault(c, 0) == 0)
                continue;

            ImageView colIV = new ImageView(new Image("images/table/students/" + c.toString().toLowerCase() + "Student3D.png"));
            colIV.setFitWidth(20.0);
            colIV.setFitHeight(20.0);
            colIV.setLayoutX(layoutXs.get(i));
            colIV.setLayoutY(layoutYs.get(i));
            colIV.setId("student_" + c.toString().toLowerCase() + "_" + islandIndex);

            ap.getChildren().add(colIV);

            Text numberText = new Text(island.getStudents().get(c) + "");
            numberText.setLayoutX(layoutXs.get(i) + 20);
            numberText.setLayoutY(layoutYs.get(i) + 20);
            numberText.setFill(Paint.valueOf("WHITE"));

            ap.getChildren().add(numberText);

            i++;
        }

        if (island.isMotherNature()) {
            ImageView mniv = new ImageView(new Image("images/table/motherNature.png"));
            mniv.setFitHeight(45.0);
            mniv.setFitWidth(45.0);
            mniv.setLayoutX(37.0);
            mniv.setLayoutY(87.0);
            ap.getChildren().add(mniv);
        }

        if (island.getNoEntryTiles() > 0) {
            ImageView noEntryIv = new ImageView(new Image("images/table/noEntry.png"));
            noEntryIv.setFitHeight(45.0);
            noEntryIv.setFitWidth(45.0);
            noEntryIv.setLayoutX(75.0);
            noEntryIv.setLayoutY(87.0);
            ap.getChildren().add(noEntryIv);

            Text noEntryCount = new Text(island.getNoEntryTiles() + "");
            noEntryCount.setLayoutX(120.0);
            noEntryCount.setLayoutY(132.0);
            noEntryCount.setFill(Paint.valueOf("WHITE"));
            ap.getChildren().add(noEntryCount);
        }

        long finalMillis = System.currentTimeMillis();
        System.out.println("island " + islandIndex + " took " + (finalMillis-startMillis));
    }

    private ImageView studentImage(Color c) {
        return studentImage(c, 1.0);
    }

    private ImageView studentImage(Color c, double scale) {
        Image studentImage = ImagesUtil.studentImage(c);

        ImageView iv = new ImageView(studentImage);
        iv.setFitHeight(20.0 * scale);
        iv.setFitWidth(20.0 * scale);
        return iv;
    }

    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        phase = Phase.PLANNING_2;
        showMessage("Please, choose an assistant Card!");
        chosenAssisant = null;

        Platform.runLater(() -> {
            playButton.setDisable(false);
            handGridPane.getChildren().clear();

            handButtons.clear();

            int i = 0, j = 0; // used to determine row and col for handGidPane (row, col)
            for (int assistantPri : hand.keySet()) {
                Button assistantButton = new Button();
                handButtons.add(assistantButton);
                assistantButton.setOnAction((e) -> this.chosenAssisant = assistantPri);
                assistantButton.setUserData(assistantPri);
                assistantButton.setPrefHeight(104.0);
                assistantButton.setPrefWidth(71.0);
                ImageView assistantIV = new ImageView(ImagesUtil.assistantImage(assistantPri));
                assistantIV.setFitHeight(104.0);
                assistantIV.setFitWidth(71.0);
                assistantButton.setGraphic(assistantIV);
                assistantButton.getStyleClass().add("assistantButton");

                if (notPlayable.contains(assistantPri))
                    assistantButton.setDisable(true);

                System.out.println("add in " + j + ", " + i);
                handGridPane.add(assistantButton, j, i);

                j++;
                if (j == handGridPane.getColumnCount()) {
                    i++;
                    j = 0;
                }
            }
        });
    }

    public void showMessage(String content) {
        promptText.setText(content);
    }

    public void showWinnerToOthers(String winnerNick) {
        phase = Phase.END;
        showMessage(winnerNick + " has won. The game ends here.");
    }

    public void notifyWinner() {
        phase = Phase.END;
        showMessage("You won! The game ends here.");
    }
}
