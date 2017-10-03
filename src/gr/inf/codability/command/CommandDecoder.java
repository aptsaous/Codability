package gr.inf.codability.command;

import com.intellij.openapi.diagnostic.Logger;
import gr.inf.codability.core.Notifications;

import java.util.List;
import java.util.Map;

import static gr.inf.codability.command.CommandType.OP;

public class CommandDecoder
{
    static CommandType currentCmdType = OP; /* Modes of Codability: Insert or Op Mode */

    protected static final Logger LOG = Logger.getInstance( CommandDecoder.class );

    public void decode( Map<String, List<String>> parameters )
    {
        String command;

        if ( parameters.isEmpty() )
        {
            Notifications.showWarning( "Warning", "No command sent" );
            return;
        }

        command = parameters.get( "cmd" ).get( 0 );
        Command cmd = new Command( command );
        cmd.analyze();

        if ( !cmd.isAllowedWithinCurrentContext( currentCmdType ) )
            return;

        Notifications.showInfo( "Command", cmd.getCommand() );

        cmd.execute();
    }


}
