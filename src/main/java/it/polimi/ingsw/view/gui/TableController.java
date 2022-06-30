package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedCharacterCard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedIsland;
import it.polimi.ingsw.model.reduced.ReducedSchoolDashboard;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Semaphore;

import static it.polimi.ingsw.view.gui.UpdateUtils.*;

public class TableController extends ViewObservable implements Initializable {
    public GridPane handGridPane;
    public ImageView currentPlayerAssistant;
    public VBox cloudCardsVBox;
    public HBox unusedProfHBox;
    public VBox characterCardsVBox;
    public VBox currentPlayerCoins;
    private ReducedGame game;
    private Phase phase;
    private Color actionPhaseSelectedColor;
    public Text promptText;
    public Button playButton;
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
    private Integer chosenAssistant = 1;
    private final List<Button> handButtons = new ArrayList<>();
    private final List<Button> characterCardButtons = new ArrayList<>();
    private List<Integer> allowedClodCards = new ArrayList<>();
    private List<ImageView> islandsImageViews = new ArrayList<>();
    private Set<Integer> activatedCharacterCards = new HashSet<>();
    private List<ImageView> entranceStudents = new ArrayList<>();
    private List<ImageView> diningRoomStudents = new ArrayList<>();
    private Semaphore characterCardUpdateSem;
    private Semaphore diningRoomUpdateSem;


    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public void handlePlayButton(ActionEvent actionEvent) {
        if(chosenAssistant == null)
            return;

        notifyObservers(o -> o.onAssistantCardChosen(chosenAssistant));

        handButtons.stream().filter(hb -> hb.getUserData() == chosenAssistant).findFirst().ifPresent(hb -> {
            hb.getStyleClass().add("chosen");
        });

        // show the chosen assistant
        currentPlayerAssistant.setImage(UpdateUtils.assistantImages.get(chosenAssistant - 1));

        handButtons.forEach(hb -> hb.setOnAction(null));

        playButton.setDisable(true);
        chosenAssistant = null;
    }

    public void askActionPhase1(int count) {
        phase = Phase.ACTION_1;
        showMessage("Action phase 1 - move " + count + ". Please, select a student from the entrance");

        // allow to play character cards
        characterCardButtons.forEach(b -> b.setDisable(false));
    }

    public void askActionPhase2(int maxMNStpes) {
        phase = Phase.ACTION_2;
        this.maxMNSteps = maxMNStpes;
        showMessage("Action phase 2: please select the island to move MN to (max " + maxMNStpes + " step(s) allowed).");

        // allow to play character cards
        characterCardButtons.forEach(b -> b.setDisable(false));
    }

    public void askActionPhase3(List<Integer> alloweValues) {
        phase = Phase.ACTION_3;
        allowedClodCards = alloweValues;
        showMessage("Action phase 3: please select a cloud Card to refill your School Dashboard's entrance.");

        // allow to play character cards
        characterCardButtons.forEach(b -> b.setDisable(false));
    }

    public void showPlayedAssistant(String player, int card) {
        chosenAssistant = null;
        ImageView assistantIV = (ImageView) Gui.getStage().getScene().lookup("#assistantIV" + player);
        assistantIV.setImage(UpdateUtils.assistantImages.get(card - 1));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handGridPane.getChildren().clear();

        characterCardUpdateSem = new Semaphore(0);
        diningRoomUpdateSem = new Semaphore(0);
    }

    public void update(ReducedGame game) {
        this.game = game;

        if (game.isExpert()) {
            Platform.runLater(() -> {
                updateCharacterCards(game);

                System.out.println("update characters done");
                characterCardUpdateSem.release();
            });
        }

        // update islands
        Platform.runLater(() -> updateIslands(game));

        // update cloud cards
        Platform.runLater(() -> {
            updateCloudCards(game);
        });

        // update schoolDashboards and coins
        Platform.runLater(() -> {
            updateSchoolDashboards(game);

            System.out.println("update dr done");
            diningRoomUpdateSem.release();
        });

        // update unused professors
        Platform.runLater(() -> updateUnusedProfessors(game));
    }

