package Homework4;

// Name: Roger Cheng
//SBU ID: 112830881
//Homework #4: Problem 2 (Bonus)
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import java.util.LinkedList;

public class LinkedListVisualizer extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		// Create the textfields for the visual
		TextField tfKey = new TextField();
		tfKey.setPrefColumnCount(3);
		tfKey.setAlignment(Pos.BASELINE_RIGHT);
		TextField tfIndex = new TextField();
		tfKey.setPrefColumnCount(3);
		tfKey.setAlignment(Pos.BASELINE_RIGHT);

		// Create the superpane to contain all the elements of the program
		BorderPane pane = new BorderPane();
		// Create the linkedlist to instiantiate the visualizer
		LinkedList<Integer> list = new LinkedList<>();
		LinkedListFrame frame = new LinkedListFrame(list);

		// Define the insert functionality of the respective button
		Button btInsert = new Button("Insert");
		btInsert.setOnAction(e -> {
			try {
			int key = Integer.parseInt(tfKey.getText());

			if (tfIndex.getText() == null || tfIndex.getText().trim().isEmpty()) {
				list.add(key);
			} else {
				int index = Integer.parseInt(tfIndex.getText());
				list.add(index, key);
			}
			frame.displayList(false, 0);
			}
			catch( Exception ex) {
				System.out.println("Invalid insertion index");
			}
		});

		// Define the delete functionality of the respective button
		Button btDelete = new Button("Delete");
		btDelete.setOnAction(e -> {
			try {
				if ((tfIndex.getText() != "")) {
					int removeIndex = Integer.parseInt(tfIndex.getText());
					if (removeIndex < list.size()) {
						list.remove(removeIndex);
						frame.displayList(false, 0);
					}
				}
			} catch (Exception ex) {
				System.out.println("Error caught, either index field was empty or the index was out of range");
			}
		});
		// Using the indexOf method of the LinkedList Class
		Button btSearch = new Button("Search");
		btSearch.setOnAction(e -> {
			int desiredValue = Integer.parseInt(tfKey.getText());
			tfIndex.setText("" + list.indexOf(desiredValue));
			frame.displayList(true, list.indexOf(desiredValue));
		});
		// Create the tool bar on the bottom
		HBox hBox = new HBox(5);
		hBox.getChildren().addAll(new Label("Enter a key value for search/insertion: "), tfKey,
				new Label("(Enter the index of deletion/ Insertion) OR (Display index of desired element)"), tfIndex, btInsert, btSearch,
				btDelete);
		hBox.setAlignment(Pos.CENTER);
		pane.setCenter(frame);
		pane.setBottom(hBox);
		Scene scene = new Scene(pane, 1250, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Linked List Visualization");
		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}

	class LinkedListFrame extends Pane {
		private LinkedList<Integer> list = new LinkedList<>();
		private int x = 50;
		private int y = 200;

		public LinkedListFrame() {

		}

		public LinkedListFrame(LinkedList<Integer> list) {
			this.list = list;
		}
		// The main method being called to display the Linked list 
		public void displayList(boolean searchDisplay, int index) {
			this.getChildren().clear();
			displayList(list, x, y, searchDisplay, index);

		}

		// The current is the list being passed in,
		public void displayList(LinkedList<Integer> current, double x, double y, boolean search, int index) {
			//Iterate through the elements in the linkedlist
			for (int i = 0; i < current.size(); i++) {
				Circle circle = new Circle(x, y, 25);
				x += 100;
				double prevX = x - 100;
				circle.setFill(Color.WHITE);
				if (!search) {
					circle.setStroke(Color.BLACK);
				} else {
					if (i == index)
						circle.setStroke(Color.RED);

				}
				if (i == 0) {
					// Connecting lines betweeen nodes
					Line line = new Line(prevX + 25, y, prevX + 100, y);
					Line arrow1 = new Line(prevX + 63, y + 12, prevX + 75, y);
					Line arrow2 = new Line(prevX + 63, y - 12, prevX + 75, y);

					// Set up the head pointer arrow
					Line headLine = new Line(prevX, y - 25, prevX, y - 100);
					headLine.setStroke(Color.RED);
					Line headArrow1 = new Line(prevX - 12, y - 37, prevX, y - 25);
					headArrow1.setStroke(Color.RED);
					Line headArrow2 = new Line(prevX + 12, y - 37, prevX, y - 25);
					headArrow2.setStroke(Color.RED);
					// If there is only one element in the entire linked list, then the head and
					// tail pointers point to the same element
					if (list.size() == 1) {
						Line tailLine = new Line(prevX, y + 100, prevX, y + 25);
						tailLine.setStroke(Color.RED);
						Line tailArrow1 = new Line(prevX - 12, y + 37, prevX, y + 25);
						tailArrow1.setStroke(Color.RED);
						Line tailArrow2 = new Line(prevX + 12, y + 37, prevX, y + 25);
						tailArrow2.setStroke(Color.RED);
						getChildren().addAll(circle, new Text(prevX - 10, y, "" + current.get(i)), headLine, headArrow1,
								headArrow2, new Text(prevX - 25, y - 120, "Head Node"), tailLine, tailArrow1,
								tailArrow2, new Text(prevX - 25, y + 120, "Tail Node"));
						//Maintain that the first node with have the head pointer pointing to it
					} else {
						getChildren().addAll(circle, new Text(prevX - 10, y, "" + current.get(i)), headLine, headArrow1,
								headArrow2, new Text(prevX - 25, y - 120, "Head Node"), line, arrow1, arrow2);
					}
					//If the last element is reached, attach the tail pointer to the end
				} else if (i != current.size() - 1) {
					Line line = new Line(prevX + 25, y, prevX + 100, y);
					Line arrow1 = new Line(prevX + 63, y + 12, prevX + 75, y);
					Line arrow2 = new Line(prevX + 63, y - 12, prevX + 75, y);
					getChildren().addAll(circle, new Text(prevX - 10, y, "" + current.get(i)), line, arrow1, arrow2);
					//Otherwise, make a new arrow to point to the node that will come after
				} else {
					Line tailLine = new Line(prevX, y + 100, prevX, y + 25);
					tailLine.setStroke(Color.RED);
					Line tailArrow1 = new Line(prevX - 12, y + 37, prevX, y + 25);
					tailArrow1.setStroke(Color.RED);
					Line tailArrow2 = new Line(prevX + 12, y + 37, prevX, y + 25);
					tailArrow2.setStroke(Color.RED);
					getChildren().addAll(circle, new Text(prevX - 10, y, "" + current.get(i)), tailLine, tailArrow1,
							tailArrow2, new Text(prevX - 25, y + 120, "Tail Node"));
				}
			}

		}
	}
}
