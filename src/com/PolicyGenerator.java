package com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
public class PolicyGenerator {
    PrintWriter writer;
    HashMap<String,ArrayList<String>> policyMap;
    public PolicyGenerator(HashMap<String,ArrayList<String>> policy){
        policyMap=policy;
    }
    public void generatePolicy(){
        try{
            writer=new PrintWriter(new PrintWriter(new FileOutputStream("../policy.txt"), true));
            writer.println("Privacy Policy for the app\n");
            writer.print("Privacy and online safety are important to this app. We offer this android application and");
            writer.print(" services and we may collect and store information for serving the users. This privacy policy ");
            writer.println("describes what and why we collect certain data, who do we share the data with and why.");
            writer.println("");
            writer.println("1. Types of Data collected");
            List<String> tmp=policyMap.get("access user's location");
            if(tmp!=null) {
                writer.println(" Location:");
                for (String str : tmp) {
                    writer.println("  * " + str);
                }
            }
            tmp=policyMap.get("access user's calendar");
            if(tmp!=null) {
                writer.println(" Calendar:");
                for (String str : tmp) {
                    writer.println("  * " + str);
                }
            }
            tmp=policyMap.get("access user's contact");
            if(tmp!=null) {
                writer.println(" Contact:");
                for (String str : tmp) {
                    writer.println("  * " + str);
                }
            }
            writer.println("");
            writer.println("2. Third Parties");
            writer.println("Third parties may receive information about you as follows:");
            tmp=policyMap.get("share location with third party");
            if(tmp!=null) {
                writer.println(" Location:");
                for (String str : tmp) {
                    writer.println("  * " + str);
                }
            }
            tmp=policyMap.get("share calendar with third party");
            if(tmp!=null){
                writer.println(" Calendar:");
                for(String str:tmp){
                    writer.println("  * "+str);
                }
            }
            writer.println("");
            writer.println("3. Contact");
            writer.print("If you have question about the privacy policy or if you believe the app has not adhere to");
            writer.println(" this privacy policy, you may contact the app by writing to the e-mail provided on the Google Play Store.");

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(writer!=null) writer.close();
        }
    }
}