    /**
     * Updates the character cards in the view based on the input ReducedGame.
     * @param game updated state of the game.
     */
    private void updateCharacterCards(ReducedGame game) {
        // clear mockup
        characterCardsVBox.getChildren().clear();

        int cardIndex = 0;
        for (ReducedCharacterCard rc : game.getCharacterCards()) {
            AnchorPane characterCardAnchorPane = characterCardAnchorPane(rc, cardIndex, this::handleCharacterButton);
            characterCardsVBox.getChildren().add(characterCardAnchorPane);

            cardIndex++;
        }
    }

    /**
     * Updates the islands in the view based on the input ReducedGame.
     * @param game updated state of the game.
     */
    private void updateIslands(ReducedGame game) {
        islandsHBox1.getChildren().clear();
        islandsHBox2.getChildren().clear();
        int i = 1;

        islandsImageViews.clear();

        for (ReducedIsland island : game.getIslands()) {
            if (i <= game.getIslands().size() / 2)
                addIsland(island, i, islandsHBox1, this::onIslandClicked);
            else
                addIsland(island, i, islandsHBox2, this::onIslandClicked);
            i++;
        }

        // reverse the order of the islands in the second line
        ObservableList<Node> islandsHBox2Children = FXCollections.observableArrayList(islandsHBox2.getChildren());
        FXCollections.reverse(islandsHBox2Children);
        islandsHBox2.getChildren().setAll(islandsHBox2Children);
    }

    /**
     * Updates cloud cards in the view based on the given ReducedGame.
     * @param game updated game state.
     */
    private void updateCloudCards(ReducedGame game) {
        // clear previous content
        cloudCardsVBox.getChildren().clear();

        List<Double> layoutXs; // contains coordinates to place the students on the cloud card
        List<Double> layoutYs;

        if (game.getCloudCards().size() == 2) {
            layoutXs = Arrays.asList(11.5, 62.0, 50.0);
            layoutYs = Arrays.asList(33.5, 19.0, 72.0);
        } else {
            layoutXs = Arrays.asList(9.0, 54.0, 71.0, 33.0);
            layoutYs = Arrays.asList(33.0, 16.0, 54.0, 73.0);
        }

        int ccIndex = 1;
        for (CloudCard cc : game.getCloudCards()) {
            AnchorPane ap = new AnchorPane();
            ap.setMaxWidth(100.0);

            ImageView ccImageView = UpdateUtils.cloudCardImageView(game.getCloudCards().size());
            ccImageView.setUserData(ccIndex);
            ccImageView.setOnMouseClicked(this::onCloudCardClicked);

            // add cc image
            ap.getChildren().add(ccImageView);

            // add students
            int i = 0;
            for (Color c : Color.values()) {
                if (cc.getStudents().getOrDefault(c, 0) > 0) {
                    for (int j = 0; j < cc.getStudents().get(c); j++) {
                        ImageView iv = UpdateUtils.studentImageView(c, 20.0);
                        iv.setLayoutX(layoutXs.get(i));
                        iv.setLayoutY(layoutYs.get(i));
                        ap.getChildren().add(iv);

                        i++;
                    }
                }
            }

            cloudCardsVBox.getChildren().add(ap);

            ccIndex++;
        }
    }

