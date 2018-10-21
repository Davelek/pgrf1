package Message;

import drawable.DrawableType;

import javax.swing.*;

public class Message {



    //   private String upozorneni;
   // private Object[] possibilities;
  //  private String title;
  //  private String starting;

    public Message() {
    }

    public void upozorneni(String upozorneni){
                JOptionPane frame = new JOptionPane();
                JOptionPane.showMessageDialog(frame,
                        upozorneni);
    }

   /* public void setPossibilities(Object[] possibilities) {
        this.possibilities = possibilities;
    }*/

    public String vyber(String upozorneni, String title, Object[] moznosti, String prvni){

        JOptionPane frame = new JOptionPane();
        String s = (String) JOptionPane.showInputDialog(frame,upozorneni,title,JOptionPane.PLAIN_MESSAGE,null,moznosti,prvni);
        return s;
    }


}
