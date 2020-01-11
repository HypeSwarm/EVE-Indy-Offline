/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.BlueprintTree.SortableObject;
import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import sde.Blueprints.Blueprint;
import sde.FileHandler;
import sde.MarketGroups.MarketGroup;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class BlueprintTree {
    public static final ArrayList<SortableObject> ALL_ADDED_BLUEPRINTS=new ArrayList<>();
    public static BlueprintSearchable searchBlueprints;
    
    public static void loadBlueprintTree(){
        MainInterface.blueprintTree.setModel(loadTree());
        MainInterface.blueprintTree.setCellRenderer(new IconTreeCellRenderer());
        MainInterface.blueprintTree.setRootVisible(false);
        searchBlueprints=new BlueprintSearchable();
    }
        
    private static TreeModel loadTree() {
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("Root");
        DefaultTreeModel model = new DefaultTreeModel(root);
        ArrayList<MarketGroup> rootGroups=SDEDatabase.MARKET_GROUPS.getSubMarketGroups(0);
        ArrayList<SortableObject> objs=new ArrayList<>();
        rootGroups.stream().forEach((rootGroup) -> {
            objs.add(new SortableObject(rootGroup));
        });
        Collections.sort(objs);
        objs.stream().map((obj) -> buildTreeNode(obj)).filter((node) -> (node!=null&&node.getChildCount()>0)).forEach((node) -> {
            root.add(node);
        });
        return model;
    }
    
    private static DefaultMutableTreeNode buildTreeNode(SortableObject SO){
        DefaultMutableTreeNode root=new DefaultMutableTreeNode(SO);
        ArrayList<MarketGroup> subGroups=SDEDatabase.MARKET_GROUPS.getSubMarketGroups(SO.marketGroup.getGroupID());
        ArrayList<SortableObject> objs=new ArrayList<>();
        if(subGroups.isEmpty()){
            ArrayList<Integer> itemIDs=SDEDatabase.TYPE_IDS.getByMarketGroup(SO.marketGroup.getGroupID());
            if(!itemIDs.isEmpty()){
                itemIDs.stream().filter((itemID) -> (SDEDatabase.BLUEPRINTS.isBlueprintOutcome(itemID))).map((itemID) -> SDEDatabase.BLUEPRINTS.getBlueprintByOutputID(itemID)).filter((blueprint) -> (SDEDatabase.TYPE_IDS.isPublished(blueprint.getBluePrintID()))).forEach((blueprint) -> {
                    SortableObject newBlueprint =new SortableObject(blueprint);
                    objs.add(newBlueprint);
                    ALL_ADDED_BLUEPRINTS.add(newBlueprint);
                });
                Collections.sort(objs);
                objs.stream().map((obj) -> new DefaultMutableTreeNode(obj)).forEach((node) -> {
                    root.add(node);
                });
            }else{
                return null;
            }
        }else{
            subGroups.stream().forEach((subGroup) -> {
                objs.add(new SortableObject(subGroup));
            });
            Collections.sort(objs);
            objs.stream().map((obj) -> buildTreeNode(obj)).filter((node) -> (node!=null&&node.getChildCount()>0)).forEach((node) -> {
                root.add(node);
            });
        }
        return root;
    }
    static class IconTreeCellRenderer implements TreeCellRenderer{
        private final JLabel label;

        IconTreeCellRenderer(){
            label=new JLabel();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Object obj=((DefaultMutableTreeNode)value).getUserObject();
            if(obj instanceof SortableObject){
                SortableObject SO=(SortableObject) obj;
                ImageIcon icon=null;
                try {
                    icon = SO.getIcon();
                } catch (IOException ex) {
                }
                label.setIcon(icon);
                if(SO.marketGroup!=null){
                    label.setText(SO.marketGroup.getName());
                }else{
                    label.setText(SO.getBlueprintName());
                }
            }
            return label;
        }
    }

    public static class SortableObject implements Comparable<SortableObject>{
        private final MarketGroup marketGroup;
        private final Blueprint blueprint;
        private final String blueprintName;

        public MarketGroup getGroup() {
            return marketGroup;
        }

        public Blueprint getBlueprint() {
            return blueprint;
        }

        public String getBlueprintName() {
            return blueprintName;
        }

        public SortableObject(Blueprint blueprint) {
            this.marketGroup = null;
            this.blueprint = blueprint;
            this.blueprintName=SDEDatabase.TYPE_IDS.getTypeName(blueprint.getBluePrintID());
        }
        
        public SortableObject(MarketGroup marketGroup) {
            this.marketGroup = marketGroup;
            this.blueprint = null;
            this.blueprintName=null;
        }
        

        @Override
        public int compareTo(SortableObject o){
            if(blueprint==null)return this.marketGroup.getName().compareTo(o.marketGroup.getName());
            return this.getBlueprintName().compareTo(o.getBlueprintName());
        }

        @Override
        public String toString() {
            if(blueprint==null)return marketGroup.getName();
            return blueprintName;
        }

        public ImageIcon getIcon() throws IOException {
            String path;
            if(blueprint==null){
                path=SDEDatabase.ICON_IDS.getIconPath(marketGroup.getIconID());
            }else{
                path="Types/"+blueprint.getBluePrintID()+"_64.png";
            }
            ImageIcon imageIcon=FileHandler.getIconFile(path);
            Image image=imageIcon.getImage();
            Image resized=image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(resized);
        }
    }
    
    interface Searchable<E,V>{
        public Collection<E> search(V value);
    }
    
    public static class BlueprintSearchable implements Searchable<SortableObject,String>{
            private final ArrayList<SortableObject> blueprints;

            public BlueprintSearchable(){
                this.blueprints=BlueprintTree.ALL_ADDED_BLUEPRINTS;
                Collections.sort(blueprints);
            }

            @Override
            public Collection<SortableObject> search(String value) {
                ArrayList<SortableObject> found=new ArrayList<>();
                blueprints.stream().filter((blueprint) -> (blueprint.getBlueprintName().toLowerCase().contains(value.toLowerCase()))).forEach((blueprint) -> {
                    found.add(blueprint);
                });
                return found;
            }
        }
}
