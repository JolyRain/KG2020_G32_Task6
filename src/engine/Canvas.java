/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;


import engine.shapes.IShape;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Canvas {
    private List<IShape> shapes;
    private Map<IShape, Boolean> selectShapesMap = new HashMap<>();

    public Canvas() {
        shapes = new LinkedList<>();
        initMap();
    }

    public Canvas(List<IShape> shapes) {
        this.shapes = shapes;
        initMap();
    }

    public void removeShape(IShape shape) {
        shapes.remove(shape);
    }

    public void clear() {
        shapes.clear();
        selectShapesMap.clear();
    }

    private void initMap() {
        for (IShape shape : shapes) {
            setUnelected(shape);
        }
    }

    public List<IShape> getSelectedShapes() {
        List<IShape> shapes = new LinkedList<>();
        for (Map.Entry<IShape, Boolean> entry : selectShapesMap.entrySet()) {
            if (entry.getValue()) shapes.add(entry.getKey());
        }
        return shapes;
    }

    public List<IShape> getShapes() {
        return shapes;
    }

    public void setShapes(List<IShape> shapes) {
        this.shapes = shapes;
        initMap();
    }

    public void addShape(IShape shape) {
        shapes.add(shape);
        setUnelected(shape);
    }

    public void addShapes(List<IShape> shapes) {
        this.shapes.addAll(shapes);
        initMap();
    }

    public Map<IShape, Boolean> getSelectShapesMap() {
        return selectShapesMap;
    }

    public void setSelectShapesMap(Map<IShape, Boolean> selectShapesMap) {
        this.selectShapesMap = selectShapesMap;
    }

    public void setSelected(IShape shape) {
        selectShapesMap.put(shape, true);
    }


    public void setUnelected(IShape shape) {
        selectShapesMap.put(shape, false);
    }
}
