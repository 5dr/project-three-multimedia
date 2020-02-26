/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_3_multimedia;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author asd
 */
public class FXMLDocumentController implements Initializable {
    
  @FXML
    private Pane main, Quantization,Transfom,LZW,Huffman,Shanonn,Arithmetic,Run_lenth;

    @FXML
    private JFXTextField q_sample, q_bit, q_mini,q_max, q_level, q_step, q_out
            ,q_bitstream,q_error, x, y, tx, ty, sx, sy, angle, trans,scale, rotate
            ,text, range, text_sh, bit_befor, bit_after, rat_befor, rat_after,out,run_out
          ,  run_text,lzw_text;

    @FXML
    private Label out_l, total_bit,com_rat;

    @FXML
    private JFXTextArea com_befor, com_after;
       
       
    //////////////////الزراير//////////////
  @FXML private void Quantization(ActionEvent event) {
      
        main.setVisible(false);
        Quantization.setVisible(true);
        Transfom.setVisible(false);
        LZW.setVisible(false);
        Shanonn.setVisible(false);
        Arithmetic.setVisible(false);
        Run_lenth.setVisible(false);   
    }   
  @FXML private void Transfom(ActionEvent event) {
      
        main.setVisible(false);
        Quantization.setVisible(false);
        Transfom.setVisible(true);
        LZW.setVisible(false);
        Shanonn.setVisible(false);
        Arithmetic.setVisible(false);
        Run_lenth.setVisible(false);   
    }
  @FXML private void LZW(ActionEvent event) {
      
        main.setVisible(false);
        Quantization.setVisible(false);
        Transfom.setVisible(false);
        LZW.setVisible(true);
        Shanonn.setVisible(false);
        Arithmetic.setVisible(false);
        Run_lenth.setVisible(false);   
    }    
  @FXML private void Shanonn(ActionEvent event) {
      
        main.setVisible(false);
        Quantization.setVisible(false);
        Transfom.setVisible(false);
        LZW.setVisible(false);
        Shanonn.setVisible(true);
        Arithmetic.setVisible(false);
        Run_lenth.setVisible(false);   
    }
  @FXML private void Arithmetic(ActionEvent event) {
      
        main.setVisible(false);
        Quantization.setVisible(false);
        Transfom.setVisible(false);
        LZW.setVisible(false);
        Shanonn.setVisible(false);
        Arithmetic.setVisible(true);
        Run_lenth.setVisible(false);   
    }
  @FXML private void  Run_lenth(ActionEvent event) {
      
        main.setVisible(false);
        Quantization.setVisible(false);
        Transfom.setVisible(false);
        LZW.setVisible(false);
        Shanonn.setVisible(false);
        Arithmetic.setVisible(false);
        Run_lenth.setVisible(true);   
    }
  @FXML private void back(ActionEvent event) {
      
        main.setVisible(true);
        Quantization.setVisible(false);
        Transfom.setVisible(false);
        LZW.setVisible(false);
        Shanonn.setVisible(false);
        Arithmetic.setVisible(false);
        Run_lenth.setVisible(false);   
    }
  //////////////////نهاية الزراير //////////////
  
  
  ////////////الكونتيزشن //////////////
  
