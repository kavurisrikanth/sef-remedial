
package calculator;

import UNumber.UNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import calculator.BusinessLogic;

import java.util.HashMap;

/**
 * <p> Title: UserInterface Class. </p>
 * 
 * <p> Description: The Java/FX-based user interface for the calculator. The class works with String
 * objects and passes work to other classes to deal with all other aspects of the computation.</p>
 * 
 * <p> Copyright: Lynn Robert Carter Â© 2017 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @author Srikanth Kavuri
 * 
 * @version 4.00	2017-10-17 The JavaFX-based GUI for the implementation of a calculator
 * @version 4.10	2018-12-30 Units added.
 */

public class UserInterface {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	/* Constants used to parameterize the graphical user interface.  We do not use a layout manager for
	   this application. Rather we manually control the location of each graphical element for exact
	   control of the look and feel. */

	// These are the application values required by the user interface
	private Label label_IntegerCalculator = new Label("Astronomical Calculator");
	
	private Label label_Operand1 = new Label("First operand");
	private TextField text_Operand1 = new TextField();
	
	private Label label_Operand1_error = new Label("error");
	private TextField text_Operand1error = new TextField();
	
	private Label label_Operand2 = new Label("Second operand");
	private TextField text_Operand2 = new TextField();
	
	private Label label_Operand2_error = new Label("error");
	private TextField text_Operand2error = new TextField();
	
	private Label label_Result = new Label("Result");
	private TextField text_Result = new TextField();
	
	private Label label_Result_error = new Label("error");
	private TextField text_Result_error = new TextField(); 
	
	private Button button_Add = new Button("+");
	private Button button_Sub = new Button("-");
	private Button button_Mpy = new Button("\u00D7");				// The multiply symbol: \u00D7
	private Button button_Div = new Button("\u00F7");				// The divide symbol: \u00F7
	private Button button_Sqrt = new Button("\u221A");
	private Button button_Clr = new Button("CLR");
	private Button button_MoveToFirst = new Button("Move to operand 1");
	private Button button_ChangeUnits = new Button("Change units");
	
	private Label label_errOperand1 = new Label("");
	private Label label_errOperand1error = new Label("");
	private Label label_errOperand1Unit = new Label("");
	
	private Label label_errOperand2 = new Label("");
	private Label label_errOperand2error = new Label("");
	private Label label_errOperand2Unit = new Label("");
	
	private Label label_errResult = new Label("");
	
	private TextFlow err1MeasuredValue;
    private Text err1MVPart1 = new Text();
    private Text err1MVPart2 = new Text();
    
	private TextFlow err2MeasuredValue;
    private Text err2MVPart1 = new Text();
    private Text err2MVPart2 = new Text();
    
	private TextFlow err1MeasuredValueError;
    private Text err1MVEPart1 = new Text();
    private Text err1MVEPart2 = new Text();
    
	private TextFlow err2MeasuredValueError;
    private Text err2MVEPart1 = new Text();
    private Text err2MVEPart2 = new Text();

    private Label plusMinusLabel_operand1 = new Label("\u00B1"),
				plusMinusLabel_operand2 = new Label("\u00B1"),
				plusMinusLabel_result = new Label("\u00B1");

    // These variables are used to set up the UI for units.
    private Label unitsLabel_operand1 = new Label("Units"),
				unitsLabel_operand2 = new Label("Units"),
				unitsLabel_result = new Label("Units");
    private TextField op1UnitValue_mass = new TextField(),
			op1UnitValue_length = new TextField(),
			op1UnitValue_time = new TextField(),
			op2UnitValue_mass = new TextField(),
			op2UnitValue_length = new TextField(),
			op2UnitValue_time = new TextField(),
			resultUnitValue_mass = new TextField(),
			resultUnitValue_length = new TextField(),
			resultUnitValue_time = new TextField();
//    private Label unit_result = new Label();
	private ComboBox<String> massUnitsOp1 = new ComboBox<>(),
							 massUnitsOp2 = new ComboBox<>(),
							 massUnitsResult = new ComboBox<>(),
							 massScaleOp1 = new ComboBox<>(),
							 massScaleOp2 = new ComboBox<>(),
							 massScaleResult = new ComboBox<>(),
							 lengthUnitsOp1 = new ComboBox<>(),
							 lengthUnitsOp2 = new ComboBox<>(),
							 lengthUnitsResult = new ComboBox<>(),
							 lengthScaleOp1 = new ComboBox<>(),
							 lengthScaleOp2 = new ComboBox<>(),
							 lengthScaleResult = new ComboBox<>(),
							 timeUnitsOp1 = new ComboBox<>(),
							 timeUnitsOp2 = new ComboBox<>(),
							 timeUnitsResult = new ComboBox<>(),
							 timeScaleOp1 = new ComboBox<>(),
							 timeScaleOp2 = new ComboBox<>(),
							 timeScaleResult = new ComboBox<>();
	
	// If the multiplication and/or division symbols do not display properly, replace the 
	// quoted strings used in the new Button constructor call with the <backslash>u00xx values
	// shown on the same line. This is the Unicode representation of those characters and will
	// work regardless of the underlying hardware being used.

	// These variables are used for "automating" the UI widths and co-ordinates.
	private double buttonSpace;

	private final double BUTTON_WIDTH  = Calculator.WINDOW_WIDTH/20;
	private final double BUTTON_BUFFER = BUTTON_WIDTH * 2 ;