    /**
     * Updates school dashboards based on the given ReducedGame.
     * @param game updated game state.
     */
    private void updateSchoolDashboards(ReducedGame game) {
        // remove mockup from fxml
        VBox mockup = (VBox) topHBox.lookup("#mockupPlayerVBox");

        if (mockup != null)
            topHBox.getChildren().clear();

        entranceStudents.clear();

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

        List<Double> towersLayoutsX = Arrays.asList(454.0, 498.0, 454.0, 498.0, 454.0, 498.0, 454.0, 498.0);
        List<Double> towersLayoutsY = Arrays.asList(17.0, 17.0, 58.0, 58.0, 99.0, 99.0, 140.0, 140.0);

        for (String nick : game.getSchoolDashboards().keySet()) {
            boolean currentPlayer = nick.equals(guiManager.getNickname());
            if (currentPlayer) {
                destAP = currentPlayerSchoolDashboardAP;
                destAP.getChildren().clear();
                ImageView sdIV = new ImageView(UpdateUtils.schoolDashboardImage);
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

                towersLayoutsX = Arrays.asList(454.0, 498.0, 454.0, 498.0, 454.0, 498.0, 454.0, 498.0);
                towersLayoutsY = Arrays.asList(17.0, 17.0, 58.0, 58.0, 99.0, 99.0, 140.0, 140.0);

                // coin vbox
                currentPlayerCoins.getChildren().clear();
                currentPlayerCoins.setSpacing(10.0);
                currentPlayerCoins.setAlignment(Pos.CENTER);
                ImageView coinImageview = UpdateUtils.coinImageView();
                Text coinsCount = UpdateUtils.islandText(game.getCoins().get(nick));
                currentPlayerCoins.getChildren().add(coinImageview);
                currentPlayerCoins.getChildren().add(coinsCount);
            } else
            {
                VBox vBox = (VBox) topHBox.lookup("#" + nick + "VBox");
                AnchorPane ap; // school dashboard AnchorPane
                VBox coinVbox;

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

                    // coin vbox
                    coinVbox = new VBox();
                    coinVbox.setId(nick + "_coinVbox");
                    hBox.getChildren().add(coinVbox);

                    ap.setId("schoolDashboardAP" + nick);
                } else {
                    ap = (AnchorPane) topHBox.lookup("#schoolDashboardAP" + nick);
                    coinVbox = (VBox) topHBox.lookup("#" + nick + "_coinVbox");
                }

                coinVbox.getChildren().clear();
                coinVbox.setSpacing(10.0);
                coinVbox.setAlignment(Pos.CENTER);
                ImageView coinImageview = UpdateUtils.coinImageView();
                Text coinsCount = UpdateUtils.islandText(game.getCoins().get(nick));
                coinVbox.getChildren().add(coinImageview);
                coinVbox.getChildren().add(coinsCount);

                ImageView sdIV = new ImageView(UpdateUtils.schoolDashboardImage);
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

                towersLayoutsX = Arrays.asList(454.0 * .7, 498.0 * .7, 454.0 * .7, 498.0 * .7, 454.0 * .7, 498.0 * .7, 454.0 * .7, 498.0 * .7);
                towersLayoutsY = Arrays.asList(17.0 * .7, 17.0 * .7, 58.0 * .7, 58.0 * .7, 99.0 * .7, 99.0 * .7, 140.0 * .7, 140.0 * .7);
            }

            ReducedSchoolDashboard sd = game.getSchoolDashboards().get(nick);

            // fill entrance
            int i = 0;
            for (Color c : Color.values()) {
                for (int j = 0; j < sd.getEntrance().getOrDefault(c, 0); j++) {
                    ImageView iv = studentImage(c, scale);
                    if (currentPlayer) {
                        iv.setUserData(c);
                        entranceStudents.add(iv);
                    }
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

            // fill dr
            for (Color c : Color.values()) {
                i = 0;
                for (int j = 0; j < sd.getDiningRoom().getOrDefault(c, 0); j++) {
                    ImageView iv = studentImage(c, scale);
                    if (currentPlayer) {
                        iv.setUserData(c);
                        diningRoomStudents.add(iv);
                    }
                    iv.setLayoutX(drLayoutXStart + i * drLayoutXDelta);
                    iv.setLayoutY(drLayoutsY.get(c.ordinal()));
                    destAP.getChildren().add(iv);

                    i++;
                }
            }

            // fill professors
            for (Color c : sd.getProfessors()) {
                ImageView profIV = UpdateUtils.profImageView(c, scale);
                profIV.setLayoutX(profLayoutX * scale);
                profIV.setLayoutY(profBaseLayoutY * scale + c.ordinal() * profLayoutYDelta * scale);

                destAP.getChildren().add(profIV);
            }

            // fill towers
            for (int j = 0; j < sd.getTowers(); j++) {
                ImageView towerImage = UpdateUtils.towerImageView(sd.getTowerColor(), 60.0 * scale);
                towerImage.setLayoutX(towersLayoutsX.get(j));
                towerImage.setLayoutY(towersLayoutsY.get(j));
                destAP.getChildren().add(towerImage);
            }
        }
    }

    /**
     * Updates unused professors based on the given ReducedGame.
     * @param game updated game state.
     */
    private void updateUnusedProfessors(ReducedGame game) {
        unusedProfHBox.getChildren().clear();

        for (Color c : game.getUnusedProfessors()) {
            ImageView profIV = UpdateUtils.profImageView(c, 1.0);
            unusedProfHBox.getChildren().add(profIV);
        }
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
        ImageView source = (ImageView) mouseEvent.getSource();
        int islandIndex = (int) source.getUserData(); // 1-indexed island index

        switch (phase) {
            case ACTION_1 -> {
                if (actionPhaseSelectedColor != null) {
                    notifyObservers(o -> o.onStudentMovedToIsland(islandIndex, actionPhaseSelectedColor));
                    actionPhaseSelectedColor = null;
                }
            } case ACTION_2 -> {
                int requestedSteps = (islandIndex - 1 - game.getMNPosition()) % game.getIslands().size();

                if (islandIndex < game.getMNPosition())
                    requestedSteps = islandIndex - 1 + game.getIslands().size() - game.getMNPosition();

                if (requestedSteps <= maxMNSteps && requestedSteps >= 1) {
                    int finalRequestedSteps = requestedSteps;
                    notifyObservers(o -> o.onMotherNatureMoved(finalRequestedSteps));
                }
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
            if (allowedClodCards.contains(ccIndex)) {
                // prevent to play character cards when other players are active
                characterCardButtons.forEach(b -> b.setDisable(true));

                // reset the set of played character cards
                activatedCharacterCards.clear();

                notifyObservers(o -> o.onCloudCardChosen(ccIndex));
            }
        }
    }

    public void addIsland(ReducedIsland island, int islandIndex, HBox hBox, EventHandler<MouseEvent> callback) {
        AnchorPane ap = new AnchorPane();
        hBox.getChildren().add(ap);

        // island img
        ImageView islandIV = islandImageView(islandIndex, callback);
        ap.getChildren().add(islandIV);
        islandsImageViews.add(islandIV);

        // students images
        List<Double> layoutXs = Arrays.asList(39.0, 85.0, 24.0, 64.0, 107.0);
        List<Double> layoutYs = Arrays.asList(24.0, 24.0, 57.0, 57.0, 57.0);
        int i = 0;
        for (Color c : Color.values()) {
            if (island.getStudents().getOrDefault(c, 0) == 0)
                continue;

            attachStudentWithCounter(ap, c, island.getStudents().get(c), layoutXs.get(i), layoutYs.get(i), Paint.valueOf("WHITE"), null);

            i++;
        }

        // mother nature
        if (island.isMotherNature()) {
            ImageView mniv = new ImageView(new Image("images/table/motherNature.png"));
            mniv.setFitHeight(45.0);
            mniv.setFitWidth(45.0);
            mniv.setLayoutX(37.0);
            mniv.setLayoutY(87.0);
            ap.getChildren().add(mniv);
        }

        // no Entry tiles
        if (island.getNoEntryTiles() > 0) {
            ImageView noEntryIv = new ImageView(new Image("images/table/noEntry.png"));
            noEntryIv.setFitHeight(45.0);
            noEntryIv.setFitWidth(45.0);
            noEntryIv.setLayoutX(75.0);
            noEntryIv.setLayoutY(87.0);
            ap.getChildren().add(noEntryIv);

            Text noEntryCount = UpdateUtils.islandText(island.getNoEntryTiles());
            noEntryCount.setLayoutX(120.0);
            noEntryCount.setLayoutY(132.0);
            noEntryCount.setFill(Paint.valueOf("WHITE"));
            ap.getChildren().add(noEntryCount);
        }

        // towers
        if (island.getTowers() > 0) {
            ImageView tower = UpdateUtils.towerImageView(island.getTowerColor(), 35.0);
            ap.getChildren().add(tower);

            Text towersCount = UpdateUtils.islandText(island.getTowers());
            towersCount.setLayoutX(125.0);
            towersCount.setLayoutY(115.0);
            ap.getChildren().add(towersCount);
        }
    }

    /**
     * Builds an AnchorPane suitable to represent a Character Card and its attributes.
     * @param card card to create the AnchorPane for.
     * @param cardIndex index of the card in the game.
     * @param eventHandler action event associated with the button inside the AnchorPane.
     * @return built AnchorPane
     */
    public AnchorPane characterCardAnchorPane(ReducedCharacterCard card, int cardIndex, EventHandler<ActionEvent> eventHandler) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("characterAnchorPane");
        anchorPane.setPrefHeight(174.0);

        Button button = new Button();
        button.getStyleClass().add("characterButton");
        button.getStyleClass().add(card.getName());
        if (activatedCharacterCards.contains(cardIndex))
            button.getStyleClass().add("activated");
        button.setDisable(true);
        characterCardButtons.add(button);
        button.setLayoutY(1.0);
        button.setLayoutX(30.0);
        button.setUserData(cardIndex);
        button.setId(card.getName());
        button.setOnAction(eventHandler);
        anchorPane.getChildren().add(button);

        // add tooltip to the button, with the description of the card
        Tooltip tooltip = new Tooltip(card.getDescription());
        tooltip.setShowDelay(new Duration(1.0));
        tooltip.setShowDuration(Duration.INDEFINITE);
        button.setTooltip(tooltip);

        /*ImageView imageView = characterCardImageView(card);
        button.setGraphic(imageView);*/

        List<Double> layoutsY = Arrays.asList(0.0, 30.0, 60.0, 90.0, 120.0, 150.0);
        int i = 0; // used to iterate over layoutsY

        // if card has students, attach them to the AnchorPane
        if (card.getStudents().size() > 0) {
            for (Color c : Color.values()) {
                if (card.getStudents().getOrDefault(c, 0) > 0) {
                    attachStudentWithCounter(anchorPane, c, card.getStudents().get(c), 3, layoutsY.get(i), Paint.valueOf("BLACK"), card.getName() + "_" + c.toString());
                    i++;
                }
            }
        }

        // if the card has no entry tiles, attach them to the AnchorPane
        if (card.getNoEntryTiles() > 0) {
            for (i = 0; i < card.getNoEntryTiles(); i++) {
                ImageView noEntryImageView = noEntryImageView(24.0);
                noEntryImageView.setLayoutX(3);
                noEntryImageView.setLayoutY(layoutsY.get(i));
                anchorPane.getChildren().add(noEntryImageView);
            }
        }

        String cardName = card.getName();

        // for Choose3ToEntrance and Exchange2Students add a Stop button, to be clicked if the user wants to do less than the maximum swaps (3 and 2 respectively)
        if (cardName.equals("Choose3toEntrance") || cardName.equals("Exchange2Students")) {
            Button stop = new Button();
            stop.getStyleClass().add("stopButton");
            stop.setId(cardName + "_stop");
            stop.setLayoutX(3);
            stop.setLayoutY(layoutsY.get(i));
            anchorPane.getChildren().add(stop);
        }

        // for AllRemoveColorInput and BlockColorOnce, add a student for every color to allow color input
        if (cardName.equals("AllRemoveColor") ||
            cardName.equals("BlockColorOnce")) {
            i = 0;
            for (Color c : Color.values()) {
                ImageView colorImageView = studentImageView(c, 20.0);
                colorImageView.setId(cardName + "_" + c);
                colorImageView.setLayoutX(3);
                colorImageView.setLayoutY(layoutsY.get(i));
                anchorPane.getChildren().add(colorImageView);
                i++;
            }
        }

        return anchorPane;
    }

    private ImageView studentImage(Color c) {
        return studentImage(c, 1.0);
    }

    private ImageView studentImage(Color c, double scale) {
        Image studentImage = UpdateUtils.studentImage(c);

        ImageView iv = new ImageView(studentImage);
        iv.setFitHeight(20.0 * scale);
        iv.setFitWidth(20.0 * scale);
        return iv;
    }

    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        phase = Phase.PLANNING_2;

        showMessage("Please, choose an assistant Card!");
        chosenAssistant = null;

        Platform.runLater(() -> {
            playButton.setDisable(false);
            handGridPane.getChildren().clear();

            handButtons.clear();

            int i = 0, j = 0; // used to determine row and col for handGidPane (row, col)
            for (int assistantPri : hand.keySet()) {
                Button assistantButton = new Button();
                handButtons.add(assistantButton);
                assistantButton.setOnAction((e) -> handleAssistantButton(e));
                assistantButton.setUserData(assistantPri);
                assistantButton.setPrefHeight(104.0);
                assistantButton.setPrefWidth(71.0);
                ImageView assistantIV = new ImageView(UpdateUtils.assistantImage(assistantPri));
                assistantIV.setFitHeight(104.0);
                assistantIV.setFitWidth(71.0);
                assistantButton.setGraphic(assistantIV);
                assistantButton.getStyleClass().add("assistantButton");

                if (notPlayable.contains(assistantPri))
                    assistantButton.setDisable(true);

                handGridPane.add(assistantButton, j, i);

                j++;
                if (j == handGridPane.getColumnCount()) {
                    i++;
                    j = 0;
                }
            }
        });
    }


