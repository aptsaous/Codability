package gr.inf.codability.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

public class CodabilityRegistration implements ApplicationComponent
{
    private MulticastServerThread multicastServerThread;
    static String activationCode;


    public CodabilityRegistration()
    {
    }

    @Override
    public void initComponent()
    {
        multicastServerThread = new MulticastServerThread();

        ApplicationManager.getApplication().executeOnPooledThread( multicastServerThread );

        Notifications.showActivationCode();
    }

    @Override
    public void disposeComponent()
    {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName()
    {
        return "Codability";
    }
}