	private int buffer = 5;
	private double plusMinusWidth = 30,
					textFieldWidth = Calculator.WINDOW_WIDTH/3 - 5 * (buffer) - plusMinusWidth,
					labelWidth = Calculator.WINDOW_WIDTH/4,
					comboBoxWidth = textFieldWidth/8;
	private int theLeftX = 10,
				plusMinusX = theLeftX + (int)textFieldWidth + 2 * buffer,
				errorTermStartX = plusMinusX + (int)plusMinusWidth,
				unitsStartX = errorTermStartX + (int)textFieldWidth + 2 * buffer;

	
	/* This is the link to the business logic */
	public BusinessLogic perform = new BusinessLogic();
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface. These assignments
	 * determine the location, size, font, color, and change and event handlers for each GUI object.
	 */
	public UserInterface(Pane theRoot) {
				
		// There are five gaps. Compute the button space accordingly.
		buttonSpace = Calculator.WINDOW_WIDTH / 5;
		
		// Label theScene with the name of the calculator, centered at the top of the pane
		setupLabelUI(label_IntegerCalculator, "Arial", 28, Calculator.WINDOW_WIDTH, Pos.CENTER, 0, 10);

		// Items required for setting up units.
		ObservableList<String> mass = FXCollections.observableArrayList (
				"--", "oz", "lb", "kg", "g");
		massUnitsOp1.setItems(mass);
		massUnitsOp2.setItems(mass);
		massUnitsResult.setItems(mass);

		ObservableList<String> length = FXCollections.observableArrayList (
				"--","yd", "mi", "ft", "in","m", "km");
		lengthUnitsOp1.setItems(length);
		lengthUnitsOp2.setItems(length);
		lengthUnitsResult.setItems(length);

		ObservableList<String> time = FXCollections.observableArrayList (
				"--", "s", "min", "hr", "day");
		timeUnitsOp1.setItems(time);
		timeUnitsOp2.setItems(time);
		timeUnitsResult.setItems(time);

		ObservableList<String> scales = FXCollections.observableArrayList (
				"--","exa", "peta", "tera", "giga","mega", "kilo", "hecto","deca", "deci", "centi","milli","micro", "nano","pico","femto","atto");
		massScaleOp1.setItems(scales);
		massScaleOp2.setItems(scales);
		massScaleResult.setItems(scales);
		lengthScaleOp1.setItems(scales);
		lengthScaleOp2.setItems(scales);
		lengthScaleResult.setItems(scales);
		timeScaleOp1.setItems(scales);
		timeScaleOp2.setItems(scales);
		timeScaleResult.setItems(scales);

		// Now set up the UI items.
		setupOperand1(theRoot);
		
		setupOperand2(theRoot);
		
		setupResult(theRoot);
		
		setupButtons(theRoot);

		// Clear everything to start with.
		clearAll();
		
		err1MVPart1.setFill(Color.BLACK);
	    err1MVPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err1MVPart2.setFill(Color.RED);
	    err1MVPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err1MeasuredValue = new TextFlow(err1MVPart1, err1MVPart2);
		err1MeasuredValue.setMinWidth(Calculator.WINDOW_WIDTH-10); 
		err1MeasuredValue.setLayoutX(22);  
		err1MeasuredValue.setLayoutY(120);
		
		err2MVPart1.setFill(Color.BLACK);
	    err2MVPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err2MVPart2.setFill(Color.RED);
	    err2MVPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err2MeasuredValue = new TextFlow(err2MVPart1, err2MVPart2);
		err2MeasuredValue.setMinWidth(Calculator.WINDOW_WIDTH-10); 
		err2MeasuredValue.setLayoutX(22);  
		err2MeasuredValue.setLayoutY(240);
		
		
		err1MVEPart1.setFill(Color.BLACK);
	    err1MVEPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err1MVEPart2.setFill(Color.RED);
	    err1MVEPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err1MeasuredValueError = new TextFlow(err1MVEPart1, err1MVEPart2);
		err1MeasuredValueError.setMinWidth(Calculator.WINDOW_WIDTH-10/2); 
		err1MeasuredValueError.setLayoutX(Calculator.WINDOW_WIDTH/2+20);  
		err1MeasuredValueError.setLayoutY(120);
		
		err2MVEPart1.setFill(Color.BLACK);
	    err2MVEPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err2MVEPart2.setFill(Color.RED);
	    err2MVEPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    err2MeasuredValueError = new TextFlow(err2MVEPart1, err2MVEPart2);
		err2MeasuredValueError.setMinWidth(Calculator.WINDOW_WIDTH-10/2); 
		err2MeasuredValueError.setLayoutX(Calculator.WINDOW_WIDTH/2+20);  
		err2MeasuredValueError.setLayoutY(240);
		
		// Place all of the just-initialized GUI elements into the pane
		theRoot.getChildren().addAll(label_IntegerCalculator, err1MeasuredValue, err2MeasuredValue,err1MeasuredValueError, err2MeasuredValueError);

	}