  @FXML private void Q(ActionEvent event) {
      String[] s = q_sample.getText().split(";");
      double[] d = Arrays.stream(s)
                        .mapToDouble(Double::parseDouble)
                        .toArray();
      int n=Integer.parseInt(q_bit.getText()); 
      nBitQuantization(n, d);
      getBitstream();
      q_max.setVisible(true);q_mini.setVisible(true);q_step.setVisible(true);
      q_bitstream.setVisible(true);q_error.setVisible(true);q_level.setVisible(true);
      q_out.setVisible(true);
           
    }
  private int bit;   private double[] samples;
  void  nBitQuantization(int bit, double[] samples) {
        this.bit = bit;
        this.samples = samples;
    }
    public void getBitstream() {
        int level = (int) Math.pow(2, bit);
        double min = samples[0];
        double max = samples[0];
        for (double val : samples) {
            if (val < min) {
                min = val;
            }
            if (val > max) {
                max = val;
            }
        }
        System.out.println(min + " " + max + " " + level);
        q_max.setText(""+max);q_mini.setText(""+min);q_level.setText(""+level);
        double step = (max - min) / level;
        System.out.println(step);
        q_step.setText(""+step);
        ArrayList<Double[]> ranges = new ArrayList<>();
        for (int i = 0; i < level; i++) {
            ranges.add(new Double[]{min,min+step});
            min += step;
        }
        StringBuilder bitstream = new StringBuilder();
        int c;
        ArrayList<Double> analogue = new ArrayList<>();
        for (int i = 0; i < samples.length; i++) {
            c = 0;
            for (Double[] value:ranges){
                if ((samples[i] >= value[0] && samples[i] < value[1]) || (samples[i] == max && value[1] == max)){
                    bitstream.append(String.format("%" + bit + "s", Integer.toBinaryString(c)).replace(' ', '0'));
                    bitstream.append(' ');
                    analogue.add((value[0]+value[1]) / 2);
                    break;
                }
                c++;
            }
        }
        System.out.println(analogue);
        q_out.setText(""+analogue);
        double error = 0;
        if (samples.length == analogue.size()){
            for (int i = 0;i<samples.length;i++){
                error += Math.abs(samples[i] - analogue.get(i));
            }
        }
        System.out.println("Bitstream: "+bitstream);
        q_bitstream.setText(""+bitstream);
        System.out.println("Error: "+error);
        q_error.setText(""+error);
    }
  ///////////////////نهاية الكوانتيزيشن//////////////////////
    
///////////////////////الترانسفورم/////////////////////////
    @FXML private void T(ActionEvent event) {
     
   trans.setText(translate(Double.parseDouble(x.getText()), Double.parseDouble(y.getText())
                , Double.parseDouble(tx.getText()),Double.parseDouble(ty.getText()) ));
   scale.setText(scale(Double.parseDouble(x.getText()), Double.parseDouble(y.getText())
            , Double.parseDouble(sx.getText()),Double.parseDouble(sy.getText()) ));
   rotate.setText(rotate(Double.parseDouble(x.getText()), Double.parseDouble(y.getText())
                , Double.parseDouble(angle.getText())));
   trans.setVisible(true);scale.setVisible(true);rotate.setVisible(true);
           
    }
    public String translate(double x, double y, double tx, double ty) {
        x = x + tx;
        y = y + ty;
        return "[" + format(x) + "," + format(y) + "]";
    }
    public String scale(double x, double y, double sx, double sy) {
        x = x * sx;
        y = y * sy;
        return "[" + format(x) + "," + format(y) + "]";
    }
    public String rotate(double x, double y, double angle) {
        x = x * df(Math.cos(Math.toRadians(angle))) - y * df(Math.sin(Math.toRadians(angle)));
        y = x * df(Math.sin(Math.toRadians(angle))) + y * df(Math.cos(Math.toRadians(angle)));
        return "[" + format(x) + "," + format(y) + "]";
    }
    private double df(double value) {
        return (double) Math.round(value * 1000000) / 1000000;
    }
    private String format(double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else {
            return String.format("%s", d);
        }
    }
  //////////////////// /الترانسفورم end/////////////////////////
    
    ////////////////////arithmatic////////////
    @FXML private void A(ActionEvent event) {
     
        ArithmeticCoding(text.getText());range.setVisible(true);
        
    }
    public void ArithmeticCoding(String s) {
        java.lang.String text = s;
        int chars[] = new int[26];
        for (int i = 0; i < text.length(); ++i) {
            ++chars[text.charAt(i) - 'a'];
        }
        List<Map.Entry<String, Float>> charsMap = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            int aChar = chars[i];
            if (aChar > 0) {
                HashMap<String, Float> sub = new HashMap<>();
                char c = (char) (i + 'a');
                sub.put(c + "", (float) aChar / text.length());
                charsMap.add(sub.entrySet().iterator().next());

            }
        }

        sortHashMap(charsMap);
        HashMap<String, HashMap<Float, Float>> probabilities = getPropabilities(charsMap);
        arithmeticCode(text, probabilities);