    /**
     * to be executed when an assistant card is clicked
     * @param e
     */
    private void handleAssistantButton(ActionEvent e) {
        Button source = (Button) e.getSource();
        this.chosenAssistant = (int) source.getUserData();
        currentPlayerAssistant.setImage(UpdateUtils.assistantImage(chosenAssistant));
    }

    /**
     * to be executed when a character card's button gets triggered
     * @param e
     */
    private void handleCharacterButton(ActionEvent e) {
        phase = Phase.CHARACTER;

        Button source = (Button) e.getSource();
        int cardIndex = (int) source.getUserData();

        source.getStyleClass().add("activated");
        activatedCharacterCards.add(cardIndex);

        // if phase is ACTION_2, reload the prompt
        // necessary for TwoAdditionalMoves, to update the maximum steps displayed in the prompt
        if (phase == Phase.ACTION_2)
            askActionPhase2(maxMNSteps);

        characterCardUpdateSem.drainPermits();
        characterCardUpdateSem.release();

        diningRoomUpdateSem.drainPermits();
        diningRoomUpdateSem.release();

        notifyObservers(o -> o.onCCChosen(cardIndex + 1));
     }

    public void showMessage(String content) {
        promptText.setText(content);
    }

    /**
     * Handles the input to activate AllRemoveColorInput
     */
    public void askCCAllRemoveColorInput() {
        showMessage("Please, select a color from the card");

        for (Color c : Color.values()) {
            ImageView student = (ImageView) Gui.getStage().getScene().lookup("#AllRemoveColor_" + c);

            if (student != null) {
                student.setOnMouseClicked((e) -> {
                    notifyObservers(o -> o.onCCAllRemoveColorInput(c));
                });
            }
        }
    }

