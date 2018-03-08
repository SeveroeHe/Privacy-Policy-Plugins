package com;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;
import java.util.*;
import org.jetbrains.annotations.NotNull;

public class PaserClass extends AnAction {
    public static int count=0;
    public static Map<String,String> keyWords=new HashMap<String,String>();
    public static Map<String,String> policy_text=new HashMap<String,String>();
    public PaserClass() {
        keyWords.put("LocationManager","access user's location");
        keyWords.put("ACCESS_FINE_LOCATION","access user's location");
        keyWords.put("ContactsContract","access user's contact");
        //keyWords.put("Contacts","access user's contact");
        keyWords.put("CalendarContract","access user's calendar");

        keyWords.put("PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION","access user's location");
        keyWords.put("GoogleMap","share location with third party");
        keyWords.put("FaceBook","share location with third party");
        keyWords.put("com.google.android.gms.maps.GoogleMap;","share location with third party");
        keyWords.put("java.com.google.android.gms.maps.*;","share location with third party");
        keyWords.put("com.google.android.gms.maps.*;","share location with third party");


    }

//    @Override
//
//    public void update(AnActionEvent e) {
//        super.update(e);
//
//
//        showDialog();
//
//    }
    private void showDialog() {
        //InputReason dialog = new InputReason();
        policyDialog dialog=new policyDialog(policy_text);
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
         //TODO: insert action logic here
        Processor<PsiClass> processor=new Processor<PsiClass>() {
            @Override
            public boolean process(PsiClass psiClass) {

                check(psiClass.getName(),psiClass.getText());

                return true;
            }
        };

        AllClassesGetter.processJavaClasses(
                new PlainPrefixMatcher(""),
                e.getProject(),
                GlobalSearchScope.projectScope(e.getProject()),
                processor
        );
        showDialog();

    }
    public void check(String className,String statement){
        String[] strs=statement.split("\\s+",-1);
        String subpart;
        for(String str:strs){
            int left=0,right=0;
            char[] arr=str.toCharArray();
            while(right<=arr.length){
                if(right==arr.length||arr[right]==';'||arr[right]=='('||arr[right]=='.'
                        ||arr[right]=='{'||arr[right]=='['||arr[right]==','||
                        arr[right]==')'||arr[right]=='}'||arr[right]==']'){
                    subpart=str.substring(left,right);
                    if(keyWords.containsKey(subpart)){
                        policy_text.put(keyWords.get(subpart),"We detected you are trying to "+keyWords.get(subpart)+" in class "
                                +className+". Please" +
                                " provide a short reason below");
                    }
                    left=right+1;

                }
                right++;
            }

        }
    }


}
