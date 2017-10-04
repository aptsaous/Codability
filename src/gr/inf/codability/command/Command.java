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
        else if ( command.startsWith( "open class" ) )
        {
            name = OPEN_CLASS;
            type = OP;

            setParams( "name" );
        }
        else if ( command.startsWith( "create class" ) )
        {
            name = CREATE_CLASS;
            type = OP;

            setParams( "name" );
        }
        else if ( command.startsWith( "open project" ) )
        {
            name = OPEN_PROJECT;
            type = OP;

            setParams( "name" );
        }
        else if ( command.startsWith( "create project" ) )
        {
            name = CREATE_PROJECT;
            type = OP;

            setParams( "name" );
        }
        else if ( command.equals( "execute" ) )
        {
            name = EXECUTE;
            type = OP;
        }
        else if ( command.startsWith( "insert instance variable" ) )
        {
            name = INSERT_INST_VAR;
            type = INS;

            String[] keys = { "class", "name", "type" };
            String[] prefix = { "with", "of" };

            setParamsInstanceVar( keys, prefix );
        }
        else if ( command.equals( "insert main function" ) )
        {
            name = INSERT_MAIN;
            type = INS;
        }
        else if ( command.equals( "insert default constructor" ) )
        {
            name = INSERT_DFLT_CONSTR;
            type = INS;
        }
        else if ( command.equals( "insert expression" ) )
        {
            name = INSERT_EXPR;
            type = INS;
        }
        else if ( command.startsWith( "insert get function" ) )
        {
            name = INSERT_GETTER;
            type = INS;

            setParams( "variable" );
        }
        else if ( command.startsWith( "insert set function" ) )
        {
            name = INSERT_SETTER;
            type = INS;

            setParams( "variable" );
        }
        else if ( command.startsWith( "print" ) )
        {
            name = PRINT;
            type = INS;
        }

    }

    void execute()
    {
        if ( name == GO_TO )
            goTo( params );
        else if ( name == INS_MODE )
            changeMode( INS );
        else if ( name == OP_MODE )
            changeMode( OP );
        else if ( name == OPEN_CLASS )
            openClassName( params );
        else if ( name == CREATE_CLASS )
            createClassName( params );
        else if ( name == CREATE_PROJECT )
            createProjectByName( params );
        else if ( name == OPEN_PROJECT )
            openProjectByName( params );
        else if ( name == EXECUTE )
            runApp();
        else if ( name == INSERT_INST_VAR )
            addInstanceVar( params );
        else if ( name == INSERT_MAIN )
            createMain();
        else if ( name == INSERT_DFLT_CONSTR )
            createDefaultConstructor();
        else if ( name == INSERT_EXPR )
            createExpression();
        else if ( name == INSERT_GETTER )
            createGetter( params );
        else if ( name == INSERT_SETTER )
            createSetter( params );
        else if ( name == PRINT )
            print( command );
    }

    private void setParamsInstanceVar( String[] keys, String[] prefix )
    {
        for ( String key : keys )
        {
            for ( int i = 0; i < parameters.length; i++ )
            {
                if ( parameters[i].equals( key ) )
                {
                    StringBuilder paramList = new StringBuilder();

                    for ( int j = i + 1; j < parameters.length; j++ )
                    {
                        if ( ( parameters[j].equals( prefix[0] ) && parameters[j+1].equals( keys[1] ) )  || ( parameters[j].equals( prefix[1] ) && parameters[j+1].equals( keys[2] ) ) )
                            break;

                        paramList.append( parameters[ j ] + ' ' );
                    }

                    params.put( key, paramList.toString().trim() );

                    break;
                }
            }
        }
    }

    private void setParams( String key )
    {

        for ( int i = 0; i < parameters.length; i++ )
        {
            if ( parameters[i].equals( key ) )
            {
                StringBuilder paramList = new StringBuilder();

                for ( int j = i + 1; j < parameters.length; j++ )
                    paramList.append( parameters[j] + ' ' );

                params.put( key, paramList.toString().trim() );

                break;
            }
        }
    }

    public String getCommand()
    {
        return command;
    }
}
