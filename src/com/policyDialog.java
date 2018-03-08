package com;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.*;

public class policyDialog extends JDialog {
    private JPanel contentPane=new JPanel();
    private JButton buttonOK;
    private  JButton buttonCancel;
    private ArrayList<JButton> buttonList;
    private ArrayList<JTextField> textList;
    private HashMap<String,ArrayList<String>> messageMap=new HashMap<>();
    private HashMap<Integer,String> groupMap=new HashMap<>();
    public policyDialog(Map<String,String> policyText) {
        setContentPane(contentPane);
        setPreferredSize(new Dimension(800,300));
        setModal(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        //getRootPane().setDefaultButton(buttonOK);
        textList=new ArrayList<JTextField>();
        buttonList=new ArrayList<JButton>();
        int index=0;
        for(String reason:policyText.keySet()) {
            JTextField text = new JTextField();
            JButton button=new JButton("Submit this reason");
            text.setPreferredSize(new Dimension(400, 50));
            getContentPane().add(new JLabel(policyText.get(reason)));
            textList.add(text);
            buttonList.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onOK(buttonList.indexOf(button));
                }
            });
            groupMap.put(index,reason);
            getContentPane().add(text);
            getContentPane().add(button);
            index++;
        }
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(buttonOK);
        getContentPane().add(buttonPanel);
        //getContentPane().add(buttonOK);
        //getContentPane().add(buttonCancel);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(int index){
        JTextField text=textList.get(index);
        String mytext=text.getText();
        if(mytext!=null&&mytext.length()!=0){
            String reason=groupMap.get(index);
            if(!messageMap.containsKey(reason)) messageMap.put(reason,new ArrayList<>());
            messageMap.get(reason).add(mytext);

        }
        text.setText("");
    }

    private void onOK() {
        // add your code here
        for(int i=0;i<textList.size();i++) onOK(i);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        int a=JOptionPane.showConfirmDialog(this,"All of your submission will be put to the text. Click OK to generate");
        if(a!=JOptionPane.YES_OPTION) return ;

        PolicyGenerator generator=new PolicyGenerator(messageMap);
        generator.generatePolicy();
        dispose();




    }

    private void onCancel() {
         //add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        policyDialog dialog = new policyDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
