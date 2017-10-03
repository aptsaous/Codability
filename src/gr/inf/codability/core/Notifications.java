package gr.inf.codability.core;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;

import javax.swing.*;

import static gr.inf.codability.core.ActivationCodeGenerator.getActivationCode;

public class Notifications
{
    private static String groupDisplayId = "Codability";
    private static int timerDelay = 3000;
    public static Notification syncNotification;

    public static void showInfo( String title, String content )
    {
        final Notification notification = new Notification( groupDisplayId, title, content, NotificationType.INFORMATION );

        notification.notify(null);

        final Timer timer = new Timer( timerDelay, e -> notification.expire() );

        timer.setRepeats( false );
        timer.start();
    }

    public static void showWarning( String title, String content )
    {
        final Notification notification = new Notification( groupDisplayId, title, content, NotificationType.WARNING );

        notification.notify(null);

        final Timer timer = new Timer( timerDelay, e -> notification.expire() );

        timer.setRepeats( false );
        timer.start();
    }

    static void showActivationCode()
    {

        String title = "Activation Code";

        syncNotification = new Notification( groupDisplayId, title, getActivationCode(), NotificationType.INFORMATION );

        syncNotification.notify(null);
    }

}
