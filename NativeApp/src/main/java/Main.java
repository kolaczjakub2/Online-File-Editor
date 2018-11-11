import frames.MainFrame;
import jobs.RequestJobs;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        initTask();
    }

    private static void initTask() {
        Timer time = new Timer();
        RequestJobs st = new RequestJobs();
        time.schedule(st, 0, 6000);
    }
}
