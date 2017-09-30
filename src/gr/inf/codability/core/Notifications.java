package gr.inf.codability.core;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;

import javax.swing.*;
import java.util.Random;

import static gr.inf.codability.core.CodabilityRegistration.activationCode;

public class Notifications
{
    static String groupDisplayId = "Codability";
    static int timerDelay = 3000;
    static Notification syncNotification;

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

    public static void showActivationCode()
    {

        String title = "Activation Code";
        Random random = new Random();
        activationCode = String.format( "%04d", random.nextInt(10000) );

        syncNotification = new Notification( groupDisplayId, title, activationCode, NotificationType.INFORMATION );

        syncNotification.notify(null);
    }

}