    /**
     * Handles the input to activate BlockColorOnce
     */
    public void askCCBlockColorOnceInput() {
        showMessage("Please, select a color from the card");

        for (Color c : Color.values()) {
            ImageView student = (ImageView) Gui.getStage().getScene().lookup("#BlockColorOnce_" + c);

            if (student != null) {
                student.setOnMouseClicked((e) -> {
                    notifyObservers(o -> o.onCCBlockColorOnceInput(c));
                });
            }
        }
    }

    /**
     * Handles the input to activate Choose1DiningRoom
     */
    public void askCCChoose1DiningRoomInput() {
        showMessage("Please, pick a student from the Character Card");

        for (Color c : Color.values()) {
            ImageView student = (ImageView) Gui.getStage().getScene().lookup("#Choose1DiningRoom_" + c);

            if (student != null) {
                // if color c is present in the cc, attach click listener to the student
                student.setOnMouseClicked((e) -> {
                    notifyObservers(o -> o.onCCChoose1DiningRoomInput(c));

                    // remove the listener once the color has been chosen
                    student.setOnMouseClicked(null);
                });
            }
        }
    }

    /**
     * Handles the input to activate Choose1ToIsland
     */
    public void askCCChoose1ToIslandInput() {
        showMessage("Please, pick a student from the Character Card");

        // lookup the character card's students
        for (Color c : Color.values()) {
            ImageView student = (ImageView) Gui.getStage().getScene().lookup("#Choose1ToIsland_" + c);

            if (student != null) {
                // if color c is present in the cc, attach click listener to the student
                student.setOnMouseClicked((e) -> {
                    showMessage("Please, select the destination island for the " + c.toString().toLowerCase() + " student from the card");

                    // replace the islands' click handler
                    islandsImageViews.forEach(i -> {
                        i.setOnMouseClicked((event) -> {
                            notifyObservers(o -> o.onCCChose1ToIslandInput(c, (int) i.getUserData()));

                            // once the choice is done, roll back to the default click handler
                            resetIslandsClickHandler();
                        });
                    });

                    // remove the listener once the color has been chosen
                    student.setOnMouseClicked(null);
                });
            }
        }
    }

