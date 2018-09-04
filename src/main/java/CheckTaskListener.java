import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create with PACKAGE_NAME
 * USER: husterfox
 */
public class CheckTaskListener implements ServletContextListener {
    private static Logger log = LoggerFactory.getLogger(CheckTaskListener.class);
    private static ScheduledExecutorService scheduledExecutorService = null;

    private static long getTimeMillis(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
        try {
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public void
    contextInitialized(ServletContextEvent sce) {
        log.debug("listener class has been started...");
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        long oneDay = 24 * 60 * 60 * 1000;

        long initDelay = getTimeMillis("15:35:00") - System.currentTimeMillis();

        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        scheduledExecutorService.scheduleAtFixedRate(new CheckTask(), initDelay, oneDay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduledExecutorService.shutdown();
    }
}
