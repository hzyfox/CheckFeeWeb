import java.util.TimerTask;

/**
 * create with PACKAGE_NAME
 * USER: husterfox
 */
public class CheckTask implements Runnable {
    @Override
    public void run() {
        CheckElecPeriodically.check();
    }
}
