package gr.inf.codability.command;

import gr.inf.codability.core.Notifications;

import java.util.HashMap;

import static gr.inf.codability.command.CommandType.*;
import static gr.inf.codability.command.CommandName.*;
import static gr.inf.codability.command.CommandImpl.*;

public class Command
{
    private String command;
    private CommandType type; /* INSERT, OPERATIONAL, HYBRID, UNKNOWN */
    private HashMap<String, String> params;
    private CommandName name;
    private String[] parameters;

    public Command( String command )
    {
        this.command = command.toLowerCase();
        this.params = new HashMap<>();
        this.parameters = this.command.split( "\\s" );
    }

    private CommandType getCommandType()
    {
        if ( command.startsWith( "create" ) || command.startsWith( "delete" ) || command.startsWith( "remove" ) )
            return INS;
        else if ( command.startsWith( "compile" ) || command.startsWith( "run" ) )
            return OP;
        else
            return UNKNOWN;
    }

    boolean isAllowedWithinCurrentContext( CommandType currentCmdType )
    {
        if ( type == HYBRID )
            return true;
        else if ( type == UNKNOWN )
        {
            Notifications.showWarning( "Warning", "Unknown command" );
            return false;
        }
        else if ( currentCmdType != type )
        {
            if ( currentCmdType == OP )
                Notifications.showWarning( "Warning", "Current mode is set to OP.\nYour command isn't allowed within this context" );
            else
                Notifications.showWarning( "Warning", "Current mode is set to INS.\nYour command isn't allowed within this context" );

            return false;
        }
        else
            return true;
    }

    void analyze()
    {
        setCommandParams();
    }

    private void setCommandParams()
    {
        if ( command.equals( "insert mode" ) )
        {
            name = INS_MODE;
            type = HYBRID;
        }
        else if ( command.equals( "op mode" ) )
        {
            name = OP_MODE;
            type = HYBRID;
        }
        else if ( command.matches( "(go\\s*to).*" ) )
        {
            name = GO_TO;
            type = HYBRID;

            setParams( "line" );
            setParams( "column" );
        }
        else if ( command.startsWith( "search for" ) )
        {
            name = SEARCH_FOR;
            type = HYBRID;

            setParams( "file" );
            setParams( "class" );
            setParams( "variable" );
            setParams( "function" );
        }
        else if ( command.startsWith( "navigate to" ) )
        {
            name = NAV_TO;
            type = HYBRID;

            setParams( "file" );
            setParams( "class" );
            setParams( "variable" );
            setParams( "function" );
        }

    }

    void execute()
    {
        if ( name == GO_TO )
            go_to( params );
        else if ( ( name == INS_MODE ) || ( name == OP_MODE ) )
            change_mode( type );
    }

    private void setParams( String key )
    {

        for ( int i = 0; i < parameters.length; i++ )
        {
            if ( parameters[i].equals( key ) )
            {
                if ( i + 1 <= parameters.length )
                    params.put( key, parameters[ i + 1 ] );

                break;
            }
        }
    }

    public CommandType getType()
    {
        return type;
    }

    public void setType( CommandType type )
    {
        this.type = type;
    }

    public String getCommand()
    {
        return command;
    }
}
