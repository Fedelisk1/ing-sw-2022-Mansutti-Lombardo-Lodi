package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedCharacterCard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.beans.property.BooleanProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class TableController extends ViewObservable implements Initializable {
    @FXML
    public ListView handListView;
    public Text promptText;
    public Button playButton;
    public HBox charactersHBox;
    private ObservableMap<Integer, Integer> hand;
    private ObservableList<AssistantCard> handOL;
    private List<Integer> alreadyPlayedCards;

    public void handlePlayButton(ActionEvent actionEvent) {
        AssistantCard chosen = (AssistantCard) handListView.getSelectionModel().getSelectedItem();

        if (chosen == null) {
            showMessage("Please select an Assistant Card!");
            return;
        }

        notifyObservers(o -> o.onAssistantCardChosen(chosen.getPriority()));
        playButton.setDisable(true);
    }

    class AssistantListCell extends ListCell<AssistantCard> {
        @Override
        protected void updateItem(AssistantCard ac, boolean empty) {
            super.updateItem(ac, empty);

            if (ac == null || empty) {
                setGraphic(null);
                setText(null);
            } else {
                Image assistantImage = new Image("/images/table/assistantCards/" + ac.getPriority() + ".png");
                ImageView assistantIV = new ImageView(assistantImage);
                assistantIV.setFitHeight(89.0);
                assistantIV.setFitWidth(61.0);
                setGraphic(assistantIV);
                setText("Priority: " + ac.getPriority() + " - max MN steps: " + ac.getMaxSteps());

                if(alreadyPlayedCards.contains(ac.getPriority())) {
                    setDisable(true);
                    setTooltip(new Tooltip("Already played by another player"));
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handOL = FXCollections.observableArrayList();
        hand = FXCollections.observableHashMap();

        hand.addListener((MapChangeListener<? super Integer, ? super Integer>) change -> {
            System.out.println("hand change ");
            int k = change.getKey();

            if (change.wasRemoved()) {
                int v = change.getValueRemoved();
                System.out.println("removed");
                handOL.removeIf(ac -> ac.getPriority() == k);
            } else if (change.wasAdded()) {
                int v = change.getValueAdded();
                System.out.println("added");
                handOL.add(new AssistantCard(k, v));
            }

        });

        handListView.setItems(handOL);
        handListView.setCellFactory((a) -> new AssistantListCell());
    }

    public void update(ReducedGame game) {
        if (game.isExpert()) {
            System.out.println(game.getCharacterCards());
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
        }
    }

    private ImageView studentImage(Color color) {
        Image studentImage = new Image("/images/students/" + color.toString().toLowerCase() + "Student3D.png");
        return new ImageView(studentImage);
    }

    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        showMessage("Please, choose an assistant Card!");
        playButton.setDisable(false);
        this.alreadyPlayedCards = notPlayable;

        if(this.hand.size() == 0)
            this.hand.putAll(hand);

        Set<Integer> removed = new HashSet<>(this.hand.keySet());
        Set<Integer> updatedHand = hand.keySet();
        System.out.println("hand received " + updatedHand);
        removed.removeAll(updatedHand); // removed now contains the removed cards from this.hand
        System.out.println("removed " + removed);

        for (int removedPri : removed)
            if (hand.get(removedPri) != null)
                hand.remove(removedPri);
    }

    public void showMessage(String content) {
        promptText.setText(content);
    }
}