	/**
	 * This method sets up the UI elements for operand 1.
	 * @param theRoot - The Root pane.
	 */
	private void setupOperand1(Pane theRoot) {
		// Label the first operand just above it, left aligned
		setupLabelUI(label_Operand1, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, theLeftX, 60);

		// Establish the first text input operand field and when anything changes in operand 1,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand1, "Arial", 18, textFieldWidth, Pos.BASELINE_LEFT, theLeftX, 90, true);
		text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1(); });
		// Move focus to the second operand when the user presses the enter (return) key
		text_Operand1.setOnAction((event) -> { text_Operand1error.requestFocus(); });

		setupLabelUI(plusMinusLabel_operand1, "Arial", 32, plusMinusWidth, Pos.BASELINE_LEFT, plusMinusX, 90);

		// Label the first operand just above it, left aligned
		setupLabelUI(label_Operand1_error, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, errorTermStartX, 60);

		// Establish the first text input operand field and when anything changes in operand 1,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand1error, "Arial", 18, textFieldWidth, Pos.BASELINE_LEFT, errorTermStartX, 90, true);
		text_Operand1error.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1(); });
		// Move focus to the second operand when the user presses the enter (return) key
		text_Operand1error.setOnAction((event) -> { text_Operand2.requestFocus(); });

		// Establish an error message for the first operand just above it with, left aligned
		setupLabelUI(label_errOperand1, "Arial", 12, labelWidth, Pos.BASELINE_LEFT, 22, 145);
		label_errOperand1.setTextFill(Color.RED);

		// Establish an error message for the first operand just above it with, left aligned
		setupLabelUI(label_errOperand1error, "Arial", 12, labelWidth, Pos.BASELINE_LEFT, errorTermStartX + 10, 145);
		label_errOperand1error.setTextFill(Color.RED);

		// Set up the error message for the unit.
		setupLabelUI(label_errOperand1Unit, "Arial", 12, labelWidth, Pos.BASELINE_LEFT, errorTermStartX + 10 + labelWidth + 10, 145);
		label_errOperand1Unit.setTextFill(Color.RED);

		// Set up the units.
		setupLabelUI(unitsLabel_operand1, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, unitsStartX, 60);

		// Set up the mass scale combo box.
		setupComboBoxUI(massScaleOp1, unitsStartX, 90, comboBoxWidth);
		massScaleOp1.setOnAction((event) -> setOperand1());

		// Set up the mass units combo box.
		setupComboBoxUI(massUnitsOp1, unitsStartX + comboBoxWidth + buffer, 90, comboBoxWidth);
		massUnitsOp1.setOnAction((event) -> setOperand1());

		// Set up the value text field for mass.
		setupTextUI(op1UnitValue_mass, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 2 * (comboBoxWidth + buffer), 90, true);
		op1UnitValue_mass.textProperty().addListener(((observable, oldValue, newValue) -> setOperand1()));

		setupComboBoxUI(lengthScaleOp1, unitsStartX + 3 * (comboBoxWidth + buffer), 90, comboBoxWidth);
		lengthScaleOp1.setOnAction((event) -> setOperand1());

		setupComboBoxUI(lengthUnitsOp1, unitsStartX + 4 * (comboBoxWidth + buffer), 90, comboBoxWidth);
		lengthUnitsOp1.setOnAction((event) -> setOperand1());

		// Set up the value text field for length.
		setupTextUI(op1UnitValue_length, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 5 * (comboBoxWidth + buffer), 90, true);
		op1UnitValue_length.textProperty().addListener(((observable, oldValue, newValue) -> setOperand1()));

		setupComboBoxUI(timeScaleOp1, unitsStartX + 6 * (comboBoxWidth + buffer), 90, comboBoxWidth);
		timeScaleOp1.setOnAction((event) -> setOperand1());

		setupComboBoxUI(timeUnitsOp1, unitsStartX + 7 * (comboBoxWidth + buffer), 90, comboBoxWidth);
		timeUnitsOp1.setOnAction((event) -> setOperand1());

		// Set up the value text field for time.
		setupTextUI(op1UnitValue_time, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 8 * (comboBoxWidth + buffer), 90, true);
		op1UnitValue_time.textProperty().addListener(((observable, oldValue, newValue) -> setOperand1()));

		// Add all the above elements to the pane.
		theRoot.getChildren().addAll(label_Operand1, text_Operand1,label_Operand1_error, label_errOperand1Unit,
				text_Operand1error, label_errOperand1,label_errOperand1error, plusMinusLabel_operand1,
				unitsLabel_operand1, massUnitsOp1, lengthUnitsOp1, timeUnitsOp1, op1UnitValue_mass,
				op1UnitValue_length, op1UnitValue_time, massScaleOp1, lengthScaleOp1, timeScaleOp1);
	}

	/**
	 * This method sets up the UI elements for operand 2.
	 * @param theRoot - The Root pane.
	 */
	private void setupOperand2(Pane theRoot) {
		// Label the second operand just above it, left aligned
		setupLabelUI(label_Operand2, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, theLeftX, 180);

		// Establish the second text input operand field and when anything changes in operand 2,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand2, "Arial", 18, textFieldWidth, Pos.BASELINE_LEFT, theLeftX, 210, true);
		text_Operand2.textProperty().addListener((observable, oldValue, newValue) -> {setOperand2(); });
		// Move the focus to the result when the user presses the enter (return) key
		text_Operand2.setOnAction((event) -> { text_Operand2error.requestFocus(); });

		setupLabelUI(plusMinusLabel_operand2, "Arial", 32, plusMinusWidth, Pos.BASELINE_LEFT, plusMinusX, 210);

		// Establish an error message for the second operand just above it with, left aligned
		setupLabelUI(label_errOperand2, "Arial", 12, labelWidth, Pos.BASELINE_LEFT, 22, 265);
		label_errOperand2.setTextFill(Color.RED);

		// Label the second operand just above it, left aligned
		setupLabelUI(label_Operand2_error, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, errorTermStartX, 180);

		// Establish the second text input operand field and when anything changes in operand 2,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand2error, "Arial", 18, textFieldWidth, Pos.BASELINE_LEFT, errorTermStartX, 210, true);
		text_Operand2error.textProperty().addListener((observable, oldValue, newValue) -> {setOperand2(); });
		// Move the focus to the result when the user presses the enter (return) key
		text_Operand2error.setOnAction((event) -> { text_Result.requestFocus(); });

		// Establish an error message for the second operand just above it with, left aligned
		setupLabelUI(label_errOperand2error, "Arial", 12, labelWidth, Pos.BASELINE_LEFT, errorTermStartX, 265);
		label_errOperand2error.setTextFill(Color.RED);

		setupLabelUI(label_errOperand2Unit, "Arial", 12, labelWidth, Pos.BASELINE_LEFT, errorTermStartX + 10 + labelWidth + 10, 265);
		label_errOperand2Unit.setTextFill(Color.RED);

		// Set up units - The same procedure as for operand 1.
		setupLabelUI(unitsLabel_operand2, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, unitsStartX, 180);

		setupComboBoxUI(massScaleOp2, unitsStartX, 210, comboBoxWidth);
		massScaleOp2.setOnAction((event) -> { setOperand2(); });

		setupComboBoxUI(massUnitsOp2, unitsStartX + comboBoxWidth + buffer, 210, comboBoxWidth);

		massUnitsOp2.setOnAction((event) -> { setOperand2(); });

		setupTextUI(op2UnitValue_mass, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 2 * (comboBoxWidth + buffer), 210, true);
		op2UnitValue_mass.textProperty().addListener(((observable, oldValue, newValue) -> setOperand2()));

		setupComboBoxUI(lengthScaleOp2, unitsStartX + 3 * (comboBoxWidth + buffer), 210, comboBoxWidth);
		lengthScaleOp2.setOnAction((event) -> setOperand2());

		setupComboBoxUI(lengthUnitsOp2, unitsStartX + 4 * (comboBoxWidth + buffer), 210, comboBoxWidth);
		lengthUnitsOp2.setOnAction((event) -> setOperand2());

		setupTextUI(op2UnitValue_length, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 5 * (comboBoxWidth + buffer), 210, true);
		op2UnitValue_length.textProperty().addListener((observable, oldValue, newValue) -> setOperand2());

		setupComboBoxUI(timeScaleOp2, unitsStartX + 6 * (comboBoxWidth + buffer), 210, comboBoxWidth);
		timeScaleOp2.setOnAction((event) -> setOperand2());

		setupComboBoxUI(timeUnitsOp2, unitsStartX + 7 * (comboBoxWidth + buffer), 210, comboBoxWidth);
		timeUnitsOp2.setOnAction((event) -> setOperand2());

		setupTextUI(op2UnitValue_time, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 8 * (comboBoxWidth + buffer), 210, true);
		op2UnitValue_time.textProperty().addListener((observable, oldValue, newValue) -> setOperand2());

		theRoot.getChildren().addAll(label_Operand2, text_Operand2, label_errOperand2,label_Operand2_error, text_Operand2error, label_errOperand2error, label_errOperand2Unit, plusMinusLabel_operand2, unitsLabel_operand2, massUnitsOp2, lengthUnitsOp2, timeUnitsOp2, op2UnitValue_mass, op2UnitValue_length, op2UnitValue_time, massScaleOp2, lengthScaleOp2, timeScaleOp2);
	}

	/**
	 * This method sets up the UI elements for the result.
	 * @param theRoot - The Root pane.
	 */
	private void setupResult(Pane theRoot) {
		// Label the result just above the result output field, left aligned
		setupLabelUI(label_Result, "Arial", 18, Region.USE_PREF_SIZE, Pos.BASELINE_LEFT, theLeftX, 310);

		// Setup the moving button.
		setupButtonUI(button_MoveToFirst, "Symbol", 14, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + labelWidth + buffer, 390);
		button_MoveToFirst.setOnAction((event -> { moveResultToFirst(); }));

		// Setup the change units button.
		setupButtonUI(button_ChangeUnits, "Symbol", 14, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + labelWidth + 2 * BUTTON_WIDTH + 3 * buffer, 390);
		button_ChangeUnits.setOnAction(event -> changeUnits());

		// Establish the result output field.  It is not editable, so the text can be selected and copied,
		// but it cannot be altered by the user.  The text is left aligned.
		setupTextUI(text_Result, "Arial", 18, textFieldWidth, Pos.BASELINE_LEFT, theLeftX, 340, false);

		setupLabelUI(plusMinusLabel_result, "Arial", 32, plusMinusWidth, Pos.BASELINE_LEFT, plusMinusX, 340);

		// Label the result just above the result output field, left aligned
		setupLabelUI(label_Result_error, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, errorTermStartX, 310);

		// Establish the result output field.  It is not editable, so the text can be selected and copied,
		// but it cannot be altered by the user.  The text is left aligned.
		setupTextUI(text_Result_error, "Arial", 18, textFieldWidth, Pos.BASELINE_LEFT, errorTermStartX, 340, false);

		// Establish an error message for the second operand just above it with, left aligned
		setupLabelUI(label_errResult, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, 450,310);
		label_errResult.setTextFill(Color.RED);

		// Set up the units - The same procedure as for operand 1.
		setupLabelUI(unitsLabel_result, "Arial", 18, labelWidth, Pos.BASELINE_LEFT, unitsStartX, 310);

		setupComboBoxUI(massScaleResult, unitsStartX, 340, comboBoxWidth);

		setupComboBoxUI(massUnitsResult, unitsStartX + comboBoxWidth + buffer, 340, comboBoxWidth);

		setupTextUI(resultUnitValue_mass, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 2 * (comboBoxWidth + buffer), 340, false);

		setupComboBoxUI(lengthScaleResult, unitsStartX + 3 * (comboBoxWidth + buffer), 340, comboBoxWidth);

		setupComboBoxUI(lengthUnitsResult, unitsStartX + 4 * (comboBoxWidth + buffer), 340, comboBoxWidth);

		setupTextUI(resultUnitValue_length, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 5 * (comboBoxWidth + buffer), 340, false);

		setupComboBoxUI(timeScaleResult, unitsStartX + 6 * (comboBoxWidth + buffer), 340, comboBoxWidth);

		setupComboBoxUI(timeUnitsResult, unitsStartX + 7 * (comboBoxWidth + buffer), 340, comboBoxWidth);

		setupTextUI(resultUnitValue_time, "Arial", 18, comboBoxWidth, Pos.BASELINE_LEFT, unitsStartX + 8 * (comboBoxWidth + buffer), 340, false);

		theRoot.getChildren().addAll(label_Result, text_Result,label_Result_error,
				text_Result_error, label_errResult, plusMinusLabel_result, button_MoveToFirst, button_ChangeUnits, unitsLabel_result, massScaleResult, massUnitsResult, resultUnitValue_mass, lengthScaleResult, lengthUnitsResult, resultUnitValue_length, timeScaleResult, timeUnitsResult, resultUnitValue_time);
	}

	/**
	 * This method sets up the buttons under the result elements.
	 * @param theRoot - The Root pane.
	 */
	private void setupButtons(Pane theRoot) {
		// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Add, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + BUTTON_BUFFER, 450);
		button_Add.setOnAction((event) -> {
			addOperands();
			enableButtonsIfDisabled();
		});


		// Establish the SUB "-" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Sub, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + (2 * BUTTON_BUFFER + BUTTON_WIDTH), 450);
		button_Sub.setOnAction((event) -> {
			subOperands();
			enableButtonsIfDisabled();
		});


		// Establish the MPY "x" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Mpy, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + (3 * BUTTON_BUFFER + 2 * BUTTON_WIDTH), 450);
		button_Mpy.setOnAction((event) -> {
			mpyOperands();
			enableButtonsIfDisabled();
		});


		// Establish the DIV "/" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Div, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + (4 * BUTTON_BUFFER + 3 * BUTTON_WIDTH), 450);
		button_Div.setOnAction((event) -> {
			divOperands();
			enableButtonsIfDisabled();
		});


		// Establish the DIV "/" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Sqrt, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + (5 * BUTTON_BUFFER + 4 * BUTTON_WIDTH), 450);
		button_Sqrt.setOnAction((event) -> {
			SqrOperands();
			enableButtonsIfDisabled();
		});

		// Establish the clear "CLR" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Clr, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, theLeftX + (6 * BUTTON_BUFFER + 5 * BUTTON_WIDTH), 450);
		button_Clr.setOnAction(event -> {
			clearAll();
			enableButtonsIfDisabled();
		});

		theRoot.getChildren().addAll(button_Add, button_Sub, button_Mpy, button_Div,button_Sqrt, button_Clr);
	}

	/**
	 * This method clears out the values of all the UI elements.
	 */
	private void clearAll() {
		// Clear operand 1 elements.
		text_Operand1.setText("");
		text_Operand1.setPromptText("Enter operand 1");
		text_Operand1error.setText("");
		text_Operand1error.setPromptText("Enter error term 1. Default: 0");
		massScaleOp1.getSelectionModel().clearSelection();
		massScaleOp1.getSelectionModel().selectFirst();
		massScaleOp1.setPromptText("M");
		lengthScaleOp1.getSelectionModel().clearSelection();
		lengthScaleOp1.getSelectionModel().selectFirst();
		lengthScaleOp1.setPromptText("L");
		timeScaleOp1.getSelectionModel().clearSelection();
		timeScaleOp1.getSelectionModel().selectFirst();
		timeScaleOp1.setPromptText("T");
		massUnitsOp1.getSelectionModel().clearSelection();
		massUnitsOp1.setPromptText("M");
		lengthUnitsOp1.getSelectionModel().clearSelection();
		lengthUnitsOp1.setPromptText("L");
		timeUnitsOp1.getSelectionModel().clearSelection();
		timeUnitsOp1.setPromptText("T");
		op1UnitValue_mass.setText("");
		op1UnitValue_length.setText("");
		op1UnitValue_time.setText("");
		label_errOperand1.setText("");
		label_errOperand1error.setText("");
		label_errOperand1Unit.setText("");

		// Clear operand 2 elements.
		text_Operand2.setText("");
		text_Operand2.setPromptText("Enter operand 2");
		text_Operand2error.setText("");
		text_Operand2error.setPromptText("Enter error term 2. Default: 0");
		massScaleOp2.getSelectionModel().clearSelection();
		massScaleOp2.getSelectionModel().selectFirst();
		massScaleOp2.setPromptText("M");
		lengthScaleOp2.getSelectionModel().clearSelection();
		lengthScaleOp2.getSelectionModel().selectFirst();
		lengthScaleOp2.setPromptText("L");
		timeScaleOp2.getSelectionModel().clearSelection();
		timeScaleOp2.getSelectionModel().selectFirst();
		timeScaleOp2.setPromptText("T");
		massUnitsOp2.getSelectionModel().clearSelection();
		massUnitsOp2.setPromptText("M");
		lengthUnitsOp2.getSelectionModel().clearSelection();
		lengthUnitsOp2.setPromptText("L");
		timeUnitsOp2.getSelectionModel().clearSelection();
		timeUnitsOp2.setPromptText("T");
		op2UnitValue_mass.setText("");
		op2UnitValue_length.setText("");
		op2UnitValue_time.setText("");
		label_errOperand2.setText("");
		label_errOperand2error.setText("");
		label_errOperand2Unit.setText("");

		// Clear buttons
		button_MoveToFirst.setDisable(true);
		button_ChangeUnits.setDisable(true);
		button_Add.setDisable(true);
		button_Sub.setDisable(true);
		button_Mpy.setDisable(true);
		button_Div.setDisable(true);
		button_Sqrt.setDisable(true);
		button_Clr.setDisable(true);

		// Clear result elements.
		label_Result.setText("Result");
		text_Result.setText("");
		text_Result_error.setText("");
		massScaleResult.getSelectionModel().clearSelection();
		massScaleResult.getSelectionModel().selectFirst();
		massScaleResult.setPromptText("M");
		lengthScaleResult.getSelectionModel().clearSelection();
		lengthScaleResult.getSelectionModel().selectFirst();
		lengthScaleResult.setPromptText("L");
		timeScaleResult.getSelectionModel().clearSelection();
		timeScaleResult.getSelectionModel().selectFirst();
		timeScaleResult.setPromptText("T");
		massUnitsResult.getSelectionModel().clearSelection();
		lengthUnitsResult.getSelectionModel().clearSelection();
		timeUnitsResult.getSelectionModel().clearSelection();
		resultUnitValue_mass.setText("");
		resultUnitValue_length.setText("");
		resultUnitValue_time.setText("");

		perform.resetUnitData();
	}

	/**********
	 * Private local method to move contents of second text box to first.
	 */
	private void moveResultToFirst() {
		String resultMV = text_Result.getText(),
				resultET = text_Result_error.getText();
		text_Operand1.setText(resultMV);
		text_Operand1error.setText(resultET);

		text_Result.setText("");
		text_Result_error.setText("");
//		text_Operand2.setText("");
//		text_Operand2error.setText("");

//		massScaleOp1.getSelectionModel().clearSelection();
//		massUnitsOp1.getSelectionModel().clearSelection();
//		op1UnitValue_mass.setText("");
//		lengthScaleOp1.getSelectionModel().clearSelection();
//		lengthUnitsOp1.getSelectionModel().clearSelection();
//		op1UnitValue_length.setText("");
//		timeScaleOp1.getSelectionModel().clearSelection();
//		timeUnitsOp1.getSelectionModel().clearSelection();
//		op1UnitValue_time.setText("");

		populateUnits(massScaleOp1, massUnitsOp1, op1UnitValue_mass,
				lengthScaleOp1, lengthUnitsOp1, op1UnitValue_length,
				timeScaleOp1, timeUnitsOp1, op1UnitValue_time);

		resultUnitValue_mass.setText("");
		resultUnitValue_length.setText("");
		resultUnitValue_time.setText("");
		massScaleResult.getSelectionModel().clearSelection();
		massUnitsResult.getSelectionModel().clearSelection();
		lengthScaleResult.getSelectionModel().clearSelection();
		lengthUnitsResult.getSelectionModel().clearSelection();
		timeScaleResult.getSelectionModel().clearSelection();
		timeUnitsResult.getSelectionModel().clearSelection();

//		op2UnitValue_mass.setText("");
//		op2UnitValue_length.setText("");
//		op2UnitValue_time.setText("");
//		massScaleOp2.getSelectionModel().clearSelection();
//		massUnitsOp2.getSelectionModel().clearSelection();
//		lengthScaleOp2.getSelectionModel().clearSelection();
//		lengthUnitsOp2.getSelectionModel().clearSelection();
//		timeScaleOp2.getSelectionModel().clearSelection();
//		timeUnitsOp2.getSelectionModel().clearSelection();
	}

	/**
	 * This generic method populates the units for a number given the different UI elements to write to.
	 * @param massScale
	 * @param massUnits
	 * @param massValue
	 * @param lengthScale
	 * @param lengthUnits
	 * @param lengthValue
	 * @param timeScale
	 * @param timeUnits
	 * @param timeValue
	 */
	private void populateUnits(ComboBox<String> massScale, ComboBox<String> massUnits, TextField massValue, ComboBox<String> lengthScale, ComboBox<String> lengthUnits, TextField lengthValue, ComboBox<String> timeScale, ComboBox<String> timeUnits, TextField timeValue) {
		// Check if there is any data to read from. If there isn't, then return.
		String[] unitData = perform.getUnitData();
		if (unitData == null || unitData.length == 0)
			return;

		// Get the scale and name indices
		int mass_scale_index = Integer.parseInt(unitData[0]),
				mass_name_index = Integer.parseInt(unitData[1]),
				length_scale_index = Integer.parseInt(unitData[3]),
				length_name_index = Integer.parseInt(unitData[4]),
				time_scale_index = Integer.parseInt(unitData[6]),
				time_name_index = Integer.parseInt(unitData[7]);

		// And also the values
		double mass_value = Double.parseDouble(unitData[2]),
				length_value = Double.parseDouble(unitData[5]),
				time_value = Double.parseDouble(unitData[8]);

		// And start populating the ComboBoxes and TextFields.
		// The rule for ComboBoxes is that if the index is -1, then just select the first
		// element in the ComboBox. Otherwise, select based on the index.

		// Set the values for the ComboBoxes.
		if (mass_scale_index != -1)
			massScale.getSelectionModel().select(mass_scale_index);
		else
			massScale.getSelectionModel().selectFirst();

		if (mass_name_index != -1)
			massUnits.getSelectionModel().select(mass_name_index);
		else
			massUnits.getSelectionModel().selectFirst();

		if (length_scale_index != -1)
			lengthScale.getSelectionModel().select(length_scale_index);
		else
			lengthScale.getSelectionModel().selectFirst();

		if (length_name_index != -1)
			lengthUnits.getSelectionModel().select(length_name_index);
		else
			lengthUnits.getSelectionModel().selectFirst();

		if (time_scale_index != -1)
			timeScale.getSelectionModel().select(time_scale_index);
		else
			timeScale.getSelectionModel().selectFirst();

		if (time_name_index != -1)
			timeUnits.getSelectionModel().select(time_name_index);
		else
			timeUnits.getSelectionModel().selectFirst();

		// Set up TextFields.
		if (mass_value != -1)
			massValue.setText(Double.toString(mass_value));
		else
			massValue.setText("");
		if (length_value != -1)
			lengthValue.setText(Double.toString(length_value));
		else
			lengthValue.setText("");
		if (time_value != -1)
			timeValue.setText(Double.toString(time_value));
		else
			timeValue.setText("");
	}

	/**
	 * This method is called when the user clicks the "Change units" button.
	 */
	private void changeUnits() {
		// Get the new values for the units.
		String massScale = massScaleResult.getSelectionModel().getSelectedItem(),
				massUnit = massUnitsResult.getSelectionModel().getSelectedItem(),
				lenScale = lengthScaleResult.getSelectionModel().getSelectedItem(),
				lenUnit = lengthUnitsResult.getSelectionModel().getSelectedItem(),
				timeScale = timeScaleResult.getSelectionModel().getSelectedItem(),
				timeUnit = timeUnitsResult.getSelectionModel().getSelectedItem();

		// Pass them on to the BusinessLogic method and get the new result.
		String newResult = perform.changeUnits(
				massScale, massUnit,
				lenScale, lenUnit,
				timeScale, timeUnit);

		// Get the different parts of the string.
		String[] pieces = newResult.split(" ");
		text_Result.setText(pieces[0]);
		text_Result_error.setText(pieces[1]);
		perform.translateUnitString(pieces[2]);

		// And populate the units.
		populateUnits(massScaleResult, massUnitsResult, resultUnitValue_mass,
				lengthScaleResult, lengthUnitsResult, resultUnitValue_length,
				timeScaleResult, timeUnitsResult, resultUnitValue_time);
	}

	/**********
	 * Private local method to enable the five(?) buttons if they are disabled.
	 */
	private void enableButtonsIfDisabled() {
		String operand1Text = text_Operand1.getText(),
				operand2Text = text_Operand2.getText(),
				operand1ErrorTermText = text_Operand1error.getText(),
				operand2ErrorTermText = text_Operand2error.getText(),
				resultText = text_Result.getText(),
				resultErrorTermText = text_Result_error.getText();

		/*
		 * A bit of a special case. If there is nothing in the second operand text bar,
		 * then enabling this button makes no sense.
		 */
		if (!resultText.isEmpty() && ! resultErrorTermText.isEmpty()) {
			if (button_MoveToFirst.isDisabled())
				button_MoveToFirst.setDisable(false);

			if (button_ChangeUnits.isDisabled())
				button_ChangeUnits.setDisable(false);
		} else {
			if (!button_MoveToFirst.isDisabled())
				button_MoveToFirst.setDisable(true);

			if (!button_ChangeUnits.isDisabled())
				button_ChangeUnits.setDisable(true);
		}

		if (!operand1Text.isEmpty() || !operand1ErrorTermText.isEmpty() || !operand2Text.isEmpty() || !operand2ErrorTermText.isEmpty()) {
			/*
			 * If any of the text boxes has something in it, then enable all disabled buttons.
			 */
			if (button_Add.isDisabled())
				button_Add.setDisable(false);

			if (button_Sub.isDisabled())
				button_Sub.setDisable(false);

			if (button_Mpy.isDisabled())
				button_Mpy.setDisable(false);

			if (button_Div.isDisabled())
				button_Div.setDisable(false);

			if (button_Sqrt.isDisabled())
				button_Sqrt.setDisable(false);

			if(button_Clr.isDisabled())
				button_Clr.setDisable(false);
		} else {
			/*
			 * Otherwise, disable all enabled buttons.
			 */
			if (!button_Add.isDisabled())
				button_Add.setDisable(true);

			if (!button_Sub.isDisabled())
				button_Sub.setDisable(true);

			if (!button_Mpy.isDisabled())
				button_Mpy.setDisable(true);

			if (!button_Div.isDisabled())
				button_Div.setDisable(true);

			if (!button_Sqrt.isDisabled())
				button_Sqrt.setDisable(true);

			if(!button_Clr.isDisabled())
				button_Clr.setDisable(true);
		}
	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupComboBoxUI(ComboBox<String> list, double x, double y, double w) {

		list.setMinWidth(w);
		list.setMaxWidth(w);
		list.setLayoutX(x);
		list.setLayoutY(y);
	}
	
	
	/**********************************************************************************************

	User Interface Actions
	
	**********************************************************************************************/

	/**********
	 * Private local method to set the value of the first operand given a text value. The method uses the
	 * business logic class to perform the work of checking the string to see it is a valid value and if 
	 * so, saving that value internally for future computations. If there is an error when trying to convert
	 * the string into a value, the called business logic method returns false and actions are taken to
	 * display the error message appropriately.
	 */
	private void setOperand1() {
		enableButtonsIfDisabled();
		text_Result.setText("");								// Any change of an operand probably invalidates
		text_Result_error.setText("");
		label_Result.setText("Result");						// the result, so we clear the old result.
		label_errResult.setText("");
		err1MVPart1.setText("");
		err1MVPart2.setText("");
		err1MVEPart1.setText("");
		err1MVEPart2.setText("");

		// Create the units string.
		StringBuilder sb = new StringBuilder();
		String temp;

		sb.append(massScaleOp1.getSelectionModel().getSelectedItem());
		sb.append(":");
		sb.append(massUnitsOp1.getSelectionModel().getSelectedItem());
		sb.append(":");
		temp = op1UnitValue_mass.getText();
		sb.append(temp.length() > 0 ? temp : "0");
		sb.append("|");

		sb.append(lengthScaleOp1.getSelectionModel().getSelectedItem());
		sb.append(":");
		sb.append(lengthUnitsOp1.getSelectionModel().getSelectedItem());
		sb.append(":");
		temp = op1UnitValue_length.getText();
		sb.append(temp.length() > 0 ? temp : "0");
		sb.append("|");

		sb.append(timeScaleOp1.getSelectionModel().getSelectedItem());
		sb.append(":");
		sb.append(timeUnitsOp1.getSelectionModel().getSelectedItem());
		sb.append(":");
		temp = op1UnitValue_time.getText();
		sb.append(temp.length() > 0 ? temp : "0");

		System.out.println("Setting operand 1 - mv: " + text_Operand1.getText() + ",  et: " + text_Operand1error.getText() + ", unit: " + sb.toString());
		if (perform.setOperand1(text_Operand1.getText(), text_Operand1error.getText(), sb.toString())) {	// Set the operand and see if there was an error
			label_errOperand1.setText("");						// If no error, clear this operands error
			label_errOperand1error.setText("");					// If no error, clear this operands error
			if (text_Operand2.getText().length() == 0)			// If the other operand is empty, clear its error
				label_errOperand2.setText("");					// as well.
			if (text_Operand2error.getText().length() == 0)		// If the other operand is empty, clear its error
				label_errOperand2error.setText("");				// as well.
			// Since unit error messages aren't fatal (power value is just set to 0), just display them.
			label_errOperand1Unit.setText(perform.getOperand1UnitErrorMessage());
			label_errOperand2Unit.setText(perform.getOperand2UnitErrorMessage());
		}
		else {
			// If there's a problem with the operand, display
			if(!perform.getOperand1ErrorMessage().equals("")) {
				label_errOperand1.setText(perform.getOperand1ErrorMessage());	// the error message that was generated
				if (MeasuredValueRecognizer.measuredValueIndexofError <= -1) return;
				String input = MeasuredValueRecognizer.measuredValueInput;
				err1MVPart1.setText(input.substring(0, MeasuredValueRecognizer.measuredValueIndexofError));
				err1MVPart2.setText("\u21EB");
			}else {
				label_errOperand1error.setText(perform.getOperand1errErrorMessage());	// the error message that was generated
				if (MeasuredValueRecognizer.measuredValueIndexofError <= -1) return;
				String input = MeasuredValueRecognizer.measuredValueInput;
				err1MVEPart1.setText(input.substring(0, MeasuredValueRecognizer.measuredValueIndexofError));
				err1MVEPart2.setText("\u21EB");
			}
			
		}
			
	}

	/**********
	 * Private local method to set the value of the second operand given a text value. The logic is exactly the
	 * same as used for the first operand, above.
	 */
	private void setOperand2() {
		enableButtonsIfDisabled();
		text_Result.setText("");								// See setOperand1's comments. The logic is the same!
		text_Result_error.setText("");
		label_Result.setText("Result");				
		label_errResult.setText("");
		err2MVPart1.setText("");
		err2MVPart2.setText("");
		err2MVEPart1.setText("");
		err2MVEPart2.setText("");

		StringBuilder sb = new StringBuilder();
		String temp;

		sb.append(massScaleOp2.getSelectionModel().getSelectedItem());
		sb.append(":");
		sb.append(massUnitsOp2.getSelectionModel().getSelectedItem());
		sb.append(":");
		temp = op2UnitValue_mass.getText();
		sb.append(temp.length() > 0 ? temp : "0");
		sb.append("|");

		sb.append(lengthScaleOp2.getSelectionModel().getSelectedItem());
		sb.append(":");
		sb.append(lengthUnitsOp2.getSelectionModel().getSelectedItem());
		sb.append(":");
		temp = op2UnitValue_length.getText();
		sb.append(temp.length() > 0 ? temp : "0");
		sb.append("|");

		sb.append(timeScaleOp2.getSelectionModel().getSelectedItem());
		sb.append(":");
		sb.append(timeUnitsOp2.getSelectionModel().getSelectedItem());
		sb.append(":");
		temp = op2UnitValue_time.getText();
		sb.append(temp.length() > 0 ? temp : "0");

		System.out.println("Setting operand 2 - mv: " + text_Operand2.getText() + ",  et: " + text_Operand2error.getText() + ", unit: " + sb.toString());
		if (perform.setOperand2(text_Operand2.getText(),text_Operand2error.getText(), sb.toString())) {
			label_errOperand2.setText("");
			label_errOperand2error.setText("");
			if (text_Operand1.getText().length() == 0)
				label_errOperand1.setText("");
			if (text_Operand1error.getText().length() == 0)
				label_errOperand1error.setText("");
			label_errOperand1Unit.setText(perform.getOperand1UnitErrorMessage());
			label_errOperand2Unit.setText(perform.getOperand2UnitErrorMessage());
		}
		else {
			if(!perform.getOperand2ErrorMessage().equals("")) {
				label_errOperand2.setText(perform.getOperand2ErrorMessage());
				if (MeasuredValueRecognizer.measuredValueIndexofError <= -1) return;
				String input = MeasuredValueRecognizer.measuredValueInput;
				err2MVPart1.setText(input.substring(0, MeasuredValueRecognizer.measuredValueIndexofError));
				err2MVPart2.setText("\u21EB");
			}else {
				label_errOperand2error.setText(perform.getOperand2errErrorMessage());
				if (MeasuredValueRecognizer.measuredValueIndexofError <= -1) return;
				String input = MeasuredValueRecognizer.measuredValueInput;
				err2MVEPart1.setText(input.substring(0, MeasuredValueRecognizer.measuredValueIndexofError));
				err2MVEPart2.setText("\u21EB");
			}
		}
			
	}
	
	/**********
	 * This method is called when an binary operation button has been pressed. It assesses if there are issues 
	 * with either of the binary operands or they are not defined. If not return false (there are no issues)
	 * 
	 * @return	True if there are any issues that should keep the calculator from doing its work.
	 */
	private boolean binaryOperandIssues() {
		String errorMessage1 = perform.getOperand1ErrorMessage();	// Fetch the error messages, if there are any
		String errorMessage2 = perform.getOperand2ErrorMessage();
//		String errorMessage3 = perform.getOperand1errErrorMessage();	// Fetch the error messages, if there are any
//		String errorMessage4 = perform.getOperand2errErrorMessage();
		if (errorMessage1.length() > 0) {						// Check the first.  If the string is not empty
			label_errOperand1.setText(errorMessage1);			// there's an error message, so display it.
			if (errorMessage2.length() > 0) {					// Check the second and display it if there is
				label_errOperand2.setText(errorMessage2);		// and error with the second as well.
				return true;										// Return true when both operands have errors
			}
			else {
				return true;										// Return true when only the first has an error
			}
		}
		else if (errorMessage2.length() > 0) {					// No error with the first, so check the second
			label_errOperand2.setText(errorMessage2);			// operand. If non-empty string, display the error
			return true;											// message and return true... the second has an error
		}														// Signal there are issues
		
		// If the code reaches here, neither the first nor the second has an error condition. The following code
		// check to see if the operands are defined.
		if (!perform.getOperand1Defined()) {						// Check to see if the first operand is defined
			label_errOperand1.setText("No value found");			// If not, this is an issue for a binary operator
			if (!perform.getOperand2Defined()) {					// Now check the second operand. It is is also
				label_errOperand2.setText("No value found");		// not defined, then two messages should be displayed
				return true;										// Signal there are issues
			}
			return true;
		} else if (!perform.getOperand2Defined()) {				// If the first is defined, check the second. Both
			label_errOperand2.setText("No value found");			// operands must be defined for a binary operator.
			return true;											// Signal there are issues
		}
		
		return false;											// Signal there are no issues with the operands
	}
	
	private boolean uniOperandIssues() {
		String errorMessage1 = perform.getOperand1ErrorMessage();	// Fetch the error messages, if there are any
		if (errorMessage1.length() > 0) {						// Check the first.  If the string is not empty
			label_errOperand1.setText(errorMessage1);			// there's an error message, so display it.
			return true;										// Return true when only the first has an error
		}

		// If the code reaches here, neither the first nor the second has an error condition. The following code
		// check to see if the operands are defined.
		if (!perform.getOperand1Defined()) {						// Check to see if the first operand is defined
			label_errOperand1.setText("No value found");			// If not, this is an issue for a binary operator
			return true;
		}
		
		return false;											// Signal there are no issues with the operands
	}

	/*******************************************************************************************************
	 * This portion of the class defines the actions that take place when the various calculator
	 * buttons (add, subtract, multiply, and divide) are pressed.
	 */

	/**********
	 * This is the add routine
	 * 
	 */
	private void addOperands(){
		// Check to see if both operands are defined and valid
		if (binaryOperandIssues()) 									// If there are issues with the operands, return
			return;													// without doing the computation
		
		// If the operands are defined and valid, request the business logic method to do the addition and return the
		// result as a String. If there is a problem with the actual computation, an empty string is returned
		String theAnswer = perform.addition();						// Call the business logic add method
		String[] ans = theAnswer.split(" ");
		label_errResult.setText("");									// Reset any result error messages from before
		if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
			text_Result.setText(ans[0]);							// If okay, display it in the result field and
			text_Result_error.setText(ans[1]);
			populateUnits(massScaleResult, massUnitsResult, resultUnitValue_mass,
					lengthScaleResult, lengthUnitsResult, resultUnitValue_length,
					timeScaleResult, timeUnitsResult, resultUnitValue_time);
			label_Result.setText("Sum");								// change the title of the field to "Sum"
		}
		else {														// Some error occurred while doing the addition.
			text_Result.setText("");									// Do not display a result if there is an error.				
			label_Result.setText("Result");							// Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
		}
	}

	/**********
	 * This is the subtract routine
	 * 
	 */
	private void subOperands(){
		// Check to see if both operands are defined and valid
		if (binaryOperandIssues()) 									// If there are issues with the operands, return
			return;													// without doing the computation
		
		// If the operands are defined and valid, request the business logic method to do the subtraction and return the
		// result as a String. If there is a problem with the actual computation, an empty string is returned
		String theAnswer = perform.subtraction();						// Call the business logic add method
		String[] ans = theAnswer.split(" ");
		label_errResult.setText("");									// Reset any result error messages from before
		if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
			text_Result.setText(ans[0]);							// If okay, display it in the result field and
			text_Result_error.setText(ans[1]);
			populateUnits(massScaleResult, massUnitsResult, resultUnitValue_mass,
					lengthScaleResult, lengthUnitsResult, resultUnitValue_length,
					timeScaleResult, timeUnitsResult, resultUnitValue_time);
			label_Result.setText("Difference");								// change the title of the field to "Sub"
		}
		else {														// Some error occurred while doing the subtract.
			text_Result.setText("");									// Do not display a result if there is an error.				
			label_Result.setText("Result");							// Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
		}
	}

	/**********
	 * This is the multiply routine
	 * 
	 */
	private void mpyOperands(){
		// Check to see if both operands are defined and valid
		if (binaryOperandIssues()) 									// If there are issues with the operands, return
			return;													// without doing the computation
		
		// If the operands are defined and valid, request the business logic method to do the multiplication and return the
		// result as a String. If there is a problem with the actual computation, an empty string is returned
		String theAnswer = perform.multiplication();						// Call the business logic add method
		System.out.println("the answer: " + theAnswer);
		String[] ans = theAnswer.split(" ");
		label_errResult.setText("");									// Reset any result error messages from before
		if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
			System.out.println("ans[0]: " + ans[0]);
			text_Result.setText(ans[0]);							// If okay, display it in the result field and
			text_Result_error.setText(ans[1]);
			populateUnits(massScaleResult, massUnitsResult, resultUnitValue_mass,
					lengthScaleResult, lengthUnitsResult, resultUnitValue_length,
					timeScaleResult, timeUnitsResult, resultUnitValue_time);
			label_Result.setText("Product");								// change the title of the field to "Mpy"
		}
		else {														// Some error occurred while doing the multiplication.
			text_Result.setText("");									// Do not display a result if there is an error.				
			label_Result.setText("Result");							// Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
		}
	}

	/**********
	 * This is the divide routine.  If the divisor is zero, the divisor is declared to be invalid.
	 * 
	 */
	private void divOperands(){
		// Check to see if both operands are defined and valid
		if (binaryOperandIssues()) 									// If there are issues with the operands, return
			return;													// without doing the computation
		
		// If the operands are defined and valid, request the business logic method to do the division and return the
		// result as a String. If there is a problem with the actual computation, an empty string is returned
		String theAnswer = perform.division();						// Call the business logic add method
		String[] ans = theAnswer.split(" ");
		label_errResult.setText("");									// Reset any result error messages from before
		if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
			text_Result.setText(ans[0]);							// If okay, display it in the result field and
			text_Result_error.setText(ans[1]);
			populateUnits(massScaleResult, massUnitsResult, resultUnitValue_mass,
					lengthScaleResult, lengthUnitsResult, resultUnitValue_length,
					timeScaleResult, timeUnitsResult, resultUnitValue_time);
			label_Result.setText("division");								// change the title of the field to "div"
		}
		else {														// Some error occurred while doing the division.
			text_Result.setText("");									// Do not display a result if there is an error.				
			label_Result.setText("Result");							// Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
		}
	}
	
	
	/**********
	 * This is the Square root routine.  If the number is negative, the number is declared to be invalid.
	 * 
	 */
	private void SqrOperands(){
		// Check to see if both operands are defined and valid
		if (uniOperandIssues()) 									// If there are issues with the operands, return
			return;													// without doing the computation
		
		// If the operands are defined and valid, request the business logic method to do the division and return the
		// result as a String. If there is a problem with the actual computation, an empty string is returned
		String theAnswer = perform.SquareRoot();						// Call the business logic add method
		String[] ans = theAnswer.split(" ");
		label_errResult.setText("");									// Reset any result error messages from before
		if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
			text_Result.setText(ans[0]);							// If okay, display it in the result field and
			text_Result_error.setText(ans[1]);
			populateUnits(massScaleResult, massUnitsResult, resultUnitValue_mass,
					lengthScaleResult, lengthUnitsResult, resultUnitValue_length,
					timeScaleResult, timeUnitsResult, resultUnitValue_time);
			label_Result.setText("SquareRoot for operand 1");								// change the title of the field to "div"
		}
		else {														// Some error occurred while doing the division.
			text_Result.setText("");									// Do not display a result if there is an error.				
			label_Result.setText("Result");							// Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
		}
	}
}
