import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class main extends Thread{

    private static boolean started = false;
    private static JTextField hour;
    private static JTextField minutes;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Timed Shutdown");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setResizable(false);
        frame.setLayout(null);

        JButton button = new JButton("Start");
        button.setBounds(10, 90, 90,30);

        JLabel hourLabel = new JLabel("Hour");
        hourLabel.setBounds(10, 10, 80,30);

        JLabel minutesLabel = new JLabel("Minute");
        minutesLabel.setBounds(10, 50, 80,30);

        hour = new JTextField();
        hour.setBounds(75, 10, 80,30);

        minutes = new JTextField();
        minutes.setBounds(75, 50, 80,30);

        frame.setIconImage(ImageIO.read(new File("pic.png")));

        frame.add(hourLabel);
        frame.add(minutesLabel);
        frame.add(hour);
        frame.add(minutes);
        frame.add(button);

        button.addActionListener(e -> {
            if(started){
                started = false;
                button.setText("Start");
                hour.setEditable(true);
                minutes.setEditable(true);
            }else{
                try{
                    int d = Integer.parseInt(hour.getText());
                    int f = Integer.parseInt(minutes.getText());
                    if(d>0 && d<25 && f>-1 && f<60){
                        started = true;
                        button.setText("Stop");
                        hour.setEditable(false);
                        minutes.setEditable(false);
                        main thread = new main();
                        thread.start();
                    }

                } catch (NumberFormatException nfe) {

                }
            }
        });

        frame.setVisible(true);
    }
    public void run() {
        while(started){
            try {
                Thread.sleep(10);
                Calendar c1 = Calendar.getInstance();
                Date dateOne = c1.getTime();
                if(dateOne.getHours() == Integer.parseInt(hour.getText()) && dateOne.getMinutes() == Integer.parseInt(minutes.getText())){
                    Runtime runtime = Runtime.getRuntime();
                    Process proc = runtime.exec("shutdown -s -t 0");
                    System.exit(0);
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