        System.out.println();

    }
    public  void arithmeticCode(String text, HashMap<String, HashMap<Float, Float>> probabilities) {
        double high = 1.0;
        double low = 0.0;
        for (int i = 0; i < text.length(); ++i) {
            String c = text.charAt(i)+"";
            HashMap<Float, Float> p = probabilities.get(c);
//            System.out.println(p);
            Map.Entry<Float, Float> next = p.entrySet().iterator().next();
            double range = high - low;
            high = low + range * next.getValue();
            low = low + range * next.getKey();
        }
        System.out.println(low + (high - low) / 2);
        range.setText(""+(low + (high - low) / 2));
    }
    private  HashMap<String, HashMap<Float, Float>> getPropabilities(List<Map.Entry<String, Float>> charsMap) {
        float temp = 0;
        HashMap<String, HashMap<Float, Float>> result = new HashMap<>();
        for (Map.Entry<String, Float> entry : charsMap) {
            HashMap<Float, Float> pair = new HashMap<>();
            pair.put(temp, temp + entry.getValue());
            result.put(entry.getKey(), pair);
            temp += entry.getValue();
        }

        return result;


    }
    private void sortHashMap(List<Map.Entry<String, Float>> charsMap) {


        int j;
        boolean flag = true;   // set flag to true to begin first pass
        Map.Entry<String, Float> temp;   //holding variable

        while (flag) {
            flag = false;    //set flag to false awaiting a possible swap
            for (j = 0; j < charsMap.size() - 1; j++) {
                if (charsMap.get(j).getValue() < charsMap.get(j + 1).getValue())   // change to > for ascending sort
                {
                    temp = charsMap.get(j);                //swap elements
                    charsMap.add(j, charsMap.get(j + 1));
                    charsMap.remove(j + 1);
                    charsMap.add(j + 1, temp);
                    charsMap.remove(j + 2);
                    flag = true;              //shows a swap occurred
                }
            }
        }

        System.out.println();


    }
