package app;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawers.canvasDrawers.CanvasDrawer;
import drawers.canvasDrawers.ICanvasDrawer;
import engine.Canvas;
import engine.ReferencePoint;
import engine.shapes.IShape;
import engine.shapes.Style;
import math.Field;
import math.Vector2;
import math.affineTransformation.Rotate;
import screen.ScreenConverter;
import screen.ScreenPoint;
import utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static defaults.Defaults.ZOOM_DECREASE;
import static defaults.Defaults.ZOOM_INCREASE;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel implements ActionListener, KeyListener,
        MouseListener, MouseMotionListener, MouseWheelListener {
    private ScreenConverter screenConverter;
    private Canvas canvas = new Canvas();
    private Stack<IShape> shapes = new Stack<>();
    private Stack<IShape> removedShapes = new Stack<>();
    private boolean shift = false;
    private ScreenPoint lastPosition = null;
    private boolean grab = false;
    private IShape selectedShape = null;
    private ReferencePoint selectedRefPoint = null;
    private StylePanel stylePanel = new StylePanel();

    DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        Field field = new Field(-10, 10, 20, 20);
        screenConverter = new ScreenConverter(field, 1000, 1000); //magic number

    }

    public IShape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(IShape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setWs(getWidth());  //че нибудь придумать
        screenConverter.setHs(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) bi.getGraphics();
        graphics2D.setColor(new Color(230, 230, 230));
        graphics2D.fillRect(0, 0, screenConverter.getWs(), screenConverter.getHs());
        ICanvasDrawer canvasDrawer = new CanvasDrawer(graphics2D, screenConverter);

//        if (currentNewLine != null) canvas.addShape(currentNewLine);
//        if (currentNewRect != null) canvas.addShape(currentNewRect);

        canvasDrawer.draw(canvas);
        g.drawImage(bi, 0, 0, null);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void addShape(IShape shape) {
        canvas.addShape(shape);
        shapes.push(shape);
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            IShape removed = shapes.pop();
            removedShapes.push(removed);
            canvas.removeShape(removed);
            if (!shapes.isEmpty()) canvas.addShape(shapes.pop());
            System.out.println(canvas.getShapes());
        }
    }

    public void redo() {
        if (!removedShapes.isEmpty()) {
            IShape restored = removedShapes.pop();
            shapes.push(restored);
            canvas.addShape(restored);
        }
    }

    public void clear() {
        canvas.clear();
        shapes.clear();
        removedShapes.clear();
        selectedShape = null;
        selectedRefPoint = null;
        grab = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        affine.setA(2);
//        affine.setC(1);
//        affine.setB(0.1);
        Rotate f = new Rotate(Math.PI / 12);

        lastPosition = new ScreenPoint(e.getX(), e.getY());
        if (canvas.getShapes().size() > 0) {
            Vector2 point = screenConverter.s2r(new ScreenPoint(e.getX(), e.getY()));
            for (IShape shape : canvas.getShapes()) {
                if (shape.isInnerPoint(point)) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        canvas.setSelected(shape);
                        selectedShape = shape;
                        grab = true;
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        canvas.setSelected(shape);
                        selectedShape = shape;
                        selectedShape.setStyle(stylePanel.getStyle());
                    }
                } else canvas.setUnelected(shape);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3) lastPosition = null;
        else if (e.getButton() == MouseEvent.BUTTON1) {
            grab = false;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
        if (grab) {
            if (getGrab(currentPosition) != null && lastPosition != null) {
                ScreenPoint position = getGrab(currentPosition).getSecond();
                ReferencePoint referencePoint = getGrab(currentPosition).getFirst();
                ScreenPoint deltaScreen = new ScreenPoint(
                        currentPosition.getI() - lastPosition.getI(),
                        currentPosition.getJ() - lastPosition.getJ());
                ScreenPoint vector = new ScreenPoint(position.getI() + deltaScreen.getI(), position.getJ() + deltaScreen.getJ());
                Vector2 deltaReal = screenConverter.s2r(vector);
                System.out.println(selectedShape.getWrapper().getPoints().size());
                selectedShape.setReferencePoint(new ReferencePoint(deltaReal, selectedRefPoint.getType()));
                lastPosition = currentPosition;
                repaint();
            } else if (lastPosition != null) {
                ScreenPoint deltaScreen = new ScreenPoint(
                        currentPosition.getI() - lastPosition.getI(),
                        currentPosition.getJ() - lastPosition.getJ());
                Vector2 deltaReal = screenConverter.s2r(deltaScreen);
                Vector2 zeroReal = screenConverter.s2r(new ScreenPoint(0, 0));
                Vector2 newVector = new Vector2(
                        deltaReal.getX() - zeroReal.getX(),
                        deltaReal.getY() - zeroReal.getY());
                selectedShape.getWrapper().getAffineTransform().getTransfer().setVector(
                        selectedShape.getWrapper().getAffineTransform().getTransfer().getVector().plus(newVector)
                );
                lastPosition = currentPosition;
                repaint();
                return;
            }
            return;
        }

        if (lastPosition != null) {
            ScreenPoint deltaScreen = new ScreenPoint(
                    currentPosition.getI() - lastPosition.getI(),
                    currentPosition.getJ() - lastPosition.getJ());
            Vector2 deltaReal = screenConverter.s2r(deltaScreen);
            Vector2 zeroReal = screenConverter.s2r(new ScreenPoint(0, 0));
            Vector2 vector = new Vector2(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY());
            screenConverter.setXr(screenConverter.getXr() - vector.getX());
            screenConverter.setYr(screenConverter.getYr() - vector.getY());
            lastPosition = currentPosition;
        }

        repaint();
    }

    private Pair<ReferencePoint, ScreenPoint> getGrab(ScreenPoint grabPoint) {
        Vector2 grab = screenConverter.s2r(grabPoint);
        double x = grab.getX(), y = grab.getY();
        for (IShape shape : canvas.getSelectedShapes()) {
            for (ReferencePoint referencePoint : shape.getWrapper().getPoints()) {
                if (referencePoint.isInnerPoint(grab)) {
                    selectedRefPoint = referencePoint;
                    return new Pair<>(referencePoint, grabPoint);
                }
            }
        }
        return null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double zoom = 1;
        double coefficient = clicks < 0 ? ZOOM_INCREASE : ZOOM_DECREASE;
        for (int i = 0; i < Math.abs(clicks); i++) {
            zoom *= coefficient;
        }
        screenConverter.scale(zoom);
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = true;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = false;
            repaint();
        }
    }

    private class StylePanel extends JPanel {

        private JSpinner spinInt = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        private JCheckBox filled = new JCheckBox("filled");
        private Style style;
        private JLabel strokeLabel = new JLabel("stroke width");
        private JLabel borderColorLabel = new JLabel("border color");
        private JLabel fillColorLabel = new JLabel("fill color");


        StylePanel() {
            this.add(filled);
            this.add(strokeLabel);
            this.add(spinInt);
            this.add(borderColorLabel);
            this.add(borderComboBox);
            this.add(fillColorLabel);
            this.add(fillComboBox);
            init();
        }

        private void clear() {
            filled.setSelected(selectedShape.getStyle().isFilled());
            borderComboBox.setSelectedItem(selectedShape.getStyle().getBorderColor());
            fillComboBox.setSelectedItem(selectedShape.getStyle().getColor());

        }

        Style getStyle() {
            clear();
            style = selectedShape.getStyle();
            this.setVisible(true);
            int result = JOptionPane.showConfirmDialog(null, this,
                    "Style", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    style.setFilled(filled.isSelected());
                    style.setStroke(new BasicStroke((Integer) spinInt.getValue()));
                    style.setColor(COLORS.get(fillComboBox.getSelectedItem()));
                    style.setBorderColor(COLORS.get(borderComboBox.getSelectedItem()));

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return style;
                }
            } else return style;
            return style;
        }

        private final Map<String, Color> COLORS = new HashMap<>();

        private final String[] nameColors = {"Black", "Yellow", "Orange", "Blue", "Red",
                "Cyan", "Gray", "Green", "Magenta", "Pink", "White"};

        private final JComboBox<String> fillComboBox = new JComboBox<>(nameColors);
        private final JComboBox<String> borderComboBox = new JComboBox<>(nameColors);

        private void init() {
            COLORS.put(nameColors[0], Color.BLACK);
            COLORS.put(nameColors[1], Color.YELLOW);
            COLORS.put(nameColors[2], Color.ORANGE);
            COLORS.put(nameColors[3], Color.BLUE);
            COLORS.put(nameColors[4], Color.RED);
            COLORS.put(nameColors[5], Color.CYAN);
            COLORS.put(nameColors[6], Color.GRAY);
            COLORS.put(nameColors[7], Color.GREEN);
            COLORS.put(nameColors[8], Color.MAGENTA);
            COLORS.put(nameColors[9], Color.PINK);
            COLORS.put(nameColors[10], Color.WHITE);
        }


    }
}