    /**
     * Handles the input to activate Choose3ToEntrance
     */
    public void askCCChoose3ToEntranceInput(int inputCount) {
        // wait for the character cards to be rendered by the update method
        System.out.println("wait for characterCardUpdateSem");
        try {
            characterCardUpdateSem.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("characterCardUpdateSem acquired");

        showMessage("[Swap " + inputCount + " of 3] Please, choose a student from the card");

        Button stop = (Button) Gui.getStage().getScene().lookup("#Choose3toEntrance_stop");
        Tooltip stopTooltip = new Tooltip("Stop (skip next swaps)");
        stop.setTooltip(stopTooltip);
        stop.setOnAction(e -> {
            notifyObservers(ViewObserver::onCCChoose3ToEntranceStop);
            stop.setOnAction(null);
            stop.setTooltip(null);
        });

        for (Color c : Color.values()) {
            ImageView student = (ImageView) Gui.getStage().getScene().lookup("#Choose3toEntrance_" + c);

            if (student != null) {
                student.setOnMouseClicked((e) -> {
                    showMessage("[Swap " + inputCount + " of 3] Please, choose a student in the entrance to swap with the " + c.toString().toLowerCase() + " from the card.");

                    entranceStudents.forEach(es -> {
                        es.setOnMouseClicked((event) -> {
                            characterCardUpdateSem.drainPermits();

                            notifyObservers(o -> o.onCCChoose3ToEntranceSingleInput(c, (Color) es.getUserData(), inputCount));

                            if (inputCount == 3) {
                                stop.setOnAction(null);
                                stop.setTooltip(null);
                            }

                            entranceStudents.forEach(es1 -> es1.setOnMouseClicked(null));
                        });
                    });

                    student.setOnMouseClicked(null);
                });
            }
        }
    }

    /**
     * Handles the input to activate ChooseIsland
     */
    public void askCCChooseIslandInput() {
        showMessage("Please, choose an island on which apply the card");

        islandsImageViews.forEach(island -> {
            island.setOnMouseClicked((e) -> {
                notifyObservers(o -> o.onCCChooseIslandInput((int) island.getUserData() - 1));

                // once the choice is done, roll back to the default click handler
                resetIslandsClickHandler();
            });
        });
    }

    /**
     * Handles the input to activate Exchange2Students
     */
    public void askCCExchange2StudentsInput(int inputCount) {
        // wait for the character cards to be rendered by the update method
        System.out.println("waiting for characterCardUpdateSem");
        try {
            characterCardUpdateSem.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("characterCardUpdateSem acquired");

        showMessage("[Swap " + inputCount + " of 2] Please, choose a student from the entrance");

        Button stop = (Button) Gui.getStage().getScene().lookup("#Exchange2Students_stop");
        Tooltip stopTooltip = new Tooltip("Stop (skip next swap)");
        stop.setTooltip(stopTooltip);
        stop.setOnAction(e -> {
            notifyObservers(ViewObserver::onCCExchange2StudentsStop);
            stop.setOnAction(null);
            stop.setTooltip(null);
        });

        // wait for the school dashboard to be rendered by the update method
        System.out.println("waiting for diningRoomUpdateSem");
        try {
            diningRoomUpdateSem.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("diningRoomUpdateSem acquired");

        entranceStudents.forEach(es -> {
            es.setOnMouseClicked(event -> {
                Color fromEntrance = (Color) es.getUserData();
                showMessage("[Swap " + inputCount + " of 2] Please, choose a student in the dining room to swap with the " + fromEntrance.toString().toLowerCase() + " from the entrance.");

                diningRoomStudents.forEach(drs -> {
                    drs.setOnMouseClicked(e -> {
                        Color fromDining = (Color) drs.getUserData();

                        characterCardUpdateSem.drainPermits();
                        diningRoomUpdateSem.drainPermits();

                        notifyObservers(o -> o.onCCExchange2StudentsSingleInput(fromEntrance, fromDining, inputCount));
                    });
                });
            });
        });
    }

    /**
     * Handles the input to activate NoEntryIsland
     */
    public void askCCNoEntryIslandInput() {
        showMessage("Please, choose an island on which apply the card");

        islandsImageViews.forEach(island -> {
            island.setOnMouseClicked((e) -> {
                notifyObservers(o -> o.onCCNoEntryIslandInput((int) island.getUserData() - 1));

                // once the choice is done, roll back to the default click handler
                resetIslandsClickHandler();
            });
        });

    }

    /**
     * Sets this::onIslandClicked as the islands' click handler.
     * Useful if the click handler needs to be 1-shot overwritten, as in some character cards' input.
     */
    private void resetIslandsClickHandler() {
        islandsImageViews.forEach(island -> {
            island.setOnMouseClicked(this::onIslandClicked);
        });
    }


    /**
     * Displays an alert showing the user that he lost the game, and the nick of the winner.
     * @param winnerNick nick of the winner
     */
    public void showLoser(String winnerNick) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setOnCloseRequest(de -> Platform.exit());
        a.setHeaderText("Game over");
        a.setContentText(winnerNick + " won the game.");
        a.show();
    }

    /**
     * Shows the user that he won the game
     */
    public void showWinner() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setOnCloseRequest(de -> Platform.exit());
        a.setHeaderText("You won!");
        a.setContentText("The game ends here.");
        a.show();
    }
}