/////////////////////////end arith///////////////
    //////////////////////////huffman///////////////
    @FXML private void H(ActionEvent event) {
     
        Huffman o=new Huffman();    
    }
    //////////////////////end ///////////////////////
    
    /////////////////////////shannon////////////////////
    
    @FXML private void SH(ActionEvent event) {
     
            Shannon(text_sh.getText());
     bit_after.setVisible(true);bit_befor.setVisible(true);rat_after.setVisible(true);
     rat_befor.setVisible(true);com_after.setVisible(true);com_befor.setVisible(true);
     out.setVisible(true);out_l.setVisible(true);com_rat.setVisible(true);
     total_bit.setVisible(true);
    }
    public void Shannon(String s) {
        String text = s;
        //"AAAAAAAAAAAAAAABBBBBBBCCCCCCDDDDDDEEEEE"
        
        int chars[] = new int[100];
        HashMap<Character, Integer> charsMap = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            ++chars[text.charAt(i) - 'A'];
        }
        for (int i = 0; i < chars.length; i++) {
            int aChar = chars[i];
            if (aChar > 0)
                charsMap.put(java.lang.Character.valueOf((char) ('A' + i)), aChar);
        }


        List<Map.Entry<Character, Integer>> characterInteger = sortHashMapByValues(charsMap);

        HashMap<Character, String> result = new HashMap<>();
        shanAlgor(result, characterInteger, "");
        float befor = 0, after = 0;
        for (Map.Entry<Character, Integer> charr : characterInteger) {
            int beforCompression = charr.getValue() * 8;
            int afterCompression = charr.getValue() * result.get(charr.getKey()).length();

            befor += beforCompression;
            after += afterCompression;

            System.out.println(beforCompression + "   " + afterCompression);
            com_befor.setText(com_befor.getText()+""+beforCompression+"\n");
            com_after.setText(com_after.getText()+""+afterCompression+"\n");
        }


        System.out.println("Total # bits: "+befor + "  " + after);
        bit_befor.setText(""+befor);bit_after.setText(""+after);
        System.out.println("compression ratio : "+ (befor / text.length()) + "  " + after / text.length());
        rat_befor.setText(""+(befor / text.length()));rat_after.setText(""+(after / text.length()));
        System.out.println("the code for each char :"+result);
        out.setText(""+result);
    }
    private static void shanAlgor(HashMap<Character, String> result, List<Map.Entry<Character, Integer>> characterIntegerLinkedHashMap, String bit) {
        if (characterIntegerLinkedHashMap.size() == 1) {
            result.put(characterIntegerLinkedHashMap.get(0).getKey(), bit);
            return;
        }

        int temp = 0;
        List<Map.Entry<Character, Integer>> finalSub1 = null;
        List<Map.Entry<Character, Integer>> finalSub2 = null;

        for (int i = 1; i < characterIntegerLinkedHashMap.size(); i++) {

            List<Map.Entry<Character, Integer>> sub1 = characterIntegerLinkedHashMap.subList(0, i);
            List<Map.Entry<Character, Integer>> sub2 = characterIntegerLinkedHashMap.subList(i, characterIntegerLinkedHashMap.size());
            int difference = Math.abs(subMapValue(sub1) - subMapValue(sub2));

            if (difference < temp || i == 1) {
                temp = difference;
                finalSub1 = sub1;
                finalSub2 = sub2;
            }


        }


        shanAlgor(result, finalSub1, bit + "0");
        shanAlgor(result, finalSub2, bit + "1");

    }
    public static int subMapValue(List<Map.Entry<Character, Integer>> sub) {
        int result = 0;
        for (Map.Entry<Character, Integer> row : sub) {

            result += row.getValue();
        }
        return result;
    }
    public static List<Map.Entry<Character, Integer>> sortHashMapByValues(
            HashMap<Character, Integer> passedMap) {
        List<Character> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Character, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<Character> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Character key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }

        return entriesSortedByValues(sortedMap);
    }
    static List<Map.Entry<Character, Integer>> entriesSortedByValues(LinkedHashMap<java.lang.Character, java.lang.Integer> map) {

        List<Map.Entry<Character, Integer>> sortedEntries = new ArrayList<Map.Entry<Character, Integer>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Map.Entry<Character, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );

        return sortedEntries;
    }

     /////////////////////////shannon////////////////////
    
     /////////////////////////run lenth////////////////////
    
    @FXML private void Run(ActionEvent event) {
     
     run_out.setText(compress(run_text.getText())) ;
      
     
    }
    
     public String compress(String text) {
//        StringBuilder compressionText = new StringBuilder();
//        for (int i = 0; i < text.length(); i++) {
//            int count = 1;
//            while (i < text.length() - 1 &&
//                    text.charAt(i) == text.charAt(i + 1)) {
//                count++;
//                i++;
//            }
//            compressionText.append(text.charAt(i));
//            compressionText.append(count);
//        }
//        return compressionText.toString();


if(text.charAt(0)=='0'){
    StringBuilder compressionText = new StringBuilder();
    int count = 1;
     for (int i = 0; i < text.length(); i++) {
            while (i < text.length() - 1 &&
                    text.charAt(i) == text.charAt(i + 1)) {
                count++;
                i++;
            }
            compressionText.append(text.charAt(i)+"=");
            compressionText.append(count+";");
     count=1;   
     }
     return compressionText.toString();

}else if(text.charAt(0)=='1'){

StringBuilder compressionText = new StringBuilder();
 compressionText.append("0=");
            compressionText.append("0;");
    int count = 1;
     for (int i = 0; i < text.length(); i++) {
            while (i < text.length() - 1 &&
                    text.charAt(i) == text.charAt(i + 1)) {
                count++;
                i++;
            }
             compressionText.append(text.charAt(i)+"=");
            compressionText.append(count+";");
            count=1;
        }
     return compressionText.toString();


}

else {

return "not binary";


}


}
    
    
    
    


    
     /////////////////////////run lenth////////////////////
     
     
      /////////////////////////lzw////////////////////
     
     @FXML private void L(ActionEvent event) {
     
      LZW(lzw_text.getText()) ;
      
     
    }
     public void LZW(String s){
        String text = s;
        //AABAABAAA
        
        HashMap<String, Integer> table = new HashMap<>();
        for (char c = 'A'; c <= 'z'; ++c) {
            table.put(c + "", (int) c);
        }
        

        int index = 256;
        String p = text.charAt(0) + "";
        for (int i = 1; i < text.length(); ++i) {
            String c = text.charAt(i) + "";
            String pc = p + c;
            if (table.get(pc) != null) {
                p = pc;
            } else {
                System.out.println(table.get(p));
                table.put(pc, index++);
                p=c;
            }
        }
        System.out.println(table.get(p));
       

        
    }
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
