package app;

import engine.shapes.Rectangle;
import engine.shapes.*;
import math.Vector2;
import screen.ScreenConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame implements ActionListener {
    private static final int WIDTH_OF_PANEL = 100;
    private static final int WIDTH_COORDINATE_PANEL = 50;
    private static final Font FONT = new Font(Font.SERIF, Font.PLAIN, 25);
    private static final int WIDTH_LABEL = 200;

    private final JButton rectButton = new JButton("rect");
    private final JButton lineButton = new JButton("line");
    private final JButton clear = new JButton("Clear");
    private final JButton undo = new JButton("Undo");
    private final JLabel colorLabel = new JLabel();
    private DrawPanel drawPanel;
    private JPanel buttonsPanel;
    private JPanel checksPanel;
    private JComponent shapes;
    private JLabel velLabel = new JLabel();
    private JButton redo = new JButton("Redo");
    private JButton diamondButton = new JButton("Diamond");
    private JButton triangleButton = new JButton("Triangle");
    private JButton ellipseButton = new JButton("Ellipse");
    private JComponent style;
    private JCheckBox filled = new JCheckBox();
    private IShape selectedShape;
    private boolean showStyle = false;

    public MainWindow() throws HeadlessException {
        super("Solar system");
        this.addMouseListener(drawPanel);


        panel();
    }

    private void panel() {
        drawPanel = new DrawPanel();
        this.add(drawPanel);

        setButtonsPanel();
        buttonsPanel.setPreferredSize(new Dimension(500, getHeight()));
        this.add(buttonsPanel, BorderLayout.WEST);

        JTabbedPane tabbedPane = new JTabbedPane();
        shapes = new JPanel();
        tabbedPane.addTab("Shapes", shapes);

        setAddShapePanel();
        tabbedPane.setPreferredSize(new Dimension(400, 400));
        tabbedPane.setFont(FONT);
        buttonsPanel.add(tabbedPane);

    }

    private void addStyle() {
        JTabbedPane tabbedPaneStyle = new JTabbedPane();
        style = new JPanel();
        tabbedPaneStyle.addTab("Style", style);
        setStylePane();
        tabbedPaneStyle.setPreferredSize(new Dimension(400, 400));
        tabbedPaneStyle.setFont(FONT);
        buttonsPanel.add(tabbedPaneStyle);
        tabbedPaneStyle.setVisible(showStyle);

    }
    private void removeStyle() {
        style = null;
    }

    private void setStylePane() {
        filled.setSelected(false);
        filled.setText("filled");
        filled.setPreferredSize(new Dimension(300, 25));
        filled.setFont(FONT);
        filled.addActionListener(e -> {
            drawPanel.getSelectedShape().getStyle().setFilled(true);
            drawPanel.repaint();
        });
        style.add(filled);
    }

    public IShape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(IShape selectedShape) {
        this.selectedShape = selectedShape;
    }

    private void setButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(500, getHeight()));

        rectButton.setPreferredSize(new Dimension(300, 25));
        rectButton.setFont(FONT);
        rectButton.addActionListener(e -> {
        });
        buttonsPanel.add(rectButton);

        lineButton.setPreferredSize(new Dimension(300, 25));
        lineButton.addActionListener(actionEvent -> {
        });
        lineButton.setFont(FONT);
        buttonsPanel.add(lineButton);

        clear.setPreferredSize(new Dimension(300, 25));
        clear.addActionListener(e -> {
            drawPanel.clear();
            repaint();
        });
        clear.setFont(FONT);
        buttonsPanel.add(clear);

        undo.setPreferredSize(new Dimension(300, 25));
        undo.addActionListener(e -> {
            drawPanel.undo();
            repaint();
        });
        undo.setFont(FONT);
        buttonsPanel.add(undo);


        redo.setPreferredSize(new Dimension(300, 25));
        redo.addActionListener(e -> {
            drawPanel.redo();
            repaint();
        });
        redo.setFont(FONT);
        buttonsPanel.add(redo);

//        buttonsPanel.add(reset);


    }

    private void setAddShapePanel() {
        rectButton.setPreferredSize(new Dimension(300, 25));
        rectButton.setFont(FONT);
        rectButton.addActionListener(e -> {
            ScreenConverter sc = drawPanel.getScreenConverter();
            drawPanel.addShape(
                    new Rectangle(
                            new Vector2(-2, 2),
                            new Vector2(2, -2)));
            drawPanel.repaint();
        });
        shapes.add(rectButton);

        lineButton.setPreferredSize(new Dimension(300, 25));
        lineButton.setFont(FONT);
        lineButton.addActionListener(e -> {
            ScreenConverter sc = drawPanel.getScreenConverter();
            drawPanel.addShape(
                    new Line(
                            new Vector2(-2, 2),
                            new Vector2(2, -2)));
            drawPanel.repaint();
        });
        shapes.add(lineButton);

        ellipseButton.setPreferredSize(new Dimension(300, 25));
        ellipseButton.setFont(FONT);
        ellipseButton.addActionListener(e -> {
            ScreenConverter sc = drawPanel.getScreenConverter();
            drawPanel.addShape(
                    new Ellipse(
                            new Vector2(0, 0),
                            3, 3));
            drawPanel.repaint();
        });
        shapes.add(ellipseButton);

        diamondButton.setPreferredSize(new Dimension(300, 25));
        diamondButton.setFont(FONT);
        diamondButton.addActionListener(e -> {
            ScreenConverter sc = drawPanel.getScreenConverter();
            drawPanel.addShape(
                    new Diamond(
                            new Vector2(-2, 2),
                            new Vector2(2, 2)));
            drawPanel.repaint();
        });
        shapes.add(diamondButton);


        triangleButton.setPreferredSize(new Dimension(300, 25));
        triangleButton.setFont(FONT);
        triangleButton.addActionListener(e -> {
            ScreenConverter sc = drawPanel.getScreenConverter();
            drawPanel.addShape(
                    new Triangle(
                            new Vector2(-2, 2),
                            new Vector2(2, 2)));
            drawPanel.repaint();
        });
        shapes.add(triangleButton);
    }

    private void addELabel(String text) {
        JLabel eLabel = new JLabel();
        eLabel.setText(text);
        eLabel.setPreferredSize(new Dimension(75, 20));
        shapes.add(eLabel);

    }

    private void addLabel(JLabel label, String text) {
        label.setPreferredSize(new Dimension(200, 25));
        label.setFont(FONT);
        label.setText(text);
        shapes.add(label);
    }

    private void addLongField(JTextField field) {
        field.setPreferredSize(new Dimension(100, 25));
        shapes.add(field);
    }

    private void addSmallField(JTextField field) {
        field.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        shapes.add(field);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        addStyle();
    }
}

